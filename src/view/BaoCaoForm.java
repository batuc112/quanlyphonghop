package view;

import dao.BaoCaoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class BaoCaoForm extends JPanel {

    JTable table;
    JComboBox<Integer> cbThang;
    JLabel lblTong;

    BaoCaoDAO dao = new BaoCaoDAO();

    public BaoCaoForm() {
        setLayout(null);

        JLabel lbThang = new JLabel("Chọn tháng:");
        lbThang.setBounds(20, 20, 100, 25);
        add(lbThang);

        cbThang = new JComboBox<>();
        for (int i = 1; i <= 12; i++) cbThang.addItem(i);
        cbThang.setBounds(120, 20, 100, 25);
        add(cbThang);

        JButton btnXem = new JButton("Xem báo cáo");
        btnXem.setBounds(240, 20, 130, 25);
        add(btnXem);

  
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 60, 740, 300);
        add(sp);

  
        lblTong = new JLabel("Tổng doanh thu: 0");
        lblTong.setBounds(20, 380, 400, 25);
        add(lblTong);

 
        btnXem.addActionListener(e -> loadBaoCao());

 
        loadBaoCao();
    }

    void loadBaoCao() {
        int thang = (int) cbThang.getSelectedItem();

        DefaultTableModel model = new DefaultTableModel(
                dao.getHeader(), 0
        );

        Vector<Vector<Object>> data = dao.BaoCaoThang(thang);

        for (Vector<Object> row : data) {
            model.addRow(row);
        }

        table.setModel(model);

        double tong = dao.DoanhThuThang(thang);
        lblTong.setText("Tổng doanh thu tháng " + thang + ": " + tong);
    }
}
