package me.yufanlin.colorizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import me.yufanlin.colorizer.database.DataSource;
import me.yufanlin.colorizer.model.ColorInfo;
import me.yufanlin.colorizer.sample.SampleDataProvider;

public class ListActivity extends AppCompatActivity {

    List<ColorInfo> colorInfoList = SampleDataProvider.colorList;

    DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Open database
        mDataSource = new DataSource(this);
        mDataSource.open();
        mDataSource.seedDatabase(colorInfoList);

        //Set adapter and recycler view
        List<ColorInfo> listFromDB = mDataSource.getAllColors();
        ColorInfoAdapter adapter = new ColorInfoAdapter(this, listFromDB);

        RecyclerView recyclerView = findViewById(R.id.rvColorsList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set toolbar and actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectedActivity.class);
                startActivity(intent);
            }
        });

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //Prevent database leak
    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }
}
