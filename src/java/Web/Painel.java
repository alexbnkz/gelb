package Web;

import Base.DataAccess;
import Utils.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Painel", urlPatterns = {"/Painel"})
public class Painel extends HttpServlet {
    private Settings s = new Settings();
    private XMLTransform transform = new XMLTransform();
    private DataAccess Base = new DataAccess();
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        Hashtable hash = new Hashtable();
        String xml = "";
        String page = "";
        String cmd = "";
        
        HttpSession session = request.getSession(true);

        try {
            for (Map.Entry en : request.getParameterMap().entrySet()) {
                hash.put(en.getKey(), request.getParameter(en.getKey().toString()));
            }
 
            if(!hash.containsKey("cmd")){
                hash.put("cmd", "Painel/lst");
                cmd = "LST";
            }else {
                if(hash.get("id") == "" && hash.get("cmd") != "lst"){
                    cmd = "INS";
                }else{
                    cmd = hash.get("cmd").toString().split("/")[1].toUpperCase();
                }
            }
            
            //page = hash.get("cmd").toString().split("/")[0];
            
            String login = (String)session.getAttribute("login");
            
            if(!login.equals("")){
                xml += Base.getLogin(login);
            } else {
                session.setAttribute("root_message", "<message type= 'erro' text='Usuário deslogado!' />");
                response.sendRedirect("login.jsp");
            }
            
            String id_experimento = Base.getDados("SELECT id_experimento FROM tExperimento WHERE ct_painel = 'S';");
            
            if(!id_experimento.trim().equals("")){
                page = Base.getDados("SELECT nm_arquivo FROM tExperimento WHERE ct_painel = 'S';"); //Vriesea
                xml += Base.buscarMeio("", id_experimento);
                
                // Nome da planta
                String dt_experimento = Base.getDados("SELECT dt_experimento FROM tExperimento " +
                "WHERE id_experimento = " + id_experimento);

                SimpleDateFormat DateFormatBra = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
                SimpleDateFormat DateFormatUsa = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", new Locale("en", "US"));

                Date d1 = DateFormatUsa.parse((new Date()).toString());
                Date d2 = DateFormatBra.parse(dt_experimento);

                long nm_planta = TimeUnit.MILLISECONDS.toHours(d1.getTime() - d2.getTime())/24;
                
                xml += "<sistema nm_planta='VB" + nm_planta + "D' />";
                xml += "<sistema dt_hoje='" + (new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm").format(new Date()).toString()) + "' />";

            } else {
                page = "painel";
            }
            
            
        } catch (Exception e) {
            xml += "<message type= 'erro' text='" + transform.toText(e.toString()) + "' />";
        } 
        
        try{
            
            xml = "<root>" + "<cmd>" + transform.toText(cmd) + "</cmd>" + xml + "</root>";
            
            String html;
            html = transform.toHtml(s.getSetting("root") + "web\\xsl\\" + page + ".xsl", xml);
            
            out.println(html);
        } catch (Exception e) {
            xml += "<message type= 'erro' text='" + transform.toText(e.toString()) + "' />";
        } finally {
            out.close();
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
