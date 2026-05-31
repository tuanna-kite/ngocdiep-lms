package io.kite;

public class Student {

    private String id;
    private String name;
    private String email;
    private String phone;

    public Student(String id, String name, String email) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or blank");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
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