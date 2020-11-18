package com.example.cst438_meditationapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private FirebaseFirestore db;
    private HashMap<String, Object> newUser;

    @Before
    public void setup(){
        db = FirebaseFirestore.getInstance();
        newUser = new HashMap<>();
        newUser.put("username", "cst");
        newUser.put("password", "cst123");
        assertTrue(Util.addUserToDB(db, newUser));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testUsername() {
        assertFalse(Util.checkForUserInDB(db, newUser));
        assertNotEquals("abc", newUser.get("username"));
    }
}