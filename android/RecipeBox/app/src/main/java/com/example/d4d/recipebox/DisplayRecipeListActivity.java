package com.example.d4d.recipebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DisplayRecipeListActivity extends ActionBarActivity {

    public static final String DEBUG_TAG = "DisplayRecipeList";
    public static final String RECIPELIST_INTENT_KEY = "recipelist";
    private ListView listView;
    private ProgressDialog progress;
    private RecipeListAdapter adapter;
    private List<Recipe> listvalues;

    public class RecipeListAdapter extends ArrayAdapter<Recipe> {
        private final Context context;
        private final List<Recipe> recipelist;

        public RecipeListAdapter(Context context, List<Recipe> recipelist) {
            super(context, R.layout.row_recipe_list, recipelist);

            this.context = context;
            this.recipelist = recipelist;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {
            // Create inflater
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Get rowview from inflater
            View rowview = inflater.inflate(R.layout.row_recipe_list, parent, false);

            // get fields in rowview
            TextView recipename = (TextView)rowview.findViewById(R.id.list_recipeName);
            TextView recipedescription = (TextView)rowview.findViewById(R.id.list_recipeDescription);
            ImageView recipepicture = (ImageView)rowview.findViewById(R.id.list_recipePicture);

            // set the fields in the rowview
            Recipe recipe = recipelist.get(position);
            recipename.setText(recipe.getName());
            recipedescription.setText(recipe.getDescription());
            if(recipe.getPicture() != null) {
                recipepicture.setImageBitmap(recipe.getPicture());
            }

            return rowview;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe_list);

        // retrieve list of Recipes from intent
        listvalues = getIntent().getExtras().getParcelableArrayList(RECIPELIST_INTENT_KEY);
        if(listvalues == null) {
            // we didn't get an intent, wtf
            Log.e(DEBUG_TAG, "Not passed a List<Recipe>, aborting");
            return;
        }


        // get handle on ListView object
        listView = (ListView)findViewById(R.id.listView);

        // create and set the adapter
        adapter = new RecipeListAdapter(this, listvalues);
        listView.setAdapter(adapter);

        // Assign an onClick listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // start DisplayRecipeActivity
                Intent intent = new Intent(DisplayRecipeListActivity.this, DisplayRecipeActivity.class);
                intent.putExtra(DisplayRecipeActivity.RECIPE_INTENT_KEY, listvalues.get(position));
                startActivity(intent);
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_recipe_list, menu);
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
