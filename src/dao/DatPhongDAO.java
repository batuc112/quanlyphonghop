package dao;

import java.sql.*;

public class DatPhongDAO {

    public boolean datPhong(
            String maPhong,
            Date ngay,
            Time gioBD,
            Time gioKT,
            String username
    ) {

        String checkSql =
            "SELECT COUNT(*) FROM datphong " +
            "WHERE ma_phong = ? AND ngay = ? " +
            "AND gio_bat_dau < ? AND gio_ket_thuc > ?";

        String insertSql =
            "INSERT INTO datphong(ma_phong, username, ngay, gio_bat_dau, gio_ket_thuc) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection()) {

            // ===== CHECK TRÙNG =====
            PreparedStatement psCheck = c.prepareStatement(checkSql);
            psCheck.setString(1, maPhong);
            psCheck.setDate(2, ngay);
            psCheck.setTime(3, gioKT);
            psCheck.setTime(4, gioBD);

            ResultSet rs = psCheck.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                return false; // TRÙNG THẬT
            }

            // ===== INSERT =====
            PreparedStatement ps = c.prepareStatement(insertSql);
            ps.setString(1, maPhong);
            ps.setString(2, username);
            ps.setDate(3, ngay);
            ps.setTime(4, gioBD);
            ps.setTime(5, gioKT);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean phongDaCoLich(String maPhong) {
    String sql = "SELECT COUNT(*) FROM datphong WHERE ma_phong = ?";
    try (Connection c = DBConnection.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setString(1, maPhong);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return true;
    }
}

}
