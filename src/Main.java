import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1:
                        addItem();
                        break;
                    case 2:
                        addMember();
                        break;
                    case 3:
                        borrowItem();
                        break;
                    case 4:
                        returnItem();
                        break;
                    case 5:
                        library.listCatalog();
                        break;
                    case 6:
                        library.printReport();
                        break;
                    case 7:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1-7.");
                }
            } catch (LibraryException | IllegalArgumentException e) {
                System.out.println("Could not complete operation: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Library Lending System =====");
        System.out.println("1. Add Item");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Item");
        System.out.println("4. Return Item");
        System.out.println("5. List Catalog");
        System.out.println("6. Report");
        System.out.println("7. Exit");
    }

    private static void addItem() {
        System.out.println("1. Book");
        System.out.println("2. Magazine");
        System.out.println("3. DVD");

        int type = readInt("Choose item type: ");
        String title = readString("Title: ");

        switch (type) {
            case 1:
                String author = readString("Author: ");
                int pages = readPositiveInt("Pages: ");
                library.addItem(new Book(title, author, pages));
                break;

            case 2:
                int issueNumber = readPositiveInt("Issue number: ");
                library.addItem(new Magazine(title, issueNumber));
                break;

            case 3:
                int runtime = readPositiveInt("Runtime minutes: ");
                library.addItem(new DVD(title, runtime));
                break;

            default:
                System.out.println("Invalid item type.");
                return;
        }

        System.out.println("Item added successfully.");
    }

    private static void addMember() {
        String memberId = readString("Member ID: ");
        String name = readString("Name: ");
        int maxAllowed = readPositiveInt("Maximum allowed items: ");

        library.addMember(new Member(memberId, name, maxAllowed));
        System.out.println("Member added successfully.");
    }

    private static void borrowItem() throws LibraryException {
        String memberId = readString("Member id: ");
        String itemId = readString("Item id: ");

        try {
            library.borrowItem(memberId, itemId);
        } catch (LibraryException e) {
            System.out.println("Could not borrow: " + e.getMessage());
        }
    }

    private static void returnItem() throws LibraryException {
        String memberId = readString("Member id: ");
        String itemId = readString("Item id: ");

        try {
            library.returnItem(memberId, itemId);
        } catch (LibraryException e) {
            System.out.println("Could not return: " + e.getMessage());
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);

            if (value > 0) {
                return value;
            }

            System.out.println("Value must be greater than zero.");
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }
}