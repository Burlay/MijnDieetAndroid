package me.rasing.mydiet.util;

import me.rasing.mydiet.diary.Entries;
import me.rasing.mydiet.diary.EntriesFoods;
import me.rasing.mydiet.nutritiontable.Foods;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class MyDietProvider extends ContentProvider {
	public static final String AUTHORITY = "me.rasing.mydiet.util.MyDietProvider";
	
	private static final UriMatcher sUriMatcher;
	
	public static final Uri ENTRIES_URI =
			Uri.parse("content://" + AUTHORITY + "/" + Entries.TABLE_NAME);
	public static final Uri FOODS_URI =
			Uri.parse("content://" + AUTHORITY + "/" + Foods.TABLE_NAME);
	public static final Uri ENTRIES_FOODS_URI =
			Uri.parse("content://" + AUTHORITY + "/" + EntriesFoods.TABLE_NAME);

	private static final int ENTRIES = 0;
	private static final int ENTRIES_ID = 1;
	private static final int FOODS = 2;
	private static final int FOODS_ID = 3;
	private static final int ENTRIES_FOODS = 4;
	
	private DbHelper mDbHelper;

	@Override
	public boolean onCreate() {
		mDbHelper = new DbHelper(getContext());
		
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = null;
		switch (sUriMatcher.match(uri)) {
		case ENTRIES:
			String query = "SELECT "
					+ "strftime('%Y-%m-%d', " + Entries.COLUMN_NAME_DATE_OF_MEAL + ") AS " + Entries.COLUMN_NAME_DATE_OF_MEAL + ", "
					+ "SUM (" + EntriesFoods.COLUMN_NAME_CALORIES + ") AS " + EntriesFoods.COLUMN_NAME_CALORIES + ", "
					+ Entries.TABLE_NAME + "." + Entries._ID
					+ " FROM " + Entries.TABLE_NAME
					+ " LEFT JOIN " + EntriesFoods.TABLE_NAME
					+ " ON " + Entries.TABLE_NAME + "." + Entries._ID +
					" = " + EntriesFoods.COLUMN_NAME_ENTRIES_ID
					+ " GROUP BY strftime('%Y-%m-%d', " + Entries.COLUMN_NAME_DATE_OF_MEAL + ")";
			Log.e("QUERY", ">>>>" + query);
			cursor = db.rawQuery(query, null);
			break;
		case FOODS:
			qb.setTables(Foods.TABLE_NAME);
			cursor  = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case FOODS_ID:
			qb.setTables(Foods.TABLE_NAME);
			selection = Foods._ID + " = ?";
			selectionArgs = new String[] {
					uri.getLastPathSegment()
			};
			cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId = 0;
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		switch (sUriMatcher.match(uri)) {
		case ENTRIES:
			rowId = db.insert(Entries.TABLE_NAME, null, values);
			break;
		case FOODS:
			rowId = db.insert(Foods.TABLE_NAME, null, values);
			break;
		case ENTRIES_FOODS:
			rowId = db.insert(EntriesFoods.TABLE_NAME, null, values);
		}
		
		if (rowId == -1)
			throw new RuntimeException();
		
		getContext().getContentResolver().notifyChange(uri, null);
		return ContentUris.withAppendedId(uri, rowId);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	    sUriMatcher.addURI(AUTHORITY, Entries.TABLE_NAME       , ENTRIES);
	    sUriMatcher.addURI(AUTHORITY, Entries.TABLE_NAME + "/#", ENTRIES_ID);
	    sUriMatcher.addURI(AUTHORITY, Foods.TABLE_NAME         , FOODS);
	    sUriMatcher.addURI(AUTHORITY, Foods.TABLE_NAME + "/#"  , FOODS_ID);
	    sUriMatcher.addURI(AUTHORITY, EntriesFoods.TABLE_NAME  , ENTRIES_FOODS);
	}
}
