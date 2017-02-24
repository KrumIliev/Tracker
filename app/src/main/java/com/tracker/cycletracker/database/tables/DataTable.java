package com.tracker.cycletracker.database.tables;

import android.provider.BaseColumns;

/**
 * Created by Krum Iliev on 2/23/2017.
 */

public class DataTable implements BaseColumns {

    public static final String TABLE_NAME = "data_table";
    public static final String DATE = "date";
    public static final String TOTAL_TIME = "total_time";
    public static final String TRACK_ID = "track_id";

    public static final String[] PROJECTION = {_ID, DATE, TOTAL_TIME, TRACK_ID};

    public static String getCreateString () {
        return "CREATE TABLE " + TABLE_NAME + " (" +
               _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               DATE + "  INTEGER NOT NULL, " +
               TOTAL_TIME + "  INTEGER NOT NULL, " +
               TRACK_ID + "  INTEGER NOT NULL);";
    }
}
