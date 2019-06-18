package com.example.neyesek;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class RegisterScreen extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText fullname;
    Button register;
    EditText email;
    EditText password;
    private StorageReference storage;
    private FirebaseDatabase database;
    private DatabaseReference gittimRef;
    private DatabaseReference favRef, levelRef;
    private FirebaseAuth mAuth;
    private Uri uri = null;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        register = (Button) findViewById(R.id.registerButton);
        fullname = (EditText) findViewById(R.id.et_reg_fullname);
        email = (EditText) findViewById(R.id.et_reg_email);
        password = (EditText) findViewById(R.id.et_reg_password);

        auth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailS = email.getText().toString();
                String passwordS = password.getText().toString();

                if(TextUtils.isEmpty(emailS)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordS)){
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
                }
                if (passwordS.length()<6){
                    Toast.makeText(getApplicationContext(),"Password length must be at least 6 digit",Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(emailS,passwordS)
                        .addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                //İşlem başarısız olursa kullanıcıya bir Toast mesajıyla bildiriyoruz.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterScreen.this, "Yetkilendirme Hatası",
                                            Toast.LENGTH_SHORT).show();
                                }

                                //İşlem başarılı olduğu takdir de giriş yapılıp MainActivity e yönlendiriyoruz.
                                else {

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName("" + fullname.getText().toString())
                                            .build();

                                    mAuth = FirebaseAuth.getInstance();
                                    mCurrentUser = mAuth.getCurrentUser();
                                    mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
                                    gittimRef = database.getInstance().getReference().child("NeYesek").child(mCurrentUser.getUid()).child("Fullname");
                                    gittimRef.setValue(fullname.getText().toString());
                                    Toast.makeText(RegisterScreen.this,"KAYIT BAŞARILI. HOŞGELDİNİZ",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterScreen.this, ButtonsScreen.class));
                                    finish();
                                }

                            }
                        });






            }
        });


    }

    // TODO(3) Send the user back to the loginScreen
    // try to use this without intent
    public void action_returnLogin(View v){

    }

}
