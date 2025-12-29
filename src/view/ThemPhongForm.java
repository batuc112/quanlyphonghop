
package view;

import javax.swing.*;
import dao.PhongHopDAO;

public class ThemPhongForm extends JFrame {

    JTextField txtMa = new JTextField();
    JTextField txtTen = new JTextField();
    JTextField txtSuc = new JTextField();
    JTextField txtGia = new JTextField();

    MainForm parent;

    public ThemPhongForm(MainForm parent) {
        this.parent = parent;

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
        setVisible(true);
    }

    void themPhong() {
        try {
            String ma = txtMa.getText().trim();
            String ten = txtTen.getText().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Không được để trống");
                return;
            }

            int suc = Integer.parseInt(txtSuc.getText());
            double gia = Double.parseDouble(txtGia.getText());

            PhongHopDAO dao = new PhongHopDAO();

            if (dao.tonTaiMaPhong(ma)) {
                JOptionPane.showMessageDialog(this,"Mã phòng đã tồn tại");
                return;
            }

            dao.themPhong(ma, ten, suc, gia);

            JOptionPane.showMessageDialog(this,"Thêm phòng thành công");

            parent.loadPhong();
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"Sức chứa và giá phải là số");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Lỗi thêm phòng");
            e.printStackTrace();
        }
    }
}