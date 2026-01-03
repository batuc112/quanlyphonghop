package dao;

import java.sql.*;

public class AdminDAO {

    public ResultSet tatCaLich() {
        String sql = """
            SELECT 
                d.id,
                d.ma_phong,
                d.username,
                d.ngay,
                d.gio_bat_dau,
                d.gio_ket_thuc,
                d.trang_thai
            FROM datphong d
            ORDER BY d.ngay DESC, d.gio_bat_dau
        """;

        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
