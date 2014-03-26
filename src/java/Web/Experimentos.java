package Web;

import Utils.XMLTransform;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Experimentos", urlPatterns = {"/Experimentos"})
public class Experimentos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            Hashtable hash = new Hashtable();
            
            for (Map.Entry en : request.getParameterMap().entrySet()) {
                hash.put(en.getKey(), en.getValue());
            }
            
            if(!hash.containsKey("cmd")){
                hash.put("cmd", "Experimentos/lst");
            }
            
            String page = hash.get("cmd").toString().split("/")[0];
            String cmd = hash.get("cmd").toString().split("/")[1].toUpperCase();
            
            if(cmd.equals("INS") || cmd.equals("UPD") || cmd.equals("DEL")){
                salvarExperimento(cmd, hash);
            }
            
            String xml = "<root>";
            
            xml += listarExperimento(cmd, hash);
            
            xml += "</root>";
            
            String html;
            XMLTransform transform = new XMLTransform();
            html = transform.toHtml("D:\\GELB\\web\\xsl\\" + page + ".xsl", xml);

            out.println(html);
        } catch (Exception e) {
            out.println(e.toString());
        } finally {            
            out.close();
        }
    }
    
    private void salvarExperimento(String cmd, Hashtable hash){ 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String id_experimento = hash.get("id_experimento").toString();
            String nm_experimento = hash.get("nm_experimento").toString();
            String dt_experimento = hash.get("dt_experimento").toString();
            String tp_experimento = hash.get("tp_experimento").toString();
            String de_experimento = hash.get("de_experimento").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_experimento)+1 AS NewCodigo FROM tExperimento;");
                
                int novoCodigo = 1;
                
                if(result.next()){
                    novoCodigo = result.getInt("NewCodigo");
                }
                
                String SQL = "INSERT INTO";
                SQL += " tExperimento(id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento) ";
                SQL += " VALUES(" + novoCodigo + ", '" + nm_experimento + "', '" + dt_experimento + "', '" + tp_experimento + "', '" + de_experimento + "');";
                
                con.createStatement().execute(SQL);    
            }
            if(cmd.equals("UPD")){
                String SQL = " UPDATE tExperimento SET ";
                SQL += " nm_experimento='" + nm_experimento + "', ";
                SQL += " dt_experimento='" + dt_experimento + "', ";
                SQL += " tp_experimento='" + tp_experimento + "', ";
                SQL += " de_experimento='" + de_experimento + "' ";
                SQL += " WHERE id_experimento=" + id_experimento + "; ";
                
                con.createStatement().execute(SQL);    
            }
            if(cmd.equals("DEL")){
                String SQL = " DELETE FROM tExperimento ";
                SQL += " WHERE id_experimento=" + id_experimento + "; ";
                
                con.createStatement().execute(SQL);    
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
        }
    }
    
    private String listarExperimento(String cmd, Hashtable hash){ 
        String xml = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
//            String id_experimento = hash.get("id_experimento").toString();
//            String nm_experimento = hash.get("nm_experimento").toString();
//            String dt_experimento = hash.get("dt_experimento").toString();
//            String tp_experimento = hash.get("tp_experimento").toString();
//            String de_experimento = hash.get("de_experimento").toString();
                    
            if(cmd.equals("LST")){
                String SQL = " SELECT  ";
                SQL += " id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento ";
                SQL += " FROM tExperimento ";        
                
                ResultSet result = con.createStatement().executeQuery(SQL);
                
                if(!result.wasNull()){
                    while(result.next()){
                        xml += " <experimento ";
                        xml += " id_experimento = '" + result.getInt("id_experimento") + "' ";
                        xml += " nm_experimento = '" + result.getString("nm_experimento") + "' ";
                        xml += " dt_experimento = '" + result.getString("dt_experimento") + "' ";
                        xml += " tp_experimento = '" + result.getString("tp_experimento") + "' ";
                        xml += " de_experimento = '" + result.getString("de_experimento") + "' ";
                        xml += " > </experimento> ";
                    }
                }
            }
//            if(cmd.equals("UPD")){
//                String SQL = " UPDATE tExperimento SET ";
//                SQL = SQL + " nm_experimento='" + nm_experimento + "', ";
//                SQL = SQL + " dt_experimento='" + dt_experimento + "', ";
//                SQL = SQL + " tp_experimento='" + tp_experimento + "', ";
//                SQL = SQL + " de_experimento='" + de_experimento + "' ";
//                SQL = SQL + " WHERE id_experimento=" + id_experimento + "; ";
//                
//                con.createStatement().execute(SQL);    
//            }
//            if(cmd.equals("DEL")){
//                String SQL = " DELETE FROM tExperimento ";
//                SQL = SQL + " WHERE id_experimento=" + id_experimento + "; ";
//                
//                con.createStatement().execute(SQL);    
//            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
        } finally {
            return xml;
        }
    }
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
