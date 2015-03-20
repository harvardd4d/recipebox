package com.example.d4d.recipebox;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.ViewAsserts;
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

    private static Recipe recipe1 = new Recipe("toast", "toast", 1, 1, 1, Collections.singletonList("toast"), "toast", 1, null);
//    private static Recipe recipe2 = new Recipe("cereal", "cereal", 2, 2, 2, Collections.singletonList("cereal"), "cereal", 2, null);
//    private static Recipe recipe3 = new Recipe("toasted cereal", "toasted cereal", 3, 3, 3, Collections.singletonList("toasted cereal"), "toasted cereal", 3, null);

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // initialize a dummy intent with an extra Recipe passed in
        Intent testIntent = new Intent(this.getActivity(), DisplayRecipeActivity.class);
        testIntent.putExtra(DisplayRecipeActivity.RECIPE_INTENT_KEY, recipe1);

        activity = getActivity();
    }

    public void testStartSecondActivity() throws Exception {

        // monitor to check for activity
        Instrumentation.ActivityMonitor monitor =
                getInstrumentation().addMonitor(DisplayRecipeActivity.class.getName(), null, false);

        // wait 2 seconds for the start of the activity
        DisplayRecipeActivity startedActivity = (DisplayRecipeActivity) monitor
                .waitForActivityWithTimeout(2000);
        assertNotNull(startedActivity);

        // search for modifiable Views
        ImageView recipepicture = (ImageView) startedActivity.findViewById(R.id.recipePicture);
        TextView recipename = (TextView) startedActivity.findViewById(R.id.recipeName);
        TextView recipeinstructions = (TextView) startedActivity.findViewById(R.id.recipeInstructions);
        TextView recipedescription = (TextView) startedActivity.findViewById(R.id.recipeDescription);
        ListView recipeingredientlist = (ListView) startedActivity.findViewById(R.id.recipeIngredientList);
        TextView recipecuisine = (TextView) startedActivity.findViewById(R.id.recipeCuisine);
        TextView recipemealtype = (TextView) startedActivity.findViewById(R.id.recipeMealType);
        TextView recipeseason = (TextView) startedActivity.findViewById(R.id.recipeSeason);


        // check that the Views are on the screen
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipepicture);
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipename);
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipeinstructions);
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipedescription);
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipeingredientlist);
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipecuisine);
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipemealtype);
        ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
                recipeseason);

        // validate the text in the TextViews
        assertEquals("Text incorrect", "toast", recipename.getText().toString());
    }
}
