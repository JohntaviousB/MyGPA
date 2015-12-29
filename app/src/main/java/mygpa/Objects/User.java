package mygpa.Objects;
import java.util.Map;

public class User {
	
	private String firstName;
	private String lastName;
    private String school;
	private String major;
	private Map<Course, Double> classes;
	private double gpa;

	public User(String first, String last, String school, String major, Map<Course, Double> classes) {
		this.firstName = first;
		this.lastName = last;
		this.school = school;
		this.major = major;
		this.classes = classes;
		this.gpa = classes.isEmpty() ? 0 : Utils.calcGPA(classes);
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
    public void addCourse(Course course, double grade) {
        if ( !(classes.containsKey(course)) ) {
            classes.put(course, grade);
            //recalc gpa TODO
        }
    }
    public void removeCourse(Course course) {
        classes.remove(course);
        //recalc gpa TODO
    }
}