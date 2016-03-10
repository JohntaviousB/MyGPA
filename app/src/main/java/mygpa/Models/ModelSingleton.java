package mygpa.Models;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mygpa.Objects.Course;
import mygpa.Objects.Instructor;
import mygpa.Objects.Semester;
import mygpa.Objects.User;
import mygpa.Persistence.SQLiteHelper;

/**
 * The Singleton that will exist for the duration of the app's life
 */
public class ModelSingleton {

    private static ModelSingleton instance;
    private static SQLiteHelper sql;
    private User currentUser;

    private ModelSingleton(){}

    public static ModelSingleton getInstance(Context context) {
        sql = new SQLiteHelper(context);
        if (instance == null) {
            instance = new ModelSingleton();
        }
        return instance;
    }

    /**
     * Used to prevent multiple users on the app. If an account is already
     * created then the user must input the credentials or use the recovery
     * option
     * @return true if there is no user, false if the user is already registered
     */
    public boolean canUserRegister() {
        if (currentUser != null) {
            return false;
        } else {
            try {
                currentUser = sql.retrieveUser();
            } catch (SQLiteHelper.NoRegisteredUserException e) {
                return true;
            }
            return false;
        }
    }
    /**
     * Used to register a user
     * @param fn the first name
     * @param ln the last name
     * @param un the username
     * @param pw the password
     * @param school the school
     * @param major the major
     */
    public void addUser(String fn, String ln, String un, String pw, String
            school, String major) {
        User toAdd = new User(fn, ln, un, pw, school, major);
        if (!doesUserExist(un, pw)) {
            sql.addUser(toAdd);
            this.currentUser = toAdd;
        }
    }
    public void signIn() {
        currentUser = sql.retrieveUser();
        try {
            currentUser.setClasses(sql.retrieveCourses());
        }
        catch (SQLiteHelper.NoRegisteredCoursesException e) {
            //No harm no foul, the user will just have to add some courses
            Log.e("NO_COURSES", e.getMessage());
        }
    }
    public void addCourse(String name, String instructorFirstName, String
            instructorLastName, String sem, int year, int hours, boolean
            ip, Map<String, Double> weights, double grade) {

        Semester semester = sem.equals("Spring") ? Semester.Spring :
                            sem.equals("Summer") ? Semester.Summer :
                                    Semester.Fall;

        Course toAdd = new Course(name, new Instructor(instructorFirstName,
                instructorLastName), semester, year, hours, weights, ip);
        sql.addCourse(toAdd);
        sql.addUserToCourse(currentUser, toAdd, grade);
        currentUser.addCourse(toAdd, grade);
    }

    public String getUserFirstName() {
        return currentUser.getFirstName();
    }
    public String getUserLastName() {
        return currentUser.getLastName();
    }
    public double getUserGPA() {
        return currentUser.getGpa();
    }
    public Map<Course, Double> getUserClasses() {
        return currentUser.getClasses();
    }

    public boolean doesUserExist(String username, String password) {
        return sql.correctUser(username, password);
    }

    public boolean doesCourseExist(Course course) {
        return false;
    }

    /**
     * This method creates a comma-separated list of course details in an array
     * for easy parsing
     * @return the array with each entry containing comma-separated values or
     * null if there are no courses
     */
    public List<String> getCourseDetails() {
        Map<Course, Double> courses = getUserClasses();
        if (courses != null && courses.size() > 0){
            List<String> result = new ArrayList<>();

            for (Map.Entry<Course, Double> entry : courses.entrySet()) {

                result.add(entry.getKey().toString() + "," + entry.getValue());
            }
            return result;
        }
        return null;
    }
}
