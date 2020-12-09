package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {
    private String username = LoginActivity.loggedUser;
    private TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        user = findViewById(R.id.username);
        user.setText(username);
    }

    public void startMainActivity(View view){
        Intent intent = MainActivity.getIntent(this.getApplicationContext(),"");
        startActivity(intent);
    }

    public void startFeed(View v) {
        Intent intent = FeedActivity.getIntent(this,"");
        startActivity(intent);
    }

    public void startHome(View v) {
        Intent intent = Home.getIntent(this,"");
        startActivity(intent);
    }

    public void startUserProfile(View v) {
        Intent intent = UserProfile.getIntent(this,"");
        startActivity(intent);
    }

    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, UserProfile.class);
        intent.putExtra("EXTRA", val);
        return intent;
    }


}