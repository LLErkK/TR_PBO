package controller;

import model.Menu;
import model.User;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

//    public Menu getTopSales(){
//        int limit = 5;
//
//    }
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel keranjang = new DefaultTableModel();
    public DefaultTableModel createSearchTable(){
        dtm.addColumn("ID");
        dtm.addColumn("Nama");
        dtm.addColumn("Harga");
        dtm.addColumn("Tipe");

        return dtm;
    }
    public DefaultTableModel createKeranjangTable(){
        keranjang.addColumn("ID");
        keranjang.addColumn("Nama");
        keranjang.addColumn("Harga");
        keranjang.addColumn("Tipe");
        keranjang.addColumn("Jumlah");
        keranjang.addColumn("Total");

        return keranjang;
    }
    //mencari Menu berdasarkan nama
    public List<Menu> findMenuByName(String input){
        int limit = 10;
        String query ="SELECT * FROM menu WHERE nama LIKE ? LIMIT ?";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,"%"+input+"%");
            stmt.setInt(2,limit);

            ResultSet rs = stmt.executeQuery();
            List<Menu> menus = new ArrayList<>();
            while (rs.next()){
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNama_menu(rs.getString("nama"));
                menu.setHarga(rs.getDouble("harga"));
                menu.setStok(rs.getInt("stok"));
                menu.setFoto(rs.getString("foto"));
                menu.setKategori(rs.getString("tipe"));

                menus.add(menu);
            }

            return menus;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void tampilkanSearchTable(String input){
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<Menu> menus = findMenuByName(input);
        for(Menu menu:menus){
            Object[] obj = new Object[4];
            obj[0] = menu.getId();
            obj[1] = menu.getNama_menu();
            obj[2] = menu.getHarga();
            obj[3] = menu.getKategori();

            dtm.addRow(obj);
        }

    }
    //cara memindahkan data dari table searchtable ke keranjang
    //ketika row diklik maka akan mengambil row id
    //dari id kita ambil data menu
    //kemudian add row ke keranjang
    //contoh di view:
//    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
//        //koversi ke dtm
//        DefaultTableModel dtm2 = (DefaultTableModel) this.jTable2.getModel();
//
//        int pilih = this.jTable2.getSelectedRow();
//        int id = dtm2.getValueAt(pilih,0);
//        OrderController oc = new OrderController();
//        Menu item = oc.getMenuById(id);
//       //sekrang menambahkan ketablenya
//        oc.tambahItemKeranjang(item);
//    }

    public void tampilkanKeranjangTable(){
        keranjang.getDataVector().removeAllElements();
        keranjang.fireTableDataChanged();
    }
    //menambahkan ke table keranjang
    public void tambahItemKeranjang(Menu item) {
        boolean itemExists = false;

        // Iterasi untuk memeriksa apakah item sudah ada di keranjang
        for (int i = 0; i < keranjang.getRowCount(); i++) {
            if (keranjang.getValueAt(i, 1).equals(item.getNama_menu())) { // Cek nama menu
                // Update jumlah dan subtotal
                int jumlah = (int) keranjang.getValueAt(i, 4) + 1; // Ambil jumlah saat ini dan tambah 1
                double subtotal = jumlah * item.getHarga(); // Hitung subtotal baru
                keranjang.setValueAt(jumlah, i, 4); // Update jumlah
                keranjang.setValueAt(subtotal, i, 5); // Update subtotal
                itemExists = true;
                break;
            }
        }

        // Jika item belum ada di keranjang
        if (!itemExists) {
            Object[] obj = new Object[6];
            obj[0] = item.getId(); // ID
            obj[1] = item.getNama_menu(); // Nama Menu
            obj[2] = item.getHarga(); // Harga
            obj[3] = item.getKategori(); // Kategori
            obj[4] = 1; // Jumlah (default 1)
            obj[5] = item.getHarga(); // Subtotal (harga * jumlah, default sama dengan harga)
            keranjang.addRow(obj); // Tambahkan item baru ke tabel keranjang
        }
    }

    //mendapatkan data menu pada baris yang diklik
    public Menu getMenuById(int id){
        String query = "SELECT * FROM menu WHERE id = ?";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Menu model = new Menu();
                model.setId(rs.getInt("id"));
                model.setNama_menu(rs.getString("nama"));
                model.setHarga(rs.getDouble("harga"));
                return model;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void resetKeranjang(){
        keranjang.getDataVector().removeAllElements();
        keranjang.fireTableDataChanged();
    }

    public double getTotalKeranjang(){
        if(keranjang.getRowCount()==0){
           return 0;
        }
        double total=0;
        double tambah =0;
        for(int i =0; i<keranjang.getRowCount();i++){
            tambah = (double)keranjang.getValueAt(i,5);
            total+=tambah;
        }
        return total;
    }

    //buat method ketika klik bayar
    //validas saldo apakah cukup
    //jika cukup maka akan dikurangi
    //membuat
}
