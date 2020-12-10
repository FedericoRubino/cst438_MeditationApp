package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    Button testButton;
    // Access a Cloud Firestore instance from your Activity
//    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String EXTRA = "MAIN EXTRA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //*************TEST RECYCLER VIEW*************
//        testButton = findViewById(R.id.Test_Button);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
//                startActivity(intent);
//            }
//        });
//        //*************TEST RECYCLER VIEW*************
//
//        readFromUserDB();
    }
//
//    // starts the signup activity
//    public void startSignupActivity(View view){
//        Intent intent = SignupActivity.getIntent(this.getApplicationContext(),"");
//        startActivity(intent);
//    }

    // starts the login activity
    public void startLogInActivity(View view){
        Intent intent = LoginActivity.getIntent(this.getApplicationContext(),"");
        startActivity(intent);
    }

//
//    public void readFromUserDB(){
//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }

    public void startOnboarding(View view) {
        Intent intent = Onboarding.getIntent(this,"");
        startActivity(intent);
    }

    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }
}
