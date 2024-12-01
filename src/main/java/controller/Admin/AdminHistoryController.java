package controller.Admin;

import controller.Koneksi;
import model.HistoryPesanan;
import model.Menu;
import model.Struck;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminHistoryController {
    DefaultTableModel createHistoryMenu(){
        DefaultTableModel dtm = new DefaultTableModel();
        if(dtm.getColumnCount()==0){
            dtm.addColumn("ID");
            dtm.addColumn("User ID");
            dtm.addColumn("Total");
            dtm.addColumn("Tanggal");
        }
        return dtm;
    }
    DefaultTableModel createHistoryTopUp(){
        DefaultTableModel dtm = new DefaultTableModel();
        if(dtm.getColumnCount()==0){
            dtm.addColumn("ID");
            dtm.addColumn("User ID");
            dtm.addColumn("Ammount");
            dtm.addColumn("Tanggal");
        }
        return dtm;
    }

    public List<HistoryPesanan> getAllPesanan(){
        String sql = "SELECT * FROM pesanan";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            List<HistoryPesanan> histories = new ArrayList<>();
            while (rs.next()){
                HistoryPesanan hp = new HistoryPesanan();
                hp.setId(rs.getInt("id"));
                hp.setUser_id(rs.getInt("user_id"));
                hp.setTotal_harga(rs.getDouble("total"));
                Timestamp timestamp = rs.getTimestamp("tanggal");
                hp.setTanggal(timestamp.toLocalDateTime());

                histories.add(hp);
            }
            return histories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //ini untuk yang top up,pakai model yang sama karena strukturnya sama
    public List<HistoryPesanan> getAllTopUp(){
        String sql = "SELECT * FROM history_topup";
        try(Connection connection = Koneksi.koneksi();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            List<HistoryPesanan> histories = new ArrayList<>();
            while (rs.next()){
                HistoryPesanan hp = new HistoryPesanan();
                hp.setId(rs.getInt("id"));
                hp.setUser_id(rs.getInt("user_id"));
                hp.setTotal_harga(rs.getDouble("amount"));
                Timestamp timestamp = rs.getTimestamp("tanggal");
                hp.setTanggal(timestamp.toLocalDateTime());

                histories.add(hp);
            }
            return histories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DefaultTableModel modelHistoryPesanan(){
        DefaultTableModel dtm = createHistoryMenu();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<HistoryPesanan> historis = getAllPesanan();
        for (HistoryPesanan histori:historis){
            Object[] obj = new Object[4];
            obj[0] = histori.getId();
            obj[1] = histori.getUser_id();
            obj[2] = histori.getTotal_harga();
            obj[3] = histori.getTanggal();

            dtm.addRow(obj);
        }
        return dtm;
    }
    public DefaultTableModel modelHistoryTopUp(){
        DefaultTableModel dtm = createHistoryTopUp();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<HistoryPesanan> historis = getAllTopUp();
        for (HistoryPesanan histori:historis){
            Object[] obj = new Object[4];
            obj[0] = histori.getId();
            obj[1] = histori.getUser_id();
            obj[2] = histori.getTotal_harga();
            obj[3] = histori.getTanggal();

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
