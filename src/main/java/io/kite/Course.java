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
            throw new IllegalArgumentException("Failed to create course: course id must not be blank.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create course: course name must not be blank.");
        }
        if (maxStudents <= 0) {
            throw new IllegalArgumentException("Failed to create course: maximum capacity must be greater than 0.");
        }

        this.id = id;
        this.name = name;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
    }

    public EnrollmentResult addStudent(Student student) {
        if (student == null) {
            return new EnrollmentResult(false, "Failed to add student: ivalidate Student.");
        }

        if (isFull()) {
            return new EnrollmentResult(false, "Failed to add student: course full.");
        }

        if (hasStudent(student)) {
            return new EnrollmentResult(false, "Failed to add student: student have already regíted this course.");
        }

        enrolledStudents.add(student);
        return new EnrollmentResult(true, "Student created successfully.");
    }

    public void updateMaxStudents(int newMaxStudents) {
        if (newMaxStudents <= 0) {
            throw new IllegalArgumentException("Failed to create course: maximum capacity must be greater than 0.");
        }
        if (newMaxStudents < enrolledStudents.size()) {
            throw new IllegalArgumentException("Failed to update course: cannot reduce capacity below current enrollment count.");
        }
        this.maxStudents = newMaxStudents;
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public List<Student> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }
}