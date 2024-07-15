package customer_service_management;

import java.sql.*;
import java.util.Scanner;

public class ComplaintManagement {

    public static void complaintMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("Complaint Management");
            System.out.println("1. Register a new complaint");
            System.out.println("2. View complaint details");
            System.out.println("3. Update complaint status");
            System.out.println("4. Delete a complaint");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerNewComplaint(scanner);
                    break;
                case 2:
                    viewComplaintDetails(scanner);
                    break;
                case 3:
                    updateComplaintStatus(scanner);
                    break;
                case 4:
                    deleteComplaint(scanner);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void registerNewComplaint(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter complaint description: ");
            String description = scanner.nextLine();
            System.out.print("Enter complaint status: ");
            String status = scanner.nextLine();

            String query = "INSERT INTO Complaint (customer_id, complaint_date, description, status) VALUES (?, CURDATE(), ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, customerId);
                statement.setString(2, description);
                statement.setString(3, status);
                statement.executeUpdate();
                System.out.println("Complaint registered successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewComplaintDetails(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter complaint ID: ");
            int complaintId = scanner.nextInt();

            String query = "SELECT * FROM Complaint WHERE complaint_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, complaintId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Complaint ID: " + resultSet.getInt("complaint_id"));
                        System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
                        System.out.println("Complaint Date: " + resultSet.getDate("complaint_date"));
                        System.out.println("Description: " + resultSet.getString("description"));
                        System.out.println("Status: " + resultSet.getString("status"));
                    } else {
                        System.out.println("Complaint not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateComplaintStatus(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter complaint ID: ");
            int complaintId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter new status: ");
            String status = scanner.nextLine();

            String query = "UPDATE Complaint SET status = ? WHERE complaint_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, status);
                statement.setInt(2, complaintId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Complaint status updated successfully.");
                } else {
                    System.out.println("Complaint not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteComplaint(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter complaint ID: ");
            int complaintId = scanner.nextInt();

            String query = "DELETE FROM Complaint WHERE complaint_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, complaintId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Complaint deleted successfully.");
                } else {
                    System.out.println("Complaint not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}