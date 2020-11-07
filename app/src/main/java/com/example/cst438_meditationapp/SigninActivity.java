package com.example.cst438_meditationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class SigninActivity extends AppCompatActivity {

    public static final String EXTRA = "SIGN IN EXTRA";
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
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
            finish();
        }
    }



    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, SigninActivity.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }

}