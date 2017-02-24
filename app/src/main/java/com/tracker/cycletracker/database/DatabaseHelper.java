package com.tracker.cycletracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tracker.cycletracker.database.tables.DataTable;
import com.tracker.cycletracker.database.tables.TracksTable;

/**
 * Created by Krum Iliev on 2/23/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_BASE_NAME = "cycle.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public DatabaseHelper (Context context) {
        super(context, DATABASE_BASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public Context getContext () {
        return context;
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TracksTable.getCreateString());
        sqLiteDatabase.execSQL(DataTable.getCreateString());
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TracksTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataTable.TABLE_NAME);
    }
}
