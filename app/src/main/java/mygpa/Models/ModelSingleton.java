package mygpa.Models;

import android.content.Context;
import android.util.Log;

import java.util.Map;

import mygpa.Objects.Course;
import mygpa.Objects.Instructor;
import mygpa.Objects.Semester;
import mygpa.Objects.User;
import mygpa.Persistence.PersistentStructure;
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
     * Used to register a user
     * @param fn
     * @param ln
     * @param un
     * @param pw
     * @param school
     * @param major
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
            instructorLastName, Semester sem, int year, int hours, boolean
            ip, Map<String, Double> weights) {
        Course toAdd = new Course(name, new Instructor(instructorFirstName,
                instructorLastName), sem, year, hours, weights, ip);
        sql.addCourse(toAdd);
    }

    /**
     * Will load the classes associated with the current user
     */
//    public void loadClasses() {
//        currentUser.setClasses(null /*Todo*/);
//    }
    public boolean doesUserExist(String username, String password) {
        return sql.correctUser(username, password);
    }

    public boolean doesCourseExist(Course course) {
        return false;
    }

}
