package view;

import dao.DatPhongDAO;

import javax.swing.*;
import java.sql.Date;
import java.sql.Time;

public class DatPhongForm extends JFrame {

    JTextField txtNgay;
    JComboBox<Integer> cbBDGio, cbBDPhut;
    JComboBox<Integer> cbKTGio, cbKTPhut;
    JButton btnDat;

    String maPhong;

    public DatPhongForm(String maPhong) {
        this.maPhong = maPhong;

        setTitle("Đặt phòng " + maPhong);
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbNgay = new JLabel("Ngày (yyyy-mm-dd)");
        lbNgay.setBounds(20,20,150,25);
        add(lbNgay);

        txtNgay = new JTextField();
        txtNgay.setBounds(180,20,180,25);
        add(txtNgay);

        // ===== Giờ bắt đầu =====
        JLabel lbBD = new JLabel("Giờ bắt đầu");
        lbBD.setBounds(20,60,150,25);
        add(lbBD);

        cbBDGio = new JComboBox<>();
        cbBDPhut = new JComboBox<>();

        for (int i = 0; i < 24; i++) cbBDGio.addItem(i);
        for (int i = 0; i < 60; i += 5) cbBDPhut.addItem(i);

        cbBDGio.setBounds(180,60,60,25);
        cbBDPhut.setBounds(250,60,60,25);

        add(cbBDGio);
        add(cbBDPhut);

        // ===== Giờ kết thúc =====
        JLabel lbKT = new JLabel("Giờ kết thúc");
        lbKT.setBounds(20,100,150,25);
        add(lbKT);

        cbKTGio = new JComboBox<>();
        cbKTPhut = new JComboBox<>();

        for (int i = 0; i < 24; i++) cbKTGio.addItem(i);
        for (int i = 0; i < 60; i += 5) cbKTPhut.addItem(i);

        cbKTGio.setBounds(180,100,60,25);
        cbKTPhut.setBounds(250,100,60,25);

        add(cbKTGio);
        add(cbKTPhut);

        btnDat = new JButton("Đặt phòng");
        btnDat.setBounds(120,160,150,35);
        add(btnDat);

        btnDat.addActionListener(e -> datPhong());

        setVisible(true);
    }

    void datPhong() {
        try {
            Date ngay = Date.valueOf(txtNgay.getText());

            String bd = String.format(
                "%02d:%02d:00",
                cbBDGio.getSelectedItem(),
                cbBDPhut.getSelectedItem()
            );

            String kt = String.format(
                "%02d:%02d:00",
                cbKTGio.getSelectedItem(),
                cbKTPhut.getSelectedItem()
            );

            Time gioBD = Time.valueOf(bd);
            Time gioKT = Time.valueOf(kt);

            if (!gioKT.after(gioBD)) {
                JOptionPane.showMessageDialog(this,"Giờ kết thúc phải sau giờ bắt đầu");
                return;
            }

            boolean ok = new DatPhongDAO().datPhong(
                maPhong, ngay, gioBD, gioKT
            );

            JOptionPane.showMessageDialog(
                this,
                ok ? "Đặt phòng thành công" : "Trùng lịch, phòng đã được đặt"
            );

            if (ok) dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Dữ liệu không hợp lệ");
        }
    }
}
