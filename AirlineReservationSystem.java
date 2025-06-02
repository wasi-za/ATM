import java.util.*;
import java.io.*;

class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

class Passenger extends User {
    private String name;
    private String contact;

    public Passenger(String username, String password, String name, String contact) {
        super(username, password);
        this.name = name;
        this.contact = contact;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }
}

class Employee extends User {
    private String role;

    public Employee(String username, String password, String role) {
        super(username, password);
        this.role = role;
    }

    public String getRole() { return role; }
}

class Flight {
    private String flightId;
    private String origin;
    private String destination;
    private int seats;

    public Flight(String flightId, String origin, String destination, int seats) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.seats = seats;
    }

    public String getFlightId() { return flightId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public int getSeats() { return seats; }

    public boolean bookSeat() {
        if (seats > 0) {
            seats--;
            return true;
        }
        return false;
    }
}

class Reservation {
    private Passenger passenger;
    private Flight flight;

    public Reservation(Passenger passenger, Flight flight) {
        this.passenger = passenger;
        this.flight = flight;
    }

    public void showDetails() {
        System.out.println("Reservation Successful");
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Flight: " + flight.getFlightId());
    }
}

class TicketBooking {
    private List<Flight> flights = new ArrayList<>();
    private List<Passenger> passengers = new ArrayList<>();

    public TicketBooking() {
        flights.add(new Flight("FL101", "Dhaka", "Chittagong", 10));
        flights.add(new Flight("FL202", "Dhaka", "Sylhet", 8));
    }

    public void registerPassenger(String username, String password, String name, String contact) {
        passengers.add(new Passenger(username, password, name, contact));
        System.out.println("Registration successful.");
    }

    public Passenger loginPassenger(String username, String password) {
        for (Passenger p : passengers) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                System.out.println("Login successful.");
                return p;
            }
        }
        System.out.println("Invalid credentials.");
        return null;
    }

    public void showFlights() {
        for (Flight f : flights) {
            System.out.println("ID: " + f.getFlightId() + " | From: " + f.getOrigin() + " | To: " + f.getDestination() + " | Seats: " + f.getSeats());
        }
    }

    public Flight getFlightById(String id) {
        for (Flight f : flights) {
            if (f.getFlightId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public void bookFlight(Passenger p, String flightId) {
        Flight flight = getFlightById(flightId);
        if (flight != null && flight.bookSeat()) {
            Reservation r = new Reservation(p, flight);
            r.showDetails();
        } else {
            System.out.println("Booking failed. No seats available or invalid flight ID.");
        }
    }
}

public class AirlineReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketBooking system = new TicketBooking();
        Passenger loggedInUser = null;

        while (true) {
            System.out.println("1. Register\n2. Login\n3. View Flights\n4. Book Flight\n5. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Contact: ");
                String contact = sc.nextLine();
                system.registerPassenger(username, password, name, contact);
            } else if (choice == 2) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                loggedInUser = system.loginPassenger(username, password);
            } else if (choice == 3) {
                system.showFlights();
            } else if (choice == 4) {
                if (loggedInUser == null) {
                    System.out.println("Please login first.");
                } else {
                    system.showFlights();
                    System.out.print("Enter Flight ID to book: ");
                    String id = sc.nextLine();
                    system.bookFlight(loggedInUser, id);
                }
            } else if (choice == 5) {
                break;
            }
        }
    }
}
