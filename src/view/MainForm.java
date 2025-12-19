package view;
import javax.swing.*;

public class MainForm extends JFrame {
    public MainForm(String role){
        setTitle("Quản lý phòng họp - "+role);
        setSize(800,500);

        JMenuBar bar=new JMenuBar();
        JMenu m=new JMenu("Chức năng");

        JMenuItem dat=new JMenuItem("Đặt phòng");
        dat.addActionListener(e->new DatPhongForm().setVisible(true));
        m.add(dat);

        if(role.equals("admin")){
            JMenuItem them=new JMenuItem("Thêm phòng");
            them.addActionListener(e->new ThemPhongForm().setVisible(true));
            m.add(them);

            JMenuItem bc=new JMenuItem("Báo cáo tài chính");
            bc.addActionListener(e->new BaoCaoForm().setVisible(true));
            m.add(bc);
        }

        bar.add(m); setJMenuBar(bar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
