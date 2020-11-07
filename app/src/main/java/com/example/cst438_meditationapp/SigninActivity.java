package com.example.cst438_meditationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    public static final String EXTRA = "SIGNIN EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }


    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, SigninActivity.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }

}