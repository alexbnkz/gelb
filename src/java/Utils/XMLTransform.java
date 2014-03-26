package Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLTransform {
    public void XMLTransform() {  
    }
    public String toHtml(String xsl, String xml) {
        try {
            File f = new File(xsl);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(f));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(b));
            return b.toString();
        } catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
