package io.kite.domain;

public class CourseReport {
    public String generateSummary(Course course) {
        if (course == null) return "Course is null.";
        return course.getSummary();
    }

    public String generateEnrollmentList(Course course) {
        if (course == null) return "Course is null.";

        StringBuilder builder = new StringBuilder();
        builder.append("Active students in ").append(course.getName()).append(":").append(System.lineSeparator());
        if (course.getActiveEnrollments().isEmpty()) {
            builder.append("No active students.").append(System.lineSeparator());
        } else {
            for (Enrollment enrollment : course.getActiveEnrollments()) {
                builder.append("- ").append(enrollment.getStudent().getDisplayInfo()).append(System.lineSeparator());
            }
        }

        builder.append("Waiting list:").append(System.lineSeparator());
        if (course.getWaitlistedEnrollments().isEmpty()) {
            builder.append("No waitlisted students.").append(System.lineSeparator());
        } else {
            for (Enrollment enrollment : course.getWaitlistedEnrollments()) {
                builder.append("- ").append(enrollment.getStudent().getDisplayInfo()).append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
