package me.rasing.mydiet.diary;

import android.provider.BaseColumns;

public class EntriesFoods implements BaseColumns {
    public static final String TABLE_NAME = "entries_foods";
    public static final String COLUMN_NAME_ENTRIES_ID = "entries_id";
    public static final String COLUMN_NAME_FOODS_ID = "foods_id";
	public static final String COLUMN_NAME_CALORIES = "calories";
    
	public EntriesFoods() {}
}
