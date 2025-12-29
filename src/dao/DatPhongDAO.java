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

        String getGiaSql =
            "SELECT gia FROM phonghop WHERE ma_phong = ?";

        String insertSql =
            "INSERT INTO datphong(ma_phong, username, ngay, gio_bat_dau, gio_ket_thuc, tien) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection()) {

            // ===== CHECK TRÙNG =====
            PreparedStatement psCheck = c.prepareStatement(checkSql);
            psCheck.setString(1, maPhong);
            psCheck.setDate(2, ngay);
            psCheck.setTime(3, gioKT);
            psCheck.setTime(4, gioBD);

            ResultSet rs = psCheck.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) return false;

            // ===== LẤY GIÁ PHÒNG =====
            PreparedStatement psGia = c.prepareStatement(getGiaSql);
            psGia.setString(1, maPhong);
            ResultSet rsGia = psGia.executeQuery();
            rsGia.next();
            double gia = rsGia.getDouble(1);

            // ===== TÍNH SỐ GIỜ =====
            long diffMs = gioKT.getTime() - gioBD.getTime();
            double soGio = diffMs / (1000.0 * 60 * 60);

            double tien = soGio * gia;

            // ===== INSERT =====
            PreparedStatement ps = c.prepareStatement(insertSql);
            ps.setString(1, maPhong);
            ps.setString(2, username);
            ps.setDate(3, ngay);
            ps.setTime(4, gioBD);
            ps.setTime(5, gioKT);
            ps.setDouble(6, tien);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== KIỂM TRA PHÒNG ĐÃ CÓ LỊCH =====
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
    public ResultSet lichTheoPhong(String maPhong) {
    String sql = """
        SELECT ngay, gio_bat_dau, gio_ket_thuc, username
        FROM datphong
        WHERE ma_phong = ?
        ORDER BY ngay, gio_bat_dau
    """;

    try {
        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, maPhong);
        return ps.executeQuery();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    public ResultSet lichCaNhan(String username) {
    try {
        Connection c = DBConnection.getConnection();
        String sql = """
            SELECT id, ma_phong, ngay, gio_bat_dau, gio_ket_thuc
            FROM datphong
            WHERE username = ?
            ORDER BY ngay DESC
        """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, username);
        return ps.executeQuery();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    // ===== HUỶ ĐẶT PHÒNG =====
public boolean huyDatPhong(int id, String username) {
    String sql = """
        DELETE FROM datphong
        WHERE id = ? AND username = ?
    """;

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
