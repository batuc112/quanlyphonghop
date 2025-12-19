package dao;

import java.sql.*;

public class BaoCaoDAO {

    public double doanhThuTheoThang(int thang) {
        double tong = 0;

        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT SUM(p.gia) " +
                "FROM datphong d JOIN phonghop p " +
                "ON d.ma_phong = p.ma_phong " +
                "WHERE MONTH(d.ngay) = ?"
            );
            ps.setInt(1, thang);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tong = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tong;
    }
}
