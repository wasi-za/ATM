import java.util.*;

class Account {
    protected String username;
    protected String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }
}

class User extends Account {
    private String name;
    private List<Order> orders = new ArrayList<>();

    public User(String name, String username, String password) {
        super(username, password);
        this.name = name;
    }

    public void placeOrder(Product product) {
        Order order = new Order(this, product);
        orders.add(order);
        System.out.println("Order placed: " + product.getName());
    }

    public void viewOrders() {
        for (Order order : orders) {
            System.out.println("Product: " + order.getProduct().getName() + " | Status: " + order.getStatus());
        }
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
}

class Order {
    private User user;
    private Product product;
    private String status;

    public Order(User user, Product product) {
        this.user = user;
        this.product = product;
        this.status = "Processing";
    }

    public Product getProduct() {
        return product;
    }

    public String getStatus() {
        return status;
    }

    public void shipOrder() {
        this.status = "Shipped";
    }
}

public class OnlineShoppingSystem {
    static List<User> users = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static User currentUser = null;

    public static void register() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        users.add(new User(name, username, password));
        System.out.println("Registration successful");
    }

    public static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        for (User user : users) {
            if (user.authenticate(username, password)) {
                currentUser = user;
                System.out.println("Login successful");
                return;
            }
        }
        System.out.println("Invalid credentials");
    }

    public static void main(String[] args) {
        Product phone = new Product("Smartphone", 30000);
        Product laptop = new Product("Laptop", 70000);

        while (true) {
            System.out.println("1.Register 2.Login 3.Exit");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) register();
            else if (choice == 2) {
                login();
                if (currentUser != null) {
                    while (true) {
                        System.out.println("1.Place Order 2.View Orders 3.Logout");
                        int opt = Integer.parseInt(sc.nextLine());
                        if (opt == 1) {
                            System.out.println("1.Smartphone 2.Laptop");
                            int p = Integer.parseInt(sc.nextLine());
                            if (p == 1) currentUser.placeOrder(phone);
                            else if (p == 2) currentUser.placeOrder(laptop);
                        } else if (opt == 2) {
                            currentUser.viewOrders();
                        } else break;
                    }
                    currentUser = null;
                }
            } else break;
        }
    }
}
