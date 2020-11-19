package com.example.cst438_meditationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FeedActivity extends AppCompatActivity {

    // this is the storage for our images
    FirebaseStorage storage;
    // Create a storage reference from our app
    StorageReference storageRef;

    StorageReference pathReference;

    private FirebaseAuth mAuth;
    FirebaseUser user;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Map<String, Object>> objectArray;

    ImageView image;

    String selectedObject;
    FeedActivity.Adapter adapter;

    TextView lastItem;
    boolean firstClick = true;
    int lastObjPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        objectArray = new ArrayList();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        pathReference = null;

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


//        mAuth.signOut();
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            user = mAuth.getCurrentUser();
                            getDBInfo();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(FeedActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //getDBInfo();
                        }

                        // ...
                    }
                });
//        getDBInfo();

        final Switch toggle = (Switch) findViewById(R.id.filter_button);
        toggle.setText("All Posts");
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle.setText("Your Posts");
                    // The toggle is enabled
                    //filter posts in objectArray by current user primary key

                    // notify recycler view that list of assignments has changed
//                    adapter.notifyDataSetChanged();
                } else {
                    toggle.setText("All Posts");
                    // The toggle is disabled
                    //show all posts

                    // notify recycler view that list of assignments has changed
//                    adapter.notifyDataSetChanged();
                }
            }
        });

    }//onCreate


    private class Adapter  extends RecyclerView.Adapter<FeedActivity.ItemHolder> {

        @Override
        public FeedActivity.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(FeedActivity.this);
            return new FeedActivity.ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FeedActivity.ItemHolder holder, int position){
            holder.bind(objectArray.get(position));
        }

        @Override
        public int getItemCount() { return objectArray.size(); }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.feed_item, parent, false));
        }


        Map<String, Object> currentObj;
        Bitmap bmp;
        byte[] currentByteArray;
        public void bind(Map<String, Object> obj) {
            currentObj = obj;
            final TextView item = itemView.findViewById(R.id.postTv);;
            final TextView itemTitle = itemView.findViewById(R.id.titleTv);;

            if(obj.containsKey("imageURL")) {
                image = null;
//                while(image == null) {
                    // downloading the image from the cloud storage
                    // Create a reference with an initial file path and name
                    pathReference = storageRef.child(currentObj.get("imageURL").toString());
                    Log.d(TAG, "Tried to add an image " + pathReference);
                    final long ONE_MEGABYTE = 2024 * 2024;
                    pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] byteArray) {
                            currentByteArray = byteArray;
//                        ImageView image = (ImageView) findViewById(R.id.imageView1);
                            // Data for "images/island.jpg" is returns, use this as needed
                            bmp = BitmapFactory.decodeByteArray(currentByteArray, 0, currentByteArray.length);
                            image = itemView.findViewById(R.id.post_image);
                            image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
                            Log.d(TAG, "Successfully added " + pathReference);

                            item.setText(currentObj.get("description").toString());
                            itemTitle.setText(currentObj.get("title").toString());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Log.d(TAG, "FAILED HERE" + pathReference);
                        }
                    });
//                }
            }//if
            else {
                item.setText(currentObj.get("description").toString());
                itemTitle.setText(currentObj.get("title").toString());
            }
                //make item clickable
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //save selected object
                        selectedObject = objectArray.get(getAdapterPosition()).get("title").toString();
                        Toast.makeText(FeedActivity.this, selectedObject, Toast.LENGTH_SHORT).show();

                        //change text color of selected item
                        item.setTextColor(Color.parseColor("#FFFFFF"));
                        if (!firstClick) {
                            lastItem.setTextColor(Color.parseColor("#000000"));
                        } else {
                            firstClick = false;
                        }
                        if (lastObjPos == getAdapterPosition()) {
                            item.setTextColor(Color.parseColor("#FFFFFF"));
                        }
                        lastItem = item;
                        lastObjPos = getAdapterPosition();
                    }
                });

        }//bind()
    }

    // This gets the information from the database and then calls the
    // recyclerview set up
    public void getDBInfo(){
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> object = document.getData();
                                objectArray.add(object);
                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Log.d(TAG, document.getId() + " => " + objectArray);
                            }
                            setUpRecyclerView();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    // Sets up the recyclerview!
    public void setUpRecyclerView(){
        if(objectArray.size() > 0 ) {
            //holds objects
            RecyclerView rvAssignment = findViewById(R.id.feed_recycler_view);
            rvAssignment.setLayoutManager(new LinearLayoutManager(this));
            adapter = new Adapter();
            rvAssignment.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Empty database!!!", Toast.LENGTH_SHORT).show();
        }
    }

    // starts the add post activity
    public void addPost(View view){
        startActivity(AddPostActivity.getIntent(this,""));
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, FeedActivity.class);
        intent.putExtra("EXTRA", val);
        return intent;
    }
}