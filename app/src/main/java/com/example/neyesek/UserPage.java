package com.example.neyesek;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserPage extends AppCompatActivity {

    public TextView user_info;
    public TextView fav_rest;
    public TextView prev_rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        String restNames[] = getRestaurants();


        user_info = (TextView)findViewById(R.id.user_info);
        user_info.append("Mahmut AKTAŞ");
        user_info.append(" \n");
        user_info.append("aktasmahmut97@gmail.com");

        fav_rest = (TextView)findViewById(R.id.fav_rest);
        for (String restName : restNames) {
            fav_rest.append(restName);
            fav_rest.append(" \n");
            fav_rest.append(" \n");

        }

        prev_rest = (TextView)findViewById(R.id.prev_rest);
        for (String restName : restNames) {
            prev_rest.append(restName);
            prev_rest.append(" \n");
            prev_rest.append(" \n");

        }

        user_info.setEnabled(false);
        fav_rest.setEnabled(false);
        prev_rest.setEnabled(false);


    }

    public static String[] getRestaurants(){

        return new String[]{
            "Tavuk Dünyası",
            "Mc Donalds",
            "HD İskender",
            "Burger King",
            "Nusret",
            "Pilav Dünyası",
            "Subway"
        };

    }

}
