package DeliveryTrackerSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ItemService {
    public static void addItem(Connection conn, Scanner sc) {
        try {
            System.out.print("\nEnter Item Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Item Price: ");
            double price = sc.nextDouble();
            sc.nextLine();
            String sql="INSERT INTO items (name, price) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.executeUpdate();

            System.out.println("\n✅ Item Added Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
