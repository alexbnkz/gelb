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

@WebServlet(name = "Repiques", urlPatterns = {"/Repiques"})
public class Repiques extends HttpServlet {
    private XMLTransform transform = new XMLTransform();
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
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
                hash.put("cmd", "Repiques/lst");
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
                xml += salvarRepique(cmd, hash);
            }
            
            xml += listarRepiques(cmd, hash);
            
        } catch (Exception e) {
            xml += "<message type= 'erro' text='" + transform.toText(e.toString()) + "' />";
        } 
        
        try{
            
            xml = "<root>" + "<cmd>" + transform.toText(cmd) + "</cmd>" + xml + "</root>";
            
            String html;
            html = transform.toHtml("D:\\GELB\\web\\xsl\\" + page + ".xsl", xml);
            
            out.println(html);
        } catch (Exception e) {
            xml += "<message type= 'erro' text='" + transform.toText(e.toString()) + "' />";
        } finally {
            out.close();
        }
    }
    
    private String salvarRepique(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String id_repique = hash.get("id").toString();
            String id_meio = hash.get("id_meio").toString();
            String dt_repique = hash.get("dt_repique").toString();
            String nm_planta = hash.get("nm_planta").toString();
            String dt_planta = hash.get("dt_planta").toString();
            String qt_planta = hash.get("qt_planta").toString();
            String ct_primeiro = hash.get("ct_primeiro").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_repique)+1 AS NewCodigo FROM tRepique;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tRepique(id_repique, id_meio, dt_repique, nm_planta, dt_planta, qt_planta, ct_primeiro) ";
                SQL += " VALUES(" + novoCodigo + ", " + id_meio + ", '" + dt_repique + "', '" + nm_planta + "', '" + dt_planta + "', " + qt_planta + ", NULL);";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tRepique SET ";
                SQL += " id_meio=" + id_meio + ", ";
                SQL += " dt_repique='" + dt_repique + "', ";
                SQL += " nm_planta='" + nm_planta + "', ";
                SQL += " dt_planta='" + dt_planta + "', ";
                SQL += " qt_planta=" + qt_planta + ", ";
                SQL += " ct_primeiro='" + ct_primeiro + "' ";
                SQL += " WHERE id_repique=" + id_repique + "; ";
                 
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
            }
            if(cmd.equals("DEL")){
                SQL = " DELETE FROM tRepique ";
                SQL += " WHERE id_repique=" + id_repique + "; ";
                
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
    
    private String listarRepiques(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " id_repique, id_meio, dt_repique, nm_planta, dt_planta, qt_planta ";
            SQL += " FROM tRepique ";      
            if(cmd.equals("SRCH")){
                SQL += " WHERE ";    
                boolean also = false;
                
                if(!hash.get("id").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_repique = " + hash.get("id");  
                    also = true;
                }
                if(!hash.get("id_repique").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_repique = " + hash.get("id_repique");  
                    also = true;
                }
                if(!hash.get("id_meio").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_meio = " + hash.get("id_meio") + " ";  
                    also = true;
                }            
                if(!hash.get("nm_planta").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " nm_planta = '" + hash.get("nm_planta") + "' ";  
                    also = true;
                }            
                if(!hash.get("ct_primeiro").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " ct_primeiro = '" + hash.get("ct_primeiro") + "' ";  
                    also = true;
                }            
            }
            if(cmd.equals("DET")){
                SQL += " WHERE id_repique = " + hash.get("id");    
            }
            
            SQL += " ORDER BY id_repique ASC";      
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <repique ";
                    xml += " id_repique = '" + transform.toText(result.getString("id_repique")) + "' ";
                    xml += " id_meio = '" + transform.toText(result.getString("id_meio")) + "' ";
                    xml += " dt_repique = '" + transform.toText(result.getString("dt_repique")) + "' ";
                    xml += " nm_planta = '" + transform.toText(result.getString("nm_planta")) + "' ";
                    xml += " dt_planta = '" + transform.toText(result.getString("dt_planta")) + "' ";
                    xml += " qt_planta = '" + transform.toText(result.getString("qt_planta")) + "' ";
                    xml += " ct_primeiro = '" + transform.toText(result.getString("ct_primeiro")) + "' ";
                    xml += " > </repique>";
                }
            }
        } catch (SQLException e) {
            xml += "<message type= 'erro' text='SQL Exception: " + transform.toText(e.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
        } catch (ClassNotFoundException cE) {
            xml += "<message type= 'erro' text='Class Not Found Exception: " + transform.toText(cE.toString()) + "' />";
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
