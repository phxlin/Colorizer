package me.yufanlin.colorizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.yufanlin.colorizer.model.ColorHSV;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    public static final String MY_GLOBAL_PRES = "my_global_prefs";
    public static final String HUE_KEY = "hue_key";
    public static final String SAT_KEY = "sat_key";
    public static final String VAL_KEY = "val_key";
    public static final String SWATCH_NUMBER_KEY = "swatch_number_key";

    private Context mContext;
    private List<ColorHSV> mColorList;
    private int mSelector;
    private int mActivityId;
    private int mSwatchNum;

    //Constructor
    ColorAdapter(Context context, List<ColorHSV> colors, int selector, int activityId) {
        this.mContext = context;
        this.mColorList = colors;
        this.mSelector = selector;
        this.mActivityId = activityId;
        this.mSwatchNum = getItemCount();
    }

    //Called automatically by the adapter each time it needs a new visual representation of a color.
    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View colorView = inflater.inflate(R.layout.list_color, parent, false);

        return new ViewHolder(colorView);
    }

    //Called each time the adapter encounters a new color that needs to be displayed.
    @Override
    public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {
        position = holder.getAdapterPosition();

        //Display color from left to right using central hue
        ColorHSV mColor = mColorList.get(position);
        final float mHue = mColor.getHue();
        final float mSat = mColor.getSaturation();
        final float mVal = mColor.getValue();

        int mColorLeft = getLeftColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);
        int mColorRight = getRightColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);

        GradientDrawable drawable
                = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {mColorLeft, mColorRight} );

        holder.mText.setBackground(drawable);

        //Select color
        final float mSelectedHue = (mColorList.get(position).getHue()) + (position *(360/mSwatchNum));
        final float mSelectedSat = (float)(1.0 - (position*(1.0/(mSwatchNum-1))));
        final float mSelectedVal = (float)(1.0 - (position*(1.0/(mSwatchNum-1))));

        //Navigate and launch activity while storing selected hsv and swatch number
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mActivityId) {
                    case 1001: {
                        Intent intent = new Intent(mContext, SatActivity.class);
                        setFloat(HUE_KEY, mSelectedHue);
                        setFloat(SAT_KEY, mSat);
                        setFloat(VAL_KEY, mVal);
                        setInt(SWATCH_NUMBER_KEY, mSwatchNum);
                        mContext.startActivity(intent);
                        break;
                    }
                    case 1002: {
                        Intent intent = new Intent(mContext, ValActivity.class);
                        setFloat(HUE_KEY, mHue);
                        setFloat(SAT_KEY, mSelectedSat);
                        setFloat(VAL_KEY, mVal);
                        setInt(SWATCH_NUMBER_KEY, mSwatchNum);
                        mContext.startActivity(intent);
                        break;
                    }
                    case 1003: {
                        Intent intent = new Intent(mContext, SelectedActivity.class);
                        setFloat(HUE_KEY, mHue);
                        setFloat(SAT_KEY, mSat);
                        setFloat(VAL_KEY, mSelectedVal);
                        setInt(SWATCH_NUMBER_KEY, mSwatchNum);
                        mContext.startActivity(intent);
                        break;
                    }
                }
            }
        });

    }

    //Return number of colors in the color collection.
    @Override
    public int getItemCount() {
        return mColorList.size();
    }

    //Set up the binding to the views in the xml layout file
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;
        View mView;

        ViewHolder(View colorView) {
            super(colorView);

            mText = colorView.findViewById(R.id.color);
            mView = colorView;
        }
    }

    //Put float in preference
    private void setFloat(String id, float selected) {
        SharedPreferences.Editor editor = getSharedPrefs(mContext).edit();
        editor.putFloat(id, selected);
        editor.apply();
    }

    //Put int in preference
    private void setInt(String id, int selected) {
        SharedPreferences.Editor editor = getSharedPrefs(mContext).edit();
        editor.putInt(id, selected);
        editor.apply();
    }

    private SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(MY_GLOBAL_PRES, Context.MODE_PRIVATE);
    }

    private int getLeftColor(float hue, int swatchCount, int position, float saturation, float value, int selector){
        int mColorHSV;
        if(selector == 0){
            float mHueCentral = (hue + (position*(360/swatchCount)) - ((360/swatchCount)/2));
            while(mHueCentral < 0){
                mHueCentral += 360;
            }
            while(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 1){
            float mHueCentral = (hue - ((360/swatchCount)/2));
            while(mHueCentral < 0){
                mHueCentral += 360;
            }
            while(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, (float) (1.0-(position*(1.0/(swatchCount-1)))), value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 2){
            float mHueCentral = (hue - ((360/swatchCount)/2));
            while(mHueCentral < 0){
                mHueCentral += 360;
            }
            while(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, (float) (1.0-(position*(1.0/(swatchCount-1))))};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else{
            mColorHSV = 1;
        }

        return mColorHSV;
    }

    private int getRightColor(float hue, int swatchCount, int position, float saturation, float value, int selector){
        int mColorHSV;
        if(selector == 0){
            float mHueCentral = (hue + (position*(360/swatchCount)) + ((360/swatchCount)/2));
            while(mHueCentral < 0){
                mHueCentral += 360;
            }
            while(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 1){
            float mHueCentral = (hue + ((360/swatchCount)/2));
            while(mHueCentral < 0){
                mHueCentral += 360;
            }
            while(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, (float) (1.0-(position*(1.0/(swatchCount-1)))), value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 2){
            float mHueCentral = (hue + ((360/swatchCount)/2));
            while(mHueCentral < 0){
                mHueCentral += 360;
            }
            while(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, (float) (1.0-(position*(1.0/(swatchCount-1))))};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else{
            mColorHSV = 1;
        }

        return mColorHSV;
    }
}
