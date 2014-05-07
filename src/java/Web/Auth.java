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

@WebServlet(name = "Auth", urlPatterns = {"/Auth"})
public class Auth extends HttpServlet {
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
 
            if(hash.containsKey("cmd")){
                cmd = hash.get("cmd").toString();
                if(cmd.equals("Login/auth")){
                    if(cmd.equals("Login/auth")){
                        response.sendRedirect("Experimentos");  
                    } else {
                        response.sendRedirect("login.jsp");  
                    }
                }
            }
        } catch (Exception e) {
            xml += "<message type= 'erro' text='" + transform.toText(e.toString()) + "' />";
        } 
    }
    
    // Terminar a Autenticação
    // Terminar a Autenticação
    // Terminar a Autenticação
    // Terminar a Autenticação
    // Terminar a Autenticação
    // Terminar a Autenticação
        
    private String Autenticar(Hashtable hash){ 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento ";
            SQL += " FROM tUsuario ";      
            if(cmd.equals("SRCH")){
                SQL += " WHERE ";    
                boolean also = false;
                
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
            SQL += " ORDER BY id_experimento ASC";      
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <experimento ";
                    xml += " id_experimento = '" + result.getInt("id_experimento") + "' ";
                    xml += " nm_experimento = '" + result.getString("nm_experimento") + "' ";
                    xml += " dt_experimento = '" + result.getString("dt_experimento") + "' ";
                    xml += " tp_experimento = '" + result.getString("tp_experimento") + "' ";
                    xml += " de_experimento = '" + result.getString("de_experimento") + "' ";
                    xml += " > </experimento>";
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
