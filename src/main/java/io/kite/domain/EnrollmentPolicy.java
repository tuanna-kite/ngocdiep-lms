package io.kite.domain;

import java.util.List;

public class EnrollmentPolicy {
    public OperationResult checkCanRegister(Student student, Course course, List<Course> allCourses) {
        if (student == null) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Student must not be null.");
        }
        if (course == null) {
            return OperationResult.failure(ErrorCode.INVALID_INPUT, "Course must not be null.");
        }
        if (!student.isActive()) {
            return OperationResult.failure(ErrorCode.STUDENT_INACTIVE, "Student is not active.");
        }
        if (course.hasCurrentEnrollment(student)) {
            return OperationResult.failure(
                ErrorCode.STUDENT_ALREADY_REGISTERED,
                "Student already registered or waitlisted for this course."
            );
        }
        if (!course.isOpenForRegistration()) {
            return OperationResult.failure(ErrorCode.COURSE_CLOSED, "Course is not open for registration.");
        }
        if (course.hasPrerequisite() && !student.hasCompleted(course.getPrerequisiteCourse())) {
            return OperationResult.failure(
                ErrorCode.PREREQUISITE_NOT_MET,
                "Prerequisite course has not been completed."
            );
        }
        if (hasScheduleConflict(student, course, allCourses)) {
            return OperationResult.failure(
                ErrorCode.SCHEDULE_CONFLICT,
                "Schedule conflict with an existing course."
            );
        }
        return OperationResult.success("Student is eligible to register.");
    }

    private boolean hasScheduleConflict(Student student, Course targetCourse, List<Course> allCourses) {
        if (allCourses == null) return false;
        for (Course existingCourse : allCourses) {
            if (existingCourse == targetCourse) continue;
            if (existingCourse.hasActiveEnrollment(student)
                && existingCourse.getSchedule().conflictsWith(targetCourse.getSchedule())) {
                return true;
            }
        }
        return false;
    }
}
