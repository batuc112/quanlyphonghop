package view;

import dao.SupportDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class LichSuYeuCauForm extends JFrame {

    JTable tbl;
    DefaultTableModel model;
    String username;

    public LichSuYeuCauForm(String username) {
        this.username = username;
        setTitle("Lịch sử yêu cầu hỗ trợ");
        setSize(800, 550); 
        setLayout(null);

    
        model = new DefaultTableModel(
                new String[]{"ID", "Nội dung", "Trả lời", "Trạng thái", "Ngày gửi"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tbl = new JTable(model);
        JScrollPane sp = new JScrollPane(tbl);
        sp.setBounds(20, 20, 740, 420);
        add(sp);

   
        tbl.removeColumn(tbl.getColumnModel().getColumn(0));

     
        JButton btnChiTiet = new JButton("Xem chi tiết");
        btnChiTiet.setBounds(20, 450, 360, 30); 
        add(btnChiTiet);

      
        JButton btnHuy = new JButton("Hủy yêu cầu");
        btnHuy.setBounds(400, 450, 360, 30);
        add(btnHuy);

       
        loadYeuCau();

     
        btnChiTiet.addActionListener(e -> {
            int row = tbl.getSelectedRow();
            if (row == -1) return;

            int modelRow = tbl.convertRowIndexToModel(row);
            int id = (int) model.getValueAt(modelRow, 0);
            String noiDung = (String) model.getValueAt(modelRow, 1);
            String traLoi = (String) model.getValueAt(modelRow, 2);
            String trangThai = (String) model.getValueAt(modelRow, 3);

            new ChiTietSupportForm(id, username, noiDung, traLoi, trangThai);
        });

     
        btnHuy.addActionListener(e -> {
            int row = tbl.getSelectedRow();
            if (row == -1) return;

            int modelRow = tbl.convertRowIndexToModel(row);
            int id = (int) model.getValueAt(modelRow, 0);
            String trangThai = (String) model.getValueAt(modelRow, 3);

            if (!"chua_hoan_thanh".equals(trangThai)) {
                JOptionPane.showMessageDialog(this, "Chỉ có thể hủy các yêu cầu chưa hoàn thành!");
                return;
            }

            boolean ok = new SupportDAO().huyYeuCau(id, username);
            JOptionPane.showMessageDialog(this, ok ? "Hủy thành công" : "Hủy thất bại");
            if (ok) loadYeuCau(); 
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    void loadYeuCau() {
        model.setRowCount(0);
        ResultSet rs = new SupportDAO().layYeuCauUser(username);
        try {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),                 
                        rs.getString("noi_dung"),        
                        rs.getString("tra_loi"),       
                        rs.getString("trang_thai"),    
                        rs.getTimestamp("ngay_tao")   
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
