package controller;

import model.Menu;
import model.Struck;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StrukController {
    DefaultTableModel createMenuTable(){
        DefaultTableModel dtm = new DefaultTableModel();
        if(dtm.getColumnCount()==0){
            dtm.addColumn("ID");
            dtm.addColumn("Nama");
            dtm.addColumn("Harga");
            dtm.addColumn("Kuantitas");
        }
        return dtm;
    }

    public DefaultTableModel modelTable(Struck struck) {
        // Buat DefaultTableModel dengan kolom yang sesuai
        DefaultTableModel dtm = createMenuTable();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();

        // Ambil daftar menu dan kuantitas dari Struck
        List<Menu> menus = struck.getMenus();
        List<Integer> kuantitas = struck.getKuantitas();

        // Iterasi daftar menu dan kuantitas untuk menambahkan baris ke tabel
        for (int i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            Object[] obj = new Object[4];
            obj[0] = menu.getId();            // ID Menu
            obj[1] = menu.getNama_menu();          // Nama Menu
            obj[2] = menu.getHarga();         // Harga Menu
            obj[3] = kuantitas.get(i);        // Kuantitas sesuai index

            dtm.addRow(obj);
        }

        return dtm;
    }

}
