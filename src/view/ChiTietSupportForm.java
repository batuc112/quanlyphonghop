package view;

import dao.SupportDAO;

import javax.swing.*;
import java.awt.*;

public class ChiTietSupportForm extends JFrame {

    JTextArea txtChiTiet;
    JButton btnHuy;
    int id;
    String username;

    public ChiTietSupportForm(int id, String username, String noiDung, String traLoi, String trangThai) {
        this.id = id;
        this.username = username;

        setTitle("Chi tiết yêu cầu hỗ trợ");
        setSize(500, 400);
        setLayout(new BorderLayout());

        txtChiTiet = new JTextArea();
        txtChiTiet.setEditable(false);
        txtChiTiet.setLineWrap(true);
        txtChiTiet.setWrapStyleWord(true);

        StringBuilder sb = new StringBuilder();
        sb.append("Yêu cầu:\n").append(noiDung).append("\n\n");
        sb.append("Phản hồi:\n").append(traLoi != null ? traLoi : "Chưa có phản hồi").append("\n\n");
        sb.append("Trạng thái: ").append(trangThai);
        txtChiTiet.setText(sb.toString());

        add(new JScrollPane(txtChiTiet), BorderLayout.CENTER);

        btnHuy = new JButton("Hủy yêu cầu");
        add(btnHuy, BorderLayout.SOUTH);

        btnHuy.setEnabled("chua_hoan_thanh".equals(trangThai));

        btnHuy.addActionListener(e -> {
            boolean ok = new SupportDAO().huyYeuCau(id, username);
            JOptionPane.showMessageDialog(this, ok ? "Hủy thành công" : "Hủy thất bại");
            if (ok) dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
