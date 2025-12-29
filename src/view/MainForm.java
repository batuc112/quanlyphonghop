package view;

import dao.DatPhongDAO;
import dao.PhongHopDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class MainForm extends JFrame {


    JTable tblPhong, tblLich;
    DefaultTableModel modelPhong, modelLich;


    JComboBox<Integer> cbNgay, cbThang, cbBDGio, cbBDPhut, cbKTGio, cbKTPhut;
    JTextField txtNam;
    JButton btnDat,btnLogout;


    String role, username;

    public MainForm(String role, String username) {
        this.role = role;
        this.username = username;

        setTitle("Quản lý phòng họp - " + role);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(null);

        modelPhong = new DefaultTableModel(
                new String[]{"Mã", "Tên", "Sức chứa", "Giá", "Trạng thái"}, 0
        );
        tblPhong = new JTable(modelPhong);
        JScrollPane spPhong = new JScrollPane(tblPhong);
        spPhong.setBounds(20, 20, 700, 220);
        add(spPhong);

        loadPhong();


        modelLich = new DefaultTableModel(
                new String[]{"Ngày", "BĐ", "KT"}, 0
        );
        tblLich = new JTable(modelLich);
        JScrollPane spLich = new JScrollPane(tblLich);
        spLich.setBounds(20, 260, 700, 180);
        add(spLich);

        tblPhong.getSelectionModel().addListSelectionListener(e -> loadLichPhong());


        JLabel lbDate = new JLabel("Ngày / Tháng / Năm");
        lbDate.setBounds(760, 30, 200, 25);
        add(lbDate);

        cbNgay = new JComboBox<>();
        cbThang = new JComboBox<>();
        txtNam = new JTextField();

        for (int i = 1; i <= 31; i++) cbNgay.addItem(i);
        for (int i = 1; i <= 12; i++) cbThang.addItem(i);

        cbNgay.setBounds(760, 60, 60, 25);
        cbThang.setBounds(830, 60, 60, 25);
        txtNam.setBounds(900, 60, 80, 25);

        add(cbNgay); add(cbThang); add(txtNam);


        cbBDGio = new JComboBox<>();
        cbBDPhut = new JComboBox<>();
        cbKTGio = new JComboBox<>();
        cbKTPhut = new JComboBox<>();

        for (int i = 6; i <= 21; i++) {
            cbBDGio.addItem(i);
        } 
        for (int i = 6; i <= 23; i++) {
            cbKTGio.addItem(i);
        }
        for (int i = 0; i < 60; i += 15) {
            cbBDPhut.addItem(i);
            cbKTPhut.addItem(i);
        }

        add(new JLabel("Bắt đầu")).setBounds(760, 100, 100, 25);
        cbBDGio.setBounds(760, 130, 60, 25);
        cbBDPhut.setBounds(830, 130, 60, 25);

        add(new JLabel("Kết thúc")).setBounds(760, 170, 100, 25);
        cbKTGio.setBounds(760, 200, 60, 25);
        cbKTPhut.setBounds(830, 200, 60, 25);

        add(cbBDGio); add(cbBDPhut);
        add(cbKTGio); add(cbKTPhut);

        btnDat = new JButton("Đặt phòng");
        btnDat.setBounds(760, 250, 220, 35);
        add(btnDat);

        btnDat.addActionListener(e -> datPhong());
        
        btnLogout = new JButton("Đăng xuất");
        btnLogout.setBounds(150,600, 110, 30); 
        add(btnLogout);
        btnLogout.addActionListener(e -> dangXuat());
       

        if (role.equals("admin")) {
            JButton btnThem = new JButton("Thêm phòng");
            JButton btnSua = new JButton("Sửa phòng");
            JButton btnXoa = new JButton("Xóa phòng");
            JButton btnBaoCao = new JButton("Báo cáo");
            
            btnThem.setBounds(760, 320, 220, 30);
            btnSua.setBounds(760, 360, 220, 30);
            btnXoa.setBounds(760, 400, 220, 30);
            btnBaoCao.setBounds(760, 500, 220, 30);

            add(btnBaoCao);
            add(btnThem); add(btnSua); add(btnXoa);

            btnThem.addActionListener(e -> new ThemPhongForm(this).setVisible(true));
            btnSua.addActionListener(e -> suaPhong());
            btnXoa.addActionListener(e -> xoaPhong());
            btnBaoCao.addActionListener(e -> moBaoCao());
        }


        JButton btnMy = new JButton("Lịch của tôi");
        btnMy.setBounds(760, 460, 220, 30);
        add(btnMy);

        btnMy.addActionListener(e -> new LichCaNhanForm(username)
);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    void loadPhong() {
        modelPhong.setRowCount(0);
        PhongHopDAO dao = new PhongHopDAO();

        for (String[] p : dao.getAllPhong()) {
            boolean daDat = new DatPhongDAO().phongDaCoLich(p[0]);
            modelPhong.addRow(new Object[]{
                    p[0], p[1], p[2], p[3],
                    daDat ? "Đã có lịch" : "Trống"
            });
        }
    }


    void loadLichPhong() {
        modelLich.setRowCount(0);
        int row = tblPhong.getSelectedRow();
        if (row == -1) return;

        String ma = tblPhong.getValueAt(row, 0).toString();
        ResultSet rs = new DatPhongDAO().lichTheoPhong(ma);

        try {
            while (rs.next()) {
                
                modelLich.addRow(new Object[]{
                       
                        rs.getDate("ngay"),
                        rs.getTime("gio_bat_dau"),
                        rs.getTime("gio_ket_thuc"),
                        rs.getString("username")
                });
            }
        } catch (Exception ignored) {}
    }


    void loadLichCaNhan() {
        modelLich.setRowCount(0);
        ResultSet rs = new DatPhongDAO().lichCaNhan(username);

        try {
            while (rs.next()) {
                
                modelLich.addRow(new Object[]{
                      
                        rs.getDate("ngay"),
                        rs.getTime("gio_bat_dau"),
                        rs.getTime("gio_ket_thuc"),
                        rs.getString("ma_phong")
                });
            }
        } catch (Exception ignored) {}
    }


    void datPhong() {
        int row = tblPhong.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn phòng");
            return;
        }

        try {
            String ma = tblPhong.getValueAt(row, 0).toString();
            Date ngay = Date.valueOf(
                    txtNam.getText() + "-" +
                            cbThang.getSelectedItem() + "-" +
                            cbNgay.getSelectedItem()
            );

            Time bd = Time.valueOf(
                    String.format("%02d:%02d:00",
                            cbBDGio.getSelectedItem(),
                            cbBDPhut.getSelectedItem())
            );

            Time kt = Time.valueOf(
                    String.format("%02d:%02d:00",
                            cbKTGio.getSelectedItem(),
                            cbKTPhut.getSelectedItem())
            );

            boolean ok = new DatPhongDAO().datPhong(ma, ngay, bd, kt, username);

            JOptionPane.showMessageDialog(this,
                    ok ? "Đặt phòng thành công" : "Trùng lịch");

            if (ok) {
                loadPhong();
                loadLichPhong();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ");
        }
    }


    void suaPhong() {
        int row = tblPhong.getSelectedRow();
        if (row == -1) return;

        String ma = tblPhong.getValueAt(row, 0).toString();
        if (new DatPhongDAO().phongDaCoLich(ma)) {
            JOptionPane.showMessageDialog(this, "Phòng đã có lịch");
            return;
        }

        String ten = JOptionPane.showInputDialog("Tên mới");
        int suc = Integer.parseInt(JOptionPane.showInputDialog("Sức chứa"));
        double gia = Double.parseDouble(JOptionPane.showInputDialog("Giá"));

        new PhongHopDAO().suaPhong(ma, ten, suc, gia);
        loadPhong();
    }

    void xoaPhong() {
        int row = tblPhong.getSelectedRow();
        if (row == -1) return;

        String ma = tblPhong.getValueAt(row, 0).toString();
        if (new DatPhongDAO().phongDaCoLich(ma)) {
            JOptionPane.showMessageDialog(this, "Phòng đã có lịch");
            return;
        }

        new PhongHopDAO().xoaPhong(ma);
        loadPhong();
    }
    void moBaoCao() {
    JFrame f = new JFrame("BÁO CÁO PHÒNG HỌP");
    f.setSize(800, 450);
    f.setLocationRelativeTo(this);
    f.setContentPane(new BaoCaoForm());
    f.setVisible(true);
}
 void dangXuat() {
        int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Bạn có chắc chắn muốn đăng xuất không?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose(); 
            new LoginForm().setVisible(true); 
        }
    } 
}
