package me.rasing.mydiet.util;

import me.rasing.mydiet.diary.Entries;
import me.rasing.mydiet.diary.EntriesFoods;
import me.rasing.mydiet.nutritiontable.Foods;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 5;
	public static final String DATABASE_NAME = "IkWordGezond.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = " ,";
	private static final String UNIQUE = " UNIQUE";
	
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ Entries.TABLE_NAME + " ("
			+ Entries._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
			+ Entries.COLUMN_NAME_GUID + TEXT_TYPE + UNIQUE + COMMA_SEP
			+ Entries.COLUMN_NAME_DATE_OF_MEAL + TEXT_TYPE
			+ " )";
	private static final String SQL_CREATE_FOODS = "CREATE TABLE " + Foods.TABLE_NAME + " ("
			+ Foods._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
			+ Foods.COLUMN_NAME_GUID + TEXT_TYPE + UNIQUE + COMMA_SEP
			+ Foods.COLUMN_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP
			+ Foods.COLUMN_NAME_CALORIES + TEXT_TYPE + ")";
	private static final String SQL_ADD_COLUMN_DATE_OF_MEALS = "ALTER TABLE "
			+ Entries.TABLE_NAME + " ADD COLUMN "
			+ Entries.COLUMN_NAME_DATE_OF_MEAL + TEXT_TYPE;
	private static final String SQL_CREATE_ENTRIES_FOODS = "CREATE TABLE " + EntriesFoods.TABLE_NAME + " ("
			+ EntriesFoods._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
			+ EntriesFoods.COLUMN_NAME_ENTRIES_ID + INTEGER_TYPE + COMMA_SEP
			+ EntriesFoods.COLUMN_NAME_FOODS_ID + INTEGER_TYPE + COMMA_SEP
			+ EntriesFoods.COLUMN_NAME_CALORIES + INTEGER_TYPE + ")";
	private static final String SQL_ADD_COLUMN_CALORIES = "ALTER TABLE "
			+ EntriesFoods.TABLE_NAME + " ADD COLUMN "
			+ EntriesFoods.COLUMN_NAME_CALORIES + TEXT_TYPE;

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
		db.execSQL(SQL_CREATE_FOODS);
		db.execSQL(SQL_CREATE_ENTRIES_FOODS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (oldVersion) {
		case 1:
			db.execSQL(SQL_ADD_COLUMN_DATE_OF_MEALS);
		case 2:
			db.execSQL(SQL_CREATE_FOODS);
		case 3:
			db.execSQL("CREATE TABLE " + EntriesFoods.TABLE_NAME + " ("
					+ EntriesFoods._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
					+ EntriesFoods.COLUMN_NAME_ENTRIES_ID + INTEGER_TYPE + COMMA_SEP
					+ EntriesFoods.COLUMN_NAME_FOODS_ID + INTEGER_TYPE + ")");
		case 4:
			db.execSQL(SQL_ADD_COLUMN_CALORIES);
		}
	}
}
