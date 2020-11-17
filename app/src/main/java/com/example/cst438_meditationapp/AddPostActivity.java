package com.example.cst438_meditationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class AddPostActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    // Create a child reference
    // imagesRef now points to "images"
    StorageReference imagesRef = storageRef.child("images");

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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Take New Picture clicked
                                dispatchTakePictureIntent();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Choose Existing clicked
                                dispatchStoragePictureIntent();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPostActivity.this);
                builder.setPositiveButton("Take New Picture", dialogClickListener)
                        .setNegativeButton("Choose Existing", dialogClickListener).show();
            }
        });
    }//END OF onCreate()
    
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
        // this where the upload to the database happens
        String url = uploadImage(description.substring(2,7));
        Toast.makeText(this, "The image url is: " + url,Toast.LENGTH_SHORT).show();
        if(Util.addPostToDB(db, title, description, url)){
            Toast.makeText(this,"You have successfully created a new post", Toast.LENGTH_SHORT).show();
//            uploadImage();
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

    private void dispatchStoragePictureIntent(){
        Intent pictureStorageIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            startActivityForResult(pictureStorageIntent, 0);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            Toast.makeText(this,"An error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    //retrieve picture (picture passed as extra with key "data" from return intent)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //display retrieved image in imageView
            imageView.setImageBitmap(imageBitmap);
        }else if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                //display retrieved image in imageView
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public String uploadImage(String code){
        String url = "images/" +code+"/"+ mTitle.getText().toString();
        StorageReference tempRef = storageRef.child(url);

        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = tempRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
        return url;

    }

}//END OF AddPostActivity


