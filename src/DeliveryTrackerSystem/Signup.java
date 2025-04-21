package DeliveryTrackerSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Signup {
    public static void createUser(Connection conn, String username, String password, String email) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, email);
        ps.executeUpdate();
    }

    public static void createAdmin(Connection conn, String username, String password, String email) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO admins (username, password, email) VALUES (?, ?, ?)");
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, email);
        ps.executeUpdate();
    }

    public static boolean loginUser(Connection conn, String username, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static boolean loginAdmin(Connection conn, String username, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM admins WHERE username = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
