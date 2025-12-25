package dao;

import java.sql.*;

public class DatPhongDAO {

    public boolean datPhong(String ma, Date ngay, Time bd, Time kt) {

        String checkSQL =
            "SELECT COUNT(*) FROM datphong " +
            "WHERE ma_phong=? AND ngay=? " +
            "AND ( (? < gio_ket_thuc) AND (? > gio_bat_dau) )";

        String insertSQL =
            "INSERT INTO datphong(ma_phong,ngay,gio_bat_dau,gio_ket_thuc) " +
            "VALUES (?,?,?,?)";

        try (Connection c = DBConnection.getConnection()) {

            // check trùng
            PreparedStatement ps = c.prepareStatement(checkSQL);
            ps.setString(1, ma);
            ps.setDate(2, ngay);
            ps.setTime(3, bd);
            ps.setTime(4, kt);

            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // TRÙNG
            }

            // insert
            ps = c.prepareStatement(insertSQL);
            ps.setString(1, ma);
            ps.setDate(2, ngay);
            ps.setTime(3, bd);
            ps.setTime(4, kt);
            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
