package com.tracker.cycletracker;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tracker.cycletracker.database.DatabaseManager;
import com.tracker.cycletracker.database.models.Track;
import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.TransitionManager;

public class DataActivity extends AppCompatActivity implements OnClickListener {

    public static final String EXTRA_TRACK_ID = "track_id";

    private DatabaseManager dbManager;
    private Track track;

    private ViewGroup container;
    private FloatingActionButton btnAction;
    private TextView header;

    private boolean isRunning = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        long trackID = getIntent().getLongExtra(EXTRA_TRACK_ID, -1);
        dbManager = new DatabaseManager(this);
        track = dbManager.getTrack(trackID);

        if (track == null) {
            onBackPressed();
        }

        setTitle(track.name);

        container = (CoordinatorLayout) findViewById(R.id.activity_data);
        btnAction = (FloatingActionButton) findViewById(R.id.btn_action);
        btnAction.setOnClickListener(this);
        header = (TextView) findViewById(R.id.header);
    }

    @Override
    public void onClick (View view) {
        isRunning = !isRunning;
        runAnimations();
    }

    private void runAnimations () {
        // Animate show header
        TransitionManager.beginDelayedTransition(container);
        header.setVisibility(isRunning ? View.VISIBLE : View.GONE);

        // Change color and icon to FloatingActionButton
        btnAction.setBackgroundTintList(ColorStateList
                .valueOf(getColor(isRunning ? R.color.colorRed : R.color.colorGreen)));
        btnAction.setImageResource(isRunning ? R.drawable.ic_stop : R.drawable.ic_start);

        // Animate change position FloatingActionButton
        TransitionManager.beginDelayedTransition(container,
                new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(300));
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) btnAction
                .getLayoutParams();

        if (isRunning) {
            params.setAnchorId(header.getId());
            params.gravity = Gravity.NO_GRAVITY;
            params.anchorGravity = Gravity.BOTTOM | Gravity.END;
        } else {
            params.setAnchorId(View.NO_ID);
            params.gravity = Gravity.BOTTOM | Gravity.END;
        }

        btnAction.setLayoutParams(params);
    }
}
