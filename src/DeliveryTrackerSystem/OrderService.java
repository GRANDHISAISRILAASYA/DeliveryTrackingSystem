package DeliveryTrackerSystem;

import java.sql.*;
import java.util.Scanner;

// Service class that handles order-related operations
public class OrderService {

    // Method for user to place a new order
    public static void placeOrder(Connection conn, String username, Scanner sc) {
        try {
            // Step 1: Get user ID from username
            PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (!userRs.next()) {
                // If user not found, return
                System.out.println("User not found!");
                return;
            }

            int userId = userRs.getInt("id"); // Retrieve user ID

            // Step 2: Show all available items
            System.out.println("\nAvailable Items:");
            Statement itemStmt = conn.createStatement();
            ResultSet itemRs = itemStmt.executeQuery("SELECT * FROM items");

            while (itemRs.next()) {
                // Display item ID, name, and price
                System.out.println(itemRs.getInt("id") + ". " + itemRs.getString("name") + " - ₹" + itemRs.getDouble("price"));
            }

            // Step 3: User selects items to order
            System.out.print("\nEnter item number to order (comma separated): ");
            String[] itemNumbers = sc.nextLine().split(",");

            StringBuilder orderedItems = new StringBuilder(); // To store ordered item names
            double totalPrice = 0; // Total order price

            // Step 4: Fetch details of selected items and calculate total
            for (String numStr : itemNumbers) {
                int itemId = Integer.parseInt(numStr.trim());

                // Get item name and price using item ID
                PreparedStatement itemDetailStmt = conn.prepareStatement("SELECT name, price FROM items WHERE id = ?");
                itemDetailStmt.setInt(1, itemId);
                ResultSet itemDetailRs = itemDetailStmt.executeQuery();

                if (itemDetailRs.next()) {
                    String name = itemDetailRs.getString("name");
                    double price = itemDetailRs.getDouble("price");

                    if (orderedItems.length() > 0) {
                        orderedItems.append(", ");
                    }
                    orderedItems.append(name); // Add item name to list
                    totalPrice += price; // Add price to total
                }
            }

            // Step 5: Take delivery address
            System.out.print("Enter delivery address: ");
            String address = sc.nextLine();

            // Step 6: Insert order into orders table
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders (user_id, items, total_price, address, status) VALUES (?, ?, ?, ?, 'Pending')",
                Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, orderedItems.toString());
            ps.setDouble(3, totalPrice);
            ps.setString(4, address);
            ps.executeUpdate();

            // Step 7: Show success message with Order ID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("\n✅ Order Placed Successfully! Your Order ID: " + rs.getInt(1));
            }

        } catch (Exception e) {
            // Catch and print any errors
            e.printStackTrace();
        }
    }

    // Method for user to view their own order status
    public static void viewOrderStatus(Connection conn, String username, Scanner sc) {
        try {
            // Get user ID using username
            PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (!userRs.next()) {
                System.out.println("User not found!");
                return;
            }

            int userId = userRs.getInt("id");

            // Fetch all orders placed by the user
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders WHERE user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nYour Orders:");
            while (rs.next()) {
                // Print order details
                System.out.println("\nOrder ID: " + rs.getInt("id"));
                System.out.println("Items: " + rs.getString("items"));
                System.out.println("Total Price: ₹" + rs.getDouble("total_price"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Status: " + rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method for Admin to view all orders placed by all users
    public static void viewAllOrders(Connection conn, Scanner sc) {
        try {
            Statement stmt = conn.createStatement();

            // Join orders with users to get username along with order
            ResultSet rs = stmt.executeQuery("SELECT o.*, u.username FROM orders o JOIN users u ON o.user_id = u.id");

            System.out.println("\nAll Orders:");
            while (rs.next()) {
                // Print full order details
                System.out.println("\nOrder ID: " + rs.getInt("id"));
                System.out.println("Customer: " + rs.getString("username"));
                System.out.println("Items: " + rs.getString("items"));
                System.out.println("Total Price: ₹" + rs.getDouble("total_price"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Delivery Boy: " + rs.getString("delivery_boy"));
                System.out.println("Status: " + rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method for Admin to assign a delivery boy to an order
    public static void assignDeliveryBoy(Connection conn, Scanner sc) {
        try {
            // Get Order ID and delivery boy name from admin
            System.out.print("\nEnter Order ID: ");
            int orderId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Delivery Boy Name: ");
            String deliveryBoy = sc.nextLine();

            // Update the order with assigned delivery boy
            PreparedStatement ps = conn.prepareStatement("UPDATE orders SET delivery_boy = ? WHERE id = ?");
            ps.setString(1, deliveryBoy);
            ps.setInt(2, orderId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Delivery Boy Assigned Successfully!");
            } else {
                System.out.println("❌ Order not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method for Admin to update the delivery status of an order
    public static void updateOrderStatus(Connection conn, Scanner sc) {
        try {
            // Get Order ID and new status from admin
            System.out.print("\nEnter Order ID: ");
            int orderId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Status (Pending / Out for Delivery / Delivered): ");
            String status = sc.nextLine();

            // Update the status in orders table
            PreparedStatement ps = conn.prepareStatement("UPDATE orders SET status = ? WHERE id = ?");
            ps.setString(1, status);
            ps.setInt(2, orderId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Order Status Updated Successfully!");
            } else {
                System.out.println("❌ Order not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner class to represent an item (used for internal logic if needed)
    public static class Item {
        private int id;
        private String name;
        private double price;

        // Constructor
        public Item(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        // Getter for name
        public String getName() {
            return name;
        }

        // Getter for price
        public double getPrice() {
            return price;
        }
    }
}

