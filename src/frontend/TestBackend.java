package frontend;

import backend.*;

public class TestBackend {
    public static void main(String[] args) {
        kategori kat1 = new kategori("Novel", "Koleksi buku novel");
        kategori kat2 = new kategori("Referensi", "Buku referensi ilmiah");
        kategori kat3 = new kategori("Komik", "Komik anak-anak");

        kat1.save();
        kat2.save();
        kat3.save();

        kat2.setKeterangan("Koleksi buku referensi ilmiah");
        kat2.save();

        kat3.delete();

        for (kategori k : new kategori().getAll()) {
            System.out.println("Nama: " + k.getNama() + ", Ket: " + k.getKeterangan());
        }

        for (kategori k : new kategori().search("ilmiah")) {
            System.out.println("Nama: " + k.getNama() + ", Ket: " + k.getKeterangan());
        }
    }
}
