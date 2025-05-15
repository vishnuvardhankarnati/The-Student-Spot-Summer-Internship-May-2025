import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    int id;
    String grade;

    Student(String name, int id, String grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }
}

public class StudentRecords {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(String name, int id, String grade) {
        students.add(new Student(name, id, grade));
    }

    public void viewStudents() {
        for (Student student : students) {
            System.out.println("ID: " + student.id + ", Name: " + student.name + ", Grade: " + student.grade);
        }
    }

    public void deleteStudent(int id) {
        students.removeIf(student -> student.id == id);
    }

    public static void main(String[] args) {
        StudentRecords records = new StudentRecords();
        Scanner scanner = new Scanner(System.in);
    }
}
