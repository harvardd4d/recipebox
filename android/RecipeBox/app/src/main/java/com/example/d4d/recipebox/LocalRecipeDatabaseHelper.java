package com.example.d4d.recipebox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Richard on 3/16/2015.
 */
public class LocalRecipeDatabaseHelper extends SQLiteOpenHelper{

    public static final String DEBUG_TAG = "DatabaseHelper";

    private static String DB_NAME = "recipedatabase";
    private static int DB_VERSION = 1;

    // SQL commands
    private static String CREATE_TABLES =
            "CREATE TABLE recipes(name TEXT,description TEXT,cuisine INTEGER,mealtype INTEGER,season INTEGER,ingredientlist TEXT,instructions TEXT,id INTEGER PRIMARY KEY);";

    private static String DELETE_TABLES =
            "DROP TABLE IF EXISTS recipes;";

    public LocalRecipeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLES);
        // TODO insert test data
    }

    @Override
    public void onOpen(SQLiteDatabase database) {
        // TODO: do whatever needs to be done when the database is opened
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldversion, int newversion) {
        Log.i(DEBUG_TAG, "Upgrading from version" + oldversion + " to " + newversion + ". This will wipe all old data.");
        database.execSQL(DELETE_TABLES);
        database.execSQL(CREATE_TABLES);
        // TODO make sure I don't need to do anything else; just look at this again plz
    }

    /**
     * Executes SQL statements in bulk
     * @param database the database we want to run statements on
     * @param sql semicolon separated SQL commands
     */
    private void executeBatchSQL(SQLiteDatabase database, String sql) {
        String[] queries = sql.split(";");

        for(String query : queries) {
            database.execSQL(query);
        }
    }
}
