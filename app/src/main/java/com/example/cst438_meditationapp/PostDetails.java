package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String post = intent.getStringExtra("EXTRA");
        Toast.makeText(this, post, Toast.LENGTH_SHORT).show();
    }

    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, PostDetails.class);
        intent.putExtra("EXTRA", val);
        return intent;
    }
}