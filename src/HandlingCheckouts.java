import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HandlingCheckouts extends Transactions {

    String nameOfBuyer;
    float price;
    LocalDateTime date;
    String nameOfBook;
    String sellerName;
    String paymentMethod;
    String location;

    private static final List<HandlingCheckouts> transactionHistory = new ArrayList<>();
    private static final Set<String> validPaymentMethods = Set.of("Cash", "Card", "UPI", "Online");

    public HandlingCheckouts() {
    }

    public HandlingCheckouts(String nameOfBook, String sellerName,
                             String paymentMethod,
                             String location, LocalDateTime date,
                             float price, String nameOfBuyer) {
        this.nameOfBook = nameOfBook;
        this.sellerName = sellerName;
        this.paymentMethod = paymentMethod;
        this.location = location;
        this.date = date;
        this.price = price;
        this.nameOfBuyer = nameOfBuyer;
    }

    @Override
    public void process() {
        System.out.println("Processing checkout for book: " + nameOfBook);
    }

    public void handlingCheckoutsMenu() {
        System.out.println("\n=== Library Checkout System Menu ===");
        System.out.println("1. Print Receipt");
        System.out.println("2. Generate Invoice");
        System.out.println("3. Apply Discount");
        System.out.println("4. Log Transaction");
        System.out.println("5. View All Transactions");
        System.out.println("6. Validate Payment Method");
        System.out.println("7. Display Transaction Summary");
        System.out.println("8. Exit");
    }

    public void receipt(String bookName, float price, String libraryName,
                        String paymentMethod, LocalDateTime date, String location) {

        System.out.println("\n===== Receipt =====");
        System.out.println("Book Name      : " + bookName);
        System.out.println("Price          : $" + price);
        System.out.println("Library Name   : " + libraryName);
        System.out.println("Payment Method : " + paymentMethod);
        System.out.println("Date           : " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Location       : " + location);
    }

    public void receipt(String bookName, float price, String paymentMethod) {
        System.out.println("\n===== Receipt (Simple) =====");
        System.out.println("Book Name      : " + bookName);
        System.out.println("Price          : $" + price);
        System.out.println("Payment Method : " + paymentMethod);
    }

    public void generateInvoice(float taxRate) {
        float tax = price * taxRate;
        float total = price + tax;
        System.out.println("\n===== Invoice =====");
        System.out.println("Buyer         : " + nameOfBuyer);
        System.out.println("Book          : " + nameOfBook);
        System.out.println("Base Price    : $" + price);
        System.out.println("Tax (" + (int)(taxRate * 100) + "%)     : $" + tax);
        System.out.println("Total Amount  : $" + total);
    }

    public void applyDiscount(float discountRate) {
        float discount = price * discountRate;
        float newPrice = price - discount;
        System.out.println("\nApplying discount of " + (int)(discountRate * 100) + "%");
        System.out.println("Original Price: $" + price);
        System.out.println("Discount      : $" + discount);
        System.out.println("New Price     : $" + newPrice);
        price = newPrice; // update current price
    }

    public void validatePaymentMethod() {
        System.out.println("\nValidating payment method...");
        if (validPaymentMethods.contains(paymentMethod)) {
            System.out.println("✅ Payment method '" + paymentMethod + "' is valid.");
        } else {
            System.out.println("❌ Payment method '" + paymentMethod + "' is invalid.");
        }
    }

    public void logTransaction() {
        transactionHistory.add(this);
        System.out.println("\n📌 Transaction logged successfully.");
    }

    public void displayTransactionSummary() {
        System.out.println("\n===== Transaction Summary =====");
        System.out.println("Book      : " + nameOfBook);
        System.out.println("Buyer     : " + nameOfBuyer);
        System.out.println("Date      : " + date);
        System.out.println("Price     : $" + price);
        System.out.println("Paid via  : " + paymentMethod);
        System.out.println("Location  : " + location);
    }

    public static void viewAllTransactions() {
        System.out.println("\n📚 All Logged Transactions:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (HandlingCheckouts checkout : transactionHistory) {
                System.out.println(checkout);
            }
        }
    }

    @Override
    public String toString() {
        return "\nCheckout => " +
                "Book='" + nameOfBook + '\'' +
                ", Buyer='" + nameOfBuyer + '\'' +
                ", Price=$" + price +
                ", Date=" + date +
                ", Seller='" + sellerName + '\'' +
                ", Payment='" + paymentMethod + '\'' +
                ", Location='" + location + '\'';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HandlingCheckouts checkout = new HandlingCheckouts(
                "Harry Potter", "Central Library", "Card", "London",
                LocalDateTime.now(), 99.99f, "Vasile"
        );

        int choice;
        do {
            checkout.handlingCheckoutsMenu();
            System.out.print("\nEnter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Try again.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> checkout.receipt(checkout.nameOfBook, checkout.price,
                        "Central Library", checkout.paymentMethod,
                        checkout.date, checkout.location);

                case 2 -> checkout.generateInvoice(0.07f); // 7% tax
                case 3 -> {
                    System.out.print("Enter discount % (e.g., 0.10 for 10%): ");
                    float discount = scanner.nextFloat();
                    checkout.applyDiscount(discount);
                }
                case 4 -> checkout.logTransaction();
                case 5 -> HandlingCheckouts.viewAllTransactions();
                case 6 -> checkout.validatePaymentMethod();
                case 7 -> checkout.displayTransactionSummary();
                case 8 -> System.out.println("Exiting Checkout System...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 8);

        scanner.close();
    }
}
