package com.tracker.cycletracker.tracks;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tracker.cycletracker.R;

/**
 * Created by Krum Iliev on 2/24/2017.
 */
public class TracksMenuDialog extends Dialog implements View.OnClickListener {

    private int position; // Track position in list
    private MenuListener listener;

    public TracksMenuDialog (Context context, int position, MenuListener listener) {
        super(context);
        this.position = position;
        this.listener = listener;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_menu_track);

        findViewById(R.id.edit).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
    }

    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.edit:
                listener.onTrackEdit(position);
                break;

            case R.id.delete:
                listener.onTrackDelete(position);
                break;
        }

        dismiss();
    }

    public interface MenuListener {

        void onTrackEdit (int position);
        void onTrackDelete (int position);
    }
}
