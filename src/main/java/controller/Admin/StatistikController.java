package controller.Admin;

import controller.Koneksi;
import model.Menu;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.sql.Date;

public class StatistikController {

    public List<Menu> getTopSales(Date awal, Date akhir) {
        String sql = """
            SELECT 
                m.nama AS nama_menu,
                m.harga AS harga_menu,
                SUM(dp.kuantitas) AS total_terjual
            FROM 
                detail_pesanan dp
            JOIN 
                menu m ON dp.menu_id = m.id
            WHERE 
                dp.tanggal BETWEEN ? AND ?
            GROUP BY 
                dp.menu_id
            ORDER BY 
                total_terjual DESC
            LIMIT 5;
            """;
        try (Connection connection = Koneksi.koneksi();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, awal);
            stmt.setDate(2, akhir);

            ResultSet rs = stmt.executeQuery();
            List<Menu> topSales = new ArrayList<>();
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setNama_menu(rs.getString("nama_menu"));
                menu.setHarga(rs.getDouble("harga_menu"));
                menu.setTerjual(rs.getInt("total_terjual")); // Perbaikan pada kolom `total_terjual`
                topSales.add(menu);
            }
            return topSales;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Menu> getLowestSales(Date awal, Date akhir) {
        String sql = """
            SELECT 
                m.nama AS nama_menu,
                m.harga AS harga_menu,
                SUM(dp.kuantitas) AS total_terjual
            FROM 
                detail_pesanan dp
            JOIN 
                menu m ON dp.menu_id = m.id
            WHERE 
                dp.tanggal BETWEEN ? AND ?
            GROUP BY 
                dp.menu_id
            ORDER BY 
                total_terjual ASC
            LIMIT 5;
            """;
        try (Connection connection = Koneksi.koneksi();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, awal);
            stmt.setDate(2, akhir);

            ResultSet rs = stmt.executeQuery();
            List<Menu> lowestSales = new ArrayList<>();
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setNama_menu(rs.getString("nama_menu"));
                menu.setHarga(rs.getDouble("harga_menu"));
                menu.setTerjual(rs.getInt("total_terjual")); // Perbaikan pada kolom `total_terjual`
                lowestSales.add(menu);
            }
            return lowestSales;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getOmset(java.util.Date awal, java.util.Date akhir) {
        String sql = """
                SELECT 
                    SUM(dp.subtotal) AS total_omset
                FROM 
                    detail_pesanan dp
                WHERE 
                    dp.tanggal BETWEEN ? AND ?;
                """;
        try (Connection connection = Koneksi.koneksi();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Konversi java.util.Date ke java.sql.Date
            stmt.setDate(1, new Date(awal.getTime()));
            stmt.setDate(2, new Date(akhir.getTime()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_omset"); // Mengembalikan total omzet
            } else {
                return 0.0; // Jika tidak ada data, omzet adalah 0
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
