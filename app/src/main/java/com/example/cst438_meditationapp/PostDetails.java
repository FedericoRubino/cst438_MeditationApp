package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
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
    private boolean flag = false;
    StorageReference storageRef;
    StorageReference pathReference;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Intent intent = getIntent();
        postID = intent.getStringExtra("EXTRA");
        Toast.makeText(this, postID, Toast.LENGTH_SHORT).show();
        editButton = findViewById(R.id.editButton);
        author = findViewById(R.id.username);
        description = findViewById(R.id.description);
        post = findViewById(R.id.image_view);
        title = findViewById(R.id.postTitle);
        setButton();
        setUpDisplay();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        pathReference = null;
    }

    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, PostDetails.class);
        intent.putExtra("EXTRA", val);
        return intent;
    }

    public void setButton(){
        if(checkForUser())
        {
            editButton.setEnabled(true);
            editButton.setVisibility(View.VISIBLE);
        }
    }

    public boolean checkForUser(){
        db.collection("posts")
                .whereEqualTo("id", postID).whereEqualTo("postUser", username)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        flag = true;
                    }
                });
        return flag;
    }

    public void setUpDisplay(){
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(PostDetails.this, "In loop", Toast.LENGTH_SHORT).show();
                                Map<String, Object> object = document.getData();
                                if(object.get("id").equals(postID)) {
                                    //pathReference = storageRef.child(object.get("imageURL").toString());
                                    //post.setImageBitmap(Bitmap.createScaledBitmap(bmp, 130, 130, false));
                                    title.setText((String) object.get("title"));
                                    description.setText((String) object.get("description"));
                                    author.setText((String) object.get("postUser"));
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}