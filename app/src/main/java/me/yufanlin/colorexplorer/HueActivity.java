package me.yufanlin.colorexplorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.yufanlin.colorexplorer.model.ColorHSV;

public class HueActivity extends AppCompatActivity {

    List<ColorHSV> colorList = new ArrayList<>();
    private static final int ACTIVITY_ID_KEY = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hue);

        //Default values
        int mCentralHue = 120;
        int mSwatchNum = 6;

        //Make the color list
        for (int i = 0; i < mSwatchNum; i++){
            colorList.add( new ColorHSV(mCentralHue, (float) 1.0, (float) 1.0));
        }

        //Adapter and recycler view
        ColorAdapter adapter = new ColorAdapter(this, colorList, 0, mSwatchNum, ACTIVITY_ID_KEY);

        RecyclerView recyclerView = findViewById(R.id.rvColors);
        recyclerView.setAdapter(adapter);

        //Toolbar and actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
