package io.kite.domain;

import java.util.Objects;

public class StudentEmail {
    private final String value;

    public StudentEmail(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        if (value.contains(" ")) {
            throw new IllegalArgumentException("Email must not contain spaces");
        }
        if (!value.contains("@") || !value.contains(".")) {
            throw new IllegalArgumentException("Email format is invalid");
        }
        this.value = value;
    }

    public String getValue() { return value; }
    @Override public String toString() { return value; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentEmail)) return false;
        StudentEmail that = (StudentEmail) o;
        return value.equalsIgnoreCase(that.value);
    }
    @Override public int hashCode() { return Objects.hash(value.toLowerCase()); }
}
