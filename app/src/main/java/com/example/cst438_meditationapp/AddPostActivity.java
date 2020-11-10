package com.example.cst438_meditationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddPostActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText mTitle;
    EditText mDescription;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    Button imgButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        
        mTitle = findViewById(R.id.titleEt);
        mDescription = findViewById(R.id.descriptionEt);

        //check if device has a camera
        final boolean hasCamera = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);

        //initialize variables for add image feature
        imageView = findViewById(R.id.image_view);
        imgButton = findViewById(R.id.img_button);

        //listener for add image button
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

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

    //launch camera app on device
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            Toast.makeText(this,"No external camera application found", Toast.LENGTH_SHORT).show();
        }
    }

    //retrieve picture taken (picture passed as extra with key "data" from return intent)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //display retrieved image in imageView
            imageView.setImageBitmap(imageBitmap);
        }
    }

}