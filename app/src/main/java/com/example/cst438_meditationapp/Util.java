package com.example.cst438_meditationapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Util {

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

}
