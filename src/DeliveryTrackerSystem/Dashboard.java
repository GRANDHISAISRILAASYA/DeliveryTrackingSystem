package DeliveryTrackerSystem;

	import java.sql.Connection;
	import java.util.Scanner;

	public class Dashboard {
	    public static void userDashboard(Connection conn, String username, Scanner sc) {
	        int choice;
	        do {
	            System.out.println("\nWelcome " + username + "!");
	            System.out.println("1. Place Order");
	            System.out.println("2. View Order Status");
	            System.out.println("3. Logout");
	            System.out.print("\nEnter choice: ");
	            choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {
	                case 1:
	                    OrderService.placeOrder(conn, username, sc);
	                    break;
	                case 2:
	                    OrderService.viewOrderStatus(conn, username, sc);
	                    break;
	                case 3:
	                    System.out.println("\n✅ Logged Out Successfully.");
	                    break;
	                default:
	                    System.out.println("Invalid choice!");
	            }
	        } while (choice != 3);
	    }

	    public static void adminDashboard(Connection conn, String username, Scanner sc) {
	        int choice;
	        do {
	            System.out.println("\nWelcome " + username + "!");
	            System.out.println("1. Add Items");
	            System.out.println("2. View Orders");
	            System.out.println("3. Assign Delivery Boy");
	            System.out.println("4. Update Order Status");
	            System.out.println("5. Logout");
	            System.out.print("\nEnter choice: ");
	            choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {
	                case 1:
	                    ItemService.addItem(conn, sc);
	                    break;
	                case 2:
	                    OrderService.viewAllOrders(conn, sc);
	                    break;
	                case 3:
	                    OrderService.assignDeliveryBoy(conn, sc);
	                    break;
	                case 4:
	                    OrderService.updateOrderStatus(conn, sc);
	                    break;
	                case 5:
	                    System.out.println("\n✅ Logged Out Successfully.");
	                    break;
	                default:
	                    System.out.println("Invalid choice!");
	            }
	        } while (choice != 5);
	    }
	}


