package com.example.d4d.recipebox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Richard on 3/26/2015.
 */
public class ServerRecipeDatabase extends RecipeDatabase{
    public static final String DEBUG_TAG = "ServerRecipeDatabase";
    private static final String USER_AGENT = "SPACEDUCK";
    private static final String SERVER_URL = "http://pc-recipebox.herokuapp.com/"; // TODO move this somewhere else

    private static final String tablename = "recipes";

    @Override
    public Recipe getRecipe(int recipeId) {
        String result = "";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(SERVER_URL + "recipes/" + recipeId + "/json");
            HttpResponse httpresponse = client.execute(request);
            if(httpresponse.getStatusLine().getStatusCode() == 200) {
                String line;
                BufferedReader rd = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                while ((line = rd.readLine()) != null) {
                    result += line + "\n"; // TODO why am i adding a new line?
                }
                rd.close();
                return jsonToRecipe(new JSONObject(result));
            } else {
                // Server didn't respond properly
                return null;
            }
        } catch (IOException ex) {
            Log.e(DEBUG_TAG, "Error connecting to server at " + SERVER_URL);
            return null;
        } catch (JSONException ex) {
            Log.e(DEBUG_TAG, "Error parsing input as JSON: " + result);
            return null;
        }
    }

    @Override
    public List<Recipe> getRecipesLoose(String name, int cuisine, int mealtype, int season) {
        return getRecipes(name, cuisine, mealtype, season, 0);
    }

    @Override
    public List<Recipe> getRecipesStrict(String name, int cuisine, int mealtype, int season) {
        return getRecipes(name, cuisine, mealtype, season, 1);
    }

    private List<Recipe> getRecipes(String name, int cuisine, int mealtype, int season, int strict) {
        // TODO wait on server guys to do stuff
        return null;
    }

    public Recipe jsonToRecipe(JSONObject recipejson) {
        try {
            Bitmap image = null;
            if(!recipejson.isNull("picture")) {
                byte[] imagearray = Base64.decode(recipejson.getString("picture"), Base64.DEFAULT);
                image = BitmapFactory.decodeByteArray(imagearray, 0, imagearray.length);
            }

            return new Recipe(recipejson.getString("name"),
                    recipejson.getString("description"),
                    recipejson.getInt("cuisine"),
                    recipejson.getInt("mealtype"),
                    recipejson.getInt("season"),
                    Arrays.asList(recipejson.getString("ingredientlist").split(INGREDIENT_LIST_DELIMITER)),
                    recipejson.getString("instructions"),
                    recipejson.getInt("id"),
                    image);
        } catch (JSONException ex) {
            Log.e(DEBUG_TAG, "Error parsing JSON to Recipe: " + recipejson.toString());
            return null;
        }
    }
}
