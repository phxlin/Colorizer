package me.yufanlin.colorizer.model;

public class ColorInfo extends ColorHSV {

    private String hexCode;
    private String name;

    //Constructor
    public ColorInfo() {
    }

    public ColorInfo(float hue, float saturation, float value, String hexCode, String name) {
        super(hue, saturation, value);
        this.hexCode = hexCode;
        this.name = name;
    }

    //Getters and setters
    public String getHexCode() {
        return hexCode;
    }

    public String getName() {
        return name;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    //To string
    @Override
    public String toString() {
        return "ColorInfo{" +
                "hexCode='" + hexCode + '\'' +
                ", name='" + name + '\'' +
                ", hue='" + getHue() + '\'' +
                ", sat='" + getSaturation() + '\'' +
                ", value='" + getValue() +
                '}';
    }
}
