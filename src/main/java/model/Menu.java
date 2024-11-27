package model;

public class Menu {
    private int id;
    private String nama_menu;
    private String kategori;
    private double harga;
    private int stok;
    private String foto;

    public Menu() {

    }

    public Menu(String nama_menu, String kategori, double harga) {
        this.nama_menu = nama_menu;
        this.kategori = kategori;
        this.harga = harga;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
