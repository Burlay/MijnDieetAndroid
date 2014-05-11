package me.rasing.mydiet.nutritiontable;

import android.provider.BaseColumns;

public class Foods implements BaseColumns {
    public static final String TABLE_NAME = "foods";
    public static final String COLUMN_NAME_GUID = "guid";
    public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
    public static final String COLUMN_NAME_CALORIES = "calories";
    
    public Foods() {}
}
