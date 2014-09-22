package Base;

import Utils.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Map;

public class DataAccess {
    String erro = "";
    private Settings s = new Settings();
    private XMLTransform transform = new XMLTransform();
    
    String driver = "";
    String stringconnection = "";
    String user = "";
    String password = "";
    
    public DataAccess() { // <editor-fold defaultstate="collapsed">
        try {
            driver = s.getSetting("driver");
            stringconnection = s.getSetting("stringconnection");
            user = s.getSetting("user");
            password = s.getSetting("password");
        } catch (Exception E) {
            
        }
    } // </editor-fold>
    
    public String getDados(String SQL)// <editor-fold defaultstate="collapsed">
    {
        Connection con = null;
        erro = " ";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
                           
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    erro = transform.toText(result.getString(1));
                }
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            erro = "<message type= 'erro' text='Base.DataAccess.getDados()' />";
            erro += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                erro += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            erro = "<message type= 'erro' text='Base.DataAccess.getDados()' />";
            erro += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                erro += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return erro;
        }
    }// </editor-fold>
    public String goAutenticar(Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String SQL = "";
        erro = " ";
        
        Cripta md5 = new Cripta();
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
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
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            erro = "<message type= 'erro' text='Base.DataAccess.goAutenticar()' />";
            erro += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                erro += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            erro = "<message type= 'erro' text='Base.DataAccess.goAutenticar()' />";
            erro += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                erro += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return erro;
        }
    }// </editor-fold>
    public String getLogin(String login)// <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            SQL = " SELECT  ";
            SQL += " id_usuario, cd_login, pw_senha, nm_usuario, cd_email, ct_privilegio ";
            SQL += " FROM tUsuario ";
            SQL += " WHERE cd_login = '" + login + "'; ";      
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <login ";
                    xml += " id_usuario = '" + transform.toText(result.getString("id_usuario")) + "' ";
                    xml += " cd_login = '" + transform.toText(result.getString("cd_login")) + "' ";
                    xml += " pw_senha = '****' ";
                    xml += " nm_usuario = '" + transform.toText(result.getString("nm_usuario")) + "' ";
                    xml += " cd_email = '" + transform.toText(result.getString("cd_email")) + "' ";
                    xml += " ct_privilegio = '" + transform.toText(result.getString("ct_privilegio")) + "' ";
                    
                    if(result.getString("ct_privilegio").equals("A")){ // A - Administrador
                        xml += " nm_privilegio = 'Administrador' ";
                    } else {
                        if(result.getString("ct_privilegio").equals("P")){ // P - Pesquisador
                            xml += " nm_privilegio = 'Pesquisador' ";
                        } else {
                            xml += " nm_privilegio = 'Inativo' "; // X - Inativo
                        }
                    }
                    
                    xml += " > </login>";
                }
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.getLogin()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.getLogin()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return xml;
        }
    }// </editor-fold>
    
    public String salvarExperimento(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            String id_experimento = hash.get("id").toString();
            String nm_experimento = hash.get("nm_experimento").toString();
            String dt_experimento = hash.get("dt_experimento").toString();
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
                    SQL += " tExperimento(id_experimento, nm_experimento, dt_experimento, de_experimento, ct_painel, nm_arquivo) ";
                    SQL += " VALUES(" + novoCodigo + ", '" + nm_experimento + "', '" + dt_experimento + "', '" + de_experimento + "', 'S', 'Vriesea');";
                } else {
                    SQL = "INSERT INTO";
                    SQL += " tExperimento(id_experimento, nm_experimento, dt_experimento, de_experimento) ";
                    SQL += " VALUES(" + novoCodigo + ", '" + nm_experimento + "', '" + dt_experimento + "', '" + de_experimento + "');";
                }
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tExperimento SET ";
                SQL += " nm_experimento='" + nm_experimento + "', ";
                SQL += " dt_experimento='" + dt_experimento + "', ";
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
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarExperimento()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarExperimento()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return xml;
        }
    }// </editor-fold>
    public String listarExperimento(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            SQL = " SELECT  ";
            SQL += " id_experimento, nm_experimento, dt_experimento, de_experimento, nm_arquivo ";
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
                    xml += " de_experimento = '" + transform.toText(result.getString("de_experimento")) + "' ";
                    xml += " > </experimento>";
                }
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarExperimento()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarExperimento()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
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
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            String id_meio = "";
            String id_experimento = "";
            String nm_meio = "";
            String dt_meio = "";
            String de_meio = "";
            String ct_bloqueio = "";
            
            if(!cmd.equals("PREP")){
                id_meio = hash.get("id").toString();
                id_experimento = hash.get("id_experimento").toString();
                nm_meio = hash.get("nm_meio").toString();
                dt_meio = hash.get("dt_meio").toString();
                de_meio = hash.get("de_meio").toString();
                ct_bloqueio = hash.get("ct_bloqueio").toString();
            } else  {
                id_meio = hash.get("id").toString();
                dt_meio = hash.get("dt_meio").toString();
            }
            
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_meio)+1 AS NewCodigo FROM tMeio;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tMeio(id_meio, id_experimento, nm_meio, dt_meio, de_meio, ct_bloqueio) ";
                SQL += " VALUES(" + novoCodigo + ", " + id_experimento + ", '" + nm_meio + "', '" + dt_meio + "', '" + de_meio + "', '" + ct_bloqueio + "');";
                
                con.createStatement().execute(SQL);   
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("PREP")){
                SQL = " UPDATE tMeio SET ";
                SQL += " dt_meio='" + dt_meio + "' ";
                SQL += " WHERE id_meio=" + id_meio + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tMeio SET ";
                SQL += " nm_meio='" + nm_meio + "', ";
                SQL += " dt_meio='" + dt_meio + "', ";
                SQL += " de_meio='" + de_meio + "', ";
                SQL += " ct_bloqueio='" + ct_bloqueio + "' ";
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
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarMeio()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarMeio()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return xml;
        }
    }// </editor-fold>
    public String listarMeio(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            SQL = " SELECT  ";
            SQL += " M.id_meio, E.id_experimento, E.nm_experimento, M.nm_meio, M.dt_meio, M.de_meio, M.ct_bloqueio ";
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
                        
                            SimpleDateFormat DateFormatBra = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
                            SimpleDateFormat DateFormatUsa = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", new Locale("en", "US"));
                                                        
                            Date d1 = DateFormatUsa.parse((new Date()).toString());
                            Date d2 = DateFormatBra.parse(result.getString("dt_meio"));
                                                                                    
                            long nu_validade = 30 - (TimeUnit.MILLISECONDS.toHours(d1.getTime() - d2.getTime())/24);

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
                            xml += " dt_hoje = '" + DateFormatBra.format(d1).toString() + "' ";
                            xml += " nu_validade = '" + nu_validade  + " dias ' ";
                            xml += " de_meio = '" + transform.toText(result.getString("de_meio")) + "' ";
                            xml += " ct_bloqueio = '" + transform.toText(result.getString("ct_bloqueio")) + "' ";
                            xml += " > </meio>";
                    }
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarMeio()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarMeio()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
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

    public String salvarRepique(String cmd, Hashtable hash) // <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            String id_repique = "";
            if(!cmd.equals("INS")){
                id_repique = hash.get("id").toString();
            }
            String id_meio = hash.get("id_meio").toString();
            String dt_repique = hash.get("dt_repique").toString();
            String nm_planta = hash.get("nm_planta").toString();
            String dt_planta = hash.get("dt_planta").toString();
            String qt_planta = hash.get("qt_planta").toString();
            String ct_primeiro = hash.get("ct_primeiro").toString();
                    
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_repique)+1 AS NewCodigo FROM tRepique;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tRepique(id_repique, id_meio, dt_repique, nm_planta, dt_planta, qt_planta, ct_primeiro) ";
                SQL += " VALUES(" + novoCodigo + ", " + id_meio + ", '" + dt_repique + "', '" + nm_planta + "', '" + dt_planta + "', " + qt_planta + ", '" + ct_primeiro + "');";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tRepique SET ";
                SQL += " id_meio=" + id_meio + ", ";
                SQL += " dt_repique='" + dt_repique + "', ";
                SQL += " nm_planta='" + nm_planta + "', ";
                SQL += " dt_planta='" + dt_planta + "', ";
                SQL += " qt_planta=" + qt_planta + ", ";
                SQL += " ct_primeiro='" + ct_primeiro + "' ";
                SQL += " WHERE id_repique=" + id_repique + "; ";
                 
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
                xml += "<message type= 'erro' text='" + transform.toText(SQL) + "' />";
            }
            if(cmd.equals("DEL")){
                SQL = " DELETE FROM tRepique ";
                SQL += " WHERE id_repique=" + id_repique + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Excluido com sucesso!' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarRepique()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarRepique()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return xml;
        }
    }// </editor-fold>
    public String listarRepique(String cmd, Hashtable hash) // <editor-fold defaultstate="collapsed"> 
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        Hashtable set = new Hashtable();
        set.put("id", "");
        set.put("id_repique", "");
        set.put("id_meio", "");
        set.put("dt_repique", "'");
        set.put("nm_planta", "'");
        set.put("dt_planta", "'");
        set.put("qt_planta", "");
        set.put("ct_primeiro", "'");
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password);
            
            String SQL2 = "";
            boolean also2 = false;
            
            for (Object o: set.entrySet()) {
                Map.Entry en = (Map.Entry) o;
                
                if(!hash.get(en.getKey().toString()).equals("")){
                    if(also2){SQL2 += " AND ";}
                    SQL2 += " " + hash.get(en.getKey().toString()) + " = " + en.getValue().toString() + hash.get(en.getKey().toString()) + en.getValue().toString() + " ";  
                    also2 = true;
                }   
                
            }     
            
            
            //if(hash.contains("id_repique, id_repique, id_meio, id_meio, dt_repique, dt_repique, nm_planta, nm_planta, dt_planta, dt_planta, qt_planta, qt_planta, ct_primeiro ct_primeiro
            
            
            
            
            SQL = " SELECT  ";
            SQL += " id_repique, id_meio, dt_repique, nm_planta, dt_planta, qt_planta, ct_primeiro ";
            SQL += " FROM tRepique ";      
            if(cmd.equals("SRCH")){
                SQL += " WHERE ";    
                boolean also = false;
                
                if(!hash.get("id").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_repique = " + hash.get("id");  
                    also = true;
                }
                if(!hash.get("id_meio").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_meio = " + hash.get("id_meio") + " ";  
                    also = true;
                }            
                if(!hash.get("id_repique").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_repique = " + hash.get("id_repique");  
                    also = true;
                }
                if(!hash.get("nm_planta").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " nm_planta = '" + hash.get("nm_planta") + "' ";  
                    also = true;
                }            
                if(!hash.get("ct_primeiro").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " ct_primeiro = '" + hash.get("ct_primeiro") + "' ";  
                    also = true;
                }            
            }
            if(cmd.equals("DET")){
                SQL += " WHERE id_repique = " + hash.get("id");    
            }
            
            SQL += " ORDER BY year(str_to_date(dt_repique, '%d/%m/%y')) DESC, ";
            SQL += " month(str_to_date(dt_repique, '%d/%m/%y')) DESC, ";
            SQL += " day(str_to_date(dt_repique, '%d/%m/%y')) DESC ";
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <repique ";
                    xml += " id_repique = '" + transform.toText(result.getString("id_repique")) + "' ";
                    xml += " id_meio = '" + transform.toText(result.getString("id_meio")) + "' ";
                    xml += " dt_repique = '" + transform.toText(result.getString("dt_repique")) + "' ";
                    xml += " dia = '" + transform.toText(result.getString("dt_repique").split("/")[0]) + "' ";
                    xml += " mes = '" + transform.toText(result.getString("dt_repique").split("/")[1]) + "' ";
                    xml += " ano = '" + transform.toText(result.getString("dt_repique").split("/")[2]) + "' ";
                    xml += " nm_planta = '" + transform.toText(result.getString("nm_planta")) + "' ";
                    xml += " dt_planta = '" + transform.toText(result.getString("dt_planta")) + "' ";
                    xml += " qt_planta = '" + transform.toText(result.getString("qt_planta")) + "' ";
                    xml += " ct_primeiro = '" + transform.toText(result.getString("ct_primeiro")) + "' ";
                    xml += " > </repique>";
                }
            }

            SQL = "SELECT Distinct(dt_repique) FROM tRepique ";
            SQL += " ORDER BY year(str_to_date(dt_repique, '%d/%m/%y')) DESC, ";
            SQL += " month(str_to_date(dt_repique, '%d/%m/%y')) DESC, ";
            SQL += " day(str_to_date(dt_repique, '%d/%m/%y')) DESC LIMIT 10 ";
            
            result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <distinct ";
                    xml += " dt_repique = '" + transform.toText(result.getString("dt_repique")) + "' ";
                    xml += " dia = '" + transform.toText(result.getString("dt_repique").split("/")[0]) + "' ";
                    xml += " mes = '" + transform.toText(result.getString("dt_repique").split("/")[1]) + "' ";
                    xml += " ano = '" + transform.toText(result.getString("dt_repique").split("/")[2]) + "' ";
                    xml += " > </distinct>";
                }
            }

            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarRepique()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarRepique()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return xml;
        }
    }// </editor-fold>
    public String listarRepique() // <editor-fold defaultstate="collapsed">
    {
        Hashtable hash = new Hashtable();
        return listarMeio("LST", hash);
    }// </editor-fold>
    public String buscarRepique(String id_repcique, String id_experimento) // <editor-fold defaultstate="collapsed">
    {
        Hashtable hash = new Hashtable();
        hash.put("id_repcique", id_repcique);
        hash.put("id_experimento", id_experimento);
        return listarMeio("SRCH", hash);
    }// </editor-fold>
    
    public String salvarUsuario(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
                
        Cripta md5 = new Cripta();
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            String id_usuario = hash.get("id").toString();
            String cd_login = hash.get("cd_login").toString();
            String pw_senha = hash.get("pw_senha").toString();
            String nm_usuario = hash.get("nm_usuario").toString();
            String cd_email = hash.get("cd_email").toString();
            String ct_privilegio = hash.get("ct_privilegio").toString();
            
            if(cmd.equals("INS")){
               if(!pw_senha.equals("****")){
                   pw_senha = md5.encriptar(pw_senha);
               }
            }
            if(cmd.equals("UPD")){
               if(!pw_senha.equals("****")){
                   pw_senha = md5.encriptar(pw_senha);
               }
            }
            
            if(cmd.equals("INS")){
                ResultSet result = con.createStatement().executeQuery("SELECT MAX(id_usuario)+1 AS NewCodigo FROM tUsuario;");
                
                String novoCodigo = "1";
                
                if(result.next()){
                    if(result.getString(1) != null){
                        novoCodigo = result.getString("NewCodigo");
                    }
                }
                
                SQL = "INSERT INTO";
                SQL += " tUsuario(id_usuario, cd_login, pw_senha, nm_usuario, cd_email, ct_privilegio) ";
                SQL += " VALUES(" + novoCodigo + ", '" + cd_login + "', '" + pw_senha + "', '" + nm_usuario + "', '" + cd_email + "', '" + ct_privilegio + "');";                    
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Incluido com sucesso!' />";
            }
            if(cmd.equals("UPD")){
                SQL = " UPDATE tUsuario SET ";
                SQL += " cd_login='" + cd_login + "', ";
                
                if(!pw_senha.equals("****")){
                   SQL += " pw_senha='" + pw_senha + "', ";
                }
                
                SQL += " nm_usuario='" + nm_usuario + "', ";
                SQL += " cd_email='" + cd_email + "', ";
                SQL += " ct_privilegio='" + ct_privilegio + "' ";
                SQL += " WHERE id_usuario=" + id_usuario + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Atualizado com sucesso!' />";
            }
            if(cmd.equals("DEL")){
                SQL = " DELETE FROM tUsuario ";
                SQL += " WHERE id_usuario=" + id_usuario + "; ";
                
                con.createStatement().execute(SQL);    
                xml = "<message type= 'aviso' text='Excluido com sucesso!' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarUsuario()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.salvarUsuario()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return xml;
        }
    }// </editor-fold>
    public String listarUsuario(String cmd, Hashtable hash)// <editor-fold defaultstate="collapsed">
    { 
        Connection con = null;
        String xml = "";
        String SQL = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(stringconnection, user, password); 
            
            SQL = " SELECT  ";
            SQL += " id_usuario, cd_login, pw_senha, nm_usuario, cd_email, ct_privilegio ";
            SQL += " FROM tUsuario ";      
            
            if(cmd.equals("DET")){
                SQL += " WHERE id_usuario = " + hash.get("id");    
            }
            
            if(cmd.equals("SRCH")){
                SQL += " WHERE ";    
                boolean also = false;
                
                if(!hash.get("id_usuario").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " id_usuario = " + hash.get("id_usuario");  
                    also = true;
                }
                if(!hash.get("cd_login").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " cd_login = '" + hash.get("cd_login") + "' ";  
                    also = true;
                } 
                if(!hash.get("nm_usuario").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " nm_usuario = '" + hash.get("nm_usuario") + "' ";  
                    also = true;
                } 
                if(!hash.get("cd_email").equals("")){
                    if(also){SQL += " AND ";}
                    SQL += " cd_email = '" + hash.get("cd_email") + "' ";  
                    also = true;
                }            
            }
            SQL += " ORDER BY ct_privilegio, nm_usuario ASC;";      
            
            ResultSet result = con.createStatement().executeQuery(SQL);
            
            if(!result.wasNull()){
                while(result.next()){
                    xml += " <usuario ";
                    xml += " id_usuario = '" + transform.toText(result.getString("id_usuario")) + "' ";
                    xml += " cd_login = '" + transform.toText(result.getString("cd_login")) + "' ";
                    xml += " pw_senha = '****' ";
                    xml += " nm_usuario = '" + transform.toText(result.getString("nm_usuario")) + "' ";
                    xml += " cd_email = '" + transform.toText(result.getString("cd_email")) + "' ";
                    xml += " ct_privilegio = '" + transform.toText(result.getString("ct_privilegio")) + "' ";
                    
                    if(result.getString("ct_privilegio").equals("A")){ // A - Administrador
                        xml += " nm_privilegio = 'Administrador' ";
                    } else {
                        if(result.getString("ct_privilegio").equals("P")){ // P - Pesquisador
                            xml += " nm_privilegio = 'Pesquisador' ";
                        } else {
                            xml += " nm_privilegio = 'Inativo' "; // X - Inativo
                        }
                    }
                    
                    xml += " > </usuario>";
                }
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (SQLException E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarUsuario()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } catch (Exception E) {
            xml = "<message type= 'erro' text='Base.DataAccess.listarUsuario()' />";
            xml += "<message type= 'erro' text='" + transform.toText(E.toString()) + "' />";
            if(!SQL.equals("")) {
                xml += "<message type= 'erro' text='"+ transform.toText(SQL) + "' />";
            }
            if(!con.isClosed()){
               con.close(); 
            }
        } finally {
            return xml;
        }
    }// </editor-fold>
    public String listarUsuarios() // <editor-fold defaultstate="collapsed">
    { 
        Hashtable hash = new Hashtable();
        return listarUsuario("LST", hash);
    }// </editor-fold>
    public String buscarUsuarios(String id_usuario, String cd_login, String nm_usuario, String cd_email)// <editor-fold defaultstate="collapsed">
    { 
        Hashtable hash = new Hashtable();
        if(!id_usuario.equals("")){
            hash.put("id_usuario", id_usuario);
        }
        if(!cd_login.equals("")){
            hash.put("cd_login", cd_login);
        }
        if(!nm_usuario.equals("")){
            hash.put("nm_usuario", nm_usuario);
        }
        if(!cd_email.equals("")){
            hash.put("cd_email", cd_email);
        }
        return listarUsuario("LST", hash);
    }// </editor-fold>

}
