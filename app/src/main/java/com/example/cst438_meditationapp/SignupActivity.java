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


    final String difficultPassword = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z[0-9]]{6,}$";
    final String difficultPasswordMessage = "Minimum six characters, at least one uppercase letter, one lowercase letter, one number and one special character";

    final String mediumPassword = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,}$";
    final String mediumPasswordMessage = "Minimum six characters, at least one uppercase letter, one lowercase letter, and one number";

    final String easyPassword = "^(?=.*[A-Za-z])[A-Za-z[0-9]]{6,}$";
    final String easyPasswordMessage = "Minimum six characters, at least one uppercase letter, and one lowercase letter";

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
        } else if (username.length() < 4){
            Toast.makeText(this,"Username has to be at least of length 4.", Toast.LENGTH_SHORT).show();
            return;
        }
        final String password = mPassword.getText().toString();
        if(password.equals("")){
            Toast.makeText(this,"Password is missing", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.matches(mediumPassword)){
            Toast.makeText(this, mediumPasswordMessage, Toast.LENGTH_SHORT).show();
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
                                goToLoginPage();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * Method that sends the user to the login page
     */
    public void goToLoginPage(){
        Intent intent = LoginActivity.getIntent(this, "");
        startActivity(intent);
    }

    public void loginButton(View view){
        goToLoginPage();
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, SignupActivity.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }

}