package controller;

import model.DetailPesanan;
import model.HistoryPesanan;
import model.Menu;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriController {

    DefaultTableModel dtm = new DefaultTableModel();
    public DefaultTableModel createHistoryTable(){
        dtm.addColumn("ID");
        dtm.addColumn("Tanggal");
        dtm.addColumn("Total");
        return dtm;
        //id tanggal total

    }
    DefaultTableModel htm = new DefaultTableModel();
    public DefaultTableModel createHistoryTopUp(){
        htm.addColumn("ID");
        htm.addColumn("Jumlah");
        htm.addColumn("Tanggal");
        return htm;
    }
    //mendapatkan data History
    public List<HistoryPesanan> getLatestHistory(int userId){
        int limit = 20;
        String query ="SELECT * FROM pesanan WHERE user_id = ? LIMIT ?";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,userId);
            stmt.setInt(2,limit);

            ResultSet rs = stmt.executeQuery();
            List<HistoryPesanan> histories = new ArrayList<>();
            while (rs.next()){
                HistoryPesanan hp = new HistoryPesanan();
                hp.setId(rs.getInt("id"));
                hp.setUser_id(userId);
                Timestamp timestamp = rs.getTimestamp("tanggal");
                hp.setTanggal(timestamp.toLocalDateTime());
                hp.setTotal_harga(rs.getDouble("total"));

                histories.add(hp);
            }

            return histories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<HistoryPesanan> getLatestHistoryTopUp(int userId){
        int limit = 20;
        String query ="SELECT * FROM history_topup WHERE user_id = ? LIMIT ?";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,userId);
            stmt.setInt(2,limit);

            ResultSet rs = stmt.executeQuery();
            List<HistoryPesanan> histories = new ArrayList<>();
            while (rs.next()){
                HistoryPesanan hp = new HistoryPesanan();
                hp.setId(rs.getInt("id"));
                hp.setUser_id(userId);
                Timestamp timestamp = rs.getTimestamp("tanggal");
                hp.setTanggal(timestamp.toLocalDateTime());
                hp.setTotal_harga(rs.getDouble("amount"));

                histories.add(hp);
            }

            return histories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void displayHistoryTable(int userId){
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<HistoryPesanan> histories = getLatestHistory(userId);
        for(HistoryPesanan history:histories){
           Object[] obj = new Object[3];
           obj[0] = history.getId();
           obj[1] = history.getTanggal();
           obj[2] = history.getTotal_harga();

           dtm.addRow(obj);
        }

    }
    //history top up
    public void displayHistoryTopUpTable(int userId){
        htm.getDataVector().removeAllElements();
        htm.fireTableDataChanged();
        List<HistoryPesanan> histories = getLatestHistoryTopUp(userId);
        for(HistoryPesanan history:histories){
            Object[] obj = new Object[3];
            obj[0] = history.getId();
            obj[1] = history.getTanggal();
            obj[2] = history.getTotal_harga();

            dtm.addRow(obj);
        }

    }

    public List<DetailPesanan> getDetailByPesananId(int pesanan_id){
        String query = "SELECT dp.*, m.nama, m.harga " +
                "FROM detail_pesanan dp " +
                "JOIN menu m ON dp.menu_id = m.id " +
                "WHERE dp.pesanan_id = ?";
        try(Connection connection=Koneksi.koneksi();
        PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,pesanan_id);
            ResultSet rs = stmt.executeQuery();

            List<DetailPesanan> details = new ArrayList<>();
            while (rs.next()){
                DetailPesanan detail = new DetailPesanan();
                detail.setId(rs.getInt("id"));
                detail.setPesanan_id(rs.getInt("pesanan_id"));
                detail.setMenu_id(rs.getInt("menu_id"));
                detail.setKuantitas(rs.getInt("kuantitas"));
                detail.setSubtotal(rs.getDouble("subtotal"));
                detail.setTanggal(rs.getTimestamp("tanggal").toLocalDateTime());

                Menu menu = new Menu();
                menu.setId(rs.getInt("menu_id"));
                menu.setNama_menu(rs.getString("nama"));
                menu.setHarga(rs.getDouble("harga"));

                detail.setMenu(menu);
                details.add(detail);
            }
            return details;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    List<DetailPesanan> details = getDetailByPesananId(5);
//for (DetailPesanan detail : details) {
//        System.out.println("Nama Menu: " + detail.getMenu().getNama_menu());
//        System.out.println("Harga: " + detail.getMenu().getHarga());
//        System.out.println("Jumlah: " + detail.getJumlah());
//        System.out.println("Subtotal: " + detail.getSubtotal());
//    }

}
