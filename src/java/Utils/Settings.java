package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    public String getSetting(String Property) throws IOException{
        Properties p = new Properties();
        File file = new File("D:\\GELB\\web\\app.local.settings");
        //File file = new File("/usr/local/shared/tomcat/beninkz/webapps/ROOT/app.remote.settings");
        p.load(new FileInputStream(file));
        return p.getProperty(Property);
    }
}
