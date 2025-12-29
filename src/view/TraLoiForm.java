package view;

import dao.SupportDAO;

import javax.swing.*;
import java.awt.*;

public class TraLoiForm extends JFrame {

    JTextArea txtTraLoi;
    JButton btnGui;
    int id;

    public TraLoiForm(int id) {
        this.id = id;
        setTitle("Trả lời yêu cầu");
        setSize(500, 400);
        setLayout(new BorderLayout());

        txtTraLoi = new JTextArea();
        txtTraLoi.setLineWrap(true);
        txtTraLoi.setWrapStyleWord(true);
        add(new JScrollPane(txtTraLoi), BorderLayout.CENTER);

        btnGui = new JButton("Gửi trả lời");
        add(btnGui, BorderLayout.SOUTH);

        btnGui.addActionListener(e -> {
            String traLoi = txtTraLoi.getText().trim();
            if (traLoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nhập câu trả lời");
                return;
            }
            boolean ok = new SupportDAO().traLoiYeuCau(id, traLoi);
            JOptionPane.showMessageDialog(this, ok ? "Trả lời thành công" : "Thất bại");
            if (ok) dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
