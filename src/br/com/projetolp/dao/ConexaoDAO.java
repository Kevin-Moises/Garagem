package br.com.projetolp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    public static Connection con = null;

    public static Connection getConnection() {
        final String URL = "jdbc:postgresql://192.168.0.104:5432/garagem";
        final String USER = "admin";
        final String PSWD = "admin@garagem.com.br";
        
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
            con = DriverManager.getConnection(URL, USER, PSWD);
            
            //Servidor virtualizado: 192.168.0.104
            
            con.setAutoCommit(false);

            if (con == null) {
                System.out.println("Erro ao conectar com o Banco");

            }//close if
        } catch (SQLException e) {
            System.out.println("Problema ao conectar com o Banco de dados\n" 
                    + e.getMessage());
        }
        return con;
    }

    public static void closeDb() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Problema ao fechar o banco de dados" 
                    + e.getMessage());
        }
    }
}
