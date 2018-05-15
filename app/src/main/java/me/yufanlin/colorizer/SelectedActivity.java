package me.yufanlin.colorizer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yufanlin.colorizer.database.DataSource;
import me.yufanlin.colorizer.model.ColorInfo;
import me.yufanlin.colorizer.sample.SampleDataProvider;

public class SelectedActivity extends AppCompatActivity {

    @BindView(R.id.hueView) TextView mHueView;
    @BindView(R.id.satView) TextView mSatView;
    @BindView(R.id.valView) TextView mValView;
    @BindView(R.id.colorView) TextView mColorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        //Bind butter knife api
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);

        //Retrieve color
        float mSelectedHue = prefs.getFloat(ColorAdapter.HUE_KEY, 0);
        float mSelectedSat = prefs.getFloat(ColorAdapter.SAT_KEY, 1);
        float mSelectedVal = prefs.getFloat(ColorAdapter.VAL_KEY, 1);
        int mSwatchNum = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        //Display color information
        displayColor(mSelectedHue, mSelectedSat, mSelectedVal, mSwatchNum);

        //Display color
        int mLeftColor = getLeftColor(mSelectedHue, mSelectedSat, mSelectedVal, mSwatchNum);
        int mRightColor = getRightColor(mSelectedHue, mSelectedSat, mSelectedVal, mSwatchNum);

        GradientDrawable drawable
                = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {mLeftColor, mRightColor} );

        mColorView.setBackground(drawable);

        //Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private int getLeftColor(float hue, float sat, float val, int swat) {
        float mHueCentral = (hue - ((360/swat)/2));
        while(mHueCentral < 0){
            mHueCentral += 360;
        }
        while(mHueCentral > 360){
            mHueCentral -= 360;
        }

        float[] hsv = {mHueCentral, sat, val};

        return Color.HSVToColor(hsv);
    }

    private int getRightColor(float hue, float sat, float val, int swat) {
        float mHueCentral = (hue + ((360/swat)/2));
        while(mHueCentral < 0){
            mHueCentral += 360;
        }
        while(mHueCentral > 360){
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
        @SuppressLint("DefaultLocale") String mFormatVal = String.format("%.2f", val * 100);

        satPlaceHolder = "The chosen saturation is " + mFormatSat + "%.";
        valPlaceHolder = "The chosen value is " + mFormatVal + "%.";

        mSatView.setText(satPlaceHolder);
        mValView.setText(valPlaceHolder);
    }
}
