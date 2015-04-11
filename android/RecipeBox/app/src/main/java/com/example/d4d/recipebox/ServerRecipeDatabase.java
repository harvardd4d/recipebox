package com.example.d4d.recipebox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 3/26/2015.
 */
public class ServerRecipeDatabase extends RecipeDatabase{
    public static final String DEBUG_TAG = "ServerRecipeDatabase";
    private static final String USER_AGENT = "SPACEDUCK";
    private static final String SERVER_URL = "http://pc-recipebox.herokuapp.com/"; // TODO move this somewhere else
    private static final String ENCODING = "UTF-8";

    private static final String tablename = "recipes";

    private HttpClient client;

    public ServerRecipeDatabase() {
        client = new DefaultHttpClient();
    }

    @Override
    public Recipe getRecipe(int recipeId) {
        String result = "";
        try {
            HttpGet request = new HttpGet(SERVER_URL + "recipes/" + recipeId + "/json/");
            HttpResponse httpresponse = client.execute(request);
            if(httpresponse.getStatusLine().getStatusCode() == 200) {
                String line;
                BufferedReader rd = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                try {
                    while ((line = rd.readLine()) != null) {
                        result += line + "\n"; // TODO why am i adding a new line?
                    }
                } finally {
                    rd.close();
                }
                return jsonToRecipe(new JSONObject(result));
            } else {
                // Server didn't respond properly
                Log.e(DEBUG_TAG, "Server at " + SERVER_URL + " didn't respond with 200 OK");
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
    public ArrayList<Recipe> getRecipesLoose(String name, int cuisine, int mealtype, int season) {
        return getRecipes(name, cuisine, mealtype, season, 0);
    }

    @Override
    public ArrayList<Recipe> getRecipesStrict(String name, int cuisine, int mealtype, int season) {
        return getRecipes(name, cuisine, mealtype, season, 1);
    }

    private ArrayList<Recipe> getRecipes(String name, int cuisine, int mealtype, int season, int strict) {
        try {
            HttpPost request = new HttpPost(SERVER_URL + "recipes/jsonsearch/");
            List<NameValuePair> params = new ArrayList<NameValuePair>(5);
            params.add(new BasicNameValuePair("strict", ""+strict));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("season", ""+season));
            params.add(new BasicNameValuePair("mealtype", ""+mealtype));
            params.add(new BasicNameValuePair("cuisine", ""+cuisine));
            request.setEntity(new UrlEncodedFormEntity(params, ENCODING));

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            ArrayList<Recipe> res = new ArrayList<Recipe>();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(entity.getContent()), 65728);
            String line = null;

            try {
                while ((line = reader.readLine()) != null) {
                    res.add(jsonToRecipe(new JSONObject(line)));
                }
            } finally {
                reader.close();
            }

            return res;
        } catch (UnsupportedEncodingException ex) {
            // error, return empty list
            Log.e(DEBUG_TAG, "Unsupported encoding " + ENCODING);
            return null;
        } catch (IOException ex) {
            Log.e(DEBUG_TAG, "Network error", ex);
            return null;
        } catch(JSONException ex) {
            Log.e(DEBUG_TAG, "Server sent invalid JSON", ex);
            return null;
        }
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

    public void close() {
        // TODO i guess we don't close the httpclient?
    }
}
