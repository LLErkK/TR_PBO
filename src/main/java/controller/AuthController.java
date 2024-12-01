package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {

    public boolean login(String username,String password){
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try(Connection conn = Koneksi.koneksi();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,username);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                // Jika role adalah admin, kembalikan false
                if ("admin".equalsIgnoreCase(role)) {
                    return false;
                }
                return true; // Berhasil login jika bukan admin
            }
            return false; // Tidak ada pengguna yang cocok
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean loginAdmin(String username,String password){
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = 'admin'";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,username);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signIn(String email,String username,String password){
        String query ="INSERT INTO users (email,username,password) VALUES (?,?,?)";
        try(Connection conn = Koneksi.koneksi();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,email);
            stmt.setString(2,username);
            stmt.setString(3,password);

            int masuk = stmt.executeUpdate();
            return masuk > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signInAdmin(String email,String username,String password){
        String query ="INSERT INTO users (email,username,password,role) VALUES (?,?,?,?)";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,email);
            stmt.setString(2,username);
            stmt.setString(3,password);
            stmt.setString(4,"admin");
            int masuk = stmt.executeUpdate();
            return masuk > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
