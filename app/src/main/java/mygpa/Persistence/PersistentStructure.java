package mygpa.Persistence;

import mygpa.Objects.Course;
import mygpa.Objects.User;

/**
 * Interface representing required functionality for any means used to
 * persist the data.
 */
public interface PersistentStructure {

    void addUser(User u);
    void addCourse(Course c);
    void addUserToCourse(User u, Course c, double grade);

}
