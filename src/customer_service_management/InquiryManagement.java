package customer_service_management;

import java.sql.*;
import java.util.Scanner;

public class InquiryManagement {

    public static void inquiryMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("Inquiry Management");
            System.out.println("1. Record a new inquiry");
            System.out.println("2. View inquiry details");
            System.out.println("3. Update inquiry status");
            System.out.println("4. Delete an inquiry");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    recordNewInquiry(scanner);
                    break;
                case 2:
                    viewInquiryDetails(scanner);
                    break;
                case 3:
                    updateInquiryStatus(scanner);
                    break;
                case 4:
                    deleteInquiry(scanner);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void recordNewInquiry(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter inquiry description: ");
            String description = scanner.nextLine();
            System.out.print("Enter inquiry status: ");
            String status = scanner.nextLine();

            // Check if customer exists
            String checkCustomerQuery = "SELECT COUNT(*) FROM customer WHERE customer_id = ?";
            try (PreparedStatement checkCustomerStmt = connection.prepareStatement(checkCustomerQuery)) {
                checkCustomerStmt.setInt(1, customerId);
                try (ResultSet resultSet = checkCustomerStmt.executeQuery()) {
                    resultSet.next();
                    if (resultSet.getInt(1) == 0) {
                        System.out.println("Customer ID not found. Please enter a valid customer ID.");
                        return;
                    }
                }
            }

            String query = "INSERT INTO inquiry (customer_id, inquiry_date, description, status) VALUES (?, CURDATE(), ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, customerId);
                statement.setString(2, description);
                statement.setString(3, status);
                statement.executeUpdate();
                System.out.println("Inquiry recorded successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewInquiryDetails(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter inquiry ID: ");
            int inquiryId = scanner.nextInt();

            String query = "SELECT * FROM inquiry WHERE inquiry_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, inquiryId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Inquiry ID: " + resultSet.getInt("inquiry_id"));
                        System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
                        System.out.println("Inquiry Date: " + resultSet.getDate("inquiry_date"));
                        System.out.println("Description: " + resultSet.getString("description"));
                        System.out.println("Status: " + resultSet.getString("status"));
                    } else {
                        System.out.println("Inquiry not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateInquiryStatus(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter inquiry ID: ");
            int inquiryId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter new status: ");
            String status = scanner.nextLine();

            String query = "UPDATE inquiry SET status = ? WHERE inquiry_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, status);
                statement.setInt(2, inquiryId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Inquiry status updated successfully.");
                } else {
                    System.out.println("Inquiry not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteInquiry(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter inquiry ID: ");
            int inquiryId = scanner.nextInt();

            String query = "DELETE FROM inquiry WHERE inquiry_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, inquiryId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Inquiry deleted successfully.");
                } else {
                    System.out.println("Inquiry not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}