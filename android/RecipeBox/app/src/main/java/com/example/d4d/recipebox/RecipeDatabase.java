package com.example.d4d.recipebox;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface describing how database access will work.
 * Implementations will essentially serve as a Java api wrapper for the database
 * Created by Richard on 3/16/2015.
 */
public abstract class RecipeDatabase {

    public static final String INGREDIENT_LIST_DELIMITER = "KAPPAKAPPAHUEHUEHUEE";

    /**
     * This function, given a Recipes ID, returns the Recipe uniquely identified with said ID
     * @param recipeId the UID/Primary key of the recipe in the database
     * @return the Recipe associated with the ID, null if there is no such recipe
     */
    public abstract Recipe getRecipe(int recipeId);

    /**
     * Returns a list of Recipes that satisfy all the following criteria
     * For every Recipe in the returned list:
     * name is contained in Recipe.name. (Wildcard name would be the empty string "")
     * cuisine matches Recipe.cuisine (Wildcard cuisine is -1)
     * mealtype matches Recipe.mealtype (Wildcard mealtype is -1)
     * season matches Recipe.season (Wildcard season is -1)
     * @param name name we are searching for as substring
     * @param cuisine cuisine we want to match with
     * @param mealtype mealtype we want to match with. This is encoded as multiple choices
     *                 packed into one integer. Recipes mealtypes will be a superset of the provided mealtype
     * @param season season we want to match with. This is encoded as multiple choices
     *               packed into one integer. Recipes season will be a superset of the provided season
     * @return list of recipes that satisfy the criteria, empty list if no recipes satisfy. null if something went wrong
     */
    public abstract ArrayList<Recipe> getRecipesLoose(String name, int cuisine, int mealtype, int season);

    /**
     * Returns a list of Recipes that satisfy all the following criteria
     * For every Recipe in the returned list:
     * name matches Recipe.name (No wildcard)
     * cuisine matches Recipe.cuisine (Wildcard cuisine is -1)
     * mealtype matches Recipe.mealtype (Wildcard mealtype is -1)
     * season matches Recipe.season (Wildcard season is -1)
     * @param name name we are searching for as exact match
     * @param cuisine cuisine we want to match with
     * @param mealtype mealtype we want to match with. This is encoded as multiple choices
     *                 packed into one integer. In this case the mealtype must match exactly
     * @param season season we want to match with. This is encoded as multiple choices
     *               packed into one integer. In this case the season must match exactly
     * @return list of recipes that satisfy the criteria, empty list if no recipes satisfy
     */
    public abstract ArrayList<Recipe> getRecipesStrict(String name, int cuisine, int mealtype, int season);
}
