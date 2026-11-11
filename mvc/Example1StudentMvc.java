/*
 * MVC - Example 1: Student marks (Student / StudentView / StudentController)
 *
 * This is the exact example from the explanation (sections 3 and 4).
 *
 * Run (JDK 11+):   java Example1StudentMvc.java
 * Or compile:      javac Example1StudentMvc.java && java Example1StudentMvc
 *
 * Roles:
 *   Model       -> Student           (data + business logic: the average)
 *   View        -> StudentView       (displays the information only)
 *   Controller  -> StudentController (coordinates Model and View)
 *
 * In the explanation these classes are in separate files (Student.java,
 * StudentView.java, StudentController.java, Main.java). They are put together
 * here only so the example runs with one command; the code is the same, and
 * the body of main() below is the Main.java of the explanation.
 */
public class Example1StudentMvc {

    public static void main(String[] args) {

        Student student = new Student(
                "Tala",
                90,
                85,
                95
        );

        StudentView view = new StudentView();

        StudentController controller =
                new StudentController(student, view);

        controller.updateStudentView();
    }
}

// ---------------------------------------------------------------------------
// Model: stores the student's data and calculates the average.
// It prints nothing and knows nothing about the user interface.
// ---------------------------------------------------------------------------
class Student {

    private String name;
    private double mark1;
    private double mark2;
    private double mark3;

    public Student(String name, double mark1,
                   double mark2, double mark3) {

        this.name = name;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
    }

    public String getName() {
        return name;
    }

    public double getAverage() {
        return (mark1 + mark2 + mark3) / 3;
    }
}

// ---------------------------------------------------------------------------
// View: everything related to displaying information to the user.
// It receives the average ready-made; it never calculates it.
// ---------------------------------------------------------------------------
class StudentView {

    public void displayStudent(String name, double average) {

        System.out.println("Student Name: " + name);

        System.out.println("Average: " + average);
    }
}

// ---------------------------------------------------------------------------
// Controller: the mediator. It asks the Model for data and tells the View to
// display it. It neither calculates the average nor prints anything itself.
// ---------------------------------------------------------------------------
class StudentController {

    private Student model;
    private StudentView view;

    public StudentController(Student model, StudentView view) {

        this.model = model;
        this.view = view;
    }

    public void updateStudentView() {

        String name = model.getName();

        double average = model.getAverage();

        view.displayStudent(name, average);
    }
}
