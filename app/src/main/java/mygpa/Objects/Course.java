package mygpa.Objects;

import java.util.Map;

public class Course implements Comparable<Course> {

    private Instructor instructor;
    private Semester semester;
    private int year;
    private int creditHours;
    private Map<String, Double> weights;
    private boolean inProgress;
    private String id;

    public Course(String id, Instructor inst, Semester sem, int year, int
            credits, Map<String, Double> weights, boolean ip) {
        this.instructor = inst;
        this.semester = sem;
        this.year = year;
        this.creditHours = credits;
        this.weights = weights;
        this.inProgress = ip;
        this.id = id;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Semester getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public Map<String, Double> getWeights() {
        return weights;
    }

    public boolean inProgress() {
        return inProgress;
    }

    public boolean setInstructor(Instructor i) {
        if (i != null) {
            this.instructor = i;
            return true;
        } else {
            return false;
        }
    }

    public boolean setSemester(Semester sem) {
        if (sem != null) {
            this.semester = sem;
            return true;
        } else {
            return false;
        }
    }

    public boolean setYear(int year) {
        if (year > 2000) {
            this.year = year;
            return true;
        } else {
            return false;
        }
    }

    public boolean setCreditHours(int credits) {
        if (credits > 0) {
            this.creditHours = credits;
            return true;
        } else {
            return false;
        }
    }

    public boolean setWeight(String category, double weight) {
        if (this.weights.containsKey(category)) {
            weights.put(category, weight);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCategoryName(String old, String newName) {
        Double weight = weights.remove(old);
        if (weight != null) {
            weights.put(newName, weight);
            return true;
        } else {
            return false;
        }
    }

    public void setProgress(boolean b) {
        this.inProgress = b;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Course)) {
            return false;
        } else {
            Course that = (Course) o;
            return this.id.equals(that.id) &&
                    this.instructor == that.instructor &&
                    this.semester == that.semester &&
                    this.year == that.year;
        }
    }

    @Override
    public String toString() {
        // prints details separated by commas
        /**
         * args[0] = Course Name
         * args[1] = Instructor First Name
         * args[2] = Instructor Last Name
         * args[3] = Semester
         * args[4] = Year
         * args[5] = In Progress
         */
        return id +"," + instructor.getFirstName() + "," + instructor
                .getLastName() + "," + semester.toString() + "," + year + ","
                + inProgress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Order by year first (most recent first), then semester, then course name
     * @param another
     * @return negative number if this instance should go first
     */
    @Override
    public int compareTo(Course another) {
        if (this.year != another.year) {
            return another.year - this.year;
        }
        if (this.semester != another.semester) {
            return another.semester.ordinal() - this.semester.ordinal();
        }
        return this.id.compareTo(another.id);
    }
}