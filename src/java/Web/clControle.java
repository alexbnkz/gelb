package Web;

import Utils.XMLTransform;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class clControle {
    public String process(Hashtable hash){
        String page = hash.get("cmd").toString().split("/")[0];
        String cmd = hash.get("cmd").toString().split("/")[1];

        if(cmd.equals("ins") || cmd.equals("upd") || cmd.equals("del")){
            //experimentos("", "", "", "", "", "");
        }

        String html;
        XMLTransform transform = new XMLTransform();
        html = transform.toHtml("D:\\GELB\\web\\xsl\\" + page + ".xsl", "<root></root>");
        return html;
    }

   
}
