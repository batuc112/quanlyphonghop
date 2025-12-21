package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.PhongHopDAO;
import dao.DatPhongDAO;
import java.sql.Date;
import java.sql.Time;

public class DatPhongForm extends JFrame {

    JTable table;
    JTextField txtNgay = new JTextField();
    JTextField txtBD = new JTextField();
    JTextField txtKT = new JTextField();

    public DatPhongForm() {
        setTitle("Đặt phòng");
        setSize(600,400);
        setLayout(null);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã phòng","Tên","Sức chứa","Giá"}, 0
        );

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20,20,540,150);
        add(sp);

        loadPhong(model);

        add(new JLabel("Ngày (yyyy-mm-dd)")).setBounds(20,190,150,25);
        add(new JLabel("Giờ BD")).setBounds(20,220,150,25);
        add(new JLabel("Giờ KT")).setBounds(20,250,150,25);

        txtNgay.setBounds(180,190,150,25);
        txtBD.setBounds(180,220,150,25);
        txtKT.setBounds(180,250,150,25);

        JButton btn = new JButton("Đặt phòng");
        btn.setBounds(380,220,120,30);

        add(txtNgay); add(txtBD); add(txtKT);
        add(btn);

        btn.addActionListener(e -> datPhong());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void loadPhong(DefaultTableModel model) {
        for (String[] p : new PhongHopDAO().getAllPhong()) {
            model.addRow(p);
        }
    }

    void datPhong() {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Chọn phòng trước");
        return;
    }

    String maPhong = table.getValueAt(row, 0).toString();

    boolean ok = new DatPhongDAO().datPhong(
        maPhong,
        Date.valueOf(txtNgay.getText()),
        Time.valueOf(txtBD.getText()),
        Time.valueOf(txtKT.getText())
    );

    if (ok) {
        JOptionPane.showMessageDialog(this, "Đặt phòng thành công");
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Phòng đã có lịch trong thời gian này");
    }
}

}
