package view;
import javax.swing.*;
import dao.UserDAO;

public class RegisterForm extends JFrame {
    JTextField u=new JTextField();
    JPasswordField p=new JPasswordField();

    public RegisterForm(){
        setTitle("Đăng ký");
        setSize(300,200);
        setLayout(null);

        add(new JLabel("User")).setBounds(20,30,60,25);
        add(new JLabel("Pass")).setBounds(20,70,60,25);
        u.setBounds(80,30,150,25);
        p.setBounds(80,70,150,25);

        JButton b=new JButton("Đăng ký");
        b.setBounds(90,120,100,30);
        add(u); add(p); add(b);

        b.addActionListener(e->register());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void register(){
        if(new UserDAO().register(u.getText(),
            new String(p.getPassword()))){
            JOptionPane.showMessageDialog(this,"Đăng ký thành công");
            dispose();
        } else JOptionPane.showMessageDialog(this,"Lỗi đăng ký");
    }
}
