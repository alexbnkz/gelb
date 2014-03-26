package Web;

import Utils.XMLTransform;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Experimentos", urlPatterns = {"/Experimentos"})
public class Experimentos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            Hashtable hash = new Hashtable();
            
            for (Map.Entry en : request.getParameterMap().entrySet()) {
                hash.put(en.getKey(), en.getValue());
            }
            
            String page = hash.get("cmd").toString().split("/")[0];
            String cmd = hash.get("cmd").toString().split("/")[1].toUpperCase();
            
            String html;
            XMLTransform transform = new XMLTransform();
            html = transform.toHtml("D:\\GELB\\web\\xsl\\" + page + ".xsl", "<root></root>");

            out.println(html);
        
        } finally {            
            out.close();
        }
    }
    
    private void salvarExperimento(String cmd, String id_experimento, String nm_experimento, String dt_experimento, String tp_experimento, String de_experimento){ 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_experimento)+1 AS NewCodigo FROM tExperimento;");
                
                int novoCodigo = 1;
                
                if(result.next()){
                    novoCodigo = result.getInt("NewCodigo");
                }
                
                String SQL = "INSERT INTO";
                SQL = SQL + " tExperimento(id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento) ";
                SQL = SQL + " VALUES(" + novoCodigo + ", '" + nm_experimento + "', '" + dt_experimento + "', '" + tp_experimento + "', '" + de_experimento + "');";
                
                con.createStatement().execute(SQL);    
            }
            if(cmd.equals("UPD")){
                String SQL = " UPDATE tExperimento SET ";
                SQL = SQL + " nm_experimento='" + nm_experimento + "', ";
                SQL = SQL + " dt_experimento='" + dt_experimento + "', ";
                SQL = SQL + " tp_experimento='" + tp_experimento + "', ";
                SQL = SQL + " de_experimento='" + de_experimento + "' ";
                SQL = SQL + " WHERE id_experimento=" + id_experimento + "; ";
                
                con.createStatement().execute(SQL);    
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
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
