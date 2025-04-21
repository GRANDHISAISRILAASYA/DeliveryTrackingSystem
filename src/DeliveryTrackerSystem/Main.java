package DeliveryTrackerSystem;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.util.Scanner;

	public class Main {
	    private static final String DB_URL = "jdbc:mysql://localhost:3306/deliverytrackersystem";
	    private static final String DB_USER = "root";
	    private static final String DB_PASS = "root";

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        int ch;

	        do {
	            System.out.println("\nWelcome to Delivery Tracker System");
	            System.out.println("1. User Login");
	            System.out.println("2. User Register");
	            System.out.println("3. Admin Login");
	            System.out.println("4. Admin Register");
	            System.out.println("5. Exit");
	            System.out.print("\nEnter choice: ");
	            ch = sc.nextInt();
	            sc.nextLine();

	            switch (ch) {
	                case 1:
	                    userLogin(sc);
	                    break;
	                case 2:
	                    userRegister(sc);
	                    break;
	                case 3:
	                    adminLogin(sc);
	                    break;
	                case 4:
	                    adminRegister(sc);
	                    break;
	                case 5:
	                    System.out.println("Exiting...");
	                    break;
	                default:
	                    System.out.println("Invalid choice!");
	            }
	        } while (ch != 5);
	    }

	    private static void userLogin(Scanner sc) {
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	            System.out.print("\nEnter Username: ");
	            String username = sc.nextLine();
	            System.out.print("Enter Password: ");
	            String password = sc.nextLine();

	            if (Signup.loginUser(conn, username, password)) {
	                System.out.println("\n✅ Login Successful!");
	                Dashboard.userDashboard(conn, username, sc);
	            } else {
	                System.out.println("\n❌ Login failed!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private static void userRegister(Scanner sc) {
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	            System.out.print("\nEnter Username: ");
	            String username = sc.nextLine();
	            System.out.print("Enter Password: ");
	            String password = sc.nextLine();
	            System.out.print("Enter Email: ");
	            String email = sc.nextLine();

	            Signup.createUser(conn, username, password, email);
	            System.out.println("\n✅ User Registered Successfully!");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private static void adminLogin(Scanner sc) {
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	            System.out.print("\nEnter Admin Username: ");
	            String username = sc.nextLine();
	            System.out.print("Enter Password: ");
	            String password = sc.nextLine();

	            if (Signup.loginAdmin(conn, username, password)) {
	                System.out.println("\n✅ Admin Login Successful!");
	                Dashboard.adminDashboard(conn, username, sc);
	            } else {
	                System.out.println("\n❌ Login failed!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private static void adminRegister(Scanner sc) {
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	            System.out.print("\nEnter Admin Username: ");
	            String username = sc.nextLine();
	            System.out.print("Enter Password: ");
	            String password = sc.nextLine();
	            System.out.print("Enter Email: ");
	            String email = sc.nextLine();

	            Signup.createAdmin(conn, username, password, email);
	            System.out.println("\n✅ Admin Registered Successfully!");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

