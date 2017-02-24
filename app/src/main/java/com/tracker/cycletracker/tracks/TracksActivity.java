package com.tracker.cycletracker.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tracker.cycletracker.DataActivity;
import com.tracker.cycletracker.R;
import com.tracker.cycletracker.database.DatabaseManager;
import com.tracker.cycletracker.database.models.Track;
import com.tracker.cycletracker.tracks.TracksAddDialog.TrackListener;
import com.tracker.cycletracker.tracks.TracksListAdapter.TrackMenuListener;
import com.tracker.cycletracker.tracks.TracksMenuDialog.MenuListener;

import java.util.ArrayList;

public class TracksActivity extends AppCompatActivity
        implements OnClickListener, TrackListener, TrackMenuListener, OnItemClickListener,
        MenuListener {

    private ArrayList<Track> tracks;
    private DatabaseManager dbManager;
    private TracksListAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        setTitle(R.string.tracks_title);

        findViewById(R.id.btn_add).setOnClickListener(this);

        dbManager = new DatabaseManager(this);

        if (adapter == null) {
            tracks = dbManager.getAllTracks();
            adapter = new TracksListAdapter(tracks, this, this);
        }

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onClick (View view) {
        new TracksAddDialog(this, this).show();
    }

    @Override
    public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
        Track track = tracks.get(i);
        Intent intent = new Intent(this, DataActivity.class);
        intent.putExtra(DataActivity.EXTRA_TRACK_ID, track.id);
        startActivity(intent);
    }

    @Override
    public void onTrackMenuClick (int position) {
        new TracksMenuDialog(this, position, this).show();
    }

    @Override
    public void onTrackEdit (int position) {
        Track track = tracks.get(position);
        new TracksAddDialog(this, this, track).show();
    }

    @Override
    public void onTrackDelete (int position) {
        Track track = tracks.get(position);
        dbManager.deleteTrack(track);
        onTrackChange();
    }

    @Override
    public void onTrackChange () {
        tracks.clear();
        tracks.addAll(dbManager.getAllTracks());
        adapter.notifyDataSetChanged();
    }
}
