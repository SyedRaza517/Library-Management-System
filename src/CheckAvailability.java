import java.util.*;

public class CheckAvailability extends Transactions {

    // Book model class
    static class Book {
        String title;
        String author;
        String genre;
        boolean isAvailable;

        Book(String title, String author, String genre, boolean isAvailable) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.isAvailable = isAvailable;
        }
    }

    // Sample list of books
    private final List<Book> books = new ArrayList<>(List.of(
            new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", true),
            new Book("1984", "George Orwell", "Dystopian", false),
            new Book("Pride and Prejudice", "Jane Austen", "Romance", true),
            new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic", false),
            new Book("The Catcher in the Rye", "J.D. Salinger", "Fiction", true),
            new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", true),
            new Book("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", false),
            new Book("The Alchemist", "Paulo Coelho", "Adventure", true),
            new Book("The Diary of a Young Girl", "Anne Frank", "Biography", true),
            new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Sci-Fi", true)
    ));

    @Override
    public void process() {
        System.out.println("Checking books by scanning the library system...");
    }

    public void checkAvailabilityMenu() {
        System.out.println("\n********** Library Check Availability Menu **********");
        System.out.println("1. Check Availability by Book Name");
        System.out.println("2. List All Available Books");
        System.out.println("3. Search Books by Author");
        System.out.println("4. Search Books by Genre");
        System.out.println("5. Search by Partial Title");
        System.out.println("6. View Full Book Info");
        System.out.println("7. Return to Main Menu");
        System.out.println("8. Exit");
    }

    public void checkBookAvailabilityByName(Scanner scanner) {
        System.out.print("Enter the book name: ");
        String name = scanner.nextLine().trim();

        for (Book book : books) {
            if (book.title.equalsIgnoreCase(name)) {
                System.out.println("Book found: " + book.title);
                System.out.println("Status: " + (book.isAvailable ? "Available ✅" : "Borrowed ❌"));
                return;
            }
        }

        System.out.println("Book not found.");
    }

    public void listAllAvailableBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : books) {
            if (book.isAvailable) {
                System.out.println("- " + book.title);
            }
        }
    }

    public void searchBooksByAuthor(Scanner scanner) {
        System.out.print("Enter author name: ");
        String author = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Book book : books) {
            if (book.author.toLowerCase().contains(author)) {
                System.out.println(book.title + " by " + book.author + " (" + (book.isAvailable ? "Available" : "Borrowed") + ")");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found by that author.");
        }
    }

    public void searchBooksByGenre(Scanner scanner) {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Book book : books) {
            if (book.genre.toLowerCase().equals(genre)) {
                System.out.println(book.title + " - " + (book.isAvailable ? "Available" : "Borrowed"));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found in that genre.");
        }
    }

    public void searchByPartialTitle(Scanner scanner) {
        System.out.print("Enter part of the book title: ");
        String part = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Book book : books) {
            if (book.title.toLowerCase().contains(part)) {
                System.out.println("- " + book.title);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books matched that title.");
        }
    }

    public void viewAllBookInfo() {
        System.out.println("\n📚 All Book Information:");
        for (Book book : books) {
            System.out.printf("- %s by %s [%s] - %s\n",
                    book.title, book.author, book.genre,
                    (book.isAvailable ? "Available ✅" : "Borrowed ❌"));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CheckAvailability ca = new CheckAvailability();
        int choice;

        do {
            ca.checkAvailabilityMenu();
            System.out.print("\nEnter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> ca.checkBookAvailabilityByName(scanner);
                case 2 -> ca.listAllAvailableBooks();
                case 3 -> ca.searchBooksByAuthor(scanner);
                case 4 -> ca.searchBooksByGenre(scanner);
                case 5 -> ca.searchByPartialTitle(scanner);
                case 6 -> ca.viewAllBookInfo();
                case 7 -> System.out.println("Returning to main menu...");
                case 8 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 8);

        scanner.close();
    }
}
