package me.yufanlin.colorexplorer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import me.yufanlin.colorexplorer.model.ColorHSV;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private List<ColorHSV> mColorList;
    private Context mContext;
    private int mSelector;
    private int mSwatchNum;

    //Constructor
    ColorAdapter(Context context, List<ColorHSV> colors, int selector, int swatchNumber) {

        this.mContext = context;
        this.mColorList = colors;
        this.mSelector = selector;
        this.mSwatchNum = swatchNumber;
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
    public void onBindViewHolder(ViewHolder holder, int position) {

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
    }

    //Return number of colors in the color collection.
    @Override
    public int getItemCount() {
        return mColorList.size();
    }

    //Set up the binding to the views in the xml layout file
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mText;

        ViewHolder(View colorView) {
            super(colorView);

            mText = colorView.findViewById(R.id.color);
        }
    }

//    //Constructor
//    ColorAdapter(@NonNull Context context, @NonNull List<ColorHSV> objects, int selector, int swatchNumber) {
//        super(context, R.layout.list_color, objects);
//
//        mColorList = objects;
//        mInflater = LayoutInflater.from(context);
//        mSelector = selector;
//        mSwatchNum = swatchNumber;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.list_color, parent, false);
//        }
//
//        TextView mText = convertView.findViewById(R.id.color);
//
//        ColorHSV mColor = mColorList.get(position);
//        float mHue = mColor.getHue();
//        float mSat = mColor.getSaturation();
//        float mVal = mColor.getValue();
//
//        int mColorLeft = getLeftColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);
//        int mColorRight = getRightColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);
//
//        GradientDrawable drawable
//                = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT,
//                new int[] {mColorLeft, mColorRight} );
//
//        mText.setBackground(drawable);
//
//        return convertView;
//    }

    private int getLeftColor(float central, int swatchCount, int position, float saturation, float value, int selector ){

            int mColor;
//        if(selector == 0){
            float mHueCentral = (central + (position*(360/swatchCount)) - ((360/swatchCount)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, value};
            mColor = Color.HSVToColor(hsv);
//        }
//        else if(selector == 1){
//            float mHueCentral = (MainActivity.mSelectedHue - ((360/MainActivity.mSwatchNum)/2));
//            if(mHueCentral < 0){
//                mHueCentral += 360;
//            }
//            if(mHueCentral > 360){
//                mHueCentral -= 360;
//            }
//            float[] hsv = {mHueCentral, (float) (1.0-(position*(1.0/(swatchCount-1)))), value};
//            mColor = Color.HSVToColor(hsv);
//        }
//        else if(selector == 2){
//            float mHueCentral = (MainActivity.mSelectedHue - ((360/MainActivity.mSwatchNum)/2));
//            if(mHueCentral < 0){
//                mHueCentral += 360;
//            }
//            if(mHueCentral > 360){
//                mHueCentral -= 360;
//            }
//            float[] hsv = {mHueCentral, MainActivity.mSelectedSat, (float) (1.0-(position*(1.0/(swatchCount-1))))};
//            mColor = Color.HSVToColor(hsv);
//        }
//        else{
//            mColor = 1;
//        }

        return mColor;
    }

    private int getRightColor(float central, int swatchCount, int position, float saturation, float value, int selector){

            int mColor;
//        if(selector == 0){
            float mHueCentral = (central + (position*(360/swatchCount)) + ((360/swatchCount)/2));
            if(mHueCentral < 0){
                mHueCentral += 360;
            }
            if(mHueCentral > 360){
                mHueCentral -= 360;
            }
            float[] hsv = {mHueCentral, saturation, value};
            mColor = Color.HSVToColor(hsv);
//        }
//        else if(selector == 1){
//            float mHueCentral = (MainActivity.mSelectedHue + ((360/MainActivity.mSwatchNum)/2));
//            if(mHueCentral < 0){
//                mHueCentral += 360;
//            }
//            if(mHueCentral > 360){
//                mHueCentral -= 360;
//            }
//            float[] hsv = {mHueCentral, (float) (1.0-(position*(1.0/(swatchCount-1)))), value};
//            mColor = Color.HSVToColor(hsv);
//        }
//        else if(selector == 2){
//            float mHueCentral = (MainActivity.mSelectedHue + ((360/MainActivity.mSwatchNum)/2));
//            if(mHueCentral < 0){
//                mHueCentral += 360;
//            }
//            if(mHueCentral > 360){
//                mHueCentral -= 360;
//            }
//            float[] hsv = {mHueCentral, MainActivity.mSelectedSat, (float) (1.0-(position*(1.0/(swatchCount-1))))};
//            mColor = Color.HSVToColor(hsv);
//        }
//        else{
//            mColor = 1;
//        }

        return mColor;
    }
}
