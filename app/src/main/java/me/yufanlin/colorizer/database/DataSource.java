package me.yufanlin.colorizer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import me.yufanlin.colorizer.model.ColorInfo;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
        //Toast.makeText(mContext, "Database acquired", Toast.LENGTH_SHORT).show();
    }

    public void close() {
        mDbHelper.close();
        //Toast.makeText(mContext, "Database closed", Toast.LENGTH_SHORT).show();
    }

    public void seedDatabase(List<ColorInfo> colorInfoList) {
        long numColors = getColorInfoCount();
        if(numColors == 0) {
            for (ColorInfo color :
                    colorInfoList) {
                try {
                    createColor(color);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }
            //Toast.makeText(mContext, "Data inserted.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(mContext, "Data are already in the database", Toast.LENGTH_SHORT).show();
//        }
    }

    //Create contentValues and insert them to db
    private void createColor(ColorInfo color) {
        ContentValues values = color.toValues();
        mDatabase.insert(ColorTable.TABLE_COLORS, null, values);
    }

    //Return colorinfo count
    private long getColorInfoCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ColorTable.TABLE_COLORS);
    }

    //Retrieve and filter colorinfo objects
    public List<ColorInfo> getAllColors(float leftHue, float rightHue, float upperSat, float lowerSat, float upperVal, float lowerVal) {
        List<ColorInfo> colorInfos = new ArrayList<>();

        String[] hsvArray = {Float.toString(leftHue), Float.toString(rightHue),
                                Float.toString(lowerSat), Float.toString(upperSat),
                                    Float.toString(lowerVal), Float.toString(upperVal)};

        Cursor cursor = mDatabase.query(ColorTable.TABLE_COLORS, ColorTable.COLUMN_ALL,
                ColorTable.COLUMN_HUE + ">=? AND "
                        + ColorTable.COLUMN_HUE + "<=? AND "
                        + ColorTable.COLUMN_SATURATION + ">=? AND "
                        + ColorTable.COLUMN_SATURATION + "<=? AND "
                        + ColorTable.COLUMN_VALUE + ">=? AND "
                        + ColorTable.COLUMN_VALUE + "<=?", hsvArray, null, null, ColorTable.COLUMN_HUE);

        while(cursor.moveToNext()) {
            ColorInfo colorInfo = new ColorInfo();

            colorInfo.setColorId(cursor.getString(
                    cursor.getColumnIndex(ColorTable.COLUMN_ID)));
            colorInfo.setName(cursor.getString(
                    cursor.getColumnIndex(ColorTable.COLUMN_NAME)));
            colorInfo.setHexCode(cursor.getString(
                    cursor.getColumnIndex(ColorTable.COLUMN_HEX_CODE)));
            colorInfo.setHue(cursor.getFloat(
                    cursor.getColumnIndex(ColorTable.COLUMN_HUE)));
            colorInfo.setSaturation(cursor.getFloat(
                    cursor.getColumnIndex(ColorTable.COLUMN_SATURATION)));
            colorInfo.setValue(cursor.getFloat(
                    cursor.getColumnIndex(ColorTable.COLUMN_VALUE)));

            colorInfos.add(colorInfo);
        }

        cursor.close();

        return colorInfos;
    }
}
