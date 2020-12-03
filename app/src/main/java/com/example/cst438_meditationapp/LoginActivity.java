package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA = "LOGIN EXTRA";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String loggedUser;

    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.usernameLoginEt);
        mPassword = findViewById(R.id.passwordLoginEt);
    }

    public void loginUser(View view){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> user = document.getData();
                                if(mUsername.getText().toString().equals(user.get("username")) && mPassword.getText().toString().equals(user.get("password"))){
//                                    Toast.makeText(this,"You have succesfully logged in!!", Toast.LENGTH_SHORT).show();
                                    loggedUser = user.get("username").toString();

                                    goToHomePage();
                                    return;
                                }
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            return;
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void goToHomePage(){
        Intent intent = Home.getIntent(this, "");
        startActivity(intent);
    }

    public void goToSignUp(View view){
        Intent intent = SignupActivity.getIntent(this,"");
        startActivity(intent);
    }

    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }
}