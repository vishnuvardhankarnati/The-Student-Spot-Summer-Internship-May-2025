import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private int id;
    private int age;

    public Student(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age;
    }
}

public class StudentManager {
    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    public void addStudent(String name, int id, int age) {
        Student student = new Student(name, id, age);
        students.add(student);
        System.out.println("Student added: " + student);
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("Student Records:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void searchStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                System.out.println("Student found: " + student);
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        int choice;

        do {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next(); // consume invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter student age: ");
                    int age = scanner.nextInt();
                    manager.addStudent(name, id, age);
                    break;
                case 2:
                    manager.displayStudents();
                    break;
                case 3:
                    System.out.print("Enter student ID to search: ");
                    int searchId = scanner.nextInt();
                    manager.searchStudentById(searchId);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
