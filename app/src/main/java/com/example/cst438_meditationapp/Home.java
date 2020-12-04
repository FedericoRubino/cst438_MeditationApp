package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Home extends AppCompatActivity {

    public static final String EXTRA = "HOME EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.top_nav, menu);
//        return true;
//    }

    public void startMeditation2(View view){
        Intent intent = Meditation2Start.getIntent(this,"");
        startActivity(intent);
    }

    public void startMeditation1(View view){
        Intent intent = Meditation1Start.getIntent(this,"");
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

    public void startFeed(MenuItem item) {
        Intent intent = FeedActivity.getIntent(this,"");
        startActivity(intent);
    }

    public void startHome(MenuItem item) {
        Intent intent = Home.getIntent(this,"");
        startActivity(intent);
    }
}