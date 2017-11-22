package com.example.kevinyou.facts.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kevin You on 11/20/2017.
 */

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new TipsDatabaseHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    public void openReadableDatabase() {
        this.database = openHelper.getReadableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public long getTableRowCount(String selected) {
        Cursor cursor = database.rawQuery("SELECT data FROM facts WHERE type = ?", new String[] {selected});

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public ArrayList<String> getTips(String selected) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT data FROM facts WHERE type = ?", new String[] {selected});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }
}
