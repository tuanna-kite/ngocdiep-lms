package io.kite.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String id;
    private final String name;
    private StudentEmail email;
    private StudentStatus status;
    private final List<CourseCode> completedCourseCodes;

    public Student(String id, String name, String email) {
        requireText(id, "Student id");
        requireText(name, "Student name");
        this.id = id;
        this.name = name;
        this.email = new StudentEmail(email);
        this.status = StudentStatus.ACTIVE;
        this.completedCourseCodes = new ArrayList<>();
    }

    public OperationResult changeEmail(String newEmail) {
        StudentEmail parsedEmail;
        try {
            parsedEmail = new StudentEmail(newEmail);
        } catch (IllegalArgumentException ex) {
            return OperationResult.failure(ErrorCode.INVALID_EMAIL, ex.getMessage());
        }

        if (this.email.equals(parsedEmail)) {
            return OperationResult.failure(
                ErrorCode.DUPLICATE_EMAIL,
                "New email must be different from current email."
            );
        }

        this.email = parsedEmail;
        return OperationResult.success("Email changed successfully.");
    }

    public OperationResult suspend() {
        if (status == StudentStatus.SUSPENDED) {
            return OperationResult.failure(ErrorCode.STUDENT_INACTIVE, "Student has already been suspended.");
        }
        status = StudentStatus.SUSPENDED;
        return OperationResult.success("Student suspended successfully.");
    }

    public OperationResult activate() {
        if (status == StudentStatus.ACTIVE) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Student is already active.");
        }
        status = StudentStatus.ACTIVE;
        return OperationResult.success("Student activated successfully.");
    }

    public void completeCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Completed course must not be null");
        }
        CourseCode code = course.getCode();
        if (!completedCourseCodes.contains(code)) {
            completedCourseCodes.add(code);
        }
    }

    public boolean hasCompleted(Course course) {
        if (course == null) return false;
        return completedCourseCodes.contains(course.getCode());
    }

    public boolean hasSameId(Student other) {
        return other != null && this.id.equals(other.id);
    }

    public boolean isActive() {
        return status == StudentStatus.ACTIVE;
    }

    public String getDisplayInfo() {
        return id + " - " + name + " - " + email.getValue() + " - " + status;
    }

    public String getCompletedCourseText() {
        if (completedCourseCodes.isEmpty()) {
            return "No completed courses.";
        }
        StringBuilder builder = new StringBuilder();
        for (CourseCode code : completedCourseCodes) {
            builder.append("- ").append(code.getValue()).append(System.lineSeparator());
        }
        return builder.toString();
    }

    private static void requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public StudentStatus getStatus() { return status; }
}
