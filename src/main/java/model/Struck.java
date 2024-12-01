package model;

import java.time.LocalDateTime;
import java.util.List;

public class Struck {
    private int id;
    List<Menu> menus;
    private List<Integer> kuantitas;
    private LocalDateTime tanggal;
    private double total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Integer> getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(List<Integer> kuantitas) {
        this.kuantitas = kuantitas;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
