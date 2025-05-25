import java.time.LocalDateTime;
import java.util.*;

public class Reservations extends Transactions {

    private static final Map<String, ReservationDetail> reservations = new HashMap<>();

    private static class ReservationDetail {
        String userName;
        String bookTitle;
        LocalDateTime reservedDate;

        public ReservationDetail(String userName, String bookTitle, LocalDateTime reservedDate) {
            this.userName = userName;
            this.bookTitle = bookTitle;
            this.reservedDate = reservedDate;
        }

        @Override
        public String toString() {
            return "User: " + userName + ", Book: " + bookTitle + ", Date: " + reservedDate;
        }
    }

    public void reservationMenu() {
        System.out.println("\n=== Library Reservation Menu ===");
        System.out.println("1. Check Book Availability");
        System.out.println("2. View Reservation Options");
        System.out.println("3. Make a Reservation");
        System.out.println("4. View Existing Reservations");
        System.out.println("5. Modify a Reservation");
        System.out.println("6. Cancel a Reservation");
        System.out.println("7. Return to Main Menu");
        System.out.println("8. Exit");
    }

    @Override
    public void process() {
        System.out.println("Processing a reservation request...");
    }

    public boolean checkAvailability(String bookTitle, List<String> availableBooks) {
        return availableBooks.contains(bookTitle);
    }

    public String makeReservation(String userName, String bookTitle) {
        String reservationID = generateReservationID();
        reservations.put(reservationID, new ReservationDetail(userName, bookTitle, LocalDateTime.now()));
        System.out.println("✅ Reservation successful. Reservation ID: " + reservationID);
        return reservationID;
    }

    public void viewReservations() {
        System.out.println("\n=== Current Reservations ===");
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Map.Entry<String, ReservationDetail> entry : reservations.entrySet()) {
                System.out.println("Reservation ID: " + entry.getKey() + " | " + entry.getValue());
            }
        }
    }

    public void modifyReservation(String reservationID, String newBookTitle) {
        if (reservations.containsKey(reservationID)) {
            ReservationDetail detail = reservations.get(reservationID);
            detail.bookTitle = newBookTitle;
            detail.reservedDate = LocalDateTime.now(); // update timestamp
            System.out.println("🔁 Reservation updated successfully.");
        } else {
            System.out.println("❌ Reservation ID not found.");
        }
    }

    public void cancelReservation(String reservationID) {
        if (reservations.remove(reservationID) != null) {
            System.out.println("❌ Reservation cancelled successfully.");
        } else {
            System.out.println("❌ Reservation ID not found.");
        }
    }

    private String generateReservationID() {
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Sample driver
    public static void main(String[] args) {
        Reservations res = new Reservations();
        Scanner scanner = new Scanner(System.in);
        List<String> availableBooks = Arrays.asList("1984", "Pride and Prejudice", "Harry Potter");

        int choice;
        do {
            res.reservationMenu();
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter book title to check: ");
                    String title = scanner.nextLine();
                    boolean available = res.checkAvailability(title, availableBooks);
                    System.out.println(available ? "✅ Book is available." : "❌ Book is not available.");
                }
                case 2 -> System.out.println("Options: Reserve, Modify, Cancel, View.");
                case 3 -> {
                    System.out.print("Enter your name: ");
                    String user = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String book = scanner.nextLine();
                    if (res.checkAvailability(book, availableBooks)) {
                        res.makeReservation(user, book);
                    } else {
                        System.out.println("❌ Book is currently unavailable.");
                    }
                }
                case 4 -> res.viewReservations();
                case 5 -> {
                    System.out.print("Enter reservation ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter new book title: ");
                    String newBook = scanner.nextLine();
                    res.modifyReservation(id, newBook);
                }
                case 6 -> {
                    System.out.print("Enter reservation ID to cancel: ");
                    String cancelId = scanner.nextLine();
                    res.cancelReservation(cancelId);
                }
                case 7 -> System.out.println("Returning to main menu...");
                case 8 -> System.out.println("Exiting system.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 8);

        scanner.close();
    }
}
