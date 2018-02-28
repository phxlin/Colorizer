package me.yufanlin.colorizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class SatActivity extends AppCompatActivity {

    private List<ColorHSV> colorList = new ArrayList<>();
    private static final int ACTIVITY_KEY = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sat);

        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        //Retrieve color
        float mCentralHue = prefs.getFloat(ColorAdapter.HUE_KEY, 0);
        float mSaturation = prefs.getFloat(ColorAdapter.SAT_KEY, 1);
        float mValue = prefs.getFloat(ColorAdapter.VAL_KEY, 1);
        int mSwatchNum = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        //Make color list
        for (int i = 0; i < mSwatchNum; i++){
            colorList.add( new ColorHSV(mCentralHue, mSaturation, mValue));
        }

        //Display toast
        displayToast(mCentralHue, mSaturation, mValue, mSwatchNum);

        //Adapter and recycler view
        ColorAdapter adapter = new ColorAdapter(this, colorList, 1, ACTIVITY_KEY);

        RecyclerView recyclerView = findViewById(R.id.rvColors);
        recyclerView.setAdapter(adapter);

        //Toolbar and actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ValActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Settings menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, PrefsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void displayToast(float hue, float sat, float val, int swatch) {
        if(hue > 360) {
            hue -= 360;
        }

        if(hue < 0) {
            hue += 360;
        }

        //neatly format the chosen saturation and value
        @SuppressLint("DefaultLocale") String mFormatSat = String.format("%.2f", sat * 100);
        @SuppressLint("DefaultLocale") String mFormatVal = String.format("%.2f", val*100);

        Toast.makeText(this, "Hue: " + hue + "\u00B0"
                + ", Sat: " + mFormatSat + "%"
                + ", Val: " + mFormatVal + "%"
                + ", Swat: " + swatch, Toast.LENGTH_LONG).show();
    }
}
