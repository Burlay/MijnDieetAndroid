package me.rasing.mydiet.diary;

import android.provider.BaseColumns;

public class Entries implements BaseColumns {
    public static final String TABLE_NAME = "entries";
    public static final String COLUMN_NAME_GUID = "guid";
    public static final String COLUMN_NAME_DATE_OF_MEAL = "date_of_meal";
    
    public Entries() {}
}
