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

@WebServlet(name = "Usuarios", urlPatterns = {"/Usuarios"})
public class Usuarios extends HttpServlet {
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
                hash.put("cmd", "Usuarios/lst");
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
                xml += salvarUsuario(cmd, hash);
            }
            
            xml += listarUsuarios(cmd, hash);
            
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
    
    private String salvarUsuario(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String id_usuario = hash.get("id").toString();
            String cd_login = hash.get("cd_login").toString();
            String pw_senha = hash.get("pw_senha").toString();
            String nm_usuario = hash.get("nm_usuario").toString();
            String cd_email = hash.get("cd_email").toString();
            String ct_privilegio = hash.get("ct_privilegio").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_usuario)+1 AS NewCodigo FROM tUsuario;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tUsuario(id_usuario, cd_login, pw_senha, nm_usuario, cd_email, ct_privilegio) ";
                SQL += " VALUES(" + novoCodigo + ", '" + cd_login + "', '" + pw_senha + "', '" + nm_usuario + "', '" + cd_email + "', '" + ct_privilegio + "');";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tUsuario SET ";
                SQL += " cd_login='" + cd_login + "', ";
                SQL += " pw_senha='" + pw_senha + "', ";
                SQL += " nm_usuario='" + nm_usuario + "', ";
                SQL += " cd_email='" + cd_email + "', ";
                SQL += " ct_privilegio='" + ct_privilegio + "' ";
                SQL += " WHERE id_usuario=" + id_usuario + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
            }
            if(cmd.equals("DEL")){
                SQL = " DELETE FROM tUsuario ";
                SQL += " WHERE id_usuario=" + id_usuario + "; ";
                
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
    
    private String listarUsuarios(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " id_usuario, cd_login, pw_senha, nm_usuario, cd_email, ct_privilegio ";
            SQL += " FROM tUsuario ";      
            if(cmd.equals("SRCH")){
                SQL += " WHERE ";    
                boolean also = false;
                
                if(!hash.get("id_usuario").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_usuario = " + hash.get("id_usuario");  
                    also = true;
                }
                if(!hash.get("nm_usuario").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " nm_usuario = '" + hash.get("nm_usuario") + "' ";  
                    also = true;
                } 
                if(!hash.get("cd_email").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " cd_email = '" + hash.get("cd_email") + "' ";  
                    also = true;
                }            
            }
            SQL += " ORDER BY id_usuario ASC";      
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <usuario ";
                    xml += " id_usuario = '" + result.getInt("id_usuario") + "' ";
                    xml += " cd_login = '" + result.getString("cd_login") + "' ";
                    xml += " pw_senha = '" + result.getString("pw_senha") + "' ";
                    xml += " nm_usuario = '" + result.getString("nm_usuario") + "' ";
                    xml += " cd_email = '" + result.getString("cd_email") + "' ";
                    xml += " ct_privilegio = '" + result.getString("ct_privilegio") + "' ";
                    xml += " > </usuario>";
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
 
    protected String listarUsuarios(){ 
        Hashtable hash = new Hashtable();
        return listarUsuarios("LST", hash);
    }
    
    protected String buscarUsuarios(String id_experimento, String nm_experimento){ 
        Hashtable hash = new Hashtable();
        return listarUsuarios("LST", hash);
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
