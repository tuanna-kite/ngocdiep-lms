package io.kite.domain;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final CourseCode code;
    private String name;
    private int maxStudents;
    private boolean openForRegistration;
    private final CourseLevel level;
    private final CourseSchedule schedule;
    private Course prerequisiteCourse;
    private final List<Enrollment> enrollments;

    public Course(CourseCode code, String name, int maxStudents, CourseLevel level, CourseSchedule schedule) {
        if (code == null) throw new IllegalArgumentException("Course code must not be null");
        requireText(name, "Course name");
        requirePositive(maxStudents, "Maximum capacity");
        if (level == null) throw new IllegalArgumentException("Course level must not be null");
        if (schedule == null) throw new IllegalArgumentException("Course schedule must not be null");

        this.code = code;
        this.name = name;
        this.maxStudents = maxStudents;
        this.level = level;
        this.schedule = schedule;
        this.openForRegistration = true;
        this.enrollments = new ArrayList<>();
    }

    public OperationResult rename(String newName) {
        if (newName == null || newName.isBlank()) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Course name must not be blank.");
        }
        if (this.name.equalsIgnoreCase(newName.trim())) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "New course name must be different from current name.");
        }
        this.name = newName.trim();
        return OperationResult.success("Course renamed successfully.");
    }

    public OperationResult changeCapacity(int newMaxStudents) {
        if (newMaxStudents <= 0) {
            return OperationResult.failure(ErrorCode.INVALID_CAPACITY, "New capacity must be greater than 0.");
        }
        if (newMaxStudents < getActiveEnrollmentCount()) {
            return OperationResult.failure(
                ErrorCode.INVALID_CAPACITY,
                "New capacity cannot be less than current active student count."
            );
        }
        this.maxStudents = newMaxStudents;
        int promotedCount = promoteWaitlistedStudentsIfPossible();
        if (promotedCount > 0) {
            return OperationResult.success(
                "Course capacity changed successfully. Promoted " + promotedCount + " waitlisted student(s)."
            );
        }
        return OperationResult.success("Course capacity changed successfully.");
    }

    public OperationResult closeRegistration() {
        if (!openForRegistration) {
            return OperationResult.failure(ErrorCode.COURSE_ALREADY_CLOSED, "Course has already been closed.");
        }
        openForRegistration = false;
        return OperationResult.success("Course has been closed for registration.");
    }

    public OperationResult openRegistration() {
        if (openForRegistration) {
            return OperationResult.failure(ErrorCode.COURSE_ALREADY_OPEN, "Course is already open for registration.");
        }
        openForRegistration = true;
        return OperationResult.success("Course has been opened for registration.");
    }

    public OperationResult requirePrerequisite(Course prerequisiteCourse) {
        if (prerequisiteCourse == null) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Prerequisite course must not be null.");
        }
        if (this.code.equals(prerequisiteCourse.code)) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Course cannot require itself as prerequisite.");
        }
        this.prerequisiteCourse = prerequisiteCourse;
        return OperationResult.success("Prerequisite course assigned successfully.");
    }

    public OperationResult registerEnrollment(Student student) {
        if (student == null) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Student must not be null.");
        }
        EnrollmentStatus status = isFull() ? EnrollmentStatus.WAITLISTED : EnrollmentStatus.ACTIVE;
        enrollments.add(new Enrollment(student, this, status));

        if (status == EnrollmentStatus.WAITLISTED) {
            return OperationResult.success("Course is full. Student added to waiting list.");
        }
        return OperationResult.success("Student registered successfully.");
    }

    public OperationResult cancelEnrollment(Student student) {
        Enrollment enrollment = findCurrentEnrollmentOf(student);
        if (enrollment == null) {
            return OperationResult.failure(
                ErrorCode.STUDENT_NOT_REGISTERED,
                "Student does not have an active or waitlisted enrollment in this course."
            );
        }

        boolean wasActive = enrollment.isActive();
        OperationResult cancelResult = enrollment.cancel();
        if (cancelResult.isFailure()) return cancelResult;

        if (wasActive) {
            int promotedCount = promoteWaitlistedStudentsIfPossible();
            if (promotedCount > 0) {
                return OperationResult.success("Enrollment cancelled successfully. A waitlisted student has been promoted.");
            }
        }
        return OperationResult.success("Enrollment cancelled successfully.");
    }

    public boolean hasCurrentEnrollment(Student student) {
        return findCurrentEnrollmentOf(student) != null;
    }

    public boolean hasActiveEnrollment(Student student) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isActive() && enrollment.belongsTo(student)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        return getActiveEnrollmentCount() >= maxStudents;
    }

    public int getActiveEnrollmentCount() {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isActive()) count++;
        }
        return count;
    }

    public int getWaitlistedEnrollmentCount() {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isWaitlisted()) count++;
        }
        return count;
    }

    public int getAvailableSlots() {
        return maxStudents - getActiveEnrollmentCount();
    }

    private Enrollment findCurrentEnrollmentOf(Student student) {
        if (student == null) return null;
        for (Enrollment enrollment : enrollments) {
            if (!enrollment.isCancelled() && enrollment.belongsTo(student)) {
                return enrollment;
            }
        }
        return null;
    }

    private int promoteWaitlistedStudentsIfPossible() {
        int promotedCount = 0;
        while (!isFull()) {
            Enrollment firstWaitlisted = findFirstWaitlistedEnrollment();
            if (firstWaitlisted == null) break;
            OperationResult result = firstWaitlisted.activateFromWaitlist();
            if (result.isSuccess()) promotedCount++;
        }
        return promotedCount;
    }

    private Enrollment findFirstWaitlistedEnrollment() {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isWaitlisted()) {
                return enrollment;
            }
        }
        return null;
    }

    public List<Enrollment> getActiveEnrollments() {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isActive()) result.add(enrollment);
        }
        return result;
    }

    public List<Enrollment> getWaitlistedEnrollments() {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isWaitlisted()) result.add(enrollment);
        }
        return result;
    }

    public String getSummary() {
        return code.getValue() + " - " + name
            + " | Level: " + level
            + " | Schedule: " + schedule.getDisplayText()
            + " | Active: " + getActiveEnrollmentCount() + "/" + maxStudents
            + " | Waitlist: " + getWaitlistedEnrollmentCount()
            + " | Open: " + openForRegistration;
    }

    public boolean hasPrerequisite() { return prerequisiteCourse != null; }

    private static void requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
    }

    private static void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0");
        }
    }

    public CourseCode getCode() { return code; }
    public String getName() { return name; }
    public CourseLevel getLevel() { return level; }
    public CourseSchedule getSchedule() { return schedule; }
    public Course getPrerequisiteCourse() { return prerequisiteCourse; }
    public boolean isOpenForRegistration() { return openForRegistration; }
}
