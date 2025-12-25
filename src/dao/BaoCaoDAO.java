package dao;

import java.sql.*;
import java.util.Vector;

public class BaoCaoDAO {


    public double TongDoanhThu() {
        double tong = 0;

        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT SUM(p.gia) " +
                "FROM datphong d JOIN phonghop p " +
                "ON d.ma_phong = p.ma_phong"
            );

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tong = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tong;
    }

    
    public double DoanhThuThang(int thang) {
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

   
    public Vector<Vector<Object>> BaoCaoThang(int thang) {

        Vector<Vector<Object>> data = new Vector<>();

        try (Connection c = DBConnection.getConnection()) {

            PreparedStatement ps = c.prepareStatement(
                "SELECT p.ten_phong, " +
                "COUNT(d.ma_dat) AS so_lan_dat, " +
                "SUM(TIMESTAMPDIFF(HOUR, d.gio_bd, d.gio_kt)) AS tong_gio, " +
                "SUM(p.gia) AS doanh_thu " +
                "FROM datphong d JOIN phonghop p " +
                "ON d.ma_phong = p.ma_phong " +
                "WHERE MONTH(d.ngay) = ? " +
                "GROUP BY p.ten_phong"
            );

            ps.setInt(1, thang);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("ten_phong"));
                row.add(rs.getInt("so_lan_dat"));
                row.add(rs.getInt("tong_gio"));
                row.add(rs.getDouble("doanh_thu"));
                data.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    
    public Vector<String> getHeader() {
        Vector<String> header = new Vector<>();
        header.add("Tên phòng");
        header.add("Số lần đặt");
        header.add("Tổng giờ sử dụng");
        header.add("Doanh thu");
        return header;
    }
}
