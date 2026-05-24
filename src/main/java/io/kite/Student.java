package io.kite;

public class Student {
    private String id;
    private String name;
    private String email;
    private String phone;

    public Student(String id, String name, String email) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + email;
    }
}