package mygpa.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import mygpa.Models.ModelSingleton;
import mygpa.Objects.R;

public class MainActivity extends AppCompatActivity {

    ModelSingleton singleton;
    ArrayAdapter<String> adapter;
    ListView listView;
    List<String> courseDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleton = ModelSingleton.getInstance(this);

        TextView userName = (TextView) findViewById(R.id
                .welcome_user_name_text); //This is the user's actual name,
        // not his username. E.g. 'Johntavious Barkley' not 'jbarkley'
        TextView gpa = (TextView) findViewById(R.id.welcome_gpa_text);

        userName.setText(singleton.getUserFirstName() + " " +
                singleton.getUserLastName());
        gpa.setText(new DecimalFormat("0.00").format((singleton.getUserGPA())));

        courseDetails = singleton.getCourseDetails();
        if (courseDetails != null) {
            adapter = new CustomAdapter<>(this, R.layout
                    .course_list_row, courseDetails);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter.notifyDataSetChanged();
//    }

    public void logOut(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    //todo
    public void calculateIntent(View view) {
    }

    //todo
    public void updateProfile(View view) {
    }

    public void addCourses(View view) {
        final Dialog coursesDialog = new Dialog(this);
        coursesDialog.setContentView(R.layout.add_course_layout);
        coursesDialog.setTitle("Add Course");
        coursesDialog.show();

        Button cancelButton = (Button) coursesDialog.findViewById(R.id
                .cancel_add_course);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursesDialog.cancel();
            }
        });

        //Populate the semester spinner
        final Spinner semesterSpinner = (Spinner) coursesDialog.findViewById
                (R.id
                .semester_spinner);
        String[] semesters = {"Spring", "Summer", "Fall"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, semesters);
        semesterSpinner.setAdapter(adapter);

        //Populate the year picker
        final NumberPicker yearPicker = (NumberPicker) coursesDialog.findViewById(R.id
                .numberPicker);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2050);

        //Once the ok button is clicked, validate the input
        Button addCourseButton = (Button) coursesDialog.findViewById(R.id
                .add_course_ok_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText courseNum = (EditText) coursesDialog.findViewById(R.id
                        .course_dialog_course_id);
                EditText teacherFN = (EditText) coursesDialog.findViewById(R.id
                        .course_dialog_instructor_first);
                EditText teacherLN = (EditText) coursesDialog.findViewById(R.id
                        .course_dialog_instructor_last);
                CheckBox inProgressBox = (CheckBox) coursesDialog
                        .findViewById(R.id
                                .in_progress_check_box);
                EditText creditHours = (EditText) coursesDialog.findViewById
                        (R.id.credit_hours);
                EditText grade = (EditText) coursesDialog.findViewById(R.id
                        .course_dialog_grade);

                String actualCourseNum = courseNum.getText().toString().trim();
                String actualTeacherFN = teacherFN.getText().toString().trim();
                String actualTeacherLN = teacherLN.getText().toString().trim();
                boolean inProgress = inProgressBox.isChecked();
                int actualCreditHours;
                try {
                    actualCreditHours = Integer.parseInt(creditHours.getText()

                            .toString().trim());
                } catch (NumberFormatException e) {
                    creditHours.requestFocus();
                    creditHours.setError("Enter the credit hours!");
                    return;
                }

                int year = yearPicker.getValue();
                double actualGrade;
                try {
                    actualGrade = Double.parseDouble(grade.getText().toString()
                            .trim());
                } catch (NumberFormatException e) {
                    grade.requestFocus();
                    grade.setError("Enter your awarded points :) ");
                    return;
                }
                String actualSemester = String.valueOf(semesterSpinner
                        .getSelectedItem());

                //add method to input weights
                if (actualCourseNum.length() == 0
                        || actualTeacherFN.length() == 0
                        || actualTeacherLN.length() == 0) {
                    courseNum.setError("Please enter all information!");
                    courseNum.requestFocus();
                } else {
                    singleton.addCourse(actualCourseNum, actualTeacherFN,
                            actualTeacherLN, actualSemester, year,
                            actualCreditHours, inProgress, new HashMap<String,
                                    Double>(), actualGrade);
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
//                    coursesDialog.dismiss();

                }
            }
        });
    }
}
