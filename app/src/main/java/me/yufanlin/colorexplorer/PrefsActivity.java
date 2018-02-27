package me.yufanlin.colorexplorer;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PrefsActivity extends AppCompatActivity {

    private SeekBar mCentralHueSeekBar;
    private SeekBar mSwatchNumbSeekBar;
    private TextView mCentralHueProgress;
    private TextView mSwatchNumbProgress;
    private int mHueProgress;
    private int mSwatchProgress;
    private String chPlaceholder;
    private String swPlaceholder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);
        mHueProgress = Math.round(prefs.getFloat(ColorAdapter.HUE_KEY, 0));
        mSwatchProgress = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        mCentralHueSeekBar = findViewById(R.id.centralHueSeekBar);
        mSwatchNumbSeekBar = findViewById(R.id.swatchNumbSeekBar);
        mCentralHueProgress = findViewById(R.id.centralHueProgress);
        mSwatchNumbProgress = findViewById(R.id.swatchNumbProgress);

        String chPlaceholder = mCentralHueSeekBar.getProgress() + "/" + mCentralHueSeekBar.getMax();
        mCentralHueProgress.setText(chPlaceholder);
        String snPlaceholder = mSwatchNumbSeekBar.getProgress() + "/" + mSwatchNumbSeekBar.getMax();
        mSwatchNumbProgress.setText(snPlaceholder);

        CentralHueOnProgress();
        SwatchNumbOnProgress();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Load fragment
//        getFragmentManager()
//                .beginTransaction()
//                .add(R.id.prefs_content, new SettingsFragment())
//                .commit();
    }
    //    //Settings fragment
//    public static class SettingsFragment extends PreferenceFragment {
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.settings);
//        }
//    }

    //Listen central hue seek bar progress
    void CentralHueOnProgress() {
        mCentralHueSeekBar.setProgress(mHueProgress);
        chPlaceholder = mHueProgress + "/" + mCentralHueSeekBar.getMax();
        mCentralHueProgress.setText(chPlaceholder);

        mCentralHueSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    SharedPreferences.Editor editor = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE).edit();

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        mHueProgress = i;
                        chPlaceholder = mHueProgress + "/" + mCentralHueSeekBar.getMax();
                        mCentralHueProgress.setText(chPlaceholder);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        chPlaceholder = mHueProgress + "/" + mCentralHueSeekBar.getMax();
                        mCentralHueProgress.setText(chPlaceholder);
                        editor.putFloat(ColorAdapter.HUE_KEY, mHueProgress);
                        editor.apply();
                    }
        });

    }

    //Listen swatch number seek bar progress
    void SwatchNumbOnProgress() {
        mSwatchNumbSeekBar.setProgress(mSwatchProgress);
        swPlaceholder = mSwatchProgress + "/" + mSwatchNumbSeekBar.getMax();
        mSwatchNumbProgress.setText(swPlaceholder);

        mSwatchNumbSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    SharedPreferences.Editor editor = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE).edit();

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        mSwatchProgress = i;
                        swPlaceholder = mSwatchProgress + "/" + mSwatchNumbSeekBar.getMax();
                        mSwatchNumbProgress.setText(swPlaceholder);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        swPlaceholder = mSwatchProgress + "/" + mSwatchNumbSeekBar.getMax();
                        mSwatchNumbProgress.setText(swPlaceholder);
                        editor.putInt(ColorAdapter.SWATCH_NUMBER_KEY, mSwatchProgress);
                        editor.apply();
                    }
        });
    }


}
