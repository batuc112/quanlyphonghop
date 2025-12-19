package dao;
import java.sql.*;

public class DatPhongDAO {
    public void datPhong(String mp,Date ngay,Time bd,Time kt){
        try(Connection c=DBConnection.getConnection()){
            PreparedStatement ps=c.prepareStatement(
             "INSERT INTO datphong(ma_phong,ngay,gio_bd,gio_kt) VALUES(?,?,?,?)");
            ps.setString(1,mp);
            ps.setDate(2,ngay);
            ps.setTime(3,bd);
            ps.setTime(4,kt);
            ps.executeUpdate();
        }catch(Exception e){}
    }
}
