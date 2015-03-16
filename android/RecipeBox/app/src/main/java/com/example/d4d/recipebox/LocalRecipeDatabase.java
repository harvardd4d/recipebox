package com.example.d4d.recipebox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Implementation of RecipeDatabase that queries the local SQLite database for information
 * Created by Richard on 3/16/2015.
 */
public class LocalRecipeDatabase implements RecipeDatabase {

    public static final String DEBUG_TAG = "LocalRecipeDatabase";

    private LocalRecipeDatabaseHelper dbhelper;
    private SQLiteDatabase database;

    public LocalRecipeDatabase(Context context) {
        dbhelper = new LocalRecipeDatabaseHelper(context);
        database = dbhelper.getReadableDatabase();
    }

    public Recipe getRecipe(int recipeId) {
        // TODO
        return null;
    }

    public List<Recipe> getRecipesLoose(String name, int cuisine, int mealtype, int season) {
        // TODO
        return null;
    }

    public List<Recipe> getRecipesStrict(String name, int cuisine, int mealtype, int season) {
        // TODO
        return null;
    }

}
