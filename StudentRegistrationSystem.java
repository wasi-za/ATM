import java.io.*;
import java.util.*;

class Person {
    protected String name;
    protected String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

class Student extends Person {
    private List<Course> enrolledCourses;

    public Student(String name, String id) {
        super(name, id);
        this.enrolledCourses = new ArrayList<>();
    }

    public void enroll(Course course) {
        enrolledCourses.add(course);
    }

    public List<Course> getCourses() {
        return enrolledCourses;
    }
}

class Course {
    private String courseCode;
    private String courseName;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String toString() {
        return courseCode + " - " + courseName;
    }
}

class Account {
    private String username;
    private String password;
    private Student student;

    public Account(String username, String password, Student student) {
        this.username = username;
        this.password = password;
        this.student = student;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public Student getStudent() {
        return student;
    }
}

class RegistrationManager {
    private Map<String, Account> accounts;
    private List<Course> availableCourses;
    private final String FILE_NAME = "accounts.txt";

    public RegistrationManager() {
        accounts = new HashMap<>();
        availableCourses = new ArrayList<>();
        loadCourses();
        loadAccounts();
    }

    private void loadCourses() {
        availableCourses.add(new Course("CSE101", "Intro to CS"));
        availableCourses.add(new Course("MTH101", "Calculus I"));
        availableCourses.add(new Course("ENG101", "English Language"));
    }

    private void loadAccounts() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) file.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Student s = new Student(parts[2], parts[3]);
                Account acc = new Account(parts[0], parts[1], s);
                accounts.put(parts[0], acc);
            }
            br.close();
        } catch (Exception e) {
        }
    }

    private void saveAccount(Account acc) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
            Student s = acc.getStudent();
            bw.write(acc.getUsername() + "," + acc.checkPassword(acc.checkPassword("")) + "," + s.getName() + "," + s.getId());
            bw.newLine();
            bw.close();
        } catch (Exception e) {
        }
    }

    public boolean register(String username, String password, String name, String id) {
        if (accounts.containsKey(username)) return false;
        Student s = new Student(name, id);
        Account acc = new Account(username, password, s);
        accounts.put(username, acc);
        saveAccount(acc);
        return true;
    }

    public Account login(String username, String password) {
        if (!accounts.containsKey(username)) return null;
        Account acc = accounts.get(username);
        if (acc.checkPassword(password)) return acc;
        return null;
    }

    public void showCourses() {
        for (int i = 0; i < availableCourses.size(); i++) {
            System.out.println((i + 1) + ". " + availableCourses.get(i));
        }
    }

    public Course getCourse(int index) {
        if (index >= 0 && index < availableCourses.size()) {
            return availableCourses.get(index);
        }
        return null;
    }
}

public class StudentRegistrationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RegistrationManager manager = new RegistrationManager();
        System.out.println("1. Register\n2. Login");
        int option = sc.nextInt();
        sc.nextLine();
        if (option == 1) {
            System.out.print("Username: ");
            String u = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();
            System.out.print("Name: ");
            String n = sc.nextLine();
            System.out.print("Student ID: ");
            String i = sc.nextLine();
            if (manager.register(u, p, n, i)) {
                System.out.println("Registration successful.");
            } else {
                System.out.println("Username already exists.");
            }
        } else if (option == 2) {
            System.out.print("Username: ");
            String u = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();
            Account acc = manager.login(u, p);
            if (acc != null) {
                System.out.println("Welcome " + acc.getStudent().getName());
                while (true) {
                    System.out.println("1. Show Courses\n2. Enroll\n3. View Enrolled\n4. Logout");
                    int choice = sc.nextInt();
                    if (choice == 1) {
                        manager.showCourses();
                    } else if (choice == 2) {
                        System.out.print("Enter course number: ");
                        int c = sc.nextInt();
                        Course course = manager.getCourse(c - 1);
                        if (course != null) {
                            acc.getStudent().enroll(course);
                            System.out.println("Enrolled in " + course);
                        } else {
                            System.out.println("Invalid course.");
                        }
                    } else if (choice == 3) {
                        List<Course> list = acc.getStudent().getCourses();
                        for (Course c : list) {
                            System.out.println(c);
                        }
                    } else {
                        break;
                    }
                }
            } else {
                System.out.println("Login failed.");
            }
        }
    }
}
