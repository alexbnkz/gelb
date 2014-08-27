package Base;

import Utils.Cripta;
import Utils.XMLTransform;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class DataAccess {
    String erro = "";
    private XMLTransform transform = new XMLTransform();
    
    public String getDados(String SQL)// <editor-fold defaultstate="collapsed">
    {
        erro = " ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
                           
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    erro = transform.toText(result.getString(1));
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
            return erro;
        }
    }// </editor-fold>
    public String goAutenticar(Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        String SQL = "";
        erro = " ";
        
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
                    erro = "";
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
            return erro;
        }
    }// </editor-fold>
    
    public String salvarExperimento(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
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
                
                if(novoCodigo.equals("1")) {
                    SQL = "INSERT INTO";
                    SQL += " tExperimento(id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento, ct_painel, nm_arquivo) ";
                    SQL += " VALUES(" + novoCodigo + ", '" + nm_experimento + "', '" + dt_experimento + "', '" + tp_experimento + "', '" + de_experimento + "', 'S', 'Vriesea');";
                } else {
                    SQL = "INSERT INTO";
                    SQL += " tExperimento(id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento) ";
                    SQL += " VALUES(" + novoCodigo + ", '" + nm_experimento + "', '" + dt_experimento + "', '" + tp_experimento + "', '" + de_experimento + "');";
                }
                
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
                
                if(buscarMeio("", id_experimento).equals("")){
                    if(!getDados("SELECT ct_painel FROM tExperimento WHERE id_experimento = " + id_experimento + ";").equals("S")){
                        con.createStatement().execute(SQL);    
                        xml = "<message type= 'aviso' text='Excluido com sucesso!' />";
                    }else{
                        xml = "<message type= 'erro' text='Exclusão cancelada!' />";
                        xml += "<message type= 'erro' text='Indução de brotos em Vriesea Botafogensis é o experimento principal!' />";
                    }
                }else{
                    xml = "<message type= 'erro' text='Exclusão cancelada!' />";
                    xml += "<message type= 'erro' text='Esse experimento tem relacionamento em Meios!' />";
                }
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
    }// </editor-fold>
    public String listarExperimento(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " id_experimento, nm_experimento, dt_experimento, tp_experimento, de_experimento, nm_arquivo ";
            SQL += " FROM tExperimento ";      
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
                if(!hash.get("ct_painel").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " ct_painel = '" + hash.get("ct_painel") + "' ";  
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
    }// </editor-fold>
    public String listarExperimento()// <editor-fold defaultstate="collapsed">
    {
        Hashtable hash = new Hashtable();
        return listarExperimento("LST", hash);
    }// </editor-fold>
    public String buscarExperimento(String id_experimento, String nm_experimento)// <editor-fold defaultstate="collapsed">
    {
        Hashtable hash = new Hashtable();
        hash.put("id_experimento", id_experimento);
        hash.put("nm_experimento", nm_experimento);
        return listarExperimento("LST", hash);
    }// </editor-fold>

    public String salvarMeio(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        String xml = "";
        String SQL = "";
                
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            String id_meio = hash.get("id").toString();
            String id_experimento = hash.get("id_experimento").toString();
            String nm_meio = hash.get("nm_meio").toString();
            String dt_meio = hash.get("dt_meio").toString();
            String de_meio = hash.get("de_meio").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_meio)+1 AS NewCodigo FROM tMeio;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tMeio(id_meio, id_experimento, nm_meio, dt_meio, de_meio) ";
                SQL += " VALUES(" + novoCodigo + ", " + id_experimento + ", '" + nm_meio + "', '" + dt_meio + "', '" + de_meio + "');";
                
                con.createStatement().execute(SQL);   
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tMeio SET ";
                SQL += " nm_meio='" + nm_meio + "', ";
                SQL += " dt_meio='" + dt_meio + "' ";
                SQL += " de_meio='" + de_meio + "' ";
                SQL += " WHERE id_meio=" + id_meio + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
            }
            if(cmd.equals("DEL")){
                SQL = " DELETE FROM tMeio ";
                SQL += " WHERE id_meio=" + id_meio + "; ";
                
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
    }// </editor-fold>
    public String listarMeio(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/ifrj?user=root&password=";
            
            Connection con = DriverManager.getConnection(connectionUrl); 
            
            SQL = " SELECT  ";
            SQL += " M.id_meio, E.id_experimento, E.nm_experimento, M.nm_meio, M.dt_meio, M.de_meio ";
            SQL += " FROM tMeio M INNER JOIN tExperimento E ON (M.id_experimento = E.id_experimento) ";
            
            if(cmd.equals("DET")){
                SQL += " WHERE M.id_meio = " + hash.get("id");    
            }
            if(cmd.equals("SRCH")){
                SQL += " WHERE ";    
                boolean also = false;
                
                if(!hash.get("id_meio").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " M.id_meio = " + hash.get("id_meio");  
                    also = true;
                }
                if(!hash.get("id_experimento").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " E.id_experimento = " + hash.get("id_experimento");  
                    also = true;
                }          
            }
            
            SQL += " ORDER BY E.id_experimento ASC, M.id_meio ASC";        

            ResultSet result = con.createStatement().executeQuery(SQL);
            
            String primeiro = "";
            
            if(!result.wasNull()){
                    while(result.next()){
                            xml += " <meio ";
                            xml += " id_meio = '" + transform.toText(result.getString("id_meio")) + "' ";
                            xml += " id_experimento = '" + transform.toText(result.getString("id_experimento")) + "' ";
                            if(!primeiro.equals(result.getString("nm_experimento"))){
                                xml += " nm_experimento = '" + transform.toText(result.getString("nm_experimento")) + "' ";
                                primeiro = result.getString("nm_experimento");
                            }else{
                                xml += " nm_experimento = '' ";
                            }
                            xml += " nm_meio = '" + transform.toText(result.getString("nm_meio")) + "' ";
                            xml += " dt_meio = '" + transform.toText(result.getString("dt_meio")) + "' ";
                            xml += " de_meio = '" + transform.toText(result.getString("de_meio")) + "' ";
                            xml += " > </meio>";
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
    }// </editor-fold>
    public String listarMeio() // <editor-fold defaultstate="collapsed">
    {
        Hashtable hash = new Hashtable();
        return listarMeio("LST", hash);
    }// </editor-fold>
    public String buscarMeio(String id_meio, String id_experimento) // <editor-fold defaultstate="collapsed">
    {
        Hashtable hash = new Hashtable();
        hash.put("id_meio", id_meio);
        hash.put("id_experimento", id_experimento);
        return listarMeio("SRCH", hash);
    }// </editor-fold>

}
