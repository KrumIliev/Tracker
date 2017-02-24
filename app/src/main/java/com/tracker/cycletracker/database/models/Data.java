package com.tracker.cycletracker.database.models;

/**
 * Created by Krum Iliev on 2/23/2017.
 */

public class Data {

    public long id;
    public long date;
    public long totalTime;
    public Track track;

    public Data (long id, long date, long totalTime, Track track) {
        this.id = id;
        this.date = date;
        this.totalTime = totalTime;
        this.track = track;
    }
}
