package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    public static final String EXTRA = "HOME EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView username = findViewById(R.id.currentUser);;
        username.setText(LoginActivity.loggedUser);
    }

    public void startMeditation2(View view){
        Intent intent = Meditation2Start.getIntent(this,"");
        startActivity(intent);
    }


    public void startFeed(View view){
        Intent intent = FeedActivity.getIntent(this,"");
        startActivity(intent);
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, Home.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }
}