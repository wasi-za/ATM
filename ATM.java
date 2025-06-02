import java.util.*;
import java.io.*;

class User {
    protected String username;
    protected String pin;
    protected double balance;

    public User(String username, String pin) {
        this.username = username;
        this.pin = pin;
        this.balance = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class ATMSystem {
    private Map<String, User> users = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private final String fileName = "atm_users.txt";

    public ATMSystem() {
        loadUsers();
    }

    private void loadUsers() {
        try {
            File file = new File(fileName);
            if (!file.exists()) return;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    User user = new User(parts[0], parts[1]);
                    user.deposit(Double.parseDouble(parts[2]));
                    users.put(parts[0], user);
                }
            }
            reader.close();
        } catch (Exception e) {}
    }

    private void saveUsers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (User user : users.values()) {
                writer.write(user.getUsername() + "," + user.pin + "," + user.getBalance() + "\n");
            }
            writer.close();
        } catch (Exception e) {}
    }

    public void start() {
        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            String choice = scanner.nextLine();
            if (choice.equals("1")) register();
            else if (choice.equals("2")) login();
            else break;
        }
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return;
        }
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        User newUser = new User(username, pin);
        users.put(username, newUser);
        saveUsers();
        System.out.println("Registered successfully.");
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        User user = users.get(username);
        if (user != null && user.checkPin(pin)) {
            System.out.println("Login successful.");
            atmMenu(user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void atmMenu(User user) {
        while (true) {
            System.out.println("\n1. Check Balance\n2. Deposit\n3. Withdraw\n4. Logout");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                System.out.println("Balance: " + user.getBalance());
            } else if (option.equals("2")) {
                System.out.print("Amount to deposit: ");
                double amt = Double.parseDouble(scanner.nextLine());
                user.deposit(amt);
                System.out.println("Deposited.");
            } else if (option.equals("3")) {
                System.out.print("Amount to withdraw: ");
                double amt = Double.parseDouble(scanner.nextLine());
                if (user.withdraw(amt)) {
                    System.out.println("Withdrawn.");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                saveUsers();
                break;
            }
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        ATMSystem system = new ATMSystem();
        system.start();
    }
}
