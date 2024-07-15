package customer_service_management;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Customer Service Management System");
            System.out.println("1. Manage Inquiries");
            System.out.println("2. Manage Complaints");
            System.out.println("3. Manage Resolutions");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    InquiryManagement.inquiryMenu(scanner);
                    break;
                case 2:
                    ComplaintManagement.complaintMenu(scanner);
                    break;
                case 3:
                    ResolutionManagement.resolutionMenu(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}