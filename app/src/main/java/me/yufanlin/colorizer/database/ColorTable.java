package me.yufanlin.colorizer.database;

public class ColorTable {

    public static final String TABLE_COLORS = "colors";
    public static final String COLUMN_ID = "colorId";
    public static final String COLUMN_NAME = "colorName";
    public static final String COLUMN_HEX_CODE = "hex_code";
    public static final String COLUMN_HUE = "hue";
    public static final String COLUMN_SATURATION = "saturation";
    public static final String COLUMN_VALUE = "value";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_COLORS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_HEX_CODE + " TEXT," +
                    COLUMN_HUE + " REAL," +
                    COLUMN_SATURATION + " REAL," +
                    COLUMN_VALUE + " REAL" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_COLORS;
}
