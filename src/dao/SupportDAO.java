package dao;

import java.sql.*;

public class SupportDAO {


    public boolean guiYeuCau(String username, String noiDung) {
        String sql = "INSERT INTO yeu_cau_ho_tro(username, noi_dung) VALUES (?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, noiDung);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ResultSet layYeuCauUser(String username) {
        String sql = "SELECT * FROM yeu_cau_ho_tro WHERE username = ? ORDER BY ngay_tao DESC";
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public ResultSet layYeuCauChuaHoanThanh() {
        String sql = "SELECT * FROM yeu_cau_ho_tro WHERE trang_thai='chua_hoan_thanh' ORDER BY ngay_tao ASC";
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
    public boolean traLoiYeuCau(int id, String traLoi) {
        String sql = "UPDATE yeu_cau_ho_tro SET tra_loi=?, trang_thai='hoan_thanh' WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, traLoi);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean huyYeuCau(int id, String username) {
    String sql = "DELETE FROM yeu_cau_ho_tro WHERE id=? AND username=? AND trang_thai='chua_hoan_thanh'";
    try (Connection c = DBConnection.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.setString(2, username);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
