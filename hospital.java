import java.io.*;
import java.util.*;

class Person {
    protected String name;
    protected String username;
    protected String password;

    public Person(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }
}

class Patient extends Person {
    private String disease;

    public Patient(String name, String username, String password, String disease) {
        super(name, username, password);
        this.disease = disease;
    }

    public String getDetails() {
        return "Patient: " + name + ", Disease: " + disease;
    }
}

class Doctor extends Person {
    private String specialization;

    public Doctor(String name, String username, String password, String specialization) {
        super(name, username, password);
        this.specialization = specialization;
    }

    public String getDetails() {
        return "Doctor: " + name + ", Specialization: " + specialization;
    }
}

class HospitalSystem {
    static Scanner sc = new Scanner(System.in);
    static List<Patient> patients = new ArrayList<>();
    static List<Doctor> doctors = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            String choice = sc.nextLine();
            if (choice.equals("1")) register();
            else if (choice.equals("2")) {
                if (login()) menu();
            }
            else break;
        }
    }

    static void register() {
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println("Enter Username:");
        String user = sc.nextLine();
        System.out.println("Enter Password:");
        String pass = sc.nextLine();

        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(user + "," + pass + "," + name + "\n");
            fw.close();
            System.out.println("Registered successfully.");
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }

    static boolean login() {
        System.out.println("Enter Username:");
        String user = sc.nextLine();
        System.out.println("Enter Password:");
        String pass = sc.nextLine();

        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(user) && parts[1].equals(pass)) {
                    System.out.println("Login successful. Welcome " + parts[2]);
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error.");
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    static void menu() {
        while (true) {
            System.out.println("1. Add Patient\n2. Add Doctor\n3. View Patients\n4. View Doctors\n5. Logout");
            String choice = sc.nextLine();
            if (choice.equals("1")) addPatient();
            else if (choice.equals("2")) addDoctor();
            else if (choice.equals("3")) viewPatients();
            else if (choice.equals("4")) viewDoctors();
            else break;
        }
    }

    static void addPatient() {
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println("Enter Username:");
        String user = sc.nextLine();
        System.out.println("Enter Password:");
        String pass = sc.nextLine();
        System.out.println("Enter Disease:");
        String disease = sc.nextLine();
        patients.add(new Patient(name, user, pass, disease));
        System.out.println("Patient added.");
    }

    static void addDoctor() {
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println("Enter Username:");
        String user = sc.nextLine();
        System.out.println("Enter Password:");
        String pass = sc.nextLine();
        System.out.println("Enter Specialization:");
        String spec = sc.nextLine();
        doctors.add(new Doctor(name, user, pass, spec));
        System.out.println("Doctor added.");
    }

    static void viewPatients() {
        for (Patient p : patients)
            System.out.println(p.getDetails());
    }

    static void viewDoctors() {
        for (Doctor d : doctors)
            System.out.println(d.getDetails());
    }
}

