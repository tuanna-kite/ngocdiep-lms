package io.kite.domain;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentPolicy enrollmentPolicy;
    private final List<Course> courses;

    public EnrollmentService(EnrollmentPolicy enrollmentPolicy) {
        if (enrollmentPolicy == null) {
            throw new IllegalArgumentException("Enrollment policy must not be null");
        }
        this.enrollmentPolicy = enrollmentPolicy;
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course must not be null");
        }
        courses.add(course);
    }

    public OperationResult register(Student student, Course course) {
        OperationResult checkResult = enrollmentPolicy.checkCanRegister(student, course, courses);
        if (checkResult.isFailure()) {
            return checkResult;
        }
        return course.registerEnrollment(student);
    }

    public OperationResult cancel(Student student, Course course) {
        if (course == null) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Course must not be null.");
        }
        return course.cancelEnrollment(student);
    }
}
