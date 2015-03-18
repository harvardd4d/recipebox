package com.example.d4d.recipebox;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class DisplayRecipeActivity extends ActionBarActivity {

    public static final String RECIPE_INTENT_KEY = "recipe";
    public static final String DEBUG_TAG = "DisplayRecipeActivity";

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        recipe = getIntent().getExtras().getParcelable(RECIPE_INTENT_KEY);

        if(recipe == null) {
            // we didn't get an intent, wtf
            Log.e(DEBUG_TAG, "Not passed a Recipe, aborting");
            return;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
