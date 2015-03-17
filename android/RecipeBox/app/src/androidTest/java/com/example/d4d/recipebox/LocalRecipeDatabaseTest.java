package com.example.d4d.recipebox;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 3/17/2015.
 */
public class LocalRecipeDatabaseTest extends AndroidTestCase {
    private LocalRecipeDatabase database;

    private static Recipe recipe1 = new Recipe("toast", "toast", 1, 1, 1, Collections.singletonList("toast"), "toast", 1);
    private static Recipe recipe2 = new Recipe("cereal", "cereal", 2, 2, 2, Collections.singletonList("cereal"), "cereal", 2);
    private static Recipe recipe3 = new Recipe("toasted cereal", "toasted cereal", 3, 3, 3, Collections.singletonList("toasted cereal"), "toasted cereal", 3);

    @Override
    public void setUp() throws Exception{
        super.setUp();
        // prefixes context with test for database operations
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        database = new LocalRecipeDatabase(context);
    }

    public void testPreconditions() {
        assertTrue("Database not set to debug mode", LocalRecipeDatabase.DEBUGMODE);
        assertNotNull("database is null", database);
    }

    public void testGetRecipe() {
        Recipe r = database.getRecipe(1);
        assertTrue("Recipe id 1 is wrong", r.equals(recipe1));

        Recipe r2 = database.getRecipe(2);
        assertTrue("Recipe id 1 is wrong", r.equals(recipe1));
    }

    public void testGetRecipesStrict() {
        assertTrue("getRecipesStrict(cereal) failed",
                database.getRecipesStrict("cereal", 2, 2, 2).equals(Collections.singletonList(recipe2)));

        assertTrue("getRecipesStrict(cereal) failed",
                database.getRecipesStrict("cereal", 2, -1, -1).equals(Collections.singletonList(recipe2)));

        assertTrue("getRecipesStrict(cereal) failed",
                database.getRecipesStrict("cereal", -1, 2, -1).equals(Collections.singletonList(recipe2)));

        assertTrue("getRecipesStrict(cereal) failed",
                database.getRecipesStrict("cereal", -1, -1, 2).equals(Collections.singletonList(recipe2)));

        assertTrue("getRecipesStrict(toast) failed",
                database.getRecipesStrict("toast", 1, 1, 1).equals(Collections.singletonList(recipe1)));

        assertTrue("getRecipesStrict(toast) failed",
                database.getRecipesStrict("toast", -1, -1, -1).equals(Collections.singletonList(recipe1)));

        assertTrue("Found a recipe I shouldnt have", database.getRecipesStrict("meep", 1, 1, 1).size() == 0);
    }

    public void testGetRecipesLoose() {
        assertTrue("getRecipesLoose(cereal) failed",
                database.getRecipesLoose("cereal", 2, 2, 2).equals(Collections.singletonList(recipe2)));

        assertTrue("getRecipesLoose(toast) failed",
                database.getRecipesLoose("toast", 1, 1, 1).equals(Collections.singletonList(recipe1)));

        List<Recipe> xceralx = new LinkedList<Recipe>();
        xceralx.add(recipe2);
        xceralx.add(recipe3);

        assertTrue("getRecipesLoose(cereal) failed",
                database.getRecipesLoose("cereal", -1, -1, -1).equals(xceralx));
    }

    public void tearDown() throws Exception {
        database.close();
        super.tearDown();
    }
}
