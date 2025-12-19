package dao;
import java.sql.*;

public class UserDAO {
    public String login(String u,String p){
        try(Connection c=DBConnection.getConnection()){
            PreparedStatement ps=c.prepareStatement(
             "SELECT role FROM users WHERE username=? AND password=?");
            ps.setString(1,u); ps.setString(2,p);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) return rs.getString("role");
        }catch(Exception e){}
        return null;
    }
    public boolean register(String u,String p){
    try(Connection c=DBConnection.getConnection()){
        PreparedStatement ps=c.prepareStatement(
         "INSERT INTO users(username,password,role) VALUES(?,?,?)");
        ps.setString(1,u);
        ps.setString(2,p);
        ps.setString(3,"user");
        ps.executeUpdate();
        return true;
    }catch(Exception e){}
    return false;
}

}
