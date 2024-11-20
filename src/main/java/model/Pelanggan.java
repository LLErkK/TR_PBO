package model;

public class Pelanggan {
    private String nama_pelanggan;
    private String no_telepon;
    private String gender;

    public Pelanggan() {
    }

    public Pelanggan(String nama_pelanggan, String no_telepon, String gender) {
        this.nama_pelanggan = nama_pelanggan;
        this.no_telepon = no_telepon;
        this.gender = gender;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
