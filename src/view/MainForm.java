package view;

import dao.DatPhongDAO;
import dao.PhongHopDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.Time;

public class MainForm extends JFrame {

    JTable table;
    JTextField txtNgay, txtBD, txtKT;
    JButton btnDat, btnSua, btnXoa;

    String role;

    public MainForm(String role) {
        this.role = role;

        setTitle("Quản lý phòng họp - " + role);
        setSize(900,500);
        setLocationRelativeTo(null);
        setLayout(null);

    
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã phòng","Tên phòng","Sức chứa","Giá"},0
        );
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20,20,840,200);
        add(sp);

        loadPhong();

  
        JLabel lbNgay = new JLabel("Ngày (yyyy-mm-dd)");
        JLabel lbBD   = new JLabel("Giờ bắt đầu (hh:mm:ss)");
        JLabel lbKT   = new JLabel("Giờ kết thúc (hh:mm:ss)");

        lbNgay.setBounds(20,250,200,25);
        lbBD.setBounds(20,280,200,25);
        lbKT.setBounds(20,310,200,25);

        txtNgay = new JTextField();
        txtBD   = new JTextField();
        txtKT   = new JTextField();

        txtNgay.setBounds(230,250,200,25);
        txtBD.setBounds(230,280,200,25);
        txtKT.setBounds(230,310,200,25);

        btnDat = new JButton("Đặt phòng");
        btnDat.setBounds(460,280,150,30);

        add(lbNgay); add(lbBD); add(lbKT);
        add(txtNgay); add(txtBD); add(txtKT);
        add(btnDat);

        btnDat.addActionListener(e -> datPhong());


        if (role.equals("admin")) {

            btnSua = new JButton("Sửa phòng");
            btnXoa = new JButton("Xóa phòng");

            btnSua.setBounds(650,250,120,30);
            btnXoa.setBounds(650,290,120,30);

            add(btnSua);
            add(btnXoa);

     
            btnSua.addActionListener(e -> suaPhong());

        
            btnXoa.addActionListener(e -> xoaPhong());

        
            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("Admin");

            JMenuItem them = new JMenuItem("Thêm phòng");
            them.addActionListener(e -> new ThemPhongForm().setVisible(true));

            JMenuItem bc = new JMenuItem("Báo cáo");
            bc.addActionListener(e -> moBaoCao());


            menu.add(them);
            menu.add(bc);
            bar.add(menu);
            setJMenuBar(bar);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    void loadPhong() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (String[] p : new PhongHopDAO().getAllPhong()) {
            m.addRow(p);
        }
    }


  void datPhong() {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this,"Chọn phòng trước");
        return;
    }

    String ma = table.getValueAt(row,0).toString();
    new DatPhongForm(ma);
}


   
    void suaPhong() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,"Chọn phòng cần sửa");
            return;
        }

        try {
            String ma  = table.getValueAt(row,0).toString();
            String ten = JOptionPane.showInputDialog("Tên phòng mới");
            int suc    = Integer.parseInt(
                JOptionPane.showInputDialog("Sức chứa")
            );
            double gia = Double.parseDouble(
                JOptionPane.showInputDialog("Giá")
            );

            new PhongHopDAO().suaPhong(ma,ten,suc,gia);
            loadPhong();
            JOptionPane.showMessageDialog(this," Đã sửa phòng");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Dữ liệu không hợp lệ");
        }
    }


    void xoaPhong() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,"Chọn phòng cần xóa");
            return;
        }

        String ma = table.getValueAt(row,0).toString();

        if (JOptionPane.showConfirmDialog(
            this,
            "Xóa phòng này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {

            new PhongHopDAO().xoaPhong(ma);
            loadPhong();
            JOptionPane.showMessageDialog(this,"Đã xóa phòng");
        }
    }
    void moBaoCao() {
        JFrame f = new JFrame("Báo cáo");
        f.setSize(800, 500);
        f.setLocationRelativeTo(this);
        f.setContentPane(new BaoCaoForm());
        f.setVisible(true);
    }
    
}
