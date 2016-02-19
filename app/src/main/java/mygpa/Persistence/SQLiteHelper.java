package mygpa.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is the SQLite database that will store user data locally
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    // Constants used in construction of database
    private static final int DATABASE_VERSION = 0;
    private static final String DATABASE_NAME = "MyGPA.db";

    // Constants used in construction of main table
    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_SCHOOL = "school";
    private static final String COLUMN_MAJOR = "major";
    private static final String COLUMN_GPA = "gpa";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
