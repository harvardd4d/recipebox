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

    // SQL

    public LocalRecipeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // TODO: make this create test database
    }

    @Override
    public void onOpen(SQLiteDatabase database) {
        // TODO: do whatever needs to be done when the database is opened
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldversion, int newversion) {
        Log.i(DEBUG_TAG, "Upgrading from version" + oldversion + " to " + newversion + ". This will wipe all old data.");
        // TODO do the upgrade, whatever that entails
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
