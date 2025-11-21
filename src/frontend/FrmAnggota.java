package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmAnggota extends JFrame {
    private JTextField txtId, txtNama, txtAlamat, txtTelepon, txtCari;
    private JButton btnSimpan, btnHapus, btnTambah, btnCari;
    private JTable tblAnggota;
    private JScrollPane scrollPane;

    public FrmAnggota() {
        initComponents();
        txtId.setEditable(false);
        kosongkanForm();
        tampilkanData();
    }

    public void kosongkanForm() {
        txtId.setText("0");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().getAll();

        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, kolom);
        tblAnggota.setModel(model);

        for (Anggota a : list) {
            Object[] row = {
                a.getIdanggota(),
                a.getNama(),
                a.getAlamat(),
                a.getTelepon(),
            };
            model.addRow(row);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().search(keyword);

        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, kolom);
        tblAnggota.setModel(model);

        for (Anggota a : list) {
            Object[] row = {
                a.getIdanggota(),
                a.getNama(),
                a.getAlamat(),
                a.getTelepon(),
            };
            model.addRow(row);
        }
    }

    private void btnSimpanActionPerformed() {
        Anggota a = new Anggota();
        a.setIdanggota(Integer.parseInt(txtId.getText()));
        a.setNama(txtNama.getText());
        a.setAlamat(txtAlamat.getText());
        a.setTelepon(txtTelepon.getText());
        a.save();

        txtId.setText(Integer.toString(a.getIdanggota()));
        tampilkanData();
    }

    private void btnHapusActionPerformed() {
        int row = tblAnggota.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(tblAnggota.getModel().getValueAt(row, 0).toString());
            Anggota a = new Anggota().getById(id);
            a.delete();
            kosongkanForm();
            tampilkanData();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus!");
        }
    }

    private void btnTambahActionPerformed() {
        kosongkanForm();
    }

    private void btnCariActionPerformed() {
        cari(txtCari.getText());
    }

    private void tblAnggotaMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tblAnggota.getSelectedRow();

        txtId.setText(tblAnggota.getValueAt(row, 0).toString());
        txtNama.setText(tblAnggota.getValueAt(row, 1).toString());
        txtAlamat.setText(tblAnggota.getValueAt(row, 2).toString());
        txtTelepon.setText(tblAnggota.getValueAt(row, 3).toString());
    }

    private void initComponents() {
        setTitle("Form Anggota");
        setSize(650, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtId = new JTextField("0");
        txtNama = new JTextField();
        txtAlamat = new JTextField();
        txtTelepon = new JTextField();
        txtCari = new JTextField();

        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambah = new JButton("Tambah Baru");
        btnCari = new JButton("Cari");

        tblAnggota = new JTable();
        scrollPane = new JScrollPane(tblAnggota);

        JLabel lblId = new JLabel("ID Anggota");
        JLabel lblNama = new JLabel("Nama");
        JLabel lblAlamat = new JLabel("Alamat");
        JLabel lblTelepon = new JLabel("Telepon");

        // posisi komponen
        lblId.setBounds(20, 10, 100, 20);
        txtId.setBounds(20, 30, 120, 25);

        lblNama.setBounds(20, 60, 100, 20);
        txtNama.setBounds(20, 80, 200, 25);

        lblAlamat.setBounds(20, 110, 100, 20);
        txtAlamat.setBounds(20, 130, 200, 25);

        lblTelepon.setBounds(20, 160, 100, 20);
        txtTelepon.setBounds(20, 180, 200, 25);

        btnSimpan.setBounds(260, 30, 120, 25);
        btnHapus.setBounds(260, 60, 120, 25);
        btnTambah.setBounds(260, 90, 120, 25);

        txtCari.setBounds(20, 220, 200, 25);
        btnCari.setBounds(260, 220, 120, 25);

        scrollPane.setBounds(20, 260, 590, 130);

        add(lblId);
        add(lblNama);
        add(lblAlamat);
        add(lblTelepon);
        add(txtId);
        add(txtNama);
        add(txtAlamat);
        add(txtTelepon);
        add(btnSimpan);
        add(btnHapus);
        add(btnTambah);
        add(txtCari);
        add(btnCari);
        add(scrollPane);

        btnSimpan.addActionListener(e -> btnSimpanActionPerformed());
        btnHapus.addActionListener(e -> btnHapusActionPerformed());
        btnTambah.addActionListener(e -> btnTambahActionPerformed());
        btnCari.addActionListener(e -> btnCariActionPerformed());
        tblAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAnggotaMouseClicked(evt);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmAnggota().setVisible(true));
    }
}
