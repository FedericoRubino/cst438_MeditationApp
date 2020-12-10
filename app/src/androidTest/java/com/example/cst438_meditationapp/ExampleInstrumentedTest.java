package com.example.cst438_meditationapp;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
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
        newUser.put("password", "Cst123");
        assertTrue(Util.addUserToDB(db, newUser));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testUsername() {
        boolean result = Util.checkForUserInDB(db, newUser);
        assertTrue(result);
//        assertNotEquals("abc", newUser.get("username"));
    }

    @Test
    public void testUsername2() {
        assertEquals("cst", newUser.get("username"));
    }

    @Test
    public void testPassword() {
        assertNotEquals("abc321", newUser.get("password"));
    }

    @Test
    public void testPassword2() {
        assertEquals("Cst123", newUser.get("password"));
    }

//    @Test
//    public void testAddPost(){
//        HashMap<String, Object> testPost = new HashMap<>();
//
//        String title = "Test Post";
//        String description = "Test Description";
//        String url = "Test URL";
//        String user = "Test User";
//
//        testPost.put("title", title);
//        testPost.put("description", description);
//        testPost.put("imageURL", url);
//        testPost.put("postUser", "Test_User");
//        testPost.put("likeCount", 0);
//
//        assertTrue(Util.addPostToDB(db, testPost));
//        assertEquals("Test Post", testPost.get);
//    }
}