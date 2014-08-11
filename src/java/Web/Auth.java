package Web;

import Utils.XMLTransform;
import Utils.Cripta;
import java.io.IOException;
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
import javax.servlet.http.HttpSession;

@WebServlet(name = "Auth", urlPatterns = {"/Auth"})
public class Auth extends HttpServlet {
    private XMLTransform transform = new XMLTransform();
    String erro = "";
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        Hashtable hash = new Hashtable();
        String cmd;
        
        HttpSession session = request.getSession(true);
        
        for (Map.Entry en : request.getParameterMap().entrySet()) {
            hash.put(en.getKey(), request.getParameter(en.getKey().toString()));
        }

        if(hash.containsKey("cmd")){
            cmd = hash.get("cmd").toString();
            if(cmd.equals("Login/auth")){
                if(goAutenticar(hash)){
                    session.setAttribute("cd_usuario_logado", hash.get("cd_login").toString());
                    response.sendRedirect("Experimentos");
                } else {
                    session.setAttribute("root_message", "<message type= 'erro' text='Usuário/senha incorretos!' />" + erro);
                    response.sendRedirect("login.jsp");
                }
            } else {
                session.setAttribute("root_message", "<message type= 'erro' text='Comando não reconhecido!' />" + erro);
                response.sendRedirect("login.jsp");
            }
        } else {
            session.setAttribute("root_message", "<message type= 'erro' text='Acesso incorreto!' />" + erro);
            response.sendRedirect("login.jsp");
        }
    }
        
    private boolean goAutenticar(Hashtable hash){ 
        boolean auth = false;
        String SQL = "";
        
        Cripta md5 = new Cripta();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " id_usuario, cd_login, nm_usuario, cd_email, ct_privilegio ";
            SQL += " FROM tUsuario ";
            SQL += " WHERE  cd_login = '" + hash.get("cd_login") + "' ";
            SQL += " AND    pw_senha = '" + md5.encriptar(hash.get("pw_senha").toString()) + "'; ";
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    auth = true;
                }
            }
        } catch (SQLException e) {
            erro = "<message type= 'erro' text='SQL Exception: " + transform.toText(e.toString()) + "' />";
            if(!SQL.equals("")) {
                erro += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
        } catch (ClassNotFoundException cE) {
            erro = "<message type= 'erro' text='Class Not Found Exception: " + transform.toText(cE.toString()) + "' />";
        } finally {
            return auth;
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
