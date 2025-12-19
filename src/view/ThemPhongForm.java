package view;

import javax.swing.*;
import dao.PhongHopDAO;

public class ThemPhongForm extends JFrame {

    JTextField txtMa = new JTextField();
    JTextField txtTen = new JTextField();
    JTextField txtSuc = new JTextField();
    JTextField txtGia = new JTextField();

    public ThemPhongForm() {
        setTitle("Thêm phòng (Admin)");
        setSize(350,300);
        setLayout(null);
        setLocationRelativeTo(null);

        add(new JLabel("Mã phòng")).setBounds(20,30,80,25);
        add(new JLabel("Tên phòng")).setBounds(20,70,80,25);
        add(new JLabel("Sức chứa")).setBounds(20,110,80,25);
        add(new JLabel("Giá")).setBounds(20,150,80,25);

        txtMa.setBounds(110,30,180,25);
        txtTen.setBounds(110,70,180,25);
        txtSuc.setBounds(110,110,180,25);
        txtGia.setBounds(110,150,180,25);

        JButton btn = new JButton("Thêm");
        btn.setBounds(120,200,100,30);

        add(txtMa); add(txtTen);
        add(txtSuc); add(txtGia);
        add(btn);

        btn.addActionListener(e -> themPhong());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void themPhong() {
        try {
            new PhongHopDAO().themPhong(
                txtMa.getText(),
                txtTen.getText(),
                Integer.parseInt(txtSuc.getText()),
                Double.parseDouble(txtGia.getText())
            );
            JOptionPane.showMessageDialog(this, "Thêm phòng thành công");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ");
        }
    }
}
