package com.example.cst438_meditationapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private FirebaseFirestore db;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = FirebaseFirestore.getInstance();
    }

    @Test
    public void testUsername() {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put("username", "cst");
        newUser.put("password", "cst123");
        Util.addUserToDB(db, newUser);
//        User retrieveUser = getUserFromDB(FirebaseFirestore db, User newUser);
//        assertEquals(newUser.getUsername(), retrieveUser.getUsername());
//        assertNotEquals("abc", newUser.getUsername());
    }
}