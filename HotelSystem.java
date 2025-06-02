import java.util.*;

class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}

class Guest extends User {
    public Guest(String username, String password) {
        super(username, password);
    }
}

class Room {
    private int roomNumber;
    private String type;
    private boolean isBooked;

    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        isBooked = true;
    }

    public void freeRoom() {
        isBooked = false;
    }
}

class Booking {
    private Guest guest;
    private Room room;

    public Booking(Guest guest, Room room) {
        this.guest = guest;
        this.room = room;
    }

    public String getBookingDetails() {
        return "Room " + room.getRoomNumber() + " booked by " + guest.getUsername();
    }

    public Guest getGuest() {
        return guest;
    }
}

public class HotelSystem {
    static Scanner sc = new Scanner(System.in);
    static List<Guest> users = new ArrayList<>();
    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();
        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) register();
            else if (choice == 2) login();
            else break;
        }
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Single"));
        rooms.add(new Room(102, "Double"));
        rooms.add(new Room(103, "Suite"));
    }

    static void register() {
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        users.add(new Guest(user, pass));
        System.out.println("Registered successfully\n");
    }

    static void login() {
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        for (Guest g : users) {
            if (g.getUsername().equals(user) && g.checkPassword(pass)) {
                System.out.println("Login successful\n");
                guestMenu(g);
                return;
            }
        }
        System.out.println("Invalid credentials\n");
    }

    static void guestMenu(Guest guest) {
        while (true) {
            System.out.println("1. View Rooms\n2. Book Room\n3. My Bookings\n4. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) showRooms();
            else if (choice == 2) bookRoom(guest);
            else if (choice == 3) viewMyBookings(guest);
            else break;
        }
    }

    static void showRooms() {
        for (Room r : rooms) {
            System.out.println("Room " + r.getRoomNumber() + " - " + r.getType() + " - " + (r.isBooked() ? "Booked" : "Available"));
        }
    }

    static void bookRoom(Guest guest) {
        showRooms();
        System.out.print("Enter room number to book: ");
        int num = sc.nextInt();
        for (Room r : rooms) {
            if (r.getRoomNumber() == num && !r.isBooked()) {
                r.bookRoom();
                bookings.add(new Booking(guest, r));
                System.out.println("Room booked successfully\n");
                return;
            }
        }
        System.out.println("Room unavailable\n");
    }

    static void viewMyBookings(Guest guest) {
        for (Booking b : bookings) {
            if (b.getGuest().getUsername().equals(guest.getUsername())) {
                System.out.println(b.getBookingDetails());
            }
        }
    }
}
