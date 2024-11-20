package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restoran";
    private static final String DB_USER = "root"; // Username database
    private static final String DB_PASSWORD = ""; // Password database

    public static Connection koneksi(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            System.out.println("Koneksi berhasil");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }catch (SQLException e){
            System.out.println("GAGAL TERHUBUNG");
            e.printStackTrace();
        }
        return connection;
    }



}
