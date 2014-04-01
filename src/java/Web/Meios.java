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

@WebServlet(name = "Meios", urlPatterns = {"/Meios"})
public class Meios extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Hashtable hash = new Hashtable();
        String xml = "";
        String page = "";
        String cmd = "";

        try {
            for (Map.Entry en : request.getParameterMap().entrySet()) {
                hash.put(en.getKey(), request.getParameter(en.getKey().toString()));
            }
 
            if(!hash.containsKey("cmd")){
                hash.put("cmd", "Meios/lst");
                cmd = "LST";
            }else {
                if(hash.get("id") == "" && hash.get("cmd") != "lst"){
                    cmd = "INS";
                }else{
                    cmd = hash.get("cmd").toString().split("/")[1].toUpperCase();
                }
            }
            
            page = hash.get("cmd").toString().split("/")[0];           
            
            if(cmd.equals("INS") || cmd.equals("UPD") || cmd.equals("DEL")){
                xml += salvarMeio(cmd, hash);
            }
            
            xml += listarMeios(cmd, hash);
            
        } catch (Exception e) {
            xml += "<erro message='" + e.toString() + "' />";
        } 
        
        try{
            xml = "<root>" + xml + "</root>";
            
            String html;
            XMLTransform transform = new XMLTransform();
            html = transform.toHtml("D:\\GELB\\web\\xsl\\" + page + ".xsl", xml);
            
            out.println(html);
        } catch (Exception e) {
            xml += "<erro message='" + e.toString() + "' />";
        } finally {
            out.close();
        }
    }

    private String salvarMeio(String cmd, Hashtable hash){ 
        String xml = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String id_meio = hash.get("id_meio").toString();
            String nm_meio = hash.get("nm_meio").toString();
            String dt_meio = hash.get("dt_meio").toString();
            String de_meio = hash.get("de_meio").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_meio)+1 AS NewCodigo FROM tMeio;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                String SQL = "INSERT INTO";
                SQL += " tMeio(id_meio, nm_meio, dt_meio, de_meio) ";
                SQL += " VALUES(" + novoCodigo + ", '" + nm_meio + "', '" + dt_meio + "', '" + de_meio + "');";
                
                con.createStatement().execute(SQL);    
            }
            if(cmd.equals("UPD")){
                String SQL = " UPDATE tMeio SET ";
                SQL += " nm_meio='" + nm_meio + "', ";
                SQL += " dt_meio='" + dt_meio + "' ";
                SQL += " de_meio='" + de_meio + "' ";
                SQL += " WHERE id_meio=" + id_meio + "; ";
                
                con.createStatement().execute(SQL);    
            }
            if(cmd.equals("DEL")){
                String SQL = " DELETE FROM tMeio ";
                SQL += " WHERE id_meio=" + id_meio + "; ";
                
                con.createStatement().execute(SQL);    
            }
        } catch (SQLException e) {
            xml += "<erro message='SQL Exception: "+ e.toString() + "' />";
        } catch (ClassNotFoundException cE) {
            xml += "<erro message='Class Not Found Exception: "+ cE.toString() + "' />";
        } finally {
            return xml;
        }
    }
    
    private String listarMeios(String cmd, Hashtable hash){ 
        String xml = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String SQL = " SELECT  ";
            SQL += " id_meio, nm_meio, dt_meio, de_meio ";
            SQL += " FROM tMeio ORDER BY id_meio ASC";        

            ResultSet result = con.createStatement().executeQuery(SQL);

            if(!result.wasNull()){
                    while(result.next()){
                            xml += " <meio ";
                            xml += " id_meio = '" + result.getString("id_meio") + "' ";
                            xml += " nm_meio = '" + result.getString("nm_meio") + "' ";
                            xml += " dt_meio = '" + result.getString("dt_meio") + "' ";
                            xml += " de_meio = '" + result.getString("de_meio") + "' ";
                            xml += " > </meio>";
                    }
            }
        } catch (SQLException e) {
            xml += "<erro message='SQL \'Exception: "+ e.toString() + "' />";
        } catch (ClassNotFoundException cE) {
            xml += "<erro message='Class Not Found Exception: "+ cE.toString() + "' />";
        } finally {
            return xml;
        }
    }

    protected String listarMeios(){ 
        String xml = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String SQL = " SELECT  ";
            SQL += " id_meio, nm_meio, dt_meio, de_meio ";
            SQL += " FROM tMeio ORDER BY id_meio ASC";        

            ResultSet result = con.createStatement().executeQuery(SQL);

            if(!result.wasNull()){
                    while(result.next()){
                            xml += " <meio ";
                            xml += " id_meio = '" + result.getString("id_meio") + "' ";
                            xml += " nm_meio = '" + result.getString("nm_meio") + "' ";
                            xml += " dt_meio = '" + result.getString("dt_meio") + "' ";
                            xml += " de_meio = '" + result.getString("de_meio") + "' ";
                            xml += " > </meio>";
                    }
            }
        } catch (SQLException e) {
            xml += "<erro message='SQL \'Exception: "+ e.toString() + "' />";
        } catch (ClassNotFoundException cE) {
            xml += "<erro message='Class Not Found Exception: "+ cE.toString() + "' />";
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
