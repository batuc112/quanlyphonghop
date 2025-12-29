package view;
import javax.swing.*;
import dao.UserDAO;
import java.awt.*;

public class RegisterForm extends JFrame {
    JTextField u = new JTextField();
    JPasswordField p = new JPasswordField();

    public RegisterForm() {
        setTitle("Đăng ký");
        setSize(300,200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        BackGround bgPanel = new BackGround("/images/register.jpg");
        bgPanel.setLayout(null);
        setContentPane(bgPanel);

        JLabel lblUser = new JLabel("Tài khoản");
        JLabel lblPass = new JLabel("Mật khẩu");
        lblUser.setForeground(Color.WHITE);
        lblPass.setForeground(Color.WHITE);

        lblUser.setBounds(20,30,60,25);
        lblPass.setBounds(20,70,60,25);
        u.setBounds(80,30,150,25);
        p.setBounds(80,70,150,25);

        JButton b = new JButton("Đăng ký");
        b.setBounds(90,120,100,30);

        bgPanel.add(lblUser);
        bgPanel.add(lblPass);
        bgPanel.add(u);
        bgPanel.add(p);
        bgPanel.add(b);

        b.addActionListener(e -> register());
    }

    void register() {
        String username = u.getText().trim();
        String password = new String(p.getPassword()).trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập!");
            u.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
            p.requestFocus();
            return;
        }

        if (new UserDAO().register(username, password)) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi đăng ký (Tài khoản có thể đã tồn tại)");
        }
    }

    public static void main(String[] args) {
        new RegisterForm().setVisible(true);
    }
}