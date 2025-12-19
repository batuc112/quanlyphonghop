package view;

import javax.swing.*;
import dao.UserDAO;

public class LoginForm extends JFrame {

    JTextField u = new JTextField();
    JPasswordField p = new JPasswordField();

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(300,200);
        setLayout(null);

        JLabel lb1 = new JLabel("User");
        JLabel lb2 = new JLabel("Pass");

        lb1.setBounds(20,30,60,25);
        lb2.setBounds(20,70,60,25);
        u.setBounds(80,30,150,25);
        p.setBounds(80,70,150,25);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(160,110,80,30);

        JButton btnReg = new JButton("Đăng ký");
        btnReg.setBounds(40,110,90,30);

        add(lb1); add(lb2);
        add(u); add(p);
        add(btnLogin); add(btnReg);

        btnLogin.addActionListener(e -> login());
        btnReg.addActionListener(e -> new RegisterForm().setVisible(true));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void login() {
        String role = new UserDAO().login(
            u.getText(),
            new String(p.getPassword())
        );

        if (role != null) {
            new MainForm(role).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản");
        }
    }
}
