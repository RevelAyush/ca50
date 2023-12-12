/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuldeepshukla
 */
import java.sql.*;
import java.util.Scanner;


public class ca50 {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ca50";
    static final String USER = "ooc2023";
    static final String PASS = "ooc2023";

    static Connection conn = null;
    static Statement stmt = null;

    public static void main(String[] args) {
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Create tables if not exists
            createTables();

            // Display menu based on user/admin
              Scanner scanner = new Scanner(System.in);
            System.out.println("Are you an admin or a user? (admin/user)");
            String userType = scanner.next();

            if (userType.equalsIgnoreCase("admin")) {
                adminMenu();
            } else if (userType.equalsIgnoreCase("user")) {
                userMenu();
            } else {
                System.out.println("Invalid choice. Exiting...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    private static void createTables() {
        try {
            stmt = conn.createStatement();

            // Create User table
            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(255) UNIQUE NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                     "email VARCHAR(255) NOT NULL," + 
                    "name VARCHAR(255) NOT NULL," +  
                    "lastname VARCHAR(255) NOT NULL)";
            
            stmt.executeUpdate(createUserTableSQL);

            // Create Work table
            String createWorkTableSQL = "CREATE TABLE IF NOT EXISTS works (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "work_description VARCHAR(255)," +
                    "tax_amount DOUBLE," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))";
            stmt.executeUpdate(createWorkTableSQL);

            System.out.println("Tables created successfully.");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static void adminMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter admin username: ");
        String adminUsername = scanner.next();

        System.out.println("Enter admin password: ");
        String adminPassword = scanner.next();

        if (adminUsername.equals("CCT") && adminPassword.equals("Dublin")) {
            System.out.println("Admin login successful.");

            while (true) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. View all users");
                System.out.println("2. Modify user data");
                System.out.println("3. Delete user");
                System.out.println("4. View all user works");
                System.out.println("5. Exit");

                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewAllUsers();
                        break;
                    case 2:
                        modifyUserData();
                        break;
                    case 3:
                        deleteUser();
                        break;
                    case 4:
                        viewAllUserWorks();
                        break;
                    case 5:
                        System.out.println("Exiting admin menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } else {
            System.out.println("Invalid admin credentials. Exiting...");
        }
    }

    
}
