package com.example.cst438_meditationapp;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EditPostActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    // Create a child reference
    // imagesRef now points to "images"
    StorageReference imagesRef = storageRef.child("images");
    StorageReference pathReference;

    EditText mTitle;
    EditText mDescription;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    Button imgButton;
    private String postID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        Intent intent = getIntent();
        postID = intent.getStringExtra("EXTRA");
        mTitle = findViewById(R.id.titleEt);
        mDescription = findViewById(R.id.descriptionEt);
        setUpDisplay();

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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPostActivity.this);
                builder.setPositiveButton("Take New Picture", dialogClickListener)
                        .setNegativeButton("Choose Existing", dialogClickListener).show();
            }
        });
    }//END OF onCreate()


    Map<String, Object> currentObj;
    Bitmap bmp;
    byte[] currentByteArray;
    public void setUpDisplay(){
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> object = document.getData();
                                if(object.get("id").equals(postID)) {
                                    pathReference = storageRef.child(object.get("imageURL").toString());
                                    final long ONE_MEGABYTE = 2024 * 2024;
                                    pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] byteArray) {
                                            currentByteArray = byteArray;
                                            bmp = BitmapFactory.decodeByteArray(currentByteArray, 0, currentByteArray.length);
                                            imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 130, 130, false));
                                            Log.d(TAG, "Successfully added " + pathReference);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            Log.d(TAG, "FAILED HERE" + pathReference);
                                        }
                                    });

                                    mTitle.setHint((String) object.get("title"));
                                    mDescription.setHint((String) object.get("description"));

                                    break;
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void savePost(View view){
        StorageReference postRef = storageRef.child("posts");
//        StorageReference pathReference = storageRef.child(currentObj.get("imageURL").toString());
//        db.collection("posts").document(postID).update(title: mTitle.getText().toString());

        String title = mTitle.getText().toString();
        if(title.equals("")){
            Toast toast = Toast.makeText(this,"Title is missing", Toast.LENGTH_SHORT);
            View v = toast.getView();
            v.setBackgroundResource(R.color.colorAccent);
            toast.show();
            return;
        }
        String description = mDescription.getText().toString();
        if(description.equals("")){
            Toast toast = Toast.makeText(this,"Description is missing", Toast.LENGTH_SHORT);
            View v = toast.getView();
            v.setBackgroundResource(R.color.colorAccent);
            toast.show();
            return;
        }
        // this where the upload to the database happens
        String url = uploadImage();
        //Toast.makeText(this, "The image url is: " + url,Toast.LENGTH_SHORT).show();
        if(Util.addPostToDB(db, title, description, url)){
            Toast.makeText(this,"You have successfully created a new post", Toast.LENGTH_SHORT).show();
//            uploadImage();
            goToFeed();
        }
    }

    public void deletePost(View view){
        Util.deletePostFromDB(postID);
        startActivity(Home.getIntent(this, ""));
    }

    public void updatePost(View view){
        //TODO Bobby add update post
        //Util.updatePostFromDB(postID);
        startActivity(Home.getIntent(this, ""));
    }

    private void goToFeed() {
        startActivity(FeedActivity.getIntent(this,""));
    }

    public static Intent getIntent(Context context, String value){
        Intent intent = new Intent(context, EditPostActivity.class);
        intent.putExtra("EXTRA", value);
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


    public String uploadImage(){
        String url = "images/"+ mTitle.getText().toString();
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