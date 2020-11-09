package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    public static final String EXTRA = "SIGN IN EXTRA";
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mUsername = findViewById(R.id.usernameSigninEt);
        mPassword = findViewById(R.id.passwordSigninEt);

    }

    // create a new user method
    public void createUserAccount(View view){
        String username = mUsername.getText().toString();
        if(username.equals("")){
            Toast.makeText(this,"Username is missing", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = mPassword.getText().toString();
        if(password.equals("")){
            Toast.makeText(this,"Password is missing", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Util.addUserToDB(db, username,password)){
            Toast.makeText(this,"You have successfully created a new user", Toast.LENGTH_SHORT).show();
            goToHomePage();
        }
    }

    public void goToHomePage(){
        Intent intent = Home.getIntent(this, "");
        startActivity(intent);
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, SignupActivity.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }

}