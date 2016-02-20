package mygpa.Objects;

import java.util.Map;

public class Course {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}