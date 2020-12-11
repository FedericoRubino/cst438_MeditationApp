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

    private static boolean found = false;
    public static boolean checkForUserInDB(FirebaseFirestore db, HashMap<String, Object> user) {
        final Object username = user.get("username");
        final Object password = user.get("password");

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> foundUser = document.getData();
                                if(foundUser.get("username").equals(username) && foundUser.get("password").equals(password)){
                                    found = true;
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return found;
    }

    public static void deletePostFromDB(final String postID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> object = document.getData();
                                if(object.get("id").equals(postID)) {
                                    document.getReference().delete();
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static boolean updatePostFromDB(final String description, final String title, final String imgURL, final String postID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> object = document.getData();
                                if(object.get("id").equals(postID)) {
                                    document.getReference().update("description", description);
                                    document.getReference().update("title", title);
                                    document.getReference().update("imgURL", imgURL);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return true;
    }

    public static boolean addPostToDB(FirebaseFirestore db, String title, String description, String imageURL) {
        HashMap<String, Object> newPost = new HashMap<>();
        newPost.put("title", title);
        newPost.put("description", description);
        newPost.put("imageURL", imageURL);
        newPost.put("postUser", LoginActivity.loggedUser);
        newPost.put("likeCount", 0);
        findLargestID();
        newPost.put("id", id + "");

        // Add a new document with a generated ID
        db.collection("posts")
                .add(newPost)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Log.d(TAG, "ID: " + id);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
        return true;
    }

    public static void upDateId(int num){
        id = num + 1;
    }

    public static void findLargestID(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int currentID = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> object = document.getData();
                                String stringID = object.get("id").toString();
                                int newID = Integer.parseInt(stringID);
                                if(newID > currentID) {
                                    currentID = newID;
                                }
                            }
                            upDateId(currentID);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static void setID(int i){
        id = i;
    }

    //modified for testing purposes
    public static boolean addPostToDB(FirebaseFirestore db, HashMap<String, Object> testPost) {
        HashMap<String, Object> newPost = testPost;

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    int i = 0;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            i = task.getResult().getDocuments().size();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        setID(i);
                    }

                });

        newPost.put("id", id + "");

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
}
