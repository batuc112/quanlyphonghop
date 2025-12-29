package view;

import dao.DatPhongDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class LichCaNhanForm extends JFrame {

    JTable table;
    DefaultTableModel model;
    String username;

    public LichCaNhanForm(String username) {
        this.username = username;

        setTitle("Lịch đã đặt của tôi");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        model = new DefaultTableModel(
            new String[]{"ID", "Mã phòng", "Ngày", "Bắt đầu", "Kết thúc"}, 0
        );
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 650, 250);
        add(sp);

        JButton btnHuy = new JButton("Huỷ đặt phòng");
        btnHuy.setBounds(250, 290, 180, 30);
        add(btnHuy);

        btnHuy.addActionListener(e -> huy());

        load();

        setVisible(true);
    }

    void load() {
        model.setRowCount(0);
        ResultSet rs = new DatPhongDAO().lichCaNhan(username);

        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("ma_phong"),
                    rs.getDate("ngay"),
                    rs.getTime("gio_bat_dau"),
                    rs.getTime("gio_ket_thuc")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void huy() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn lịch cần huỷ");
            return;
        }

        int id = (int) table.getValueAt(row, 0);

        if (JOptionPane.showConfirmDialog(
            this,
            "Huỷ lịch này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {

            boolean ok = new DatPhongDAO().huyDatPhong(id, username);
            JOptionPane.showMessageDialog(
                this,
                ok ? "Đã huỷ" : "Không huỷ được"
            );
            if (ok) load();
        }
    }
}
