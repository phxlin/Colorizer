package me.yufanlin.colorizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import me.yufanlin.colorizer.model.ColorInfo;

public class ColorInfoAdapter extends RecyclerView.Adapter<ColorInfoAdapter.ViewHolder> {

    public static final String COLOR_KEY = "color_key";

    private Context mContext;
    private List<ColorInfo> mColorInfoList;

    //Constructor
    ColorInfoAdapter(Context context, List<ColorInfo> colorsInfo) {
        this.mContext = context;
        this.mColorInfoList = colorsInfo;
    }

    //Called automatically by the adapter each time it needs a new visual representation of a color.
    @Override
    public ColorInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View colorView = inflater.inflate(R.layout.list_color_info, parent, false);

        return new ViewHolder(colorView);
    }

    //Called each time the adapter encounters a new color that needs to be displayed.
    @Override
    public void onBindViewHolder(ColorInfoAdapter.ViewHolder holder, int position) {
        //Display color in db
        final ColorInfo mColor = mColorInfoList.get(position);
        String mColorName = mColor.getName();
        float mHue = mColor.getHue();
        float mSat = mColor.getSaturation();
        float mVal = mColor.getValue();

        float[] hsv = {mHue, mSat, mVal};
        int mColorHSV = Color.HSVToColor(hsv);

        holder.mColorText.setBackgroundColor(mColorHSV);
        holder.mColorNameText.setText(mColorName);

        //Navigate to detail activity
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(COLOR_KEY, mColor);
                mContext.startActivity(intent);
            }
        });
    }

    //Return number of colors in the color collection.
    @Override
    public int getItemCount() {
        return mColorInfoList.size();
    }

    //Set up the binding to the views in the xml layout file
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mColorNameText;
        TextView mColorText;
        View mView;

        ViewHolder(View colorView) {
            super(colorView);

            mColorNameText = colorView.findViewById(R.id.colorNameText);
            mColorText = colorView.findViewById(R.id.colorText);
            mView = colorView;
        }
    }
}
