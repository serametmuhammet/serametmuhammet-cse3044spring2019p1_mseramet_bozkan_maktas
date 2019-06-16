package com.example.neyesek;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.neyesek.activity.SearchNearByPlaces;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.SQLOutput;

public class ButtonsScreen extends AppCompatActivity {

    Button profile;
    Button nearbyPlaces;
    private Button postBtn;
    private StorageReference storage;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private Uri uri = null;
    private DatabaseReference mDatabaseUsers,levelRef;
    private FirebaseUser mCurrentUser;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        getSupportActionBar().hide();

        profile = (Button)findViewById(R.id.profile_screen);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonsScreen.this, UserPage.class);
                ButtonsScreen.this.startActivity(intent);
            }
        });


        storage = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
        databaseRef = database.getInstance().getReference().child("NeYesek").child(mCurrentUser.getUid()).child("Previous Rest");
        levelRef = database.getInstance().getReference().child("NeYesek").child(mCurrentUser.getUid()).child("Level");

        int count =0;

        levelRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() == null){
                    dataSnapshot.getRef().setValue("0");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void action_random(View v){

        Context context = ButtonsScreen.this;
        Class destination = SearchNearByPlaces.class;
        Intent intent = new Intent(context, destination);

        startActivity(intent);

    }
}
