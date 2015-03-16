package com.example.d4d.recipebox;

import java.util.List;

/**
 * Interface describing how database access will work.
 * Implementations will essentially serve as a Java api wrapper for the database
 * Created by Richard on 3/16/2015.
 */
public interface RecipeDatabase {

    /**
     * This function, given a Recipes ID, returns the Recipe uniquely identified with said ID
     * @param recipeId the UID/Primary key of the recipe in the database
     * @return the Recipe associated with the ID
     */
    public Recipe getRecipe(int recipeId);

    /**
     * Returns a list of Recipes that satisfy all the following criteria
     * For every Recipe in the returned list:
     * name is contained in Recipe.name. (Wildcard name would be the empty string "")
     * cuisine matches Recipe.cuisine (Wildcard cuisine is -1)
     * mealtype matches Recipe.mealtype (Wildcard mealtype is -1)
     * season matches Recipe.season (Wildcard season is -1)
     * @param name name we are searching for as substring
     * @param cuisine cuisine we want to match with
     * @param mealtype mealtype we want to match with
     * @param season season we want to match with
     * @return list of recipes that satisfy the criteria
     */
    public List<Recipe> getRecipesLoose(String name, int cuisine, int mealtype, int season);

    /**
     * Returns a list of Recipes that satisfy all the following criteria
     * For every Recipe in the returned list:
     * name matches Recipe.name (No wildcard)
     * cuisine matches Recipe.cuisine (Wildcard cuisine is -1)
     * mealtype matches Recipe.mealtype (Wildcard mealtype is -1)
     * season matches Recipe.season (Wildcard season is -1)
     * @param name name we are searching for as exact match
     * @param cuisine cuisine we want to match with
     * @param mealtype mealtype we want to match with
     * @param season season we want to match with
     * @return list of recipes that satisfy the criteria
     */
    public List<Recipe> getRecipesStrict(String name, int cuisine, int mealtype, int season);
}
