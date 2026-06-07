package io.kite.domain;

public class CourseSchedule {
    private final String dayOfWeek;
    private final int startHour;
    private final int endHour;

    public CourseSchedule(String dayOfWeek, int startHour, int endHour) {
        if (dayOfWeek == null || dayOfWeek.isBlank()) {
            throw new IllegalArgumentException("Day of week must not be blank");
        }
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        if (endHour < 1 || endHour > 24) {
            throw new IllegalArgumentException("End hour must be between 1 and 24");
        }
        if (startHour >= endHour) {
            throw new IllegalArgumentException("Start hour must be less than end hour");
        }
        this.dayOfWeek = normalize(dayOfWeek);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public boolean conflictsWith(CourseSchedule other) {
        if (other == null) return false;
        if (!this.dayOfWeek.equals(other.dayOfWeek)) return false;
        return Math.max(this.startHour, other.startHour) < Math.min(this.endHour, other.endHour);
    }

    private String normalize(String value) {
        return value.trim().toUpperCase();
    }

    public String getDisplayText() {
        return dayOfWeek + " " + formatHour(startHour) + " - " + formatHour(endHour);
    }

    private String formatHour(int hour) {
        return String.format("%02d:00", hour);
    }
}
