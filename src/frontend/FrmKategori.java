package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;

public class FrmKategori extends JFrame {
    private JTextField txtIdKategori, txtNama, txtKeterangan, txtCari;
    private JButton btnSimpan, btnHapus, btnTambahBaru, btnCari;
    private JTable tblKategori;
    private JScrollPane jScrollPane1;

    public FrmKategori() {
        initComponents();
        tampilkanData();
        kosongkanForm();
    }

    public void kosongkanForm() {
        txtIdKategori.setText("0");
        txtNama.setText("");
        txtKeterangan.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Nama", "Keterangan"};
        ArrayList<kategori> list = new kategori().getAll();
        Object rowData[] = new Object[3];

        tblKategori.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();
            rowData[2] = kat.getKeterangan();

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Nama", "Keterangan"};
        ArrayList<kategori> list = new kategori().search(keyword);
        Object rowData[] = new Object[3];

        tblKategori.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();
            rowData[2] = kat.getKeterangan();

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        kategori kat = new kategori();
        kat.setIdkategori(Integer.parseInt(txtIdKategori.getText()));
        kat.setNama(txtNama.getText());
        kat.setKeterangan(txtKeterangan.getText());
        kat.save();
        txtIdKategori.setText(Integer.toString(kat.getIdkategori()));
        tampilkanData();
    }

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
        int row = tblKategori.getSelectedRow();

        if (row >= 0) {
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            kategori kat = new kategori().getById(id);
            kat.delete();
            kosongkanForm();
            tampilkanData();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu!");
        }
    }

    private void btnTambahBaruActionPerformed(java.awt.event.ActionEvent evt) {
        kosongkanForm();
    }

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {
        cari(txtCari.getText());
    }

    private void tblKategoriMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
        int row = tblKategori.getSelectedRow();

        txtIdKategori.setText(model.getValueAt(row, 0).toString());
        txtNama.setText(model.getValueAt(row, 1).toString());
        txtKeterangan.setText(model.getValueAt(row, 2).toString());
    }

    private void initComponents() {
        txtIdKategori = new JTextField("0");
        txtNama = new JTextField();
        txtKeterangan = new JTextField();
        txtCari = new JTextField();
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");
        btnCari = new JButton("Cari");
        tblKategori = new JTable();
        jScrollPane1 = new JScrollPane(tblKategori);

        setTitle("Form Kategori");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(null);

        JLabel lblId = new JLabel("ID Kategori");
        JLabel lblNama = new JLabel("Nama Kategori");
        JLabel lblKeterangan = new JLabel("Keterangan");

        lblId.setBounds(20, 10, 100, 20);
        txtIdKategori.setBounds(20, 30, 100, 25);
        lblNama.setBounds(20, 60, 100, 20);
        txtNama.setBounds(20, 80, 200, 25);
        lblKeterangan.setBounds(20, 110, 100, 20);
        txtKeterangan.setBounds(20, 130, 200, 25);

        btnSimpan.setBounds(250, 30, 100, 25);
        btnHapus.setBounds(250, 60, 100, 25);
        btnTambahBaru.setBounds(250, 90, 100, 25);
        txtCari.setBounds(20, 170, 200, 25);
        btnCari.setBounds(250, 170, 100, 25);
        jScrollPane1.setBounds(20, 210, 550, 130);

        add(lblId);
        add(lblNama);
        add(lblKeterangan);
        add(txtIdKategori);
        add(txtNama);
        add(txtKeterangan);
        add(btnSimpan);
        add(btnHapus);
        add(btnTambahBaru);
        add(txtCari);
        add(btnCari);
        add(jScrollPane1);

        btnSimpan.addActionListener(e -> btnSimpanActionPerformed(e));
        btnHapus.addActionListener(e -> btnHapusActionPerformed(e));
        btnTambahBaru.addActionListener(e -> btnTambahBaruActionPerformed(e));
        btnCari.addActionListener(e -> btnCariActionPerformed(e));
        tblKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKategoriMouseClicked(evt);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmKategori().setVisible(true));
    }
}
