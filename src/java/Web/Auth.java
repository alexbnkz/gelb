package Web;

import Base.DataAccess;
import java.io.IOException;
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
    private DataAccess Base = new DataAccess();
    
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
                String erro_autenticacao = Base.goAutenticar(hash);
                if(erro_autenticacao.equals("")){
                    session.setAttribute("login", hash.get("cd_login").toString());
                    response.sendRedirect("Painel");
                } else {
                    if(erro_autenticacao.equals(" ")){
                        session.setAttribute("root_message", "<message type= 'erro' text='Usuário/senha incorretos!' />" + erro_autenticacao);
                        response.sendRedirect("login.jsp");
                    } else {
                        session.setAttribute("root_message", "<message type= 'erro' text='Banco de Dados!' />" + erro_autenticacao);
                        response.sendRedirect("login.jsp");
                    }
                }
            } else {
                session.setAttribute("root_message", "<message type= 'erro' text='Comando não reconhecido!' />");
                response.sendRedirect("login.jsp");
            }
        } else {
            session.setAttribute("root_message", "<message type= 'erro' text='Acesso incorreto!' />");
            response.sendRedirect("login.jsp");
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
