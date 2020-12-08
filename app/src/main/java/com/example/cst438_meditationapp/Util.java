package com.example.cst438_meditationapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Util {

    private static int id;

    public static boolean addUserToDB(FirebaseFirestore db, String username, String password){
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put("username", username);
        newUser.put("password", password);

        // Add a new document with a generated ID
        db.collection("users")
                .add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
        return true;
    }

    public static boolean addUserToDB(FirebaseFirestore db, HashMap<String, Object> newUser){

        // Add a new document with a generated ID
        db.collection("users")
                .add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
        return true;
    }

    public static boolean checkForUserInDB(FirebaseFirestore db, Map<String, Object> user) {
        final Object username = user.get("username");
        final Object password = user.get("password");
        final boolean[] found = new boolean[1];


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> foundUser = document.getData();
                                if(foundUser.get("username").equals(username) && foundUser.get("password").equals(password)){
                                    found[0] = true;
                                }
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return found[0];
    }

    public static boolean addPostToDB(FirebaseFirestore db, String title, String description, String imageURL) {
        HashMap<String, Object> newPost = new HashMap<>();
        newPost.put("title", title);
        newPost.put("description", description);
        newPost.put("imageURL", imageURL);
        newPost.put("postUser", LoginActivity.loggedUser);
        newPost.put("likeCount", 0);

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    int i = 0;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i++;
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        setID(i);
                    }

                });

        newPost.put("id", id);

        // Add a new document with a generated ID
        db.collection("posts")
                .add(newPost)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
        return true;
    }

    public static void setID(int i){
        id = i;
    }
}
