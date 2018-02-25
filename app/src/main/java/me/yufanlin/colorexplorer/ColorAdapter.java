package me.yufanlin.colorexplorer;

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

import me.yufanlin.colorexplorer.model.ColorHSV;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    public static final String HUE_ID_KEY = "hue_id_key";
    public static final String SAT_ID_KEY = "sat_id_key";
    public static final String VAL_ID_KEY = "val_id_key";
    public static final String MY_GLOBAL_PRES = "my_global_prefs";
    private List<ColorHSV> mColorList;
    private Context mContext;
    private int mSelector;
    private int mSwatchNum;
    private int mActivityId;

    //Constructor
    ColorAdapter(Context context, List<ColorHSV> colors, int selector, int swatchNumber, int activityId) {
        this.mContext = context;
        this.mColorList = colors;
        this.mSelector = selector;
        this.mSwatchNum = swatchNumber;
        this.mActivityId = activityId;
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

        //Display color
        ColorHSV mColor = mColorList.get(position);
        float mHue = mColor.getHue();
        float mSat = mColor.getSaturation();
        float mVal = mColor.getValue();

        int mColorLeft = getLeftColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);
        int mColorRight = getRightColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);

        GradientDrawable drawable
                = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {mColorLeft, mColorRight} );

        holder.mText.setBackground(drawable);

        //Selected color
        final float mSelectedHue = (mColorList.get(position).getHue()) + (position *(360/mSwatchNum));
        final float mSelectedSat = (float)(1.0 - (position*(1.0/(mSwatchNum-1))));
        final float mSelectedVal = (float)(1.0 - (position*(1.0/(mSwatchNum-1))));

        //Navigate and launch activity
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mActivityId) {
                    case 1001: {
                        Intent intent = new Intent(mContext, SatActivity.class);
                        setSelected(HUE_ID_KEY, mSelectedHue);
                        mContext.startActivity(intent);
                        break;
                    }
                    case 1002: {
                        Intent intent = new Intent(mContext, ValActivity.class);
                        setSelected(SAT_ID_KEY, mSelectedSat);
                        mContext.startActivity(intent);
                        break;
                    }
                    case 1003: {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        setSelected(VAL_ID_KEY, mSelectedVal);
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

    //Store selected color
    private void setSelected(String id, float selected) {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putFloat(id, selected);
        editor.apply();
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(MY_GLOBAL_PRES, Context.MODE_PRIVATE);
    }

    private int getLeftColor(float central, int swatchCount, int position, float saturation, float value, int selector){
        int mColorHSV;
        if(selector == 0){
            float mHueCentral = (central + (position*(360/swatchCount)) - ((360/swatchCount)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 1){
            float mHueCentral = (mColorList.get(position).getHue() - ((360/mSwatchNum)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, (float) (1.0-(position*(1.0/(swatchCount-1)))), value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 2){
            float mHueCentral = (mColorList.get(position).getHue() - ((360/mSwatchNum)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, mColorList.get(position).getSaturation(), (float) (1.0-(position*(1.0/(swatchCount-1))))};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else{
            mColorHSV = 1;
        }

        return mColorHSV;
    }

    private int getRightColor(float central, int swatchCount, int position, float saturation, float value, int selector){
        int mColorHSV;
        if(selector == 0){
            float mHueCentral = (central + (position*(360/swatchCount)) + ((360/swatchCount)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 1){
            float mHueCentral = (mColorList.get(position).getHue() + ((360/mSwatchNum)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, (float) (1.0-(position*(1.0/(swatchCount-1)))), value};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else if(selector == 2){
            float mHueCentral = (mColorList.get(position).getHue() + ((360/mSwatchNum)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, mColorList.get(position).getSaturation(), (float) (1.0-(position*(1.0/(swatchCount-1))))};
            mColorHSV = Color.HSVToColor(hsv);
        }
        else{
            mColorHSV = 1;
        }

        return mColorHSV;
    }
}
