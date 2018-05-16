package me.yufanlin.colorizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrefsActivity extends AppCompatActivity {

    private int mHueProgress;
    private int mSwatchProgress;
    private String chPlaceholder;
    private String swPlaceholder;

    @BindView(R.id.centralHueSeekBar) SeekBar mCentralHueSeekBar;
    @BindView(R.id.swatchNumbSeekBar) SeekBar mSwatchNumbSeekBar;
    @BindView(R.id.centralHueProgress) TextView mCentralHueProgress;
    @BindView(R.id.swatchNumbProgress) TextView mSwatchNumbProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        //Bind butter knife api
        ButterKnife.bind(this);

        //Set central hue and swatch number progress
        SharedPreferences prefs = getSharedPreferences(ColorAdapter.MY_GLOBAL_PRES, MODE_PRIVATE);
        mHueProgress = Math.round(prefs.getFloat(ColorAdapter.HUE_KEY, 0));
        mSwatchProgress = prefs.getInt(ColorAdapter.SWATCH_NUMBER_KEY, 13);

        String chPlaceholder = mCentralHueSeekBar.getProgress() + "/" + mCentralHueSeekBar.getMax();
        mCentralHueProgress.setText(chPlaceholder);
        String snPlaceholder = mSwatchNumbSeekBar.getProgress() + "/" + mSwatchNumbSeekBar.getMax();
        mSwatchNumbProgress.setText(snPlaceholder);

        //Set central hue and swatch number listeners
        CentralHueOnProgress();
        SwatchNumbOnProgress();

        //noinspection ConstantConditions
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_checkmark);

        //Set up navigation button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Load fragment
//        getFragmentManager()
//                .beginTransaction()
//                .add(R.id.prefs_content, new SettingsFragment())
//                .commit();
    }

      //Settings fragment
//    public static class SettingsFragment extends PreferenceFragment {
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.settings);
//        }
//    }

    @Override
    public void onBackPressed() {
        //Send back intent with result
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Listen central hue seek bar progress
    void CentralHueOnProgress() {
        while(mHueProgress > 360) {
            mHueProgress -= 360;
        }

        while(mHueProgress < 0) {
            mHueProgress += 360;
        }
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
