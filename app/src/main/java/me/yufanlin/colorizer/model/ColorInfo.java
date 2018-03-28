package me.yufanlin.colorizer.model;

import android.content.ContentValues;

import java.util.UUID;

import me.yufanlin.colorizer.database.ColorTable;

public class ColorInfo extends ColorHSV {

    private String colorId;
    private String name;
    private String hexCode;

    //Constructor
    public ColorInfo() {
    }

    public ColorInfo(float hue, float saturation, float value, String hexCode, String name) {
        super(hue, saturation, value);

        if(colorId == null) {
            colorId = UUID.randomUUID().toString();
        }
        this.hexCode = hexCode;
        this.name = name;
    }

    //Getters and setters
    public String getColorId() {return colorId; }
    public String getName() { return name; }
    public String getHexCode() {
        return hexCode;
    }
    public void setColorId(String id) { this.colorId = id; }
    public void setName(String name) { this.name = name; }
    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(6);

        values.put(ColorTable.COLUMN_ID, colorId);
        values.put(ColorTable.COLUMN_NAME, name);
        values.put(ColorTable.COLUMN_HEX_CODE, hexCode);
        values.put(ColorTable.COLUMN_HUE, getHue());
        values.put(ColorTable.COLUMN_SATURATION, getSaturation());
        values.put(ColorTable.COLUMN_VALUE, getValue());
        return values;
    }

    //To string
    @Override
    public String toString() {
        return "ColorInfo{" +
                "colorId='" + colorId + '\'' +
                ", name='" + name + '\'' +
                ", hexCode='" + hexCode + '\'' +
                ", hue='" + getHue() + '\'' +
                ", sat='" + getSaturation() + '\'' +
                ", value='" + getValue() +
                '}';
    }
}
