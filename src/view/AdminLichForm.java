package view;

import dao.AdminDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class AdminLichForm extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AdminLichForm() {
        setTitle("Admin - Quản lý lịch đặt phòng");
        setSize(900,500);
        setLocationRelativeTo(null);
        setLayout(null);

        model = new DefaultTableModel(
            new String[]{
                "ID",
                "Phòng",
                "User",
                "Ngày",
                "Giờ bắt đầu",
                "Giờ kết thúc",
                "Trạng thái"
            }, 0
        );

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20,20,850,400);
        add(sp);

        loadData();

        setVisible(true);
    }

    void loadData() {
        model.setRowCount(0);
        try {
            ResultSet rs = new AdminDAO().tatCaLich();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("ma_phong"),
                    rs.getString("username"),
                    rs.getDate("ngay"),
                    rs.getTime("gio_bat_dau"),
                    rs.getTime("gio_ket_thuc"),
                    rs.getString("trang_thai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
