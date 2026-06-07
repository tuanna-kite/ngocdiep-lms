package io.kite;

import io.kite.domain.*;
import io.kite.domain.Course;
import io.kite.domain.Student;

public class ApplicationTest {
    public static void main(String[] args) {
        CourseReport report = new CourseReport();
        EnrollmentService enrollmentService = new EnrollmentService(new EnrollmentPolicy());

        Student an = createStudent("S001", "An", "an@example.com");
        Student binh = createStudent("S002", "Binh", "binh@example.com");
        Student chi = createStudent("S003", "Chi", "chi@example.com");
        Student dung = createStudent("S004", "Dung", "dung@example.com");
        Student em = createStudent("S005", "Em", "em@example.com");

        Course javaOop = createCourse("C001", "Java OOP Foundation", 2, CourseLevel.BEGINNER, "Monday", 18, 20);
        Course dsa = createCourse("C002", "DSA Foundation", 3, CourseLevel.BEGINNER, "Monday", 20, 22);
        Course advancedJava = createCourse("C003", "Advanced Java", 3, CourseLevel.ADVANCED, "Tuesday", 18, 20);
        Course algorithm = createCourse("C004", "Algorithm Practice", 3, CourseLevel.INTERMEDIATE, "Monday", 19, 21);

        print(advancedJava.requirePrerequisite(javaOop));

        enrollmentService.addCourse(javaOop);
        enrollmentService.addCourse(dsa);
        enrollmentService.addCourse(advancedJava);
        enrollmentService.addCourse(algorithm);

        System.out.println("\n=== Constructor validation demo ===");
        tryCreateInvalidObjects();

        System.out.println("\n=== Register students into Java OOP ===");
        print(enrollmentService.register(an, javaOop));
        print(enrollmentService.register(binh, javaOop));
        print(enrollmentService.register(chi, javaOop));
        print(enrollmentService.register(dung, javaOop));
        print(enrollmentService.register(an, javaOop));

        System.out.println("\n=== Java OOP Report ===");
        System.out.println(report.generateSummary(javaOop));
        System.out.println(report.generateEnrollmentList(javaOop));

        System.out.println("\n=== Schedule conflict demo ===");
        print(enrollmentService.register(an, algorithm));
        print(enrollmentService.register(an, dsa));

        System.out.println("\n=== Course status demo ===");
        print(dsa.closeRegistration());
        print(enrollmentService.register(em, dsa));
        print(dsa.openRegistration());
        print(enrollmentService.register(em, dsa));

        System.out.println("\n=== Prerequisite demo ===");
        print(enrollmentService.register(binh, advancedJava));
        binh.completeCourse(javaOop);
        print(enrollmentService.register(binh, advancedJava));

        System.out.println("\n=== Student status demo ===");
        print(em.suspend());
        print(enrollmentService.register(em, advancedJava));
        print(em.activate());

        System.out.println("\n=== Email change demo ===");
        print(an.changeEmail("invalid-email"));
        print(an.changeEmail("an@example.com"));
        print(an.changeEmail("new.an@example.com"));

        System.out.println("\n=== Capacity change and waitlist promotion demo ===");
        print(javaOop.changeCapacity(1));
        print(enrollmentService.cancel(an, javaOop));
        System.out.println(report.generateEnrollmentList(javaOop));
        print(javaOop.changeCapacity(3));
        System.out.println(report.generateEnrollmentList(javaOop));

        System.out.println("\n=== Rename course demo ===");
        print(javaOop.rename("Java Object-Oriented Programming"));
        System.out.println(report.generateSummary(javaOop));
    }

    private static Student createStudent(String id, String name, String email) {
        return new Student(id, name, email);
    }

    private static Course createCourse(
        String code,
        String name,
        int capacity,
        CourseLevel level,
        String day,
        int startHour,
        int endHour
    ) {
        return new Course(
            new CourseCode(code),
            name,
            capacity,
            level,
            new CourseSchedule(day, startHour, endHour)
        );
    }

    private static void tryCreateInvalidObjects() {
        try {
            new Student("", "Invalid", "invalid@example.com");
        } catch (IllegalArgumentException ex) {
            System.out.println("Failed to create student: " + ex.getMessage());
        }

        try {
            new Course(new CourseCode("ABC"), "Invalid Course", 10, CourseLevel.BEGINNER, new CourseSchedule("Monday", 8, 10));
        } catch (IllegalArgumentException ex) {
            System.out.println("Failed to create course: " + ex.getMessage());
        }

        try {
            new Course(new CourseCode("C999"), "Invalid Capacity", 0, CourseLevel.BEGINNER, new CourseSchedule("Monday", 8, 10));
        } catch (IllegalArgumentException ex) {
            System.out.println("Failed to create course: " + ex.getMessage());
        }
    }

    private static void print(OperationResult result) {
        if (result.isSuccess()) {
            System.out.println("[SUCCESS] " + result.getMessage());
        } else {
            System.out.println("[FAILED]");
            System.out.println("Code: " + result.getErrorCode());
            System.out.println("Message: " + result.getMessage());
        }
    }
}
