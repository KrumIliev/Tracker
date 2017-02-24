package com.tracker.cycletracker.tracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tracker.cycletracker.R;
import com.tracker.cycletracker.database.models.Track;

import java.util.ArrayList;

/**
 * Created by Krum Iliev on 2/24/2017.
 */
public class TracksListAdapter extends BaseAdapter {

    private TrackMenuListener listener;
    private ArrayList<Track> tracks;
    private Context context;

    public TracksListAdapter (ArrayList<Track> tracks, Context context,
            TrackMenuListener listener) {
        this.tracks = tracks;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getCount () {
        return tracks.size();
    }

    @Override
    public Object getItem (int i) {
        return tracks.get(i);
    }

    @Override
    public long getItemId (int i) {
        return tracks.get(i).id;
    }

    @Override
    public View getView (final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_track, viewGroup, false);
            viewHolder = new ViewHolder(view);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Track track = tracks.get(i);
        viewHolder.typeIcon.setImageResource(Track.getTypeIcon(track.type));
        viewHolder.name.setText(track.name);
        viewHolder.distance.setText(String.format("%s km", track.distance));

        viewHolder.menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View view) {
                listener.onTrackMenuClick(i);
            }
        });

        view.setTag(viewHolder);
        return view;
    }

    private class ViewHolder {

        final ImageView typeIcon;
        final TextView name;
        final TextView distance;
        final View menu;

        ViewHolder (View v) {
            typeIcon = (ImageView) v.findViewById(R.id.type_icon);
            name = (TextView) v.findViewById(R.id.name);
            distance = (TextView) v.findViewById(R.id.distance);
            menu = v.findViewById(R.id.menu);
        }
    }

    public interface TrackMenuListener {

        void onTrackMenuClick (int position);
    }
}
