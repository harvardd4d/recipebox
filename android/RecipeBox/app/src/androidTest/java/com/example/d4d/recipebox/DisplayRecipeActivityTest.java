package com.example.d4d.recipebox;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.ViewAsserts;
import android.view.ContextThemeWrapper;
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

        // otherwise compatibility theming would break unit tests *facepalm*
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);

        // initialize a dummy intent with an extra Recipe passed in
        Intent testIntent = new Intent(getInstrumentation().getTargetContext(), DisplayRecipeActivity.class);
        testIntent.putExtra(DisplayRecipeActivity.RECIPE_INTENT_KEY, recipe1);

        // precondition testing
        startActivity(testIntent, null, null);
        activity = (DisplayRecipeActivity)getActivity();
        assertNotNull("Activity not started", activity);
    }

    public void testElementViewsShown() throws Exception {

        // list of Views to be tested
        ImageView recipepicture = (ImageView) activity.findViewById(R.id.recipePicture);
        TextView recipename = (TextView) activity.findViewById(R.id.recipeName);
        TextView recipeinstructions = (TextView) activity.findViewById(R.id.recipeInstructions);
        TextView recipedescription = (TextView) activity.findViewById(R.id.recipeDescription);
        ListView recipeingredientlist = (ListView) activity.findViewById(R.id.recipeIngredientList);
        TextView recipecuisine = (TextView) activity.findViewById(R.id.recipeCuisine);
        TextView recipemealtype = (TextView) activity.findViewById(R.id.recipeMealType);
        TextView recipeseason = (TextView) activity.findViewById(R.id.recipeSeason);

        // check that the Views are on the screen
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipepicture);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipename);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipeinstructions);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipedescription);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipeingredientlist);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipecuisine);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipemealtype);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), recipeseason);

        // validate the text in the TextViews
        assertEquals("Text incorrect", "toast", recipename.getText().toString());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }
}
