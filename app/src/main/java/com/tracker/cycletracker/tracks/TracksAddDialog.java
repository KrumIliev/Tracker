package com.tracker.cycletracker.tracks;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.tracker.cycletracker.R;
import com.tracker.cycletracker.database.DatabaseManager;
import com.tracker.cycletracker.database.models.Track;

/**
 * Created by Krum Iliev on 2/23/2017.
 */
public class TracksAddDialog extends Dialog implements View.OnClickListener {

    private TrackListener listener;

    private ImageView btnTypeCar;
    private ImageView btnTypeBike;
    private ImageView btnTypeRun;
    private ImageView btnTypeWalk;

    private EditText inpName;
    private EditText inpDistance;

    private boolean isEditing = false;
    private Track track;

    private int selectedType = Track.TYPE_CAR;

    public TracksAddDialog (Context context, TrackListener listener) {
        super(context);
        this.listener = listener;
    }

    public TracksAddDialog (Context context, TrackListener listener, Track track) {
        super(context);
        this.listener = listener;
        this.track = track;
        isEditing = true;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_track);

        btnTypeCar = (ImageView) findViewById(R.id.type_car);
        btnTypeBike = (ImageView) findViewById(R.id.type_bike);
        btnTypeRun = (ImageView) findViewById(R.id.type_run);
        btnTypeWalk = (ImageView) findViewById(R.id.type_walk);

        inpName = (EditText) findViewById(R.id.name_input);
        inpDistance = (EditText) findViewById(R.id.distance_input);

        findViewById(R.id.btn_save).setOnClickListener(this);
        btnTypeCar.setOnClickListener(this);
        btnTypeBike.setOnClickListener(this);
        btnTypeRun.setOnClickListener(this);
        btnTypeWalk.setOnClickListener(this);

        if (isEditing) {
            selectedType = track.type;
            inpName.setText(track.name);
            if (track.distance > 0) {
                inpDistance.setText(String.valueOf(track.distance));
            }
        }

        setTypeButtons();
    }

    // ---------------------------------------------------------------------------------------------
    // BUTTONS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                onSaveClick();
                break;

            case R.id.type_car:
                selectedType = Track.TYPE_CAR;
                setTypeButtons();
                break;

            case R.id.type_bike:
                selectedType = Track.TYPE_BIKE;
                setTypeButtons();
                break;

            case R.id.type_run:
                selectedType = Track.TYPE_RUN;
                setTypeButtons();
                break;

            case R.id.type_walk:
                selectedType = Track.TYPE_WALK;
                setTypeButtons();
                break;
        }
    }

    private void onSaveClick () {
        String name = inpName.getText().toString();
        int distance = Integer.valueOf(inpDistance.getText().toString());
        DatabaseManager manager = new DatabaseManager(getContext());

        if (isEditing) {
            track.name = name;
            track.distance = distance;
            track.type = selectedType;
            manager.updateTrack(track);

        } else {
            track = new Track(0, name, distance, selectedType);
            manager.addTrack(track);
        }

        listener.onTrackChange();
        dismiss();
    }

    private void setTypeButtons () {
        btnTypeCar.setSelected(selectedType == Track.TYPE_CAR);
        btnTypeBike.setSelected(selectedType == Track.TYPE_BIKE);
        btnTypeRun.setSelected(selectedType == Track.TYPE_RUN);
        btnTypeWalk.setSelected(selectedType == Track.TYPE_WALK);
    }

    // ---------------------------------------------------------------------------------------------
    // LISTENERS
    // ---------------------------------------------------------------------------------------------

    public interface TrackListener {

        void onTrackChange ();
    }
}
