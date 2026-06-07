package io.kite.domain;

import java.util.Objects;

public class CourseCode {
    private final String value;

    public CourseCode(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Course code must not be blank");
        }
        if (!value.matches("C\\d{3}")) {
            throw new IllegalArgumentException("Course code must match format C001, C002, ...");
        }
        this.value = value;
    }

    public String getValue() { return value; }

    @Override public String toString() { return value; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseCode)) return false;
        CourseCode that = (CourseCode) o;
        return value.equals(that.value);
    }
    @Override public int hashCode() { return Objects.hash(value); }
}
