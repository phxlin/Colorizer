package me.yufanlin.colorizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.yufanlin.colorizer.model.ColorHSV;

public class ValActivity extends AppCompatActivity {

    private static final int ACTIVITY_KEY = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_val);

        //Set recycler adapter
        setRecyclerAdapter();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Settings
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, PrefsActivity.class);
            startActivityForResult(settingsIntent, ACTIVITY_KEY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Back button in preference activity updates hue activity recycler view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_KEY && resultCode == RESULT_OK) {
            setRecyclerAdapter();
        }
    }

    //Set recycler adapter
    private void setRecyclerAdapter() {
        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);

        //Retrieve hsv and swatch number from preference
        float mCentralHue = prefs.getFloat(ColorAdapter.HUE_KEY, 0);
        float mSaturation = prefs.getFloat(ColorAdapter.SAT_KEY, 1);
        float mValue = prefs.getFloat(ColorAdapter.VAL_KEY, 1);
        int mSwatchNum = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        //Create colorlist array
        List<ColorHSV> colorList = new ArrayList<>();
        for (int i = 0; i < mSwatchNum; i++){
            colorList.add( new ColorHSV(mCentralHue, mSaturation, mValue));
        }

        //Display selected hsv
        displayToast(mCentralHue, mSaturation, mValue, mSwatchNum);

        //Adapter and recycler view
        ColorAdapter adapter = new ColorAdapter(this, colorList, 2, ACTIVITY_KEY);

        RecyclerView recyclerView = findViewById(R.id.rvColors);
        recyclerView.setAdapter(adapter);
    }

    //Display toast
    private void displayToast(float hue, float sat, float val, int swatch) {

        //Ensure hue's value is between 0 and 360
        while(hue > 360) {
            hue -= 360;
        }

        while(hue < 0) {
            hue += 360;
        }

        //Neatly format the chosen saturation and value
        @SuppressLint("DefaultLocale") String mFormatSat = String.format("%.2f", sat * 100);
        @SuppressLint("DefaultLocale") String mFormatVal = String.format("%.2f", val*100);

        Toast.makeText(this, "Hue: " + hue + "\u00B0"
                + ", Sat: " + mFormatSat + "%"
                + ", Val: " + mFormatVal + "%"
                + ", Swat: " + swatch, Toast.LENGTH_SHORT).show();
    }

}
