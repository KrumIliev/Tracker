package com.tracker.cycletracker.database.tables;

import android.provider.BaseColumns;

/**
 * Created by Krum Iliev on 2/23/2017.
 */

public class TracksTable implements BaseColumns {

    public static final String TABLE_NAME = "tracks_table";
    public static final String NAME = "name";
    public static final String DISTANCE = "distance";
    public static final String TYPE = "type";

    public static final String[] PROJECTION = {_ID, NAME, DISTANCE, TYPE};

    public static String getCreateString () {
        return "CREATE TABLE " + TABLE_NAME + " (" +
               _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               NAME + " TEXT NOT NULL, " +
               TYPE + "  INTEGER DEFAULT 0, " +
               DISTANCE + " INTEGER);";
    }
}
