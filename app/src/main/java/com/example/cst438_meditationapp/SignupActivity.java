package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static android.content.ContentValues.TAG;

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
        final String username = mUsername.getText().toString();
        if(username.equals("")){
            Toast.makeText(this,"Username is missing", Toast.LENGTH_SHORT).show();
            return;
        }
        final String password = mPassword.getText().toString();
        if(password.equals("")){
            Toast.makeText(this,"Password is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(mUsername.getText().toString().equals(document.getData().get("username"))){
                                    Toast.makeText(SignupActivity.this, "That username already exists", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            if(Util.addUserToDB(db, username,password)){
                                Toast.makeText(SignupActivity.this, "You have successfully created a new user", Toast.LENGTH_SHORT).show();
                                goToHomePage();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void goToHomePage(){
        Intent intent = LoginActivity.getIntent(this, "");
        startActivity(intent);
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, SignupActivity.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }

}