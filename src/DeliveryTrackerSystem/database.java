package DeliveryTrackerSystem;
import java.sql.*;

public class database {
	    // Database configuration (update with your actual credentials)
	    private static final String url = "jdbc:mysql://localhost:3306/delivery_tracker_final";
	    private static final String username = "root";
	    private static final String password = "root";
	    
	    // Initialize all tables including admins
	    public static void initializeDatabase() {
	        try (Connection conn = getConnection();
	             Statement stmt = conn.createStatement()) {
	            
	            // Create users table (regular users only)
	            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
	                "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
	                "username VARCHAR(50) UNIQUE NOT NULL, " +
	                "password VARCHAR(255) NOT NULL, " + // Store hashed passwords
	            
	            
	            // Create separate admins table
	            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS admins (" +
	                "admin_id INT AUTO_INCREMENT PRIMARY KEY, " +
	                "username VARCHAR(50) UNIQUE NOT NULL, " +
	                "password VARCHAR(255) NOT NULL, " +

	            
	            // Create items table
	            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS items (" +
	                "item_id INT AUTO_INCREMENT PRIMARY KEY, " +
	                "name VARCHAR(100) NOT NULL, " +
	                "price DECIMAL(10,2) NOT NULL, " +
	                "created_by INT, " + // References admins table
	                "is_available BOOLEAN DEFAULT TRUE, " +
	                "FOREIGN KEY (created_by) REFERENCES admins(admin_id))");
	            
	            // Create orders table (enhanced with delivery tracking)
	            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (" +
	                "order_id INT AUTO_INCREMENT PRIMARY KEY, " +
	                "user_id INT NOT NULL, " +
	                "item_id INT NOT NULL, " +
	                "quantity INT NOT NULL DEFAULT 1, " +
	                "status ENUM('Pending','Processing','Shipped','Delivered','Cancelled') DEFAULT 'Pending', " +
	                "delivery_address TEXT NOT NULL, " +
	                "delivery_boy_name TEXT, " +
	                "assigned_admin INT, " + // Admin who processed the order
	                "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
	                "FOREIGN KEY (item_id) REFERENCES items(item_id), " +
	                "FOREIGN KEY (assigned_admin) REFERENCES admins(admin_id))");
	            
	            
	            System.out.println("[SUCCESS] Database initialized with all tables");
	        } catch (SQLException e) {
	            System.err.println("[ERROR] Database initialization failed: " + e.getMessage());
	        }
	    }
	    


	    
	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(url, username, password);
	    }
	

}
