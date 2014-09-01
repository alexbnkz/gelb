package Web;

import Base.DataAccess;
import Utils.XMLTransform;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Repiques", urlPatterns = {"/Repiques"})
public class Repiques extends HttpServlet {
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
                xml += Base.salvarRepique(cmd, hash);
            }
            
            xml += Base.listarRepique(cmd, hash);
            
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
