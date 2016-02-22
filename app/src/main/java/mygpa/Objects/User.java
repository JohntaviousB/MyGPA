package mygpa.Objects;

import java.util.Map;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String school;
    private String major;
    private Map<Course, Double> classes;
    private double gpa;


    public User(String first, String last, String username, String password,
                String school, String major) {
        this.firstName = first;
        this.lastName = last;
        this.username = username;
        this.password = password;
        this.school = school;
        this.major = major;
//        this.gpa = classes.isEmpty() ? 0 : Utils.calcGPA(classes);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public Map<Course, Double> getClasses() {
        return classes;
    }

    public void setClasses(Map<Course, Double> classes) {
        this.classes = classes;
        this.gpa = classes.isEmpty() ? 0 : Utils.calcGPA(classes);
    }

    public void addCourse(Course course, Double grade) {
        if (!(classes.containsKey(course))) {
            classes.put(course, grade);
            //recalc gpa TODO
        }
    }

    public void removeCourse(Course course) {
        classes.remove(course);
        //recalc gpa TODO
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}