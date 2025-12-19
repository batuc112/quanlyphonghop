package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongHopDAO {

    // ===============================
    // 1. Thêm phòng (ADMIN)
    // ===============================
    public boolean themPhong(String ma, String ten, int sucChua, double gia) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO phonghop(ma_phong, ten_phong, suc_chua, gia) VALUES(?,?,?,?)"
            );
            ps.setString(1, ma);
            ps.setString(2, ten);
            ps.setInt(3, sucChua);
            ps.setDouble(4, gia);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===============================
    // 2. Lấy danh sách phòng (USER + ADMIN)
    // ===============================
    public List<String[]> getAllPhong() {
        List<String[]> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM phonghop");

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("ma_phong"),
                    rs.getString("ten_phong"),
                    String.valueOf(rs.getInt("suc_chua")),
                    String.valueOf(rs.getDouble("gia"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===============================
    // 3. Lấy 1 phòng theo mã
    // ===============================
    public String[] getPhongByMa(String ma) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM phonghop WHERE ma_phong=?"
            );
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new String[]{
                    rs.getString("ma_phong"),
                    rs.getString("ten_phong"),
                    String.valueOf(rs.getInt("suc_chua")),
                    String.valueOf(rs.getDouble("gia"))
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===============================
    // 4. Cập nhật phòng (ADMIN)
    // ===============================
    public boolean capNhatPhong(String ma, String ten, int sucChua, double gia) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "UPDATE phonghop SET ten_phong=?, suc_chua=?, gia=? WHERE ma_phong=?"
            );
            ps.setString(1, ten);
            ps.setInt(2, sucChua);
            ps.setDouble(3, gia);
            ps.setString(4, ma);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===============================
    // 5. Xóa phòng (ADMIN)
    // ===============================
    public boolean xoaPhong(String ma) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "DELETE FROM phonghop WHERE ma_phong=?"
            );
            ps.setString(1, ma);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
