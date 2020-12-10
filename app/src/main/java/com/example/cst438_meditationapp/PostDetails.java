package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.Map;

import static android.content.ContentValues.TAG;

public class PostDetails extends AppCompatActivity {
    private TextView editButton;
    private TextView author;
    private TextView description;
    private TextView title;
    private ImageView post;
    private String username = LoginActivity.loggedUser;
    private String postID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;
    StorageReference pathReference;
    FirebaseStorage storage;
    ImageView imageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Intent intent = getIntent();
        postID = intent.getStringExtra("EXTRA");
        editButton = findViewById(R.id.editButton);
        author = findViewById(R.id.username);
        description = findViewById(R.id.description);
        post = findViewById(R.id.image_view);
        title = findViewById(R.id.postTitle);
        setUpDisplay();
        setButton();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        pathReference = null;
        imageView = findViewById(R.id.image_view);
    }

    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, PostDetails.class);
        intent.putExtra("EXTRA", val);
        return intent;
    }

    public void editActivity(View view){
        Intent intent = EditPostActivity.getIntent(this, postID);
        startActivity(intent);
    }

    public void setButton(){
            editButton.setEnabled(true);
            editButton.setVisibility(View.VISIBLE);
    }

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

                                    title.setText((String) object.get("title"));
                                    description.setText((String) object.get("description"));
                                    author.setText((String) object.get("postUser"));

                                    if(object.get("postUser").equals(username)){
                                        setButton();
                                    }

                                    break;
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


//    public void setUpDisplay(){
//        db.collection("posts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Map<String, Object> object = document.getData();
//                                if(object.get("id").equals(postID)) {
//                                    //pathReference = storageRef.child(object.get("imageURL").toString());
//                                    //post.setImageBitmap(Bitmap.createScaledBitmap(bmp, 130, 130, false));
//                                    title.setText((String) object.get("title"));
//                                    description.setText((String) object.get("description"));
//                                    author.setText((String) object.get("postUser"));
//
//                                    if(object.get("postUser").equals(username)){
//                                        setButton();
//                                    }
//                                }
//
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }
}