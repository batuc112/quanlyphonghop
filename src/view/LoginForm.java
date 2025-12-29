package view;

import javax.swing.*;
import dao.DBConnection;

public class LoginForm extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin, btnRegister;

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(300,200);
        setLayout(null);
        setLocationRelativeTo(null);

        add(new JLabel("Tài khoản")).setBounds(20,20,80,25);
        txtUser = new JTextField();
        txtUser.setBounds(100,20,150,25);
        add(txtUser);

        add(new JLabel("Mật khẩu")).setBounds(20,60,80,25);
        txtPass = new JPasswordField();
        txtPass.setBounds(100,60,150,25);
        add(txtPass);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(160,110,110,30);
        add(btnLogin);


        btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(20,110,110,30);
        add(btnRegister);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> {
            new RegisterForm().setVisible(true);
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void login() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        try (var c = DBConnection.getConnection()) {
            var ps = c.prepareStatement(
                "SELECT role FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            var rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                dispose();

                if (role.equals("ketoan")) {
                    new KeToanForm();
                } else {
                    new MainForm(role, username);
                }
            } else {
                JOptionPane.showMessageDialog(this,"Sai username hoặc password");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
