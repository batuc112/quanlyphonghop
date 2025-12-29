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
    JComboBox<String> cbBD, cbKT;
    JButton btnDat, btnSua, btnXoa;

    String role;
    String username;

    public MainForm(String role, String username) {
        this.role = role;
        this.username = username;

        setTitle("Quản lý phòng họp - " + role);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // ===== BẢNG PHÒNG =====
        modelPhong = new DefaultTableModel(
                new String[]{"Mã phòng", "Tên phòng", "Sức chứa", "Giá"}, 0
        );
        tblPhong = new JTable(modelPhong);
        JScrollPane sp = new JScrollPane(tblPhong);
        sp.setBounds(20, 20, 840, 200);
        add(sp);

        loadPhong();

        // ===== KHU ĐẶT PHÒNG =====
        JLabel lbNgay = new JLabel("Ngày (yyyy-mm-dd)");
        lbNgay.setBounds(20, 240, 200, 25);
        add(lbNgay);

        txtNgay = new JTextField();
        txtNgay.setBounds(230, 240, 200, 25);
        add(txtNgay);

        JLabel lbBD = new JLabel("Giờ bắt đầu");
        lbBD.setBounds(20, 280, 200, 25);
        add(lbBD);

        JLabel lbKT = new JLabel("Giờ kết thúc");
        lbKT.setBounds(20, 320, 200, 25);
        add(lbKT);

        cbBD = new JComboBox<>();
        cbKT = new JComboBox<>();

        // khung giờ 07:00 → 22:00
        for (int h = 7; h <= 22; h++) {
            cbBD.addItem(String.format("%02d:00:00", h));
            cbKT.addItem(String.format("%02d:00:00", h));
        }

        cbBD.setBounds(230, 280, 120, 25);
        cbKT.setBounds(230, 320, 120, 25);

        add(cbBD);
        add(cbKT);

        btnDat = new JButton("Đặt phòng");
        btnDat.setBounds(400, 290, 150, 35);
        add(btnDat);

        btnDat.addActionListener(e -> datPhong());

        // ===== ADMIN =====
        if (role.equals("admin")) {
            btnSua = new JButton("Sửa phòng");
            btnXoa = new JButton("Xóa phòng");

            btnSua.setBounds(600, 240, 120, 30);
            btnXoa.setBounds(600, 280, 120, 30);

            add(btnSua);
            add(btnXoa);

            btnSua.addActionListener(e -> suaPhong());
            btnXoa.addActionListener(e -> xoaPhong());

            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("Admin");

            JMenuItem them = new JMenuItem("Thêm phòng");
            them.addActionListener(e -> new ThemPhongForm(this).setVisible(true));

            JMenuItem bc = new JMenuItem("Báo cáo");
            bc.addActionListener(e -> new BaoCaoForm().setVisible(true));

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

    // ===== ĐẶT PHÒNG (FIX TRÙNG) =====
    void datPhong() {
        int row = tblPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn phòng trước");
            return;
        }

        try {
            String maPhong = tblPhong.getValueAt(row, 0).toString();
            Date ngay = Date.valueOf(txtNgay.getText());

            Time gioBD = Time.valueOf(cbBD.getSelectedItem().toString());
            Time gioKT = Time.valueOf(cbKT.getSelectedItem().toString());

            if (!gioKT.after(gioBD)) {
                JOptionPane.showMessageDialog(this, "Giờ kết thúc phải sau giờ bắt đầu");
                return;
            }

            boolean ok = new DatPhongDAO().datPhong(
                    maPhong, ngay, gioBD, gioKT, username
            );

            JOptionPane.showMessageDialog(
                    this,
                    ok ? "Đặt phòng thành công" : "Trùng lịch, phòng đã có người đặt"
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

        try {
            String ma = tblPhong.getValueAt(row, 0).toString();
            String ten = JOptionPane.showInputDialog("Tên phòng mới");
            int suc = Integer.parseInt(JOptionPane.showInputDialog("Sức chứa"));
            double gia = Double.parseDouble(JOptionPane.showInputDialog("Giá"));

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

        if (JOptionPane.showConfirmDialog(
                this,
                "Xóa phòng này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {

            new PhongHopDAO().xoaPhong(ma);
            loadPhong();
            JOptionPane.showMessageDialog(this, "Đã xóa phòng");
        }
    }
}
