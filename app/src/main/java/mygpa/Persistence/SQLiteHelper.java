package mygpa.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import mygpa.Objects.Course;
import mygpa.Objects.Instructor;
import mygpa.Objects.Semester;
import mygpa.Objects.User;

/**
 * This class is the SQLite database that will store user data locally
 */
public class SQLiteHelper extends SQLiteOpenHelper implements
        PersistentStructure {


    // Constants used in construction of database
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "MyGPA.db";

    // Constants used in construction of users table
    // We may have multiple users if more than one account is created on an
    // individual Android device
    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_SCHOOL = "school";
    private static final String COLUMN_MAJOR = "major";
    private static final String COLUMN_GPA = "gpa";
    private static final String COLUMN_PASSWORD = "password";

    //Constants used in construction of course table
    private static final String TABLE_COURSES = "Courses";
    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_INSTRUCTOR_FIRST_NAME =
            "instructor_first_name";
    private static final String COLUMN_INSTRUCTOR_LAST_NAME =
            "instructor_last_name";
    private static final String COLUMN_SEMESTER = "course_semester";
    private static final String COLUMN_YEAR = "course_year";
    private static final String COLUMN_CREDIT_HOURS = "credit_hours";
    private static final String COLUMN_UC_GRADE = "grade";
    private static final String COLUMN_UC_IN_PROGRESS = "in_progress"; //0 or 1

//    //Constants used in construction of users_courses table
//    private static final String TABLE_UC = "users_courses";
//    private static final String COLUMN_UC_USERNAME = "username";
//    private static final String COLUMN_UC_ID = "id";

    //Constants used in construction of course_weights table
    private static final String TABLE_COURSE_WEIGHTS = "course_weights";
    private static final String COLUMN_CW_ID = "id";
    private static final String COLUMN_CW_CATEGORY = "category";
    private static final String COLUMN_CW_WEIGHT = "weight";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_SCHOOL + " TEXT, "
                + COLUMN_MAJOR + " TEXT, "
                + COLUMN_GPA + " REAL)";

        String createCoursesTable = "CREATE TABLE " + TABLE_COURSES + "("
                + COLUMN_COURSE_ID + " TEXT, "
                + COLUMN_INSTRUCTOR_FIRST_NAME + " TEXT, "
                + COLUMN_INSTRUCTOR_LAST_NAME + " TEXT, "
                + COLUMN_SEMESTER + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_CREDIT_HOURS + " INTEGER,"
                + COLUMN_UC_GRADE + " REAL, "
                + COLUMN_UC_IN_PROGRESS + " INTEGER, "
                + "PRIMARY KEY (" + COLUMN_COURSE_ID + ", " + COLUMN_SEMESTER
                + ", " + COLUMN_YEAR + "))";

        String createTableCourseWeights = "CREATE TABLE " +
                TABLE_COURSE_WEIGHTS + "("
                + COLUMN_CW_ID + " INTEGER, "
                + COLUMN_CW_CATEGORY + " TEXT, "
                + COLUMN_CW_WEIGHT + " REAL, "
                + "PRIMARY KEY (" + COLUMN_CW_ID + ", " + COLUMN_CW_CATEGORY
                + "), FOREIGN KEY (" + COLUMN_CW_ID + ") REFERENCES "
                + TABLE_COURSES + "(" + COLUMN_COURSE_ID + ") ON UPDATE CASCADE"
                + " ON DELETE CASCADE)";

        db.execSQL(createUsersTable);
        db.execSQL(createCoursesTable);
        db.execSQL(createTableCourseWeights);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_WEIGHTS);

        onCreate(db);
    }

    /**
     * Adds a user entity into the database
     *
     * @param user the user to be added
     */
    public void addUser(User user) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME, user.getFirstName());
        cv.put(COLUMN_LAST_NAME, user.getLastName());
        cv.put(COLUMN_SCHOOL, user.getSchool());
        cv.put(COLUMN_GPA, user.getGpa());
        cv.put(COLUMN_MAJOR, user.getMajor());
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_PASSWORD, user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERS, null, cv);
        db.close();
    }

    /**
     * Adds a course into the database as well its weights
     *
     * @param course The course to be added
     */
    public void addCourse(Course course) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COURSE_ID, course.getId());
        cv.put(COLUMN_INSTRUCTOR_FIRST_NAME, course.getInstructor()
                .getFirstName());
        cv.put(COLUMN_INSTRUCTOR_LAST_NAME, course.getInstructor()
                .getLastName());
        cv.put(COLUMN_SEMESTER, course.getSemester().toString());
        cv.put(COLUMN_YEAR, course.getYear());
        cv.put(COLUMN_CREDIT_HOURS, course.getCreditHours());
        cv.put(COLUMN_UC_IN_PROGRESS, course.inProgress() ? 1 : 0);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_COURSES, null, cv);

        cv = new ContentValues();

        //Now we include the course weights
        if (!course.getWeights().isEmpty()) {
            for (Map.Entry<String, Double> entry : course.getWeights().entrySet()) {
                cv.put(COLUMN_CW_ID, course.getId());
                cv.put(COLUMN_CW_CATEGORY, entry.getKey());
                cv.put(COLUMN_CW_WEIGHT, entry.getValue());
                db.insert(TABLE_COURSE_WEIGHTS, null, cv);
            }
        }
        db.close();
    }

    /**
     * Adds a users grade to a course
     *
     * @param user   The user to be added
     * @param course A corresponding course that is taken by this user
     * @param grade  The grade (e.g. 4.0, 3.0, 2.0, etc) for the course
     */
    //TODO this doesn't handle courses that may have been repeated. It
    // doesn't use the complete primary key for a course
    public void addUserToCourse(User user, Course course, double grade) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_UC_GRADE, grade);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_COURSES, cv, COLUMN_COURSE_ID + "= ?", new
                String[]{course.getId()});
    }

    /**
     * Since this is not an online application, just check if the user
     * entered the correct username and password to successfully login.
     * @param username The username entered by the user
     * @param pw The password entered by the user
     * @return true if the user entered valid credentials, false otherwise
     */
    public boolean correctUser(String username, String pw) {
        String query = "SELECT " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD
                + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = "
                + "'" + username + "' AND " + COLUMN_PASSWORD + "= '"
                + pw + "'";

        Cursor queryResult = this.getWritableDatabase().rawQuery(query, null);

        return queryResult.moveToFirst();
    }

    public User retrieveUser() {
        String userQuery = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = this.getWritableDatabase().rawQuery(userQuery, null);

        if (!cursor.moveToFirst()) throw new NoRegisteredUserException
                ("Attempted to retrieve a user when there is no user present " +
                        "in the database. Dang. ");
        else {
            String firstName = cursor.getString(0);
            String lastName = cursor.getString(1);
            String username = cursor.getString(2);
            String password = cursor.getString(3);
            String school = cursor.getString(4);
            String major = cursor.getString(5);
            String gpa = cursor.getString(6);
            double actualGPA = Double.parseDouble(gpa);

            User user = new User(firstName, lastName, username, password,
                    school, major);
            user.setGpa(actualGPA);
            return user;
        }
    }

    /**
     * Similar to {@code retrieveUser()}, this retrieves the Courses taken by
     * the user and maps each course to its grade
     * @return the mapping of each course to its grade
     */
    public Map<Course, Double> retrieveCourses() {
        /**
         * We have to retrieve each course and then retrieve the weights for
         * assignments for  each course we retrieved.
         */
        String coursesQuery = "SELECT * FROM " + TABLE_COURSES;
        Cursor cursor = this.getWritableDatabase().rawQuery(coursesQuery, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            throw new NoRegisteredCoursesException(" Attempted to retrieve " +
                    "courses when there were none present! ");
        } else {
            Map<Course, Double> result = new TreeMap<>();
            do {
                String id = cursor.getString(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String semester = cursor.getString(3);
                String year = cursor.getString(4);
                String creditHours = cursor.getString(5);
                String grade = cursor.getString(6);
                String inProgress = cursor.getString(7);

                Semester actualSemester = semester.equals("Spring") ?
                        Semester.Spring : semester.equals("Summer") ?
                        Semester.Summer : Semester.Fall;
                int actualYear = Integer.parseInt(year);
                int actualCreditHours = Integer.parseInt(creditHours);
                double actualGrade = Double.parseDouble(grade);
                boolean actualInProgress = Integer.parseInt(inProgress) != 0;

                //We just retrieved one course, now query the database for
                // its weights
                Map<String, Double> weights = new TreeMap<>();
                String weightsQuery = "SELECT * FROM " + TABLE_COURSE_WEIGHTS
                        + " WHERE " + COLUMN_CW_ID + " = '" + id + "'";
                Cursor weightsCursor = this.getWritableDatabase().rawQuery
                        (weightsQuery, null);
                if ( weightsCursor.moveToFirst() ) {

                    do {
                        String category = weightsCursor.getString(1);
                        String weight = weightsCursor.getString(2);
                        double actualWeight = Double.parseDouble(weight);
                        weights.put(category, actualWeight);
                    } while ( weightsCursor.moveToNext() );

                    weightsCursor.close();
                }
                //We've got all the weights (if any) for the current course. We
                // can now create and add an instance to our final Map
                // result
                Course currentCourse = new Course(id, new Instructor
                        (firstName, lastName), actualSemester,
                        actualYear, actualCreditHours, weights,
                        actualInProgress);
                result.put(currentCourse, actualGrade);
            } while (cursor.moveToNext());
            return result;
        }
    }

    public class NoRegisteredUserException extends RuntimeException {
        public NoRegisteredUserException(String m) {
            super(m);
        }
    }
    public class NoRegisteredCoursesException extends RuntimeException {
        public NoRegisteredCoursesException(String m) {
            super(m);
        }
    }
}
