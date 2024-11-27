package controller;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    //mendapatkan id ketika berhasil login
    public int getIdByusername(String username){
        String query ="SELECT id FROM users WHERE username = ?";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,username);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //mendapatkan data user
    public User getUserById(int id){
        String query="SELECT * FROM users WHERE id = ?";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User model = new User();
                model.setEmail(rs.getString("email"));
                model.setUsername(rs.getString("username"));
                return model;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
