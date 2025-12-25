package dao;

import java.sql.*;
import java.util.*;

public class PhongHopDAO {

    public List<String[]> getAllPhong() {
        List<String[]> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            ResultSet rs = c.createStatement()
                .executeQuery("SELECT * FROM phonghop");
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("ma_phong"),
                    rs.getString("ten_phong"),
                    rs.getInt("suc_chua")+"",
                    rs.getDouble("gia")+""
                });
            }
        } catch(Exception e){e.printStackTrace();}
        return list;
    }

    // ➕ thêm phòng
    public boolean themPhong(String ma, String ten, int suc, double gia) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO phonghop VALUES (?,?,?,?)"
            );
            ps.setString(1, ma);
            ps.setString(2, ten);
            ps.setInt(3, suc);
            ps.setDouble(4, gia);
            return ps.executeUpdate() > 0;
        } catch(Exception e){return false;}
    }

    // ✏️ sửa phòng
    public boolean suaPhong(String ma, String ten, int suc, double gia) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "UPDATE phonghop SET ten_phong=?, suc_chua=?, gia=? WHERE ma_phong=?"
            );
            ps.setString(1, ten);
            ps.setInt(2, suc);
            ps.setDouble(3, gia);
            ps.setString(4, ma);
            return ps.executeUpdate() > 0;
        } catch(Exception e){return false;}
    }

    // ❌ xóa phòng
    public boolean xoaPhong(String ma) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "DELETE FROM phonghop WHERE ma_phong=?"
            );
            ps.setString(1, ma);
            return ps.executeUpdate() > 0;
        } catch(Exception e){return false;}
    }
}
