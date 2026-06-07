package io.kite.domain;

import java.time.LocalDate;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final LocalDate registeredDate;
    private EnrollmentStatus status;

    public Enrollment(Student student, Course course, EnrollmentStatus initialStatus) {
        if (student == null) throw new IllegalArgumentException("Student must not be null");
        if (course == null) throw new IllegalArgumentException("Course must not be null");
        if (initialStatus == null || initialStatus == EnrollmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Initial enrollment status must be ACTIVE or WAITLISTED");
        }
        this.student = student;
        this.course = course;
        this.registeredDate = LocalDate.now();
        this.status = initialStatus;
    }

    public OperationResult cancel() {
        if (status == EnrollmentStatus.CANCELLED) {
            return OperationResult.failure(
                ErrorCode.ENROLLMENT_ALREADY_CANCELLED,
                "Enrollment has already been cancelled."
            );
        }
        status = EnrollmentStatus.CANCELLED;
        return OperationResult.success("Enrollment cancelled successfully.");
    }

    public OperationResult activateFromWaitlist() {
        if (status != EnrollmentStatus.WAITLISTED) {
            return OperationResult.failure(
                ErrorCode.INVALID_INPUT,
                "Only waitlisted enrollment can be promoted."
            );
        }
        status = EnrollmentStatus.ACTIVE;
        return OperationResult.success("Waitlisted student has been promoted.");
    }

    public boolean belongsTo(Student targetStudent) {
        return student.hasSameId(targetStudent);
    }

    public boolean isActive() { return status == EnrollmentStatus.ACTIVE; }
    public boolean isWaitlisted() { return status == EnrollmentStatus.WAITLISTED; }
    public boolean isCancelled() { return status == EnrollmentStatus.CANCELLED; }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getRegisteredDate() { return registeredDate; }
    public EnrollmentStatus getStatus() { return status; }

    public String getDisplayText() {
        return student.getName() + " | " + course.getName() + " | " + registeredDate + " | " + status;
    }
}
