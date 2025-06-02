import java.io.*;
import java.util.*;

class Bank {
    protected String bankName;
    protected List<Customer> customers;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomer(String username, String password) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }
}

class Customer {
    protected String username;
    protected String password;
    protected String accountNumber;
    protected double balance;

    public Customer(String username, String password, String accountNumber, double balance) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }

    public void displayBalance() {
        System.out.println("Balance: " + balance);
    }
}

class ATM {
    private Bank bank;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void operate(Customer customer, Scanner sc) {
        while (true) {
            System.out.println("1. Deposit\n2. Withdraw\n3. Check Balance\n4. Exit");
            int ch = sc.nextInt();
            if (ch == 1) {
                System.out.print("Enter amount to deposit: ");
                double amount = sc.nextDouble();
                customer.deposit(amount);
            } else if (ch == 2) {
                System.out.print("Enter amount to withdraw: ");
                double amount = sc.nextDouble();
                customer.withdraw(amount);
            } else if (ch == 3) {
                customer.displayBalance();
            } else if (ch == 4) {
                break;
            }
        }
    }
}

public class BankingSystem {
    static final String FILE = "users.txt";

    public static void register(Scanner sc, Bank bank) throws IOException {
        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();
        String accNo = "AC" + new Random().nextInt(10000);
        Customer newCustomer = new Customer(username, password, accNo, 0);
        bank.addCustomer(newCustomer);

        FileWriter fw = new FileWriter(FILE, true);
        fw.write(username + " " + password + " " + accNo + "\n");
        fw.close();
        System.out.println("Registration successful. Your Account No: " + accNo);
    }

    public static Customer login(Scanner sc, Bank bank) throws IOException {
        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();

        BufferedReader br = new BufferedReader(new FileReader(FILE));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(" ");
            if (data[0].equals(username) && data[1].equals(password)) {
                Customer customer = new Customer(username, password, data[2], 0);
                br.close();
                return customer;
            }
        }
        br.close();
        System.out.println("Login failed.");
        return null;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank("OOP Bank");
        ATM atm = new ATM(bank);

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = sc.nextInt();

            if (choice == 1) {
                register(sc, bank);
            } else if (choice == 2) {
                Customer customer = login(sc, bank);
                if (customer != null) {
                    atm.operate(customer, sc);
                }
            } else if (choice == 3) {
                break;
            }
        }
    }
}
