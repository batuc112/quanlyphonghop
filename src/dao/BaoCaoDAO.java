package dao;

import java.sql.*;

public class BaoCaoDAO {


    public ResultSet baoCaoPhong() {
    String sql = """
            SELECT 
                p.ma_phong,
                p.ten_phong,
                COUNT(d.id) AS so_lan_su_dung,
                IFNULL(SUM(TIMESTAMPDIFF(MINUTE, d.gio_bat_dau, d.gio_ket_thuc)) / 60, 0) AS tong_gio_su_dung,
                IFNULL(SUM(d.tien), 0) AS doanh_thu
            FROM phonghop p
            LEFT JOIN datphong d ON p.ma_phong = d.ma_phong AND d.trang_thai = 'da_thanh_toan'
            GROUP BY p.ma_phong, p.ten_phong
            ORDER BY doanh_thu DESC
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


 
    public double tongDoanhThu() {
    String sql = "SELECT IFNULL(SUM(tien), 0) FROM datphong WHERE trang_thai='da_thanh_toan'";
    try (Connection c = DBConnection.getConnection();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        rs.next();
        return rs.getDouble(1);

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}

}
