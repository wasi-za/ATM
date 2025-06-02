import java.util.*;
import java.io.*;

class Person {
    protected String name;
    protected String username;
    protected String password;

    public Person(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}

class User extends Person {
    public User(String name, String username, String password) {
        super(name, username, password);
    }
}

class Librarian extends Person {
    public Librarian(String name, String username, String password) {
        super(name, username, password);
    }
}

class Book {
    private String title;
    private String author;
    private boolean available;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public void borrowBook() { available = false; }
    public void returnBook() { available = true; }
}

class LibrarySystem {
    private List<User> users = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private User loggedInUser = null;

    public void run() {
        books.add(new Book("Java Basics", "John Doe"));
        books.add(new Book("OOP in Java", "Jane Smith"));
        books.add(new Book("Data Structures", "Alice White"));
        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) register();
            else if (choice == 2) login();
            else break;
        }
    }

    private void register() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.add(new User(name, username, password));
        System.out.println("Registered successfully.");
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                loggedInUser = u;
                System.out.println("Login successful. Welcome, " + u.getName());
                userMenu();
                return;
            }
        }
        System.out.println("Invalid credentials.");
    }

    private void userMenu() {
        while (true) {
            System.out.println("1. View Books\n2. Borrow Book\n3. Return Book\n4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) showBooks();
            else if (choice == 2) borrowBook();
            else if (choice == 3) returnBook();
            else break;
        }
    }

    private void showBooks() {
        int i = 1;
        for (Book b : books) {
            String status = b.isAvailable() ? "Available" : "Borrowed";
            System.out.println(i++ + ". " + b.getTitle() + " by " + b.getAuthor() + " - " + status);
        }
    }

    private void borrowBook() {
        showBooks();
        System.out.print("Enter book number to borrow: ");
        int num = scanner.nextInt();
        if (num > 0 && num <= books.size()) {
            Book b = books.get(num - 1);
            if (b.isAvailable()) {
                b.borrowBook();
                System.out.println("Book borrowed.");
            } else {
                System.out.println("Book already borrowed.");
            }
        }
    }

    private void returnBook() {
        showBooks();
        System.out.print("Enter book number to return: ");
        int num = scanner.nextInt();
        if (num > 0 && num <= books.size()) {
            Book b = books.get(num - 1);
            if (!b.isAvailable()) {
                b.returnBook();
                System.out.println("Book returned.");
            } else {
                System.out.println("This book was not borrowed.");
            }
        }
    }
}

public class LibraryMain {
    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();
        system.run();
    }
}
