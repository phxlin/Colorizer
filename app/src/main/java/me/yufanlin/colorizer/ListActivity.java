package me.yufanlin.colorizer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

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

        //Open and seed database
        mDataSource = new DataSource(this);
        mDataSource.open();
        mDataSource.seedDatabase(colorInfoList);

        //Set recycler adapter
        setRecyclerAdapter();

        //Set toolbar and actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), SelectedActivity.class);
//                startActivity(intent);
//            }
//        });

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

    //Set recycler adapter
    private void setRecyclerAdapter() {
        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);
        List<ColorInfo> listFromDB;

        //Retrieve hsv and swatch number from preference
        float mSelectedHue = prefs.getFloat(ColorAdapter.HUE_KEY, 0);
        float mSelectedSat = prefs.getFloat(ColorAdapter.SAT_KEY, 1);
        float mSelectedVal = prefs.getFloat(ColorAdapter.VAL_KEY, 1);
        int mSwatchNum = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        float leftHue = (mSelectedHue - ((360/mSwatchNum)/2));
        float rightHue = (mSelectedHue + ((360/mSwatchNum)/2));

        while(leftHue < 0){
            leftHue += 360;
        }
        while(leftHue > 360){
            leftHue -= 360;
        }
        while(rightHue < 0){
            rightHue += 360;
        }
        while(rightHue > 360){
            rightHue -= 360;
        }

        float upperSat = (float)(mSelectedSat + 1.0/(mSwatchNum-1));
        float lowerSat = (float)(mSelectedSat - 1.0/(mSwatchNum-1));
        float upperVal = (float)(mSelectedVal + 1.0/(mSwatchNum-1));
        float lowerVal = (float)(mSelectedVal - 1.0/(mSwatchNum-1));

        if(leftHue > rightHue){
             listFromDB = mDataSource.getAllColors(rightHue, leftHue, upperSat, lowerSat, upperVal, lowerVal);
        }
        else{
            listFromDB = mDataSource.getAllColors(leftHue, rightHue, upperSat, lowerSat, upperVal, lowerVal);
        }

        if(listFromDB.isEmpty()) {
            Toast.makeText(this, "Can't find color", Toast.LENGTH_SHORT).show();
        }

        //Set adapter and recycler view
        ColorInfoAdapter adapter = new ColorInfoAdapter(this, listFromDB);

        RecyclerView recyclerView = findViewById(R.id.rvColorsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
