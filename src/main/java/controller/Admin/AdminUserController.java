package controller.Admin;

import controller.Koneksi;
import model.User;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminUserController {
    DefaultTableModel createUserTable(){
        DefaultTableModel dtm = new DefaultTableModel();
        if(dtm.getColumnCount()==0){
            dtm.addColumn("ID");
            dtm.addColumn("Username");
            dtm.addColumn("Email");
            dtm.addColumn("Password");
            dtm.addColumn("Saldo");
        }
        return dtm;
    }

    public List<User> getAllUser(){
        String sql ="SELECT * FROM users";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getDouble("balance"));

                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DefaultTableModel modelTable(){
        DefaultTableModel dtm = createUserTable();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<User> users = getAllUser();
        for (User user:users){
            Object[] obj = new Object[5];
            obj[0] = user.getId();
            obj[1] = user.getUsername();
            obj[2] = user.getEmail();
            obj[3] = user.getPassword();
            obj[4] = user.getBalance();
            dtm.addRow(obj);
        }
        return dtm;
    }

    public User getUserById(int id){
        String sql = "SELECT * FROM users WHERE id = ?";
        try(Connection connection = Koneksi.koneksi();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createUser(String username,String password,String email,double balance){
        String sql ="INSERT INTO users (username,email,password,balance) VALUES (?,?,?,?)";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,username);
            stmt.setString(2,email);
            stmt.setString(3,password);
            stmt.setDouble(4,balance);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserById(int id,String username,String email,String password){
        String sql ="UPDATE users SET username = ?,email =?, password =? WHERE id = ?";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,username);
            stmt.setString(2,email);
            stmt.setString(3,password);
            stmt.setInt(4,id);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id){
        String sql = "DELETE FROM users WHERE id =?";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
