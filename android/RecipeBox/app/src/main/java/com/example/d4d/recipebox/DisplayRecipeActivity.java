package com.example.d4d.recipebox;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


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

        // enumerates all modifiable views
        ImageView recipepicture = (ImageView) findViewById(R.id.recipePicture);
        TextView recipename = (TextView) findViewById(R.id.recipeName);
        TextView recipeinstructions = (TextView) findViewById(R.id.recipeInstructions);
        TextView recipedescription = (TextView) findViewById(R.id.recipeDescription);

        // TODO: check if this needs to a TableView with TextView inside -- how many columns/entries?
        TextView recipeingredientlist = (TextView) findViewById(R.id.recipeIngredientList);

        TextView recipecuisine = (TextView) findViewById(R.id.recipeCuisine);
        TextView recipemealtype = (TextView) findViewById(R.id.recipeMealType);
        TextView recipeseason = (TextView) findViewById(R.id.recipeSeason);

        // sets the values of modifiable views to reflect those of the provided recipe
        recipepicture.setImageBitmap(recipe.getPicture());
        recipename.setText(recipe.getCuisine());
        recipeinstructions.setText(recipe.getInstructions());
        recipedescription.setText(recipe.getDescription());

        // TODO: fix the List<Strings> to bulleted list ja.
        recipeingredientlist.setText("INGREDIENT LIST GOES HERE");

        // TODO: convert these int values to String values!
        // TODO: using String.valueOf(int values) ATM
        recipecuisine.setText(String.valueOf(recipe.getCuisine()));
        recipemealtype.setText(String.valueOf(recipe.getMealType()));
        recipeseason.setText(String.valueOf(recipe.getSeason()));



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
