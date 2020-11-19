package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Meditation1Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation1_start);

    }

    public void startTimer(View view){
        Intent intent = Meditation1Screens.getIntent(this, "");
        startActivity(intent);
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, Meditation1Start.class);
        return intent;
    }
}