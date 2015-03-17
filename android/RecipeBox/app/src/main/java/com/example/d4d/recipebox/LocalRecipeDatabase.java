package com.example.d4d.recipebox;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of RecipeDatabase that queries the local SQLite database for information
 * Created by Richard on 3/16/2015.
 */
public class LocalRecipeDatabase extends RecipeDatabase {

    public static final String DEBUG_TAG = "LocalRecipeDatabase";

    private static final String tablename = "recipes";

    private LocalRecipeDatabaseHelper dbhelper;
    private SQLiteDatabase database;

    public LocalRecipeDatabase(Context context) {
        dbhelper = new LocalRecipeDatabaseHelper(context);
        database = dbhelper.getReadableDatabase();
    }

    @Override
    public Recipe getRecipe(int recipeId) {
        Cursor c = database.query(tablename, null, "id="+recipeId, null, null, null, null, "1");
        if(c.moveToFirst()) {
            // we have the recipe, time to make it
            return makeRecipe(c);
        } else {
            // no recipe found
            return null;
        }
    }

    @Override
    public List<Recipe> getRecipesStrict(String name, int cuisine, int mealtype, int season) {

        List<Recipe> recipes = new ArrayList<Recipe>();

        // build the where filter
        String whereclause = "name LIKE '" + name + "'";
        if(cuisine != -1) {
            whereclause += " AND cuisine=" + cuisine;
        }

        if(mealtype != -1) {
            whereclause += " AND mealtype=" + mealtype;
        }

        if(season != -1) {
            whereclause += " AND season=" + season;
        }

        Cursor c = database.query(tablename, null, whereclause, null, null, null, null);

        if(c.moveToFirst()) {
            while(!c.isAfterLast()) {
                recipes.add(makeRecipe(c));
                c.moveToNext();
            }
        }

        return recipes;
    }

    @Override
    public List<Recipe> getRecipesLoose(String name, int cuisine, int mealtype, int season) {
        return getRecipesStrict("%"+name+"%", cuisine, mealtype, season);
    }

    /**
     * Given a valid cursor c, returns the recipe that is associated with the row pointed to by
     * the cursor
     * @param c the database cursor, assumed to be pointing at valid row
     * @return
     */
    private Recipe makeRecipe(Cursor c) {
        return new Recipe(c.getString(c.getColumnIndex("name")),
                c.getString(c.getColumnIndex("description")),
                c.getInt(c.getColumnIndex("cuisine")),
                c.getInt(c.getColumnIndex("mealtype")),
                c.getInt(c.getColumnIndex("season")),
                Arrays.asList(c.getString(c.getColumnIndex("ingredientlist")).split(LIST_DELIMITER)),
                c.getString(c.getColumnIndex("instructions")),
                c.getInt(c.getColumnIndex("id")));
    }

}
