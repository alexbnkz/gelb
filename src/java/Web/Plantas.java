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

@WebServlet(name = "Plantas", urlPatterns = {"/Plantas"})
public class Plantas extends HttpServlet {
    private XMLTransform transform = new XMLTransform();
            
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
                hash.put("cmd", "Plantas/lst");
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
                xml += salvarPlanta(cmd, hash);
            }
            
            Meios M = new Meios();
            
            xml += M.listarMeios();
            xml += listarPlantas(cmd, hash);
            
        } catch (Exception e) {
            xml += "<message type= 'erro' text='" + transform.toText(e.toString()) + "' />";
        } 
        
        try{
            xml = "<root>" + xml + "</root>";
            
            String html;
            html = transform.toHtml("D:\\GELB\\web\\xsl\\" + page + ".xsl", xml);
            
            out.println(html);
        } catch (Exception e) {
            xml += "<message type= 'erro' text='" + transform.toText(e.toString()) + "' />";
        } finally {
            out.close();
        }
    }
    
    private String salvarPlanta(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String id_planta = hash.get("id").toString();
            String id_meio = hash.get("id_meio").toString();
            String nm_planta = hash.get("nm_planta").toString();
            String dt_planta = hash.get("dt_planta").toString();
            String dt_repique = hash.get("dt_repique").toString();
            String qt_planta = hash.get("qt_planta").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_planta)+1 AS NewCodigo FROM tPlanta;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tPlanta(id_planta, id_meio, nm_planta, dt_planta, dt_repique, qt_planta) ";
                SQL += " VALUES(" + novoCodigo + ", " + id_meio + ", '" + nm_planta + "', '" + dt_planta + "', NULL, " + qt_planta + ");";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tPlanta SET ";
                if(dt_repique.equals("")){
                        SQL += " id_meio=" + id_meio + ", ";
                }
                SQL += " nm_planta='" + nm_planta + "', ";
                SQL += " dt_planta='" + dt_planta + "', ";
                SQL += " dt_repique='" + dt_repique + "', ";
                SQL += " qt_planta=" + dt_planta + " ";
                SQL += " WHERE id_planta=" + id_planta + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
            }
            if(cmd.equals("DEL")){
                SQL = " DELETE FROM tPlanta ";
                SQL += " WHERE id_planta=" + id_planta + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Excluido com sucesso!' />";
            }
        } catch (SQLException e) {
            xml += "<message type= 'erro' text='SQL Exception: " + transform.toText(e.toString()) + "' />";
            if(!SQL.equals("")){
                xml += "<message type= 'erro' text='" + transform.toText(SQL) + "' />";
            }
        } catch (ClassNotFoundException cE) {
            xml += "<message type= 'erro' text='Class Not Found Exception: " + transform.toText(cE.toString()) + "' />";
        } finally {
            return xml;
        }
    }
    
    private String listarPlantas(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " id_planta, id_meio, nm_planta, dt_planta, dt_repique, qt_planta ";
            SQL += " FROM tPlanta ORDER BY id_planta ASC";        

            ResultSet result = con.createStatement().executeQuery(SQL);

            if(!result.wasNull()){
                while(result.next()){
                    xml += " <planta ";
                    xml += " id_planta = '" + result.getInt("id_planta") + "' ";
                    xml += " id_meio = '" + result.getString("id_meio") + "' ";
                    xml += " nm_planta = '" + result.getString("nm_planta") + "' ";
                    xml += " dt_planta = '" + result.getString("dt_planta") + "' ";
                    if(result.getString("dt_repique") == null) {
                        xml += " dt_repique = '--/--/----' ";
                    } else {
                        xml += " dt_repique = '" + result.getString("dt_repique") + "' ";
                    }
                    xml += " qt_planta = '" + result.getString("qt_planta") + "' ";
                    xml += " > </planta>";
                }
            }
        } catch (SQLException e) {
            xml += "<message type= 'erro' text='SQL \'Exception: " + transform.toText(e.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
        } catch (ClassNotFoundException cE) {
            xml += "<message type= 'erro' text='Class Not Found Exception: " + transform.toText(cE.toString()) + "' />";
        } finally {
            return xml;
        }
    }
 
    protected String listarPlantas(){ 
        Hashtable hash = new Hashtable();
        return listarPlantas("", hash);
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
