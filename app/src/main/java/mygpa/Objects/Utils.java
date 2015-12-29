package mygpa.Objects;
import java.util.Map;

public class Utils {
	public static double calcGPA(Map<Course, Double> classes) {
		int totalHours = 0;
		double totalPoints = 0;
		boolean haveCompletedACourse = false;
		for (Course course : classes.keySet()) {
			if ( !course.inProgress() ) {
				haveCompletedACourse = true;
				double grade = classes.get(course);
				double points = course.getCreditHours() * grade;
				totalPoints += points;
				totalHours += course.getCreditHours();
			}
		}
		return haveCompletedACourse ? totalPoints / totalHours : 0;
	}
}