package mygpa.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import mygpa.Models.ModelSingleton;
import mygpa.Objects.R;

public class RegistrationActivity extends AppCompatActivity {

    /**
     * Will be used in the alert dialog to confirm registration
     */
//    enum Answer {YES, NO};
//    private Answer answer;

    ModelSingleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        singleton = ModelSingleton.getInstance(this);
    }

    /**
     * Validates the form, registers the user if confirmed, and launches the
     * Main activity.
     * @param view boilerplate
     */
    public void registerUser(View view) {
        if (validateForm()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private boolean  validateForm() {
        EditText firstName = (EditText) findViewById(R.id
                .registration_first_name_text);
        EditText lastName = (EditText) findViewById(R.id
                .registration_last_name_text);
        EditText school = (EditText) findViewById(R.id
                .registration_school_text);
        EditText major = (EditText) findViewById(R.id.registration_major_text);
        EditText username = (EditText) findViewById(R.id
                .registration_username_text);
        EditText password = (EditText) findViewById(R.id
                .registration_password_text);
        final EditText confirmPassword = (EditText) findViewById(R.id
                .registration_password_confirm_text);

        //Get the actual text stored in the EditText views
        String actualFirstName = firstName.getText().toString();
        String actualLastName = lastName.getText().toString();
        String actualSchool = school.getText().toString();
        String actualMajor = major.getText().toString();
        String actualUsername = username.getText().toString();
        final String actualPassword = password.getText().toString();
        String actualConfirmPassword = confirmPassword.getText().toString();

        //The app will require all fields to be filled
        if (actualFirstName.trim().length()  == 0
                || actualLastName.trim().length() == 0
                || actualSchool.trim().length() == 0
                || actualMajor.trim().length() == 0
                || actualUsername.trim().length() == 0
                || actualPassword.length() == 0
                || actualConfirmPassword.length() == 0) {

            firstName.setError("All fields are required to be filled!");
            firstName.requestFocus();
            return false;
        } else if (!actualConfirmPassword.equals(actualPassword)) {
            confirmPassword.setError("Passwords do not match");
            confirmPassword.requestFocus();
            return false;
        } else {
            singleton.addUser(actualFirstName, actualLastName,
                    actualUsername, actualPassword, actualSchool, actualMajor);
            return true;
        }


    }
//    /**
//     * This method is used to display a dialog asking for user confirmation
//     * before registering that User.
//     */
//    private void showConfirmRegistrationDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder
//                (RegistrationActivity.this);
//        builder.setTitle("Registration complete?")
//                .setPositiveButton("Yes!", new DialogInterface
//                        .OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        answer = Answer.YES;
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        answer = Answer.NO;
//                        dialog.cancel();
//                    }
//                })
//                .show();
//    }

    /**
     * If the user clicks the cancel button, she will be returned to the
     * Login screen.
     * @param view boilerplate
     */
    public void cancelRegisterActivity(View view) {
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
    }
}
