package me.rasing.mydiet.diary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import me.rasing.mydiet.R;
import me.rasing.mydiet.util.MyDietProvider;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class NewEntryActivity extends Activity implements OnClickListener {
	private NewEntryFragment fragment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newentry);

		fragment = new NewEntryFragment();

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment)
				.commit();

		LayoutInflater actionBarInflater = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = actionBarInflater.inflate(
				R.layout.actionbar_custom_view_done_discard, null);
		customActionBarView.findViewById(R.id.actionbar_done)
				.setOnClickListener(this);
		customActionBarView.findViewById(R.id.actionbar_discard)
				.setOnClickListener(this);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(customActionBarView,
				new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.actionbar_done:
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
			formatter.setTimeZone(tz);
			ContentValues values = new ContentValues();
			values.put(Entries.COLUMN_NAME_GUID, UUID.randomUUID().toString());
			values.put(Entries.COLUMN_NAME_DATE_OF_MEAL,
					formatter.format(fragment.getDateOfMeal()));
			Uri uri = this.getContentResolver().insert(MyDietProvider.ENTRIES_URI, values);
			String entries_id = uri.getLastPathSegment();
			
			Cursor cursor = fragment.getFoods();
			cursor.moveToPosition(-1);
			while (cursor.moveToNext()) {
				Double product_calories = cursor.getDouble(cursor.getColumnIndexOrThrow("calories"));
				Double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
				
				values = new ContentValues();
				values.put(EntriesFoods.COLUMN_NAME_ENTRIES_ID, entries_id);
				values.put(EntriesFoods.COLUMN_NAME_FOODS_ID,
						cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
				values.put(EntriesFoods.COLUMN_NAME_CALORIES, product_calories / 100 * amount);
				this.getContentResolver().insert(MyDietProvider.ENTRIES_FOODS_URI, values);
			}
			
			this.finish();
			break;
		case R.id.actionbar_discard:
			this.finish();
			break;
		}
	}
}
