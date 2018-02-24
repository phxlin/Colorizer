package me.yufanlin.colorexplorer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.yufanlin.colorexplorer.model.ColorHSV;

public class ColorAdapter extends ArrayAdapter<ColorHSV> {

    List<ColorHSV> mColorList;
    LayoutInflater mInflater;
    int mSelector;
    int mSwatchNum;
    int mColorLeft;
    int mColorRight;

    public ColorAdapter(@NonNull Context context, @NonNull List<ColorHSV> objects, int selector, int swatchNumber) {
        super(context, R.layout.list_color, objects);

        mColorList = objects;
        mInflater = LayoutInflater.from(context);
        mSelector = selector;
        mSwatchNum = swatchNumber;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_color, parent, false);
        }

        TextView mText = convertView.findViewById(R.id.color);

        ColorHSV mColor = mColorList.get(position);
        float mHue = mColor.getHue();
        float mSat = mColor.getSaturation();
        float mVal = mColor.getValue();

        mColorLeft = getLeftColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);
        mColorRight = getRightColor(mHue, mSwatchNum, position, mSat, mVal, mSelector);

        GradientDrawable drawable
                = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] { mColorLeft, mColorRight } );

        mText.setBackground(drawable);

        return convertView;
    }

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
