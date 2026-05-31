package io.kite;

public class Student {

    private String id;
    private String name;
    private String email;
    private String phone;

    public Student(String id, String name, String email) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create student: student id must not be blank.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create student: name must not be blank.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create student: email must not be blank.");
        }

        this.id = id;
        this.name = name;
        this.email = email;
    }

    public boolean hasSameId(Student other) {
        if (other == null) {
            return false;
        }

        return this.id.equals(other.id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return id + " - " + name + " - " + email;
    }
}