package mygpa.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mygpa.Objects.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registerClick(View view) {
        Intent registerActivityIntent = new Intent(this,
                RegistrationActivity.class);
        startActivity(registerActivityIntent);
    }
}
