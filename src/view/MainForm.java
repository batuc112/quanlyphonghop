package view;

import dao.DatPhongDAO;
import dao.PhongHopDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.Time;

public class MainForm extends JFrame {

    JTable tblPhong;
    DefaultTableModel modelPhong;

    JTextField txtNgay;
    JComboBox<Integer> cbBDGio, cbBDPhut;
    JComboBox<Integer> cbKTGio, cbKTPhut;
    JButton btnDat, btnSua, btnXoa;

    String role;
    String username;

    public MainForm(String role, String username) {
        this.role = role;
        this.username = username;

        setTitle("Quản lý phòng họp - " + role + " (" + username + ")");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // ===== BẢNG PHÒNG =====
        modelPhong = new DefaultTableModel(
                new String[]{"Mã phòng", "Tên phòng", "Sức chứa", "Giá"}, 0
        );
        tblPhong = new JTable(modelPhong);
        JScrollPane sp = new JScrollPane(tblPhong);
        sp.setBounds(20, 20, 900, 220);
        add(sp);

        loadPhong();

        // ===== ĐẶT PHÒNG =====
        JLabel lbNgay = new JLabel("Ngày (yyyy-mm-dd)");
        lbNgay.setBounds(20, 260, 180, 25);
        add(lbNgay);

        txtNgay = new JTextField();
        txtNgay.setBounds(200, 260, 200, 25);
        add(txtNgay);

        JLabel lbBD = new JLabel("Giờ bắt đầu");
        lbBD.setBounds(20, 300, 180, 25);
        add(lbBD);

        cbBDGio = new JComboBox<>();
        cbBDPhut = new JComboBox<>();

        for (int h = 7; h <= 22; h++) cbBDGio.addItem(h);
        for (int p = 0; p < 60; p += 15) cbBDPhut.addItem(p);

        cbBDGio.setBounds(200, 300, 60, 25);
        cbBDPhut.setBounds(270, 300, 60, 25);
        add(cbBDGio);
        add(cbBDPhut);

        JLabel lbKT = new JLabel("Giờ kết thúc");
        lbKT.setBounds(20, 340, 180, 25);
        add(lbKT);

        cbKTGio = new JComboBox<>();
        cbKTPhut = new JComboBox<>();

        for (int h = 7; h <= 22; h++) cbKTGio.addItem(h);
        for (int p = 0; p < 60; p += 15) cbKTPhut.addItem(p);

        cbKTGio.setBounds(200, 340, 60, 25);
        cbKTPhut.setBounds(270, 340, 60, 25);
        add(cbKTGio);
        add(cbKTPhut);

        btnDat = new JButton("Đặt phòng");
        btnDat.setBounds(420, 300, 150, 40);
        add(btnDat);

        btnDat.addActionListener(e -> datPhong());

        // ===== ADMIN =====
        if (role.equals("admin")) {

            btnSua = new JButton("Sửa phòng");
            btnXoa = new JButton("Xóa phòng");

            btnSua.setBounds(600, 260, 140, 35);
            btnXoa.setBounds(600, 310, 140, 35);

            add(btnSua);
            add(btnXoa);

            btnSua.addActionListener(e -> suaPhong());
            btnXoa.addActionListener(e -> xoaPhong());

            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("Admin");

            JMenuItem them = new JMenuItem("Thêm phòng");
            them.addActionListener(e -> new ThemPhongForm(this).setVisible(true));

            JMenuItem bc = new JMenuItem("Báo cáo");
            bc.addActionListener(e -> moBaoCao());

            menu.add(them);
            menu.add(bc);
            bar.add(menu);
            setJMenuBar(bar);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // ===== LOAD PHÒNG =====
    void loadPhong() {
        modelPhong.setRowCount(0);
        for (String[] p : new PhongHopDAO().getAllPhong()) {
            modelPhong.addRow(p);
        }
    }

    // ===== ĐẶT PHÒNG =====
    void datPhong() {

        int row = tblPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn phòng trước");
            return;
        }

        try {
            String maPhong = tblPhong.getValueAt(row, 0).toString();
            Date ngay = Date.valueOf(txtNgay.getText());

            Time gioBD = Time.valueOf(
                    String.format("%02d:%02d:00",
                            cbBDGio.getSelectedItem(),
                            cbBDPhut.getSelectedItem())
            );

            Time gioKT = Time.valueOf(
                    String.format("%02d:%02d:00",
                            cbKTGio.getSelectedItem(),
                            cbKTPhut.getSelectedItem())
            );

            if (!gioKT.after(gioBD)) {
                JOptionPane.showMessageDialog(this,
                        "Giờ kết thúc phải sau giờ bắt đầu");
                return;
            }

            boolean ok = new DatPhongDAO().datPhong(
                    maPhong, ngay, gioBD, gioKT, username
            );

            JOptionPane.showMessageDialog(
                    this,
                    ok ? "Đặt phòng thành công"
                       : "Trùng lịch, phòng đã có người đặt"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ");
        }
    }

    // ===== SỬA PHÒNG =====
    void suaPhong() {
        int row = tblPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn phòng cần sửa");
            return;
        }

        String ma = tblPhong.getValueAt(row, 0).toString();

        if (new DatPhongDAO().phongDaCoLich(ma)) {
            JOptionPane.showMessageDialog(this,
                    "Phòng đã có lịch, không được sửa");
            return;
        }

        try {
            String ten = JOptionPane.showInputDialog("Tên phòng mới");
            int suc = Integer.parseInt(
                    JOptionPane.showInputDialog("Sức chứa"));
            double gia = Double.parseDouble(
                    JOptionPane.showInputDialog("Giá"));

            new PhongHopDAO().suaPhong(ma, ten, suc, gia);
            loadPhong();
            JOptionPane.showMessageDialog(this, "Đã sửa phòng");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ");
        }
    }

    // ===== XÓA PHÒNG =====
    void xoaPhong() {
        int row = tblPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn phòng cần xóa");
            return;
        }

        String ma = tblPhong.getValueAt(row, 0).toString();

        if (new DatPhongDAO().phongDaCoLich(ma)) {
            JOptionPane.showMessageDialog(this,
                    "Phòng đã có lịch, không được xóa");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                this, "Xóa phòng này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {

            new PhongHopDAO().xoaPhong(ma);
            loadPhong();
        }
    }

    // ===== BÁO CÁO =====
    void moBaoCao() {
        JFrame f = new JFrame("Báo cáo");
        f.setSize(800, 500);
        f.setLocationRelativeTo(this);
        f.setContentPane(new BaoCaoForm());
        f.setVisible(true);
    }
}
