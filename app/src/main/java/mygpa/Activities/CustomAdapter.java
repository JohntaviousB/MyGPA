package mygpa.Activities;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mygpa.Objects.R;

class CustomAdapter<T> extends ArrayAdapter<String> {

    public CustomAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.course_list_row, parent,
                false);
        String[] courseArgs = getItem(position).split(",");
        //Course toString
        // prints details separated by commas
        /**
         * args[0] = Course Name
         * args[1] = Instructor First Name
         * args[2] = Instructor Last Name
         * args[3] = Semester
         * args[4] = Year
         * args[5] = In Progress
         * an additional arg[6] is created with the objects array
         * args[6] = Grade
         */
        TextView courseName, instructorName, semesterAndYear, grade;

        courseName = (TextView) customView.findViewById(R.id
                .course_list_course_num);
        instructorName = (TextView) customView.findViewById(R.id
                .course_list_instructor);
        semesterAndYear = (TextView) customView.findViewById(R.id
                .course_list_sem_year);
        grade = (TextView) customView.findViewById(R.id.course_list_grade);

        courseName.setText(courseArgs[0]);
        instructorName.setText(courseArgs[1] + " " + courseArgs[2]);
        semesterAndYear.setText(courseArgs[3] + " " + courseArgs[4]);
        double num_grade = Double.parseDouble(courseArgs[6]);
        switch ((int)num_grade) {
            case 4:
                grade.setText("A");
                break;
            case 3:
                grade.setText("B");
                break;
            case 2:
                grade.setText("C");
                break;
            case 1:
                grade.setText("D");
                break;
            case 0:
                grade.setText("F");
                break;
        }
        if (courseArgs[5].equalsIgnoreCase("true")) {
            grade.setText("In Progress");
        }
        if (position % 2 == 0) {
            customView.setBackgroundColor(getContext().getResources()
                    .getColor(R.color.colorPrimary));
        } else {
            customView.setBackgroundColor(Color.WHITE);
        }
        return customView;
    }
}
