package com.example.d4d.recipebox;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public static final boolean DEBUGMODE = true;

    private SQLiteDatabase database;

    public LocalRecipeDatabase(Context context) {
        database = new LocalRecipeDatabaseHelper(context).getReadableDatabase();
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
    public ArrayList<Recipe> getRecipesStrict(String name, int cuisine, int mealtype, int season) {

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

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
    public ArrayList<Recipe> getRecipesLoose(String name, int cuisine, int mealtype, int season) {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        // build the where filter
        String whereclause = "name LIKE '%" + name + "%'";
        if(cuisine != -1) {
            whereclause += " AND cuisine=" + cuisine;
        }

        if(mealtype != -1) {
            whereclause += " AND mealtype&" + mealtype + "=" + mealtype;
        }

        if(season != -1) {
            whereclause += " AND season&" + season + "=" + season;
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

    public void close() {
        database.close();
    }

    /**
     * Given a valid cursor c, returns the recipe that is associated with the row pointed to by
     * the cursor
     * @param c the database cursor, assumed to be pointing at valid row
     * @return
     */
    private Recipe makeRecipe(Cursor c) {
        Bitmap picture = null;
        int pictcol = c.getColumnIndex("picture");
        if(!c.isNull(pictcol)) {
            byte[] buffer = c.getBlob(pictcol);
            picture = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        }
        return new Recipe(c.getString(c.getColumnIndex("name")),
                c.getString(c.getColumnIndex("description")),
                c.getInt(c.getColumnIndex("cuisine")),
                c.getInt(c.getColumnIndex("mealtype")),
                c.getInt(c.getColumnIndex("season")),
                Arrays.asList(c.getString(c.getColumnIndex("ingredientlist")).split(INGREDIENT_LIST_DELIMITER)),
                c.getString(c.getColumnIndex("instructions")),
                c.getInt(c.getColumnIndex("id")),
                picture);
    }

}
