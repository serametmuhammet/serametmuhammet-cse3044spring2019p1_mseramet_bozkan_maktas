package com.example.neyesek;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
    }

    // TODO(1) We will connect this with the register implicit intent
    // And write all xml codes on register activity
    public void action_register(View v){

        Context context = LoginScreen.this;
        Class destination = RegisterScreen.class;

        Intent intent = new Intent(context, destination);

        startActivity(intent);

    }

    // TODO(2) Connect this with the main page
    // Before connection make sure the user exist
    public void action_login(View v){

    }
}
