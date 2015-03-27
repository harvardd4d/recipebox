package com.example.d4d.recipebox;

import android.test.AndroidTestCase;

import java.util.Collections;

/**
 * Created by Richard on 3/27/2015.
 */
public class ServerRecipeDatabaseTest extends AndroidTestCase {

    private static Recipe toast = new Recipe("Toasted Toast", "Toasty Toasted Toast", 1, 1, 1, Collections.singletonList("Toast"), "Toast toast", 2, null);
    private static Recipe cb = new Recipe("Chinese Broccoli", "Lightly flavored Broccoli from the East", 1, 1, 1, Collections.singletonList("Broccoli; Sesame oil"), "Steam the Broccoli.  Add sesame oil and serve.", 1, null);
    private ServerRecipeDatabase db;

    @Override
    public void setUp() throws Exception {
        db = new ServerRecipeDatabase();
    }

    public void testGet() {
        Recipe servertoast = db.getRecipe(2);
        Recipe serverdb = db.getRecipe(1);
        assertTrue("THE TOAST IS A LIE", servertoast.equals(toast));
        assertTrue("THE CHINESE BROCCOLI IS A LIE", serverdb.equals(cb));

        Recipe notarecipe = db.getRecipe(9001);
        assertNull("RECIPE OVER 9000", notarecipe);
    }
}
