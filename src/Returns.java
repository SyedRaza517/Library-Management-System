import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Returns extends Transactions {

    private static final List<ReturnRecord> returnHistory = new ArrayList<>();

    static class ReturnRecord {
        String bookName;
        LocalDateTime returnDate;
        String owner;
        String libraryName;
        String location;
        float price;
        float lateFee;

        public ReturnRecord(String bookName, LocalDateTime returnDate, String owner,
                            String libraryName, String location, float price, float lateFee) {
            this.bookName = bookName;
            this.returnDate = returnDate;
            this.owner = owner;
            this.libraryName = libraryName;
            this.location = location;
            this.price = price;
            this.lateFee = lateFee;
        }

        @Override
        public String toString() {
            return "Book: " + bookName +
                    ", Returned On: " + returnDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    ", Owner: " + owner +
                    ", Library: " + libraryName +
                    ", Location: " + location +
                    ", Price: $" + price +
                    ", Late Fee: $" + lateFee;
        }
    }

    @Override
    public void process() {
        System.out.println("Returns process initiated...");
    }

    public void returnBorrowedBook(String bookName, LocalDateTime dueDate, LocalDateTime actualReturnDate,
                                   String owner, String libraryName, String location, float price) {

        float lateFee = calculateLateFee(dueDate, actualReturnDate);
        System.out.println("Processing borrowed book return...");
        System.out.println("Book Name     : " + bookName);
        System.out.println("Due Date      : " + dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Return Date   : " + actualReturnDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Owner        : " + owner);
        System.out.println("Library       : " + libraryName);
        System.out.println("Location      : " + location);
        System.out.println("Price        : $" + price);
        System.out.println("Late Fee     : $" + lateFee);

        logReturnTransaction(new ReturnRecord(bookName, actualReturnDate, owner, libraryName, location, price, lateFee));
    }

    public void returnSoldBook(String bookName, String owner, String libraryName, String location, float price, boolean refundEligible) {
        System.out.println("Processing return of sold book...");
        System.out.println("Book Name : " + bookName);
        System.out.println("Owner     : " + owner);
        System.out.println("Library   : " + libraryName);
        System.out.println("Location  : " + location);
        System.out.println("Price     : $" + price);

        if (refundEligible) {
            System.out.println("Refund Status: Eligible");
            // Additional refund logic here
        } else {
            System.out.println("Refund Status: Not Eligible");
        }

        // Log the return
        logReturnTransaction(new ReturnRecord(bookName, LocalDateTime.now(), owner, libraryName, location, price, 0));
    }

    public float calculateLateFee(LocalDateTime dueDate, LocalDateTime returnDate) {
        if (returnDate.isAfter(dueDate)) {
            long daysLate = Duration.between(dueDate, returnDate).toDays();
            float feePerDay = 2.0f; // flat fee per day late
            return daysLate * feePerDay;
        }
        return 0;
    }

    public void logReturnTransaction(ReturnRecord record) {
        returnHistory.add(record);
        System.out.println("Return transaction logged.");
    }

    public void viewReturnHistory() {
        System.out.println("\n=== Return History ===");
        if (returnHistory.isEmpty()) {
            System.out.println("No returns logged yet.");
            return;
        }
        for (ReturnRecord r : returnHistory) {
            System.out.println(r);
        }
    }

    public boolean validateReturn(String bookName, String owner, List<String> libraryBooks) {
        boolean valid = libraryBooks.contains(bookName);
        if (valid) {
            System.out.println("Return is valid for book: " + bookName);
        } else {
            System.out.println("Invalid return. Book '" + bookName + "' does not belong to this library.");
        }
        return valid;
    }

    public void menuOfReturns() {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();

        while (true) {
            System.out.println("\n*************************** Return Menu ******************************");
            System.out.println("1. Return Borrowed Book");
            System.out.println("2. Return Sold Book");
            System.out.println("3. View Return History");
            System.out.println("4. Main Menu");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int num = scanner.nextInt();
            scanner.nextLine();

            switch (num) {
                case 1 -> {
                    System.out.print("Enter book name: ");
                    String bookName = scanner.nextLine();

                    System.out.print("Enter due date (yyyy-MM-dd): ");
                    String dueDateStr = scanner.nextLine();
                    LocalDateTime dueDate = LocalDateTime.parse(dueDateStr + "T00:00:00");

                    LocalDateTime returnDate = LocalDateTime.now();

                    System.out.print("Enter owner: ");
                    String owner = scanner.nextLine();

                    System.out.print("Enter library name: ");
                    String libraryName = scanner.nextLine();

                    System.out.print("Enter location: ");
                    String location = scanner.nextLine();

                    System.out.print("Enter price: ");
                    float price = scanner.nextFloat();
                    scanner.nextLine();

                    returnBorrowedBook(bookName, dueDate, returnDate, owner, libraryName, location, price);
                }
                case 2 -> {
                    System.out.print("Enter book name: ");
                    String bookName = scanner.nextLine();

                    System.out.print("Enter owner: ");
                    String owner = scanner.nextLine();

                    System.out.print("Enter library name: ");
                    String libraryName = scanner.nextLine();

                    System.out.print("Enter location: ");
                    String location = scanner.nextLine();

                    System.out.print("Enter price: ");
                    float price = scanner.nextFloat();
                    scanner.nextLine();

                    System.out.print("Is refund eligible? (true/false): ");
                    boolean refundEligible = scanner.nextBoolean();
                    scanner.nextLine();

                    returnSoldBook(bookName, owner, libraryName, location, price, refundEligible);
                }
                case 3 -> viewReturnHistory();
                case 4 -> {
                    menu.menuItems(); // assuming this takes user to main menu
                    return;
                }
                case 5 -> {
                    System.out.println("Exiting return menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select from the menu.");
            }
        }
    }
}
