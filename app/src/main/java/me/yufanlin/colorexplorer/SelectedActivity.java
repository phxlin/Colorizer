package me.yufanlin.colorexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SelectedActivity extends AppCompatActivity {

    TextView mHueView;
    TextView mSatView;
    TextView mValView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);

        //Retrieve color
        float mSelectedHue = prefs.getFloat(ColorAdapter.HUE_KEY, 0);
        float mSelectedSat = prefs.getFloat(ColorAdapter.SAT_KEY, 1);
        float mSelectedVal = prefs.getFloat(ColorAdapter.VAL_KEY, 1);
        int mSwatchNumb = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        mHueView = findViewById(R.id.hueView);
        mSatView = findViewById(R.id.satView);
        mValView = findViewById(R.id.valView);

        displayColor(mSelectedHue, mSelectedSat, mSelectedVal, mSwatchNumb);

        //Toolbar and actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void displayColor(float hue, float sat, float val, int numb) {
        float leftHue = (hue - ((360/numb)/2));
        float rightHue = (hue + ((360/numb)/2));
        String huePlaceHolder;
        String satPlaceHolder;
        String valPlaceHolder;

        if(leftHue < 0){
            leftHue += 360;
        }
        if(leftHue > 360){
            leftHue -= 360;
        }
        if(rightHue < 0){
            rightHue += 360;
        }
        if(rightHue > 360){
            rightHue -= 360;
        }
        if(leftHue > rightHue){
            huePlaceHolder = "The chosen hues range from " + (int) leftHue + "\u00B0 to " + (int) rightHue +
                    "\u00B0 (" + (int) (rightHue + 360) + "\u00B0)";
            mHueView.setText(huePlaceHolder);
        }
        else{
            huePlaceHolder = "The chosen hues range from " + (int) leftHue + "\u00B0 to " + (int) rightHue
                    + "\u00B0";
            mHueView.setText(huePlaceHolder);
        }

        //Neatly format the chosen saturation and value
        @SuppressLint("DefaultLocale") String mFormatSat = String.format("%.2f", sat * 100);
        @SuppressLint("DefaultLocale") String mFormatVal = String.format("%.2f", val*100);

        satPlaceHolder = "The chosen saturation is " + mFormatSat + "%.";
        valPlaceHolder = "The chosen value is " + mFormatVal + "%.";

        mSatView.setText(satPlaceHolder);
        mValView.setText(valPlaceHolder);
    }
}
