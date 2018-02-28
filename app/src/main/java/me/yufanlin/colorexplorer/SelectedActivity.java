package me.yufanlin.colorexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
    TextView mColorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);

        //Retrieve color
        float mSelectedHue = prefs.getFloat(ColorAdapter.HUE_KEY, 0);
        float mSelectedSat = prefs.getFloat(ColorAdapter.SAT_KEY, 1);
        float mSelectedVal = prefs.getFloat(ColorAdapter.VAL_KEY, 1);
        int mSwatchNum = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        mHueView = findViewById(R.id.hueView);
        mSatView = findViewById(R.id.satView);
        mValView = findViewById(R.id.valView);
        mColorView = findViewById(R.id.colorView);

        //Display color information
        displayColor(mSelectedHue, mSelectedSat, mSelectedVal, mSwatchNum);

        //Display color
        int mLeftColor = getLeftColor(mSelectedHue, mSelectedSat, mSelectedVal, mSwatchNum);
        int mRightColor = getRightColor(mSelectedHue, mSelectedSat, mSelectedVal, mSwatchNum);

        GradientDrawable drawable
                = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {mLeftColor, mRightColor} );

        mColorView.setBackground(drawable);

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

    private int getLeftColor(float hue, float sat, float val, int swat) {
        float mHueCentral = (hue - ((360/swat)/2));
        if(mHueCentral < 0){
            mHueCentral += 360;
        }
        if(mHueCentral > 360){
            mHueCentral -= 360;
        }

        float[] hsv = {mHueCentral, sat, val};

        return Color.HSVToColor(hsv);
    }

    private int getRightColor(float hue, float sat, float val, int swat) {
        float mHueCentral = (hue + ((360/swat)/2));
        if(mHueCentral < 0){
            mHueCentral += 360;
        }
        if(mHueCentral > 360){
            mHueCentral -= 360;
        }

        float[] hsv = {mHueCentral, sat, val};

        return Color.HSVToColor(hsv);
    }

    //Display color information
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
