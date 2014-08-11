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
                hash.put("cmd", "Experimentos/lst");
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
                xml += salvarExperimento(cmd, hash);
            }
            
            xml += listarExperimentos(cmd, hash);
            
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
    
    private String salvarExperimento(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String id_experimento = hash.get("id").toString();
            String nm_experimento = hash.get("nm_experimento").toString();
            String dt_experimento = hash.get("dt_experimento").toString();
            String tp_experimento = hash.get("tp_experimento").toString();
            String de_experimento = hash.get("de_experimento").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_experimento)+1 AS NewCodigo FROM tExperimento;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tExperimento(id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento) ";
                SQL += " VALUES(" + novoCodigo + ", '" + nm_experimento + "', '" + dt_experimento + "', '" + tp_experimento + "', '" + de_experimento + "');";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tExperimento SET ";
                SQL += " nm_experimento='" + nm_experimento + "', ";
                SQL += " dt_experimento='" + dt_experimento + "', ";
                SQL += " tp_experimento='" + tp_experimento + "', ";
                SQL += " de_experimento='" + de_experimento + "' ";
                SQL += " WHERE id_experimento=" + id_experimento + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
            }
            if(cmd.equals("DEL")){
                SQL = " DELETE FROM tExperimento ";
                SQL += " WHERE id_experimento=" + id_experimento + "; ";
                
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
    
    private String listarExperimentos(String cmd, Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento ";
            SQL += " FROM tExperimento ";      
            if(cmd.equals("SRCH")){
                SQL += " WHERE ";    
                boolean also = false;
                
                if(!hash.get("id").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_experimento = " + hash.get("id");  
                    also = true;
                }
                if(!hash.get("id_experimento").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_experimento = " + hash.get("id_experimento");  
                    also = true;
                }
                if(!hash.get("nm_experimento").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " nm_experimento = '" + hash.get("nm_experimento") + "' ";  
                    also = true;
                }            
            }
            if(cmd.equals("DET")){
                SQL += " WHERE id_experimento = " + hash.get("id");    
            }
            
            SQL += " ORDER BY id_experimento ASC";      
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <experimento ";
                    xml += " id_experimento = '" + transform.toText(result.getString("id_experimento")) + "' ";
                    xml += " nm_experimento = '" + transform.toText(result.getString("nm_experimento")) + "' ";
                    xml += " dt_experimento = '" + transform.toText(result.getString("dt_experimento")) + "' ";
                    xml += " tp_experimento = '" + transform.toText(result.getString("tp_experimento")) + "' ";
                    xml += " de_experimento = '" + transform.toText(result.getString("de_experimento")) + "' ";
                    xml += " > </experimento>";
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
 
    protected String listarExperimentos(){ 
        Hashtable hash = new Hashtable();
        return listarExperimentos("LST", hash);
    }
    
    protected String buscarExperimentos(String id_experimento, String nm_experimento){ 
        Hashtable hash = new Hashtable();
        return listarExperimentos("LST", hash);
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
