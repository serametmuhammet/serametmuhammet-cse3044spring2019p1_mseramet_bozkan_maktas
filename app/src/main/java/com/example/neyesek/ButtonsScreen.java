package com.example.neyesek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class ButtonsScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        getSupportActionBar().hide();
    }

    public void action_random(View v){

        Context context = ButtonsScreen.this;
        Class destination = MapScreen.class;

        Intent intent = new Intent(context, destination);

        startActivity(intent);

    }
}
