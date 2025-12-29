package view;

import javax.swing.*;
import java.awt.*;
import dao.UserDAO;

public class LoginForm extends JFrame {

    JTextField u = new JTextField();
    JPasswordField p = new JPasswordField();

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        BackGround bgPanel = new BackGround("/images/HUCE.jpg");
        bgPanel.setLayout(null);
        setContentPane(bgPanel);

  
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(90, 20, 250, 30);
        bgPanel.add(lblTitle);

    
        JLabel lb1 = new JLabel("Tài khoản:");
        JLabel lb2 = new JLabel("Mật khẩu:");
        lb1.setForeground(Color.WHITE);
        lb2.setForeground(Color.WHITE);

        lb1.setBounds(60, 70, 60, 25);
        lb2.setBounds(60, 110, 60, 25);
        u.setBounds(120, 70, 200, 25);
        p.setBounds(120, 110, 200, 25);

        bgPanel.add(lb1);
        bgPanel.add(lb2);
        bgPanel.add(u);
        bgPanel.add(p);

       
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(220, 160, 100, 30);
        Color blue = new Color(0, 102, 204);
        btnLogin.setBackground(blue);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createLineBorder(blue, 2));

       
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnLogin.setBackground(new Color(0, 80, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnLogin.setBackground(blue);
            }
        });

        
        JButton btnReg = new JButton("Đăng ký");
        btnReg.setBounds(100, 160, 100, 30);
        btnReg.setBackground(Color.WHITE);
        btnReg.setForeground(blue);
        btnReg.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReg.setFocusPainted(false);
        btnReg.setBorder(BorderFactory.createLineBorder(blue, 2));

        btnReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnReg.setBackground(blue);
                btnReg.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnReg.setBackground(Color.WHITE);
                btnReg.setForeground(blue);
            }
        });

        bgPanel.add(btnLogin);
        bgPanel.add(btnReg);

        btnLogin.addActionListener(e -> login());
        btnReg.addActionListener(e -> new RegisterForm().setVisible(true));
    }

    void login() {
String username = u.getText().trim();
        String password = new String(p.getPassword()).trim();

        String role = new UserDAO().login(username, password);

        if (role != null) {
            new MainForm(role, username).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
        }
    }

    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}