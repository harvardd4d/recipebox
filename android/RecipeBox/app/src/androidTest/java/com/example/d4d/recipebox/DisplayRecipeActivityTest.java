package com.example.d4d.recipebox;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.ViewAsserts;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.Collections;

/**
 * Created by Mark on 3/19/2015.
 */
public class DisplayRecipeActivityTest extends
        ActivityUnitTestCase<DisplayRecipeActivity> {

    private DisplayRecipeActivity activity;


    public DisplayRecipeActivityTest() {
        super(DisplayRecipeActivity.class);
    }

    private static Recipe recipe1 = new Recipe("toast", "toast", 1, 1, 1,Collections.singletonList("toast"), "toast", 1, null);

    private View screen;

    // list of Views to be tested
    private ImageView recipepicture;
    private TextView recipename;
    private TextView recipeinstructions;
    private TextView recipedescription;
    private ListView recipeingredientlist;
    private TextView recipecuisine;
    private TextView recipemealtype;
    private TextView recipeseason;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // otherwise compatibility theming would break unit tests *facepalm*
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);

        // initialize a dummy intent with an extra Recipe passed in
        Intent testIntent = new Intent(getInstrumentation().getTargetContext(), DisplayRecipeActivity.class);
        testIntent.putExtra(DisplayRecipeActivity.RECIPE_INTENT_KEY, recipe1);

        // precondition testing
        startActivity(testIntent, null, null);
        activity = (DisplayRecipeActivity)getActivity();
        screen = activity.getWindow().getDecorView();

        // setting Views to be tested
        recipepicture = (ImageView) activity.findViewById(R.id.recipePicture);
        recipename = (TextView) activity.findViewById(R.id.recipeName);
        recipeinstructions = (TextView) activity.findViewById(R.id.recipeInstructions);
        recipedescription = (TextView) activity.findViewById(R.id.recipeDescription);
        recipeingredientlist = (ListView) activity.findViewById(R.id.recipeIngredientList);
        recipecuisine = (TextView) activity.findViewById(R.id.recipeCuisine);
        recipemealtype = (TextView) activity.findViewById(R.id.recipeMealType);
        recipeseason = (TextView) activity.findViewById(R.id.recipeSeason);

        assertNotNull("Activity not started", activity);
    }

    // test that the Views are shown on-screen
    public void testElementViewsShown() throws Exception {

        // check that the Views are on the screen
        ViewAsserts.assertOnScreen(screen, recipepicture);
        ViewAsserts.assertOnScreen(screen, recipename);
        ViewAsserts.assertOnScreen(screen, recipeinstructions);
        ViewAsserts.assertOnScreen(screen, recipedescription);
        ViewAsserts.assertOnScreen(screen, recipeingredientlist);
        ViewAsserts.assertOnScreen(screen, recipecuisine);
        ViewAsserts.assertOnScreen(screen, recipemealtype);
        ViewAsserts.assertOnScreen(screen, recipeseason);
    }

    // test that the Views populated with the recipe elements correctly
    public void testRecipeElementsShown() throws Exception {

        // validate the text in the TextViews
        // TODO: figure out how to test ImageView
//        assertTrue("Picture is not null", recipepicture.getDrawable() == null);

        assertEquals("Name incorrect", "toast", recipename.getText().toString());
        assertEquals("Instructions incorrect", "toast", recipeinstructions.getText().toString());
        assertEquals("Description incorrect", "toast", recipedescription.getText().toString());

        // TODO: figure out how to test ListView (how to do something with adapter?)
//        assertEquals("Ingredient List incorrect", "toast", recipeingredientlist.getAdapter().toString());

        assertEquals("Cuisine incorrect", "1", recipecuisine.getText().toString());
        assertEquals("Meal Type incorrect", "1", recipemealtype.getText().toString());
        assertEquals("Season incorrect", "1", recipeseason.getText().toString());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }
}
