package com.example.d4d.recipebox;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class file that contains recipe information. Must be parcelable or serializable so that it can
 * be sent from one activity to the next quickly.
 * Created by Richard on 3/16/2015.
 */
public class Recipe implements Parcelable {

    String name;
    String description;
    int cuisine;
    int mealtype;
    int season;
    List<String> ingredientlist;
    String instructions;
    int id;


    /**
     * No args constructor, this will create a default "blank" recipe
     */
    public Recipe() {
        name = "";
        description = "";
        cuisine = -1;
        mealtype = -1;
        season = -1;
        ingredientlist = new ArrayList<String>();
        instructions = "";
        id = -1;
    }

    public Recipe(String name, String description, int cuisine, int mealtype,
                  int season, List<String> ingredientlist, String instructions, int id) {
        this.name = name;
        this.description = description;
        this.cuisine = cuisine;
        this.mealtype = mealtype;
        this.season = season;
        this.ingredientlist = ingredientlist;
        this.instructions = instructions;
        this.id = id;

    }

    /**
     * Constructor defined to implement Parcelable
     * @param p parcel used to create the Recipe
     */
    public Recipe(Parcel p) {
        this.name = p.readString();
        this.description = p.readString();
        this.cuisine = p.readInt();
        this.mealtype = p.readInt();
        this.season = p.readInt();
        p.readStringList(this.ingredientlist);
        this.instructions = p.readString();
        this.id = p.readInt();
    }

    /**
     * Returns the name of the Recipe
     * @return name of the Recipe
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the new name of the recipe
     * @param name name the new name of the recipe
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     * Returns the description of the Recipe
     * @return description of the Recipe
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the new description of the Recipe
     * @param description new description of a recipe
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the int ID of the cuisine, an ID of a cuisine is converted to a string format
     * by lookup in a database
     * @return cuisine ID
     */
    public int getCuisine() {
        return cuisine;
    }

    /**
     * Sets the cuisine ID
     * @param cuisine new cuisine ID
     */
    public void setCuisine(int cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * Returns the int ID of a meal type, which corresponds to dinner, lunch, etc. A database is
     * used to convert it to a human readable string format
     * @return mealtype ID
     */
    public int getMealType() {
        return mealtype;
    }

    /**
     * Sets the mealtype ID
     * @param mealtype new mealtype ID
     */
    public void setMealType(int mealtype) {
        this.mealtype = mealtype;
    }

    /**
     * Returns an int for what season it is. An int is used to prevent inconsistencies when
     * selecting Fall/Autumn etc. as well as being lighter, thought that is a much smaller concern.
     * The conversion of season number to a string is stored in (TODO)
     * @return Recipe's season number
     */
    public int getSeason() {
        return season;
    }

    /**
     * Sets the new season number
     * @param season new season number
     */
    public void setSeason(int season) {
        this.season = season;
    }

    /**
     * Returns the list of ingredients required for the current Recipe
     * @return the list of ingredients required for the Recipe
     */
    public List<String> getIngredientList() {
        return ingredientlist;
    }

    /**
     * Sets the Recipe's ingredient list
     * @param ingredientlist new list of ingredients for Recipe
     */
    public void setIngredientList(List<String> ingredientlist) {
        this.ingredientlist = ingredientlist;
    }

    /**
     * Returns the instructions for preparing the Recipe
     * @return instructions for preparing thee Recipe
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Sets the instructions for preparing the Recipe
     * @param instructions new instructions for preparing the Recipe
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Returns the ID of the Recipe, this uniquely identifies a Recipe in the database
     * @return the UID of this Recipe
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the UID of the Recipe, this uniquely identifies a Recipe in the database. This function
     * should not be called very often, if at all.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Recipe)) {
            return false;
        }
        Recipe r = (Recipe)o;
        return name.equals(r.getName()) &&
                description.equals(r.getDescription()) &&
                cuisine == r.getCuisine() &&
                mealtype == r.getMealType() &&
                season == r.getSeason() &&
                ingredientlist.equals(r.getIngredientList()) &&
                instructions.equals(r.getInstructions()) &&
                id == r.getId();

    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nDescription: " + description +
                "\nCuisine: " + cuisine +
                "\nMealtype: " + mealtype +
                "\nSeason: " + season +
                "\nIngredientList: " + ingredientlist.toString() +
                "\nInstructions: " + instructions +
                "\nID: " + id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeString(name);
        p.writeString(description);
        p.writeInt(cuisine);
        p.writeInt(mealtype);
        p.writeInt(season);
        p.writeStringList(ingredientlist);
        p.writeString(instructions);
        p.writeInt(id);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

}
