package me.yufanlin.colorizer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yufanlin.colorizer.model.ColorInfo;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.colorNameView) TextView mColorName;
    @BindView(R.id.hexCodeView) TextView mHexCode;
    @BindView(R.id.colorView) TextView mColor;
    @BindView(R.id.hsvView) TextView mHSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Bind butter knife
        ButterKnife.bind(this);

        //Get selected ColorInfo object and fields
        ColorInfo colorInfo = Objects.requireNonNull(getIntent().getExtras()).getParcelable(ColorInfoAdapter.COLOR_KEY);
        assert colorInfo != null;
        String name = colorInfo.getName();
        String hexCode = colorInfo.getHexCode();
        float hue = colorInfo.getHue();
        float saturation = colorInfo.getSaturation();
        float value = colorInfo.getValue();

        //Set color name and hexcode
        mColorName.setText(name);
        mHexCode.setText(hexCode);

        //Set color and info
        float[] hsv = {hue, saturation, value};
        int mColorHSV = Color.HSVToColor(hsv);

        //Neatly format the chosen saturation and value
        @SuppressLint("DefaultLocale") String mFormatHue = String.format("%.1f", hue);
        @SuppressLint("DefaultLocale") String mFormatSat = String.format("%.2f", saturation * 100);
        @SuppressLint("DefaultLocale") String mFormatVal = String.format("%.2f", value * 100);

        String hsvStr = "Hue: " + mFormatHue + "\u00B0"
                + ", Sat: " + mFormatSat + "%"
                + ", Val: " + mFormatVal + "%";

        mColor.setBackgroundColor(mColorHSV);
        mHSV.setText(hsvStr);

        //noinspection ConstantConditions
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        //Set up navigation button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //Up navigation button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
