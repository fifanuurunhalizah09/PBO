package backend;

import java.util.ArrayList;
import java.sql.*;

public class kategori {
    
    private int idkategori;
    private String nama;
    private String keterangan;

    public kategori() {
    }

    public kategori(String nama, String keterangan) {
        this.nama = nama;
        this.keterangan = keterangan;
    }

    public int getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(int idkategori) {
        this.idkategori = idkategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public kategori getById(int id) {
        kategori kat = new kategori();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM kategori WHERE idkategori = '" + id + "'");
        try {
            while (rs.next()) {
                kat = new kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kat;
    }

    public ArrayList<kategori> getAll() {
        ArrayList<kategori> ListKategori = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM kategori");

        try {
            while (rs.next()) {
                kategori kat = new kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));

                ListKategori.add(kat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListKategori;
    }

    public ArrayList<kategori> search(String keyword) {
        ArrayList<kategori> ListKategori = new ArrayList<>();

        String sql = "SELECT * FROM kategori WHERE "
                   + " nama LIKE '%" + keyword + "%' "
                   + " OR keterangan LIKE '%" + keyword + "%'";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                kategori kat = new kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));

                ListKategori.add(kat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListKategori;
    }

    public void save() {
        if (getById(idkategori).getIdkategori() == 0) {
            String SQL = "INSERT INTO kategori (nama, keterangan) VALUES ("
                       + " '" + this.nama + "', "
                       + " '" + this.keterangan + "' "
                       + ")";
            this.idkategori = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE kategori SET "
                       + " nama = '" + this.nama + "', "
                       + " keterangan = '" + this.keterangan + "' "
                       + " WHERE idkategori = '" + this.idkategori + "'";
            DBHelper.executeQuery(SQL);
        }
    }

    public void delete() {
        String SQL = "DELETE FROM kategori WHERE idkategori = '" + this.idkategori + "'";
        DBHelper.executeQuery(SQL);
    }
}
