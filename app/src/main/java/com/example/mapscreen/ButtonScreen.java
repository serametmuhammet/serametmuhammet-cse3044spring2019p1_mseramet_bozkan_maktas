package com.example.mapscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Map;


public class ButtonScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        getSupportActionBar().hide();
    }

    public void action_random(View v){

        Context context = ButtonScreen.this;
        Class destination = MapScreen.class;

        Intent intent = new Intent(context, destination);

        startActivity(intent);

    }
}

