package view;

import dao.BaoCaoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class BaoCaoForm extends JPanel {

    JTable table;
    JLabel lbTong;

    public BaoCaoForm() {
        setLayout(null);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã phòng", "Tên phòng", "Số lần", "Tổng giờ", "Doanh thu"}, 0
        );

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 740, 300);
        add(sp);

        lbTong = new JLabel("Tổng doanh thu: 0");
        lbTong.setBounds(20, 340, 400, 30);
        add(lbTong);

        loadBaoCao();
    }

    void loadBaoCao() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);

        BaoCaoDAO dao = new BaoCaoDAO();
        ResultSet rs = dao.baoCaoPhong();

        try {
            while (rs.next()) {
                m.addRow(new Object[]{
                    rs.getString("ma_phong"),
                    rs.getString("ten_phong"),
                    rs.getInt("so_lan_su_dung"),
                    rs.getDouble("tong_gio_su_dung"),
                    rs.getDouble("doanh_thu")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lbTong.setText("Tổng doanh thu: " + dao.tongDoanhThu());
    }
}
