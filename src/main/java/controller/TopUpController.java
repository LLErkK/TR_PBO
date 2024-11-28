package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TopUpController {

    public void addBalanceById(int id,double amount){
        String query ="UPDATE users SET balance = balance + ? WHERE id = ?";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setDouble(1,amount);
            stmt.setInt(2,id);

            int update = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getbalanceById(int id){
        String query = "SELECT * FROM users WHERE id =?";
        try(Connection connection = Koneksi.koneksi();
        PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getDouble("balance");
            }else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void createTopUpHistory(int id,double amount){
        String query ="INSERT INTO history_topup (user_id,amount) VALUES (?,?)";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,id);
            stmt.setDouble(2,amount);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
