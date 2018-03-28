package me.yufanlin.colorizer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.List;

import me.yufanlin.colorizer.model.ColorInfo;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
        Toast.makeText(mContext, "Database acquired", Toast.LENGTH_SHORT).show();
    }

    public void close() {
        mDbHelper.close();
        Toast.makeText(mContext, "Database closed", Toast.LENGTH_SHORT).show();
    }

    public void seedDatabase(List<ColorInfo> colorInfoList) {
        long numColors = getColorInfoCount();
        if(numColors == 0) {
            //Insert data
            for(ColorInfo color :
                    colorInfoList) {
                try {
                    createColor(color);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(mContext, "Data inserted.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Data are already in the database", Toast.LENGTH_SHORT).show();
        }
    }

    public ColorInfo createColor(ColorInfo color) {
        ContentValues values = color.toValues();
        mDatabase.insert(ColorTable.TABLE_COLORS, null, values);
        return color;
    }

    public long getColorInfoCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ColorTable.TABLE_COLORS);
    }
}
