package com.example.neyesek;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    EditText email, password;
    TextView info;
    Button login;
    int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.btn_login);
        info = (TextView) findViewById(R.id.tv_error);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action_login(email.getText().toString(),password.getText().toString());
            }
        });

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
    public void action_login(String userEmail, String userPassword) {
        /*if (userEmail.equals("admin") && userPassword.equals("1234")) {
            Context context = LoginScreen.this;
            Class destination = ButtonsScreen.class;
            Intent intent = new Intent(context, destination);
            startActivity(intent);
        }else {
            if(userEmail == null || userPassword == null){
                info.setVisibility(View.VISIBLE);
                info.setText("Formu doldurunuz");
            }else {
                if(userEmail.equals("admin") == false){
                    info.setVisibility(View.VISIBLE);
                    info.setText("Kullanıcı bulunamadı");
                    }
                if(userEmail.equals("admin@neyesek.com") && userPassword.equals("1234") == false){
                    info.setVisibility(View.VISIBLE);
                    info.setText("Yanlış Şifre");
                    }
                }
            }*/
        Context context = LoginScreen.this;
        Class destination = ButtonsScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }
}
