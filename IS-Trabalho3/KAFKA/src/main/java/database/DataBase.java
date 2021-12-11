package database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase {

    private Connection con;

    //Ligação com a BD
    public void connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/CreditCardCompany";
        String user = "postgres";
        String password ="postgres";
        this.con = DriverManager.getConnection(url, user, password);
    }

    //Ler ficheiros para criar tabelas e respetivos dados
    public void lerFicheiro(String path) throws IOException,SQLException{
        FileInputStream stream = new FileInputStream(path);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        String linha = "";
        String linha2 = br.readLine();
        while(linha2 != null) {
            linha += linha2 + "\n";
            linha2 = br.readLine();
        }

        Statement statement = this.con.createStatement();
        statement.executeUpdate(linha);
        //fecha stream de ficheiro
        stream.close();
    }

    //Dados para colocar no Tópico DBInfo
    public ArrayList<String> getUsers() throws SQLException {

        String sql = "SELECT id,name,type FROM utilizador";
        Statement statement = this.con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> users = new ArrayList<>();
        while (rs.next()) {
           String user = rs.getInt("id")+";"+rs.getString("name")+";"+rs.getInt("type");
           users.add(user);
        }
        return users;
    }

    //Admin pediu para listar clientes
    public ArrayList<HashMap<String,String>> AdminGetClients() throws SQLException {
        
        String sql = "SELECT id,nome,tipo FROM utilizador";
        Statement statement = this.con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<HashMap<String,String>> users = new ArrayList<>();
        
        while (rs.next()) {

            if(rs.getInt("type")==0){
                HashMap<String,String> user = new HashMap<>();;
                user.put("Id",rs.getString("id"));
                user.put("Nome",rs.getString("name"));
                users.add(user);
            }
        }
        return users;
    }

    //Admin pediu para listar managers
    public ArrayList<HashMap<String,String>> AdminGetManagers() throws SQLException {

        String sql = "SELECT id,nome,tipo FROM utilizador";
        Statement statement = this.con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<HashMap<String,String>> users = new ArrayList<>();

        while (rs.next()) {

            if(rs.getInt("type")==1){
                HashMap<String,String> user = new HashMap<>();;
                user.put("Id",rs.getString("id"));
                user.put("Nome",rs.getString("name"));
                users.add(user);
            }
        }
        return users;
    }

    //Admin pediu para listar currencies
    public ArrayList<HashMap<String,String>> AdminGetCurrencies() throws SQLException {

        String sql = "SELECT currency, exchange FROM currencies";
        Statement statement = this.con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<HashMap<String,String>> currencies = new ArrayList<>();

        while (rs.next()) {
            HashMap<String,String> currency = new HashMap<>();;
            currency.put("Currency",rs.getString("currency"));
            currency.put("Exchange",rs.getString("exchange"));
            currencies.add(currency);
        }
        return currencies;
    }

    //Admin inseriu cliente
    public void AdminInsertUser(String nome,int managerid) throws SQLException {
        String sql = "INSERT INTO utilizador (name,type,managerid) values ('"+nome+"',0,"+managerid+")";
        Statement statement = this.con.createStatement();
        statement.executeUpdate(sql);
    }

    //Admin inseriu manager
    public void AdminInsertManager(String nome) throws SQLException {
        String sql = "INSERT INTO utilizador (name,type) values ('"+nome+"',1)";
        Statement statement = this.con.createStatement();
        statement.executeUpdate(sql);
    }

    //Admin inseriu currency
    public void AdminInsertCurrency(String currency,float exchange) throws SQLException {
        String sql = "INSERT INTO currency (currency,exchange) values ('"+currency+"',"+exchange+")";
        Statement statement = this.con.createStatement();
        statement.executeUpdate(sql);
    }

    //Admin pediu total credits
    public String AdminGetTotalCredits() throws SQLException {
        String sql = "SELECT total FROM totalcredits";
        Statement statement = this.con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        return rs.getString("total");
    }
}



