package com.example.d4d.recipebox;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void testGetsLoose() {
        List<Recipe> chintoast = new ArrayList<Recipe>();
        chintoast.add(cb);
        chintoast.add(toast);
        List<Recipe> serversays = db.getRecipesLoose("", 1, 1, 1);
        assertTrue("ChineseToast say what?", chintoast.equals(serversays));

        assertNull("Found ham fried bacon. Why?", db.getRecipesLoose("Ham fried bacon", 1, 2, 3));
    }

    public void testGetsStrict() {
        List<Recipe> servertoast = db.getRecipesStrict(toast.getName(), 1, 1, 1);
        assertTrue("Toast is a lie", toast.equals(servertoast.get(0)));

        List<Recipe> servercb = db.getRecipesStrict(cb.getName(), 1, 1, 1);
        assertTrue("Chinese is lie", cb.equals(servercb.get(0)));

        assertNull("Found ham fried bacon. Why?", db.getRecipesStrict("Ham fried bacon", 1, 2, 3));
    }

    // TODO get somebody to write more tests

    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }
}
