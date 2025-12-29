package view;

import dao.SupportDAO;

import javax.swing.*;

public class SupportForm extends JFrame {

    JTextArea txtNoiDung;
    JButton btnGui;
    String username;

    public SupportForm(String username) {
        this.username = username;
        setTitle("Gửi yêu cầu hỗ trợ");
        setSize(400, 300);
        setLayout(null);

        txtNoiDung = new JTextArea();
        JScrollPane sp = new JScrollPane(txtNoiDung);
        sp.setBounds(20, 20, 340, 180);
        add(sp);

        btnGui = new JButton("Gửi yêu cầu");
        btnGui.setBounds(20, 220, 340, 30);
        add(btnGui);

        btnGui.addActionListener(e -> {
            String nd = txtNoiDung.getText().trim();
            if (nd.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nhập nội dung");
                return;
            }
            boolean ok = new SupportDAO().guiYeuCau(username, nd);
            JOptionPane.showMessageDialog(this, ok ? "Gửi thành công" : "Lỗi gửi yêu cầu");
            if (ok) dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
