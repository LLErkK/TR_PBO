package model;

import java.time.LocalDateTime;

public class DetailPesanan {
    private int id;
    private int pesanan_id;
    private int menu_id;
    private int kuantitas;
    private double subtotal;
    private LocalDateTime tanggal;
    private Menu menu;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPesanan_id() {
        return pesanan_id;
    }

    public void setPesanan_id(int pesanan_id) {
        this.pesanan_id = pesanan_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
