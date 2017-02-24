package com.tracker.cycletracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tracker.cycletracker.database.models.Data;
import com.tracker.cycletracker.database.models.Track;
import com.tracker.cycletracker.database.tables.DataTable;
import com.tracker.cycletracker.database.tables.TracksTable;

import java.util.ArrayList;

/**
 * Created by Krum Iliev on 2/23/2017.
 */
public class DatabaseManager {

    private final String LOG_TAG = DatabaseManager.class.getSimpleName();

    private DatabaseHelper dbHelper;

    public DatabaseManager (Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // ---------------------------------------------------------------------------------------------
    // TRACKS
    // ---------------------------------------------------------------------------------------------

    public void addTrack (Track track) {
        ContentValues values = new ContentValues();
        values.put(TracksTable.NAME, track.name);
        values.put(TracksTable.DISTANCE, track.distance);
        values.put(TracksTable.TYPE, track.type);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            db.insert(TracksTable.TABLE_NAME, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(LOG_TAG, "addTrack | " + e.toString());

        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void deleteTrack (Track track) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            String selection = TracksTable._ID + "=?";
            String[] selectionArgs = {String.valueOf(track.id)};
            db.delete(TracksTable.TABLE_NAME, selection, selectionArgs);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(LOG_TAG, "deleteTrack | " + e.toString());

        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void updateTrack (Track track) {
        ContentValues values = new ContentValues();
        values.put(TracksTable.NAME, track.name);
        values.put(TracksTable.DISTANCE, track.distance);
        values.put(TracksTable.TYPE, track.type);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            String selection = TracksTable._ID + "=?";
            String[] selectionArgs = {String.valueOf(track.id)};

            db.update(TracksTable.TABLE_NAME, values, selection, selectionArgs);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(LOG_TAG, "updateTrack | " + e.toString());

        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public Track getTrack (long id) {
        Track track = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {

            String selection = TracksTable._ID + "=?";
            String[] selectionArgs = {String.valueOf(id)};

            cursor = db
                    .query(TracksTable.TABLE_NAME, TracksTable.PROJECTION, selection, selectionArgs,
                            null, null, null);

            if (cursor.moveToNext()) {
                track = new Track(id, cursor.getString(cursor.getColumnIndex(TracksTable.NAME)),
                        cursor.getInt(cursor.getColumnIndex(TracksTable.DISTANCE)),
                        cursor.getInt(cursor.getColumnIndex(TracksTable.TYPE)));
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "getTrack | " + e.toString());

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return track;
    }

    public ArrayList<Track> getAllTracks () {
        ArrayList<Track> tracks = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {

            cursor = db
                    .query(TracksTable.TABLE_NAME, TracksTable.PROJECTION, null, null, null, null,
                            null);

            while (cursor.moveToNext()) {
                tracks.add(new Track(cursor.getLong(cursor.getColumnIndex(TracksTable._ID)),
                        cursor.getString(cursor.getColumnIndex(TracksTable.NAME)),
                        cursor.getInt(cursor.getColumnIndex(TracksTable.DISTANCE)),
                        cursor.getInt(cursor.getColumnIndex(TracksTable.TYPE))));
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "getAllTracks | " + e.toString());

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return tracks;
    }

    // ---------------------------------------------------------------------------------------------
    // DATA
    // ---------------------------------------------------------------------------------------------

    public void addData (Data data) {
        ContentValues values = new ContentValues();
        values.put(DataTable.DATE, data.date);
        values.put(DataTable.TOTAL_TIME, data.totalTime);
        values.put(DataTable.TRACK_ID, data.track.id);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            db.insert(DataTable.TABLE_NAME, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(LOG_TAG, "addData | " + e.toString());

        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public ArrayList<Data> getAllData () {
        ArrayList<Data> data = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {

            cursor = db.query(DataTable.TABLE_NAME, DataTable.PROJECTION, null, null, null, null,
                    null);

            while (cursor.moveToNext()) {
                data.add(new Data(cursor.getLong(cursor.getColumnIndex(DataTable._ID)),
                        cursor.getLong(cursor.getColumnIndex(DataTable.DATE)),
                        cursor.getLong(cursor.getColumnIndex(DataTable.TOTAL_TIME)),
                        getTrack(cursor.getLong(cursor.getColumnIndex(DataTable.TRACK_ID)))));
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "getAllTracks | " + e.toString());

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return data;
    }
}
