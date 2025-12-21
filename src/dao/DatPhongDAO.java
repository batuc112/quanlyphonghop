package dao;

import java.sql.*;

public class DatPhongDAO {

    public boolean TrungLich(String maPhong, Date ngay, Time bd, Time kt) {
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT COUNT(*) FROM datphong " +
                "WHERE ma_phong=? AND ngay=? " +
                "AND (? < gio_kt AND ? > gio_bd)"
            );
            ps.setString(1, maPhong);
            ps.setDate(2, ngay);
            ps.setTime(3, bd);
            ps.setTime(4, kt);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0; 
        } catch (Exception e) {
            e.printStackTrace();
            return true; 
        }
    }

    
    public boolean datPhong(String mp, Date ngay, Time bd, Time kt) {

       
        if (TrungLich(mp, ngay, bd, kt)) {
            return false;
        }

        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO datphong(ma_phong,ngay,gio_bd,gio_kt) VALUES(?,?,?,?)"
            );
            ps.setString(1, mp);
            ps.setDate(2, ngay);
            ps.setTime(3, bd);
            ps.setTime(4, kt);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
