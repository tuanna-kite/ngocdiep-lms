package io.kite;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main(String[] args) {
        List<String> studentIds = new ArrayList<>();
        List<String> studentNames = new ArrayList<>();

        List<String> courseIds = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();
        List<Integer> courseMaxStudents = new ArrayList<>();
        List<Integer> courseCurrentStudents = new ArrayList<>();

        studentIds.add("S001");
        studentNames.add("An");

        courseIds.add("C001");
        courseNames.add("Java Foundation");
        courseMaxStudents.add(2);
        courseCurrentStudents.add(0);

        int courseIndex = 0;

        if (courseCurrentStudents.get(courseIndex) < courseMaxStudents.get(courseIndex)) {
            courseCurrentStudents.set(courseIndex, courseCurrentStudents.get(courseIndex) + 1);
            System.out.println(studentNames.get(0) + " enrolled in " + courseNames.get(0));
        } else {
            System.out.println("Course is full");
        }
    }
}

