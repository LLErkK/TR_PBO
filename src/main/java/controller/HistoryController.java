package controller;

import model.HistoryPesanan;
import model.Menu;
import model.Struck;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HistoryController {
    public DefaultTableModel createHistoryTable(){
        DefaultTableModel dtm = new DefaultTableModel();
        if(dtm.getColumnCount()==0){
            dtm.addColumn("ID");
            dtm.addColumn("Tanggal");
            dtm.addColumn("Total");
        }
        return dtm;
    }
    public DefaultTableModel createHistoryTopUpTable(){
        DefaultTableModel dtm = new DefaultTableModel();
        if(dtm.getColumnCount()==0){
            dtm.addColumn("ID");
            dtm.addColumn("Tanggal");
            dtm.addColumn("Total");
        }
        return dtm;
    }

    public List<HistoryPesanan> getAllPesananByUserId(int id){
        String sql = "SELECT * FROM pesanan WHERE user_id = ?";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            List<HistoryPesanan> histories = new ArrayList<>();
            while (rs.next()){
                HistoryPesanan hp = new HistoryPesanan();
                hp.setId(rs.getInt("id"));
                hp.setUser_id(rs.getInt("user_id"));
                Timestamp timestamp = rs.getTimestamp("tanggal");
                if (timestamp != null) {
                    hp.setTanggal(timestamp.toLocalDateTime());
                }
                hp.setTotal_harga(rs.getDouble("total"));

                histories.add(hp);
            }
            return histories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<HistoryPesanan> getAllTopUpByUserId(int id){
        String sql = "SELECT * FROM history_topup WHERE user_id = ?";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            List<HistoryPesanan> histories = new ArrayList<>();
            while (rs.next()){
                HistoryPesanan hp = new HistoryPesanan();
                hp.setId(rs.getInt("id"));
                hp.setUser_id(rs.getInt("user_id"));
                Timestamp timestamp = rs.getTimestamp("tanggal");
                if (timestamp != null) {
                    hp.setTanggal(timestamp.toLocalDateTime());
                }
                hp.setTotal_harga(rs.getDouble("amount"));

                histories.add(hp);
            }
            return histories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DefaultTableModel modelTable(int user_id){
        DefaultTableModel dtm = createHistoryTable();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<HistoryPesanan> Hisroris= getAllPesananByUserId(user_id);
        for(HistoryPesanan histori:Hisroris){
            Object[] obj = new Object[3];
            obj[0] = histori.getId();
            obj[1] = histori.getTanggal();
            obj[2] = histori.getTotal_harga();


            dtm.addRow(obj);
        }
        return dtm;
    }

    public DefaultTableModel modelTableTopUp(int user_id){
        DefaultTableModel dtm = createHistoryTopUpTable();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<HistoryPesanan> Hisroris= getAllTopUpByUserId(user_id);
        for(HistoryPesanan histori:Hisroris){
            Object[] obj = new Object[3];
            obj[0] = histori.getId();
            obj[1] = histori.getTanggal();
            obj[2] = histori.getTotal_harga();


            dtm.addRow(obj);
        }
        return dtm;
    }

    public Struck getDataByPesananId(int pesanan_id) {
        String sql = "SELECT " +
                "    menu.id AS menu_id, " +
                "    menu.nama AS nama_menu, " +
                "    menu.harga AS harga_menu, " +
                "    detail_pesanan.kuantitas AS kuantitas, " +
                "    pesanan.total AS total_pesanan, " +
                "    pesanan.tanggal AS tanggal " +
                "FROM " +
                "    detail_pesanan " +
                "INNER JOIN menu ON detail_pesanan.menu_id = menu.id " +
                "INNER JOIN pesanan ON detail_pesanan.pesanan_id = pesanan.id " +
                "WHERE " +
                "    pesanan.id = ?;";

        try (Connection connection = Koneksi.koneksi();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, pesanan_id);

            try (ResultSet rs = stmt.executeQuery()) {
                Struck struck = new Struck();
                List<Menu> menus = new ArrayList<>();
                List<Integer> kuantitas = new ArrayList<>();
                boolean isFirstRow = true;

                while (rs.next()) {
                    // Set data menu
                    Menu menu = new Menu();
                    menu.setId(rs.getInt("menu_id"));
                    menu.setNama_menu(rs.getString("nama_menu"));
                    menu.setHarga(rs.getDouble("harga_menu"));
                    menus.add(menu);

                    // Tambahkan kuantitas ke dalam daftar
                    kuantitas.add(rs.getInt("kuantitas"));

                    // Ambil data total dan tanggal hanya sekali
                    if (isFirstRow) {
                        struck.setId(pesanan_id);
                        struck.setTotal(rs.getDouble("total_pesanan"));

                        java.sql.Timestamp timestamp = rs.getTimestamp("tanggal");
                        if (timestamp != null) {
                            struck.setTanggal(timestamp.toLocalDateTime());
                        }
                        isFirstRow = false;
                    }
                }

                // Set daftar menu dan kuantitas ke dalam Struck
                struck.setMenus(menus);
                struck.setKuantitas(kuantitas);

                return struck;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving data", e);
        }
    }


}
