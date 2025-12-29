package dao;

import java.sql.*;

public class SanPhamDAO {

    public ResultSet getAll() {
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps =
                c.prepareStatement("SELECT * FROM sanpham");
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getGia(int id) {
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps =
                c.prepareStatement("SELECT don_gia FROM sanpham WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
