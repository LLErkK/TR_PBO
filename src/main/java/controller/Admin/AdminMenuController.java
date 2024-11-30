package controller.Admin;

import controller.Koneksi;
import model.Menu;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminMenuController {
    DefaultTableModel createMenuTable(){
        DefaultTableModel dtm = new DefaultTableModel();
        if(dtm.getColumnCount()==0){
            dtm.addColumn("ID");
            dtm.addColumn("Nama");
            dtm.addColumn("Harga");
            dtm.addColumn("Type");
        }
        return dtm;
    }

    public List<Menu> getAllMenu(){

        String query ="SELECT * FROM menu";
        try(Connection conn = Koneksi.koneksi();
            PreparedStatement stmt = conn.prepareStatement(query)){


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

    public DefaultTableModel modelTable(){
        DefaultTableModel dtm = createMenuTable();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        List<Menu> menus= getAllMenu();
        for(Menu menu:menus){
            Object[] obj = new Object[4];
            obj[0] = menu.getId();
            obj[1] = menu.getNama_menu();
            obj[2] = menu.getHarga();
            obj[3] = menu.getKategori();

            dtm.addRow(obj);
        }
        return dtm;
    }
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
                model.setKategori(rs.getString("tipe"));
                return model;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateMenuById(int id,String nama,String tipe,double harga){
        String sql = "UPDATE menu SET nama = ?, harga = ?, tipe = ? WHERE id = ?";

        try(Connection connection = Koneksi.koneksi();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,nama);
            stmt.setDouble(2,harga);
            stmt.setString(3,tipe);
            stmt.setInt(4,id);
            int rowsUpdate = stmt.executeUpdate();
            if(rowsUpdate>0){
                System.out.println("GG BERHASIL UPDATE");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean createMenu(String nama,String tipe,double harga){
        String sql = "INSERT INTO menu (nama,harga,tipe) VALUES (?,?,?)";
        try(Connection connection = Koneksi.koneksi();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,nama);
            stmt.setDouble(2,harga);
            stmt.setString(3,tipe);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
