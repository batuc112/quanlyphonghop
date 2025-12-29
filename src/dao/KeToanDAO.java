package dao;

import java.sql.*;
import java.util.List;

public class KeToanDAO {

    // ===== LỊCH CHƯA THANH TOÁN =====
    public ResultSet lichChuaThanhToan() {
        try {
            Connection c = DBConnection.getConnection();
            String sql = """
                SELECT id, ma_phong, username, ngay, tien
                FROM datphong
                WHERE trang_thai = 'chua_thanh_toan'
                ORDER BY ngay
            """;
            return c.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ===== THANH TOÁN =====
    public boolean thanhToan(
        int datPhongId,
        String username,
        String maPhong,
        Date ngay,
        double tienPhong,
        List<Object[]> dsSP
    ) {
        Connection c = null;
        try {
            c = DBConnection.getConnection();
            c.setAutoCommit(false);

            double tong = tienPhong;
            for (Object[] o : dsSP) {
                tong += (double) o[3];
            }

            // ---- HÓA ĐƠN ----
            PreparedStatement psHD = c.prepareStatement(
                "INSERT INTO hoadon(username, ma_phong, ngay, tong_tien) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            psHD.setString(1, username);
            psHD.setString(2, maPhong);
            psHD.setDate(3, ngay);
            psHD.setDouble(4, tong);
            psHD.executeUpdate();

            ResultSet rs = psHD.getGeneratedKeys();
            rs.next();
            int hoaDonId = rs.getInt(1);

            // ---- CHI TIẾT ----
            PreparedStatement psCT = c.prepareStatement(
                "INSERT INTO hoadon_chitiet(hoadon_id, sanpham_id, so_luong, thanh_tien) VALUES (?,?,?,?)"
            );

            for (Object[] o : dsSP) {
                psCT.setInt(1, hoaDonId);
                psCT.setInt(2, (int) o[0]);
                psCT.setInt(3, (int) o[2]);
                psCT.setDouble(4, (double) o[3]);
                psCT.addBatch();
            }
            psCT.executeBatch();

            // ---- CẬP NHẬT TRẠNG THÁI ----
            PreparedStatement psUpd = c.prepareStatement(
              "DELETE FROM datphong WHERE id=?"
            );
            psUpd.setInt(1, datPhongId);
            psUpd.executeUpdate();

            c.commit();
            return true;

        }
        
        catch (Exception e) {
            try { if (c != null) c.rollback(); } catch (Exception ignored) {}
            e.printStackTrace();
            return false;
        }
    }
}
