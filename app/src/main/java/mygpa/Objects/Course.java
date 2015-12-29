package mygpa.Objects;

import java.util.Map;

public class Course {

	private String name;
	private Instructor instructor;
	private String number;
	private Semester semester;
	private int year;
	private int creditHours;
	private Map<String, Double> weights;
	private boolean inProgress;

	public Course(String name, Instructor inst, String num, Semester sem, int year, int credits, Map<String, Double> weights) {
		this.name = name;
		this.instructor = inst;
		this.number = num;
		this.semester = sem;
		this.year = year;
		this.creditHours = credits;
		this.weights = weights;
		this.inProgress = false;
	}
	public String getName() { return name; }
	public Instructor getInstructor() { return  instructor; }
	public String getNumber() { return number; }
	public Semester getSemester() { return semester; }
	public int getYear() { return year; }
	public int getCreditHours() { return creditHours; }
	public Map<String, Double> getWeights() { return weights; }
	public boolean inProgress() { return inProgress; }

	public boolean setName(String s) {
		if (s != null && s.length() > 0) {
			this.name = s;
			return true;
		} else {
			return false;
		}
	}
	public boolean setInstructor(Instructor i) {
		if (i != null) {
			this.instructor = i;
			return true;
		} else {
			return false;
		}
	}
	public boolean setNumber(String n) {
		if (n != null && n.length() > 0) {
			this.number = n;
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
		if ( this.weights.containsKey(category) ) {
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
		if ( !(o instanceof Course) ) {
			return false;
		} else {
			Course that = (Course) o;
			return this.name.equals(that.name) &&
                    this.number.equals(that.number) &&
					this.instructor == that.instructor &&
					this.semester == that.semester &&
					this.year == that.year;
		}
	}
}