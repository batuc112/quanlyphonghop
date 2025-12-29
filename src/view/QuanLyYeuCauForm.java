package view;

import dao.SupportDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class QuanLyYeuCauForm extends JFrame {

    JTable tblYeuCau;
    DefaultTableModel model;
    JButton btnTraLoi;

    public QuanLyYeuCauForm() {
        setTitle("Quản lý yêu cầu hỗ trợ");
        setSize(800, 500);
        setLayout(null);

        model = new DefaultTableModel(
                new String[]{"ID", "Người gửi", "Nội dung", "Ngày gửi"}, 0
        );
        tblYeuCau = new JTable(model);
        JScrollPane sp = new JScrollPane(tblYeuCau);
        sp.setBounds(20, 20, 740, 380);
        add(sp);

        btnTraLoi = new JButton("Trả lời");
        btnTraLoi.setBounds(20, 410, 740, 30);
        add(btnTraLoi);

        loadYeuCau();

        btnTraLoi.addActionListener(e -> {
    int row = tblYeuCau.getSelectedRow();
    if (row == -1) return;
    int id = (int) model.getValueAt(row, 0);
    new TraLoiForm(id);
});


        setLocationRelativeTo(null);
        setVisible(true);
    }

    void loadYeuCau() {
        model.setRowCount(0);
        ResultSet rs = new SupportDAO().layYeuCauChuaHoanThanh();
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("noi_dung"),
                        rs.getTimestamp("ngay_tao")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
