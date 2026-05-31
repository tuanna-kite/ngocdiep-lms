package io.kite;

public class Main {
    static void main(String[] args) {
        Student an = new Student("S001", "An", "an@example.com");
        Student binh = new Student("S002", "Binh", "binh@example.com");
        Student chi = new Student("S003", "Chi", "chi@example.com");

        Course javaCourse = new Course("C001", "Java OOP Foundation", 2);

        System.out.println("=== Course Information Before Enrollment ===");
        printCourseInfo(javaCourse);

        System.out.println();

        enrollAndPrintResult(javaCourse, an);
        enrollAndPrintResult(javaCourse, binh);
        enrollAndPrintResult(javaCourse, chi);

        System.out.println();

        System.out.println("=== Course Information After Enrollment ===");
        printCourseInfo(javaCourse);

        System.out.println();

        printEnrolledStudents(javaCourse);

        System.out.println();

        System.out.println("=== Try to enroll the same student again ===");
        enrollAndPrintResult(javaCourse, an);
    }

    private static void enrollAndPrintResult(Course course, Student student) {
        EnrollmentResult result = course.addStudent(student);

        if (result.isSuccess()) {
            System.out.println(student.getName() + " enrolled in " + course.getName() + " successfully.");
        } else {
            System.out.println(student.getName() + " could not enroll in " + course.getName() + ". Reason: " + result.getReason());
        }
    }

    private static void printCourseInfo(Course course) {
        System.out.println("Course: " + course.getId() + " - " + course.getName());
        System.out.println("Max students: " + course.getMaxStudents());
        System.out.println("Enrolled students: " + course.getEnrollmentCount());
        System.out.println("Available slots: " + course.getAvailableSlots());
    }

    private static void printEnrolledStudents(Course course) {
        System.out.println("Students in course " + course.getName() + ":");

        if (course.getEnrolledStudents().isEmpty()) {
            System.out.println("No students enrolled yet.");
            return;
        }

        for (Student student : course.getEnrolledStudents()) {
            System.out.println("- " + student);
        }
    }
}

