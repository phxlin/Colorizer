package me.yufanlin.colorexplorer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ColorHSV implements Parcelable {

    private float hue;
    private float saturation;
    private float value;

    //Constructors
    public ColorHSV() {
    }

    public ColorHSV(float hue, float saturation, float value) {
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    //Getters and setters
    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    //To string
    @Override
    public String toString() {
        return "Color{" +
                "hue=" + hue +
                ", saturation=" + saturation +
                ", value=" + value +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.hue);
        dest.writeFloat(this.saturation);
        dest.writeFloat(this.value);
    }

    protected ColorHSV(Parcel in) {
        this.hue = in.readFloat();
        this.saturation = in.readFloat();
        this.value = in.readFloat();
    }

    public static final Parcelable.Creator<ColorHSV> CREATOR = new Parcelable.Creator<ColorHSV>() {
        @Override
        public ColorHSV createFromParcel(Parcel source) {
            return new ColorHSV(source);
        }

        @Override
        public ColorHSV[] newArray(int size) {
            return new ColorHSV[size];
        }
    };
}
