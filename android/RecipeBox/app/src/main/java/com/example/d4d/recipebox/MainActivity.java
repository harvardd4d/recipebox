package com.example.d4d.recipebox;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onTestButtonClick(View view) {
        // do your testy thingy
        Intent launchIntent = new Intent(MainActivity.this, DisplayRecipeListActivity.class);
        ArrayList<Recipe> arr = new LocalRecipeDatabase(this).getRecipesLoose("", -1, -1, -1);
        launchIntent.putParcelableArrayListExtra(DisplayRecipeListActivity.RECIPELIST_INTENT_KEY, arr);
        startActivity(launchIntent);
    }

    public void TOOOASTTT(View view) {
        // do your testy thingy
        Intent launchIntent = new Intent(MainActivity.this, DisplayRecipeListActivity.class);
        ArrayList<Recipe> arr = new LocalRecipeDatabase(this).getRecipesLoose("toast", -1, -1, -1);
        launchIntent.putParcelableArrayListExtra(DisplayRecipeListActivity.RECIPELIST_INTENT_KEY, arr);
        startActivity(launchIntent);
    }

    public void TOOOASTTTT(View view) {
        // do your testy thingy
        Intent launchIntent = new Intent(MainActivity.this, DisplayRecipeListActivity.class);
        ArrayList<Recipe> arr = new LocalRecipeDatabase(this).getRecipesStrict("toast", -1, -1, -1);
        launchIntent.putParcelableArrayListExtra(DisplayRecipeListActivity.RECIPELIST_INTENT_KEY, arr);
        startActivity(launchIntent);
    }

}
