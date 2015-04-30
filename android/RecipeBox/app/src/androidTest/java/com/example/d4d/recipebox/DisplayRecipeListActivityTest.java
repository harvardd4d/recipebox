package com.example.d4d.recipebox;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Richard on 3/18/2015.
 */
public class DisplayRecipeListActivityTest extends ActivityUnitTestCase<DisplayRecipeListActivity> {

    private DisplayRecipeListActivity activity;
    private Intent launchIntent;
    private ListView listView;

    private Recipe r1 = new Recipe("toast", "toast", 1, 1, 1, Collections.singletonList("toast"), "toast", 1, null);

    public DisplayRecipeListActivityTest() {
        super(DisplayRecipeListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // adjust theme because fuck all
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);
        
        launchIntent = new Intent(getInstrumentation().getTargetContext(), DisplayRecipeListActivity.class);
        ArrayList<Recipe> arr = new ArrayList<Recipe>();
        arr.add(r1);
        arr.add(new Recipe("cereal", "cereal", 2, 2, 2, Collections.singletonList("cereal"), "cereal", 2, null));
        launchIntent.putParcelableArrayListExtra(DisplayRecipeListActivity.RECIPELIST_INTENT_KEY, arr);

        // test preconditions
        startActivity(launchIntent, null, null);
        activity = getActivity();
        assertNotNull("activity is null", activity);
        listView = (ListView) getActivity().findViewById(R.id.listView);
        assertNotNull("ListView is null", listView);
    }

    public void testRecipesPresent() {
        assertTrue("listview loaded 2 elements", listView.getCount() == 2);

        View rowview = listView.getAdapter().getView(1, null, null);

        assertTrue("Recipe 2 unexpected name", "cereal".equals(((TextView) rowview.findViewById(R.id.list_recipeName)).getText().toString()));
        assertTrue("Recipe 2 unexpected description", "cereal".equals(((TextView) rowview.findViewById(R.id.list_recipeDescription)).getText().toString()));

        // click on first entry, ensure we are getting what we expect
        listView.performItemClick(listView.getAdapter().getView(0, null, null), 0, listView.getAdapter().getItemId(0));
        Intent sentIntent = getStartedActivityIntent();

       assertTrue("Didn't send intent that was expected", sentIntent.getParcelableExtra(DisplayRecipeActivity.RECIPE_INTENT_KEY).equals(r1));

    }
}
