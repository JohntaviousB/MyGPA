package mygpa.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mygpa.Models.ModelSingleton;
import mygpa.Objects.R;

public class LoginActivity extends AppCompatActivity {
    ModelSingleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        singleton = ModelSingleton.getInstance(this);
    }

    /**
     * Verifies the user entered proper credentials and attempt to sign in
     * @param view boilerplate
     */
    public void signInClick(View view) {

        EditText usernameET = (EditText) findViewById(R.id.username_edit_text);
        EditText passwordET = (EditText) findViewById(R.id.password_edit_text);
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        if (username.trim().length() == 0) {
            //If there is no username input
            usernameET.requestFocus();
            usernameET.setError("Enter your username!");
        } else if (password.trim().length() == 0) {
            passwordET.requestFocus();
            passwordET.setError("Enter your password!");
        } else {
            //Now let's try to validate these credentials!
            if ( singleton.doesUserExist(username, password) ) {
                singleton.signIn();
                Intent mainActivityIntent = new Intent(this,
                        MainActivity.class);
                startActivity(mainActivityIntent);
            } else {
                passwordET.setError("Your username or password did not match.");
                passwordET.requestFocus();
            }
        }
    }
    public void registerClick(View view) {
        Intent registerActivityIntent = new Intent(this,
                RegistrationActivity.class);
        startActivity(registerActivityIntent);
    }
}
