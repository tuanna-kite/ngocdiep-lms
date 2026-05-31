package io.kite;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String id;
    private String name;
    private int maxStudents;
    private List<Student> enrolledStudents;

    public Course(String id, String name, int maxStudents) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or blank");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (maxStudents <= 0) {
            throw new IllegalArgumentException("Max students must be greater than 0");
        }

        this.id = id;
        this.name = name;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
    }

    public boolean addStudent(Student student) {
        if (student == null) {
            return false;
        }

        if (isFull()) {
            return false;
        }

        if (hasStudent(student)) {
            return false;
        }

        enrolledStudents.add(student);
        return true;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= maxStudents;
    }

    public boolean hasStudent(Student student) {
        for (Student enrolledStudent : enrolledStudents) {
            if (enrolledStudent.hasSameId(student)) {
                return true;
            }
        }

        return false;
    }

    public int getEnrollmentCount() {
        return enrolledStudents.size();
    }

    public int getAvailableSlots() {
        return maxStudents - enrolledStudents.size();
    }

    public void printCourseInfo() {
        System.out.println("Course: " + id + " - " + name);
        System.out.println("Max students: " + maxStudents);
        System.out.println("Enrolled students: " + getEnrollmentCount());
        System.out.println("Available slots: " + getAvailableSlots());
    }

    public void printEnrolledStudents() {
        System.out.println("Students in course " + name + ":");

        if (enrolledStudents.isEmpty()) {
            System.out.println("No students enrolled yet.");
            return;
        }

        for (Student student : enrolledStudents) {
            System.out.println("- " + student);
        }
    }

    public String getName() {
        return name;
    }
}