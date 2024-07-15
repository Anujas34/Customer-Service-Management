package customer_service_management;

import java.sql.*;
import java.util.Scanner;

public class ResolutionManagement {

    public static void resolutionMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("Resolution Management");
            System.out.println("1. Provide a resolution to an inquiry or complaint");
            System.out.println("2. View resolution details");
            System.out.println("3. Update resolution information");
            System.out.println("4. Delete a resolution record");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    provideResolution(scanner);
                    break;
                case 2:
                    viewResolutionDetails(scanner);
                    break;
                case 3:
                    updateResolutionInformation(scanner);
                    break;
                case 4:
                    deleteResolution(scanner);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void provideResolution(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter inquiry ID (0 if not applicable): ");
            int inquiryId = scanner.nextInt();
            System.out.print("Enter complaint ID (0 if not applicable): ");
            int complaintId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter resolution details: ");
            String details = scanner.nextLine();

            String query = "INSERT INTO Resolution (inquiry_id, complaint_id, resolution_date, details) VALUES (?, ?, CURDATE(), ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (inquiryId == 0) {
                    statement.setNull(1, Types.INTEGER);
                } else {
                    statement.setInt(1, inquiryId);
                }

                if (complaintId == 0) {
                    statement.setNull(2, Types.INTEGER);
                } else {
                    statement.setInt(2, complaintId);
                }

                statement.setString(3, details);
                statement.executeUpdate();
                System.out.println("Resolution provided successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewResolutionDetails(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter resolution ID: ");
            int resolutionId = scanner.nextInt();

            String query = "SELECT * FROM Resolution WHERE resolution_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, resolutionId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Resolution ID: " + resultSet.getInt("resolution_id"));
                        System.out.println("Inquiry ID: " + resultSet.getInt("inquiry_id"));
                        System.out.println("Complaint ID: " + resultSet.getInt("complaint_id"));
                        System.out.println("Resolution Date: " + resultSet.getDate("resolution_date"));
                        System.out.println("Details: " + resultSet.getString("details"));
                    } else {
                        System.out.println("Resolution not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateResolutionInformation(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter resolution ID: ");
            int resolutionId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter new resolution details: ");
            String details = scanner.nextLine();

            String query = "UPDATE Resolution SET details = ? WHERE resolution_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, details);
                statement.setInt(2, resolutionId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Resolution information updated successfully.");
                } else {
                    System.out.println("Resolution not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteResolution(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.print("Enter resolution ID: ");
            int resolutionId = scanner.nextInt();

            String query = "DELETE FROM Resolution WHERE resolution_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, resolutionId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Resolution record deleted successfully.");
                } else {
                    System.out.println("Resolution not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}