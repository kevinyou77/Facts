package com.example.kevinyou.facts.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Kevin You on 11/20/2017.
 */

public class TipsDatabaseHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "dailyFacts.db";
    private static final int DB_VERSION = 1;

    public TipsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
