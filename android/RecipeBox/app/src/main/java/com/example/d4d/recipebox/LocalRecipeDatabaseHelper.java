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
    private static final String CREATE_TABLES =
            "CREATE TABLE recipes(name TEXT NOT NULL,description TEXT NOT NULL,cuisine INTEGER NOT NULL,mealtype INTEGER NOT NULL,season INTEGER NOT NULL,ingredientlist TEXT NOT NULL,instructions TEXT NOT NULL,id INTEGER PRIMARY KEY,picture BLOB);";

    private static final String make_test_info =
            "INSERT INTO recipes VALUES('toast', 'toast', 1, 1, 1, 'toast', 'toast', 1, NULL);" +
            "INSERT INTO recipes VALUES('cereal', 'cereal', 2, 2, 2, 'cereal', 'cereal', 2, NULL);" +
            "INSERT INTO recipes VALUES('toasted cereal', 'toasted cereal', 3, 3, 3, 'toasted cereal', 'toasted cereal', 3, NULL);" +
            "INSERT INTO recipes VALUES('something', 'something', 4, 4, 4, 'something', 'something', 4, NULL);";

    private static final String DELETE_TABLES =
            "DROP TABLE IF EXISTS recipes;";

    public LocalRecipeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create tables as necessary
        database.execSQL(CREATE_TABLES);

        // if debug mode, populate with test data
        if(LocalRecipeDatabase.DEBUGMODE) {
            executeBatchSQL(database, make_test_info);
        }
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
