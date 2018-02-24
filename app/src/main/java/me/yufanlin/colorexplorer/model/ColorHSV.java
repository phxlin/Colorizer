package me.yufanlin.colorexplorer.model;

public class ColorHSV {

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
}
