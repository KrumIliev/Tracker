package com.tracker.cycletracker.database.models;

import com.tracker.cycletracker.R;

/**
 * Created by Krum Iliev on 2/23/2017.
 */

public class Track {

    public static final int TYPE_CAR = 0;
    public static final int TYPE_BIKE = 1;
    public static final int TYPE_RUN = 2;
    public static final int TYPE_WALK = 3;

    public long id;
    public String name;
    public int distance;
    public int type;

    public Track (long id, String name, int distance, int type) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.type = type;
    }

    public static int getTypeIcon (int type) {
        switch (type) {
            case TYPE_CAR:
                return R.drawable.ic_car;

            case TYPE_BIKE:
                return R.drawable.ic_bike;

            case TYPE_RUN:
                return R.drawable.ic_run;

            case TYPE_WALK:
                return R.drawable.ic_walk;

            default:
                return -1;
        }
    }
}
