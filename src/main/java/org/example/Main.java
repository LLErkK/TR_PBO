package org.example;

import controller.Database;
import controller.Koneksi;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.createDatabase();

        Connection conn = Koneksi.koneksi();
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Koneksi ditutup!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}