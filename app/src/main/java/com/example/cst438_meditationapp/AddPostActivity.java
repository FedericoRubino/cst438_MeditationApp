package com.example.cst438_meditationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddPostActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText mTitle;
    EditText mDescription;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        
        mTitle = findViewById(R.id.titleEt);
        mDescription = findViewById(R.id.descriptionEt);
    }
    
    public void createPost(View view){
        String title = mTitle.getText().toString();
        if(title.equals("")){
            Toast.makeText(this,"Title is missing", Toast.LENGTH_SHORT).show();
            return;
        }
        String description = mDescription.getText().toString();
        if(description.equals("")){
            Toast.makeText(this,"Description is missing", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Util.addPostToDB(db, title,description)){
            Toast.makeText(this,"You have successfully created a new post", Toast.LENGTH_SHORT).show();
            goToFeed();
        }
    }

    private void goToFeed() {
        startActivity(FeedActivity.getIntent(this,""));
    }

    public static Intent getIntent(Context context, String value){
        Intent intent = new Intent(context, AddPostActivity.class);
        return intent;
    }


}