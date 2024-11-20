package controller;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root"; // Username database
    private static final String DB_PASSWORD = ""; // Password database

    public void createDatabase() {
        String databaseName = "restoran"; // Nama database yang akan dibuat

        // Membuka koneksi ke MySQL
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Membuat statement
            Statement statement = connection.createStatement();

            // Query untuk membuat database
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + databaseName;

            // Eksekusi query
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database '" + databaseName + "' berhasil dibuat atau sudah ada!");

        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat membuat database!");
            e.printStackTrace();
        }
    }
}
