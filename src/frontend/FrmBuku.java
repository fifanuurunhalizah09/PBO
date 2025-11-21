package frontend;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import backend.Buku;
import backend.kategori;

public class FrmBuku extends JFrame {

    private JTextField txtId, txtJudul, txtPenerbit, txtPenulis, txtCari;
    private JComboBox<kategori> cmbKategori;
    private JButton btnSimpan, btnHapus, btnTambah, btnCari;
    private JTable tblBuku;
    private JScrollPane scrollPane;

    public FrmBuku() {
        initComponents();
        txtId.setEditable(false);

        tampilkanCmbKategori();
        tampilkanData();
        kosongkanForm();
    }

    // ============================== FORM FUNCTION ==============================

    private void kosongkanForm() {
        txtId.setText("0");
        txtJudul.setText("");
        txtPenerbit.setText("");
        txtPenulis.setText("");
        if (cmbKategori.getItemCount() > 0)
            cmbKategori.setSelectedIndex(0);
    }

    private void tampilkanCmbKategori() {
        cmbKategori.removeAllItems();
        for (kategori k : new kategori().getAll()) {
            cmbKategori.addItem(k);
        }
    }

    private void tampilkanData() {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);

        for (Buku b : new Buku().getAll()) {
            Object[] row = {
                b.getIdbuku(),
                b.getKategori().getNama(),
                b.getJudul(),
                b.getPenulis(),
                b.getPenerbit()
            };
            model.addRow(row);
        }

        tblBuku.setModel(model);
    }

    private void cari(String keyword) {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);

        for (Buku b : new Buku().search(keyword)) {
            Object[] row = {
                b.getIdbuku(),
                b.getKategori().getNama(),
                b.getJudul(),
                b.getPenulis(),
                b.getPenerbit()
            };
            model.addRow(row);
        }

        tblBuku.setModel(model);
    }

    // ============================== EVENT ==============================

    private void simpan() {
        Buku b = new Buku();
        b.setIdbuku(Integer.parseInt(txtId.getText()));
        b.setKategori((kategori) cmbKategori.getSelectedItem());
        b.setJudul(txtJudul.getText());
        b.setPenulis(txtPenulis.getText());
        b.setPenerbit(txtPenerbit.getText());
        b.save();

        txtId.setText(String.valueOf(b.getIdbuku()));
        tampilkanData();
    }

    private void hapus() {
        int row = tblBuku.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(tblBuku.getValueAt(row, 0).toString());
            Buku b = new Buku().getById(id);
            b.delete();
            tampilkanData();
            kosongkanForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus dulu!");
        }
    }

    private void isiFormDariTabel() {
        int row = tblBuku.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(tblBuku.getValueAt(row, 0).toString());
            Buku b = new Buku().getById(id);

            txtId.setText(String.valueOf(b.getIdbuku()));
            cmbKategori.setSelectedItem(b.getKategori());
            txtJudul.setText(b.getJudul());
            txtPenulis.setText(b.getPenulis());
            txtPenerbit.setText(b.getPenerbit());
        }
    }

    // ============================== UI ==============================

    private void initComponents() {
        setTitle("Form Buku");
        setSize(750, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblId = new JLabel("ID Buku");
        JLabel lblKategori = new JLabel("Kategori");
        JLabel lblJudul = new JLabel("Judul");
        JLabel lblPenulis = new JLabel("Penulis");
        JLabel lblPenerbit = new JLabel("Penerbit");

        txtId = new JTextField("0");
        txtJudul = new JTextField();
        txtPenulis = new JTextField();
        txtPenerbit = new JTextField();
        txtCari = new JTextField();

        cmbKategori = new JComboBox<>();

        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambah = new JButton("Tambah Baru");
        btnCari = new JButton("Cari");

        tblBuku = new JTable();
        scrollPane = new JScrollPane(tblBuku);

        // Layout posisi (sama gaya dengan form sebelumnya)
        lblId.setBounds(20, 20, 100, 25);
        txtId.setBounds(120, 20, 100, 25);

        lblKategori.setBounds(20, 60, 100, 25);
        cmbKategori.setBounds(120, 60, 180, 25);

        lblJudul.setBounds(20, 100, 100, 25);
        txtJudul.setBounds(120, 100, 300, 25);

        lblPenulis.setBounds(20, 140, 100, 25);
        txtPenulis.setBounds(120, 140, 200, 25);

        lblPenerbit.setBounds(20, 180, 100, 25);
        txtPenerbit.setBounds(120, 180, 200, 25);

        btnSimpan.setBounds(20, 230, 100, 30);
        btnTambah.setBounds(140, 230, 130, 30);
        btnHapus.setBounds(290, 230, 100, 30);

        txtCari.setBounds(420, 230, 150, 30);
        btnCari.setBounds(580, 230, 80, 30);

        scrollPane.setBounds(20, 280, 700, 260);

        add(lblId); add(txtId);
        add(lblKategori); add(cmbKategori);
        add(lblJudul); add(txtJudul);
        add(lblPenulis); add(txtPenulis);
        add(lblPenerbit); add(txtPenerbit);

        add(btnSimpan);
        add(btnTambah);
        add(btnHapus);
        add(txtCari);
        add(btnCari);

        add(scrollPane);

        // Event
        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnTambah.addActionListener(e -> kosongkanForm());
        btnCari.addActionListener(e -> cari(txtCari.getText()));
        tblBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                isiFormDariTabel();
            }
        });
    }

    public static void main(String[] args) {
        new FrmBuku().setVisible(true);
    }
}