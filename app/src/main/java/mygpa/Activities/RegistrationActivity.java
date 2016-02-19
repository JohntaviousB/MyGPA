package mygpa.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mygpa.Objects.R;

public class RegistrationActivity extends AppCompatActivity {

    /**
     * Will be used in the alert dialog to confirm registration
     */
//    enum Answer {YES, NO};
//    private Answer answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    /**
     * Validates the form, registers the user if confirmed, and launches the
     * Main activity.
     * @param view boilerplate
     */
    public void registerUser(View view) {
        //TODO validate forms are complete
//        showConfirmRegistrationDialog();
//        if (answer == Answer.YES) {
//            //Todo Logic to store user
//            startActivity(new Intent(this, MainActivity.class));
//        }
        startActivity(new Intent(this, MainActivity.class));

    }

    private boolean validateForm() {
        //Todo
        return true;
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
