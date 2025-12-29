package view;

import dao.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

public class KeToanForm extends JFrame {

    JTable tblLich, tblSP;
    DefaultTableModel modelLich, modelSP;
    JComboBox<String> cbSP;
    JTextField txtSL;
    JLabel lbTong;

    public KeToanForm() {
        setTitle("Kế toán - Thanh toán");
        setSize(900,500);
        setLocationRelativeTo(null);
        setLayout(null);

        modelLich = new DefaultTableModel(
            new String[]{"ID","Phòng","User","Ngày","Tiền phòng"},0
        );
        tblLich = new JTable(modelLich);
        JScrollPane sp1 = new JScrollPane(tblLich);
        sp1.setBounds(20,20,400,200);
        add(sp1);

        loadLich();

        cbSP = new JComboBox<>();
        loadSP();
        cbSP.setBounds(450,30,200,25);
        add(cbSP);

        txtSL = new JTextField();
        txtSL.setBounds(660,30,50,25);
        add(txtSL);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBounds(720,30,80,25);
        add(btnAdd);

        modelSP = new DefaultTableModel(
            new String[]{"ID","Tên","SL","Tiền"},0
        );
        tblSP = new JTable(modelSP);
        JScrollPane sp2 = new JScrollPane(tblSP);
        sp2.setBounds(450,70,400,200);
        add(sp2);

        lbTong = new JLabel("Tổng tiền: 0");
        lbTong.setBounds(450,290,300,30);
        add(lbTong);

        JButton btnTT = new JButton("Thanh toán");
        btnTT.setBounds(600,330,200,40);
        add(btnTT);

        btnAdd.addActionListener(e -> themSP());
        btnTT.addActionListener(e -> thanhToan());

        setVisible(true);
    }

    void loadLich() {
        modelLich.setRowCount(0);
        try {
            ResultSet rs = new KeToanDAO().lichChuaThanhToan();
            while (rs.next()) {
                modelLich.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("ma_phong"),
                    rs.getString("username"),
                    rs.getDate("ngay"),
                    rs.getDouble("tien")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    void loadSP() {
        try {
            ResultSet rs = new SanPhamDAO().getAll();
            while (rs.next()) {
                cbSP.addItem(
                    rs.getInt("id")+" - "+rs.getString("ten_san_pham")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    void themSP() {
        try {
            String s = cbSP.getSelectedItem().toString();
            int id = Integer.parseInt(s.split(" - ")[0]);
            int sl = Integer.parseInt(txtSL.getText());
            double gia = new SanPhamDAO().getGia(id);
            double tien = sl * gia;

            modelSP.addRow(new Object[]{id, s, sl, tien});
            tinhTong();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Lỗi sản phẩm");
        }
    }

    void tinhTong() {
        int row = tblLich.getSelectedRow();
        if (row == -1) return;

        double tienPhong = (double) modelLich.getValueAt(row,4);
        double tienSP = 0;

        for (int i=0;i<modelSP.getRowCount();i++) {
            tienSP += (double) modelSP.getValueAt(i,3);
        }

        lbTong.setText("Tổng tiền: " + (tienPhong + tienSP));
    }

    void thanhToan() {
        int row = tblLich.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,"Chọn lịch");
            return;
        }

        List<Object[]> ds = new ArrayList<>();
        for (int i=0;i<modelSP.getRowCount();i++) {
            ds.add(new Object[]{
                modelSP.getValueAt(i,0),
                modelSP.getValueAt(i,1),
                modelSP.getValueAt(i,2),
                modelSP.getValueAt(i,3)
            });
        }

        boolean ok = new KeToanDAO().thanhToan(
            (int) modelLich.getValueAt(row,0),
            modelLich.getValueAt(row,2).toString(),
            modelLich.getValueAt(row,1).toString(),
            (java.sql.Date) modelLich.getValueAt(row,3),
            (double) modelLich.getValueAt(row,4),
            ds
        );

        if (ok) {
            JOptionPane.showMessageDialog(this,"Thanh toán thành công");
            loadLich();
            modelSP.setRowCount(0);
            lbTong.setText("Tổng tiền: 0");
        }
    }
}
