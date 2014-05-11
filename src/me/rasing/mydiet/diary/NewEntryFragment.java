package me.rasing.mydiet.diary;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import me.rasing.mydiet.R;
import me.rasing.mydiet.nutritiontable.Foods;
import me.rasing.mydiet.nutritiontable.NutritionTableActivity;
import me.rasing.mydiet.util.DatePickerFragment;
import me.rasing.mydiet.util.TimePickerFragment;
import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewEntryFragment extends Fragment implements OnClickListener,
		OnTimeSetListener, OnDateSetListener {
	public static final int FOOD_ITEM_REQUEST = 1;
	private static final String[] PROJECTION = new String[] {
		"product_name",
		"calories",
		"amount",
		"_id"
	};
	private Calendar dateOfMeal = Calendar.getInstance();
	private SimpleCursorAdapter mAdapter;
	private MatrixCursor matrixCursor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.diary_fragment_new_entry,
				container, false);

		TextView editDate = (TextView) rootView.findViewById(R.id.editDate);
		editDate.setOnClickListener(this);
		TextView editTime = (TextView) rootView.findViewById(R.id.editTime);
		editTime.setOnClickListener(this);

		final Calendar c = Calendar.getInstance();
		final Date today = c.getTime();

		editDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(
				today));
		editTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(
				today));
		
		Button btn = (Button) rootView.findViewById(R.id.addFood);
		btn.setOnClickListener(this);

		matrixCursor= new MatrixCursor(PROJECTION);

		int[] viewIDs = new int[] {
				R.id.position,
		};
		
		mAdapter = new SimpleCursorAdapter(
				getActivity(),
				R.layout.diary_diaryentry_listitem,
				matrixCursor,
				PROJECTION,
				viewIDs,
				0);
		
		ListView listView = (ListView) rootView.findViewById(R.id.foodList);
		listView.setAdapter(mAdapter);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editDate:
			DatePickerFragment datePicker = new DatePickerFragment();
			datePicker.setListener(this);
			datePicker.show(getFragmentManager(), "datePicker");
			break;
		case R.id.editTime:
			TimePickerFragment timePicker = new TimePickerFragment();
			timePicker.setListener(this);
			timePicker.show(getFragmentManager(), "timePicker");
			break;
		case R.id.addFood:
			Intent intent = new Intent(this.getActivity(), NutritionTableActivity.class);
			this.startActivityForResult(intent, FOOD_ITEM_REQUEST);
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case FOOD_ITEM_REQUEST:
				String food_uri = data.getStringExtra("food_uri");
				String food_amount = data.getStringExtra("amount");
				
				String[] mProjection = {
					Foods.COLUMN_NAME_PRODUCT_NAME,
					Foods.COLUMN_NAME_CALORIES,
					Foods._ID
				};
				
				Cursor mCursor = this.getActivity().getContentResolver().query(
				    Uri.parse(food_uri),
				    mProjection,
				    null,
				    null,
				    null);
				
				mCursor.moveToFirst();
				this.matrixCursor.addRow(new Object[] {
						mCursor.getString(mCursor.getColumnIndexOrThrow(Foods.COLUMN_NAME_PRODUCT_NAME)),
						mCursor.getString(mCursor.getColumnIndexOrThrow(Foods.COLUMN_NAME_CALORIES)),
						food_amount,
						mCursor.getInt(mCursor.getColumnIndexOrThrow(Foods._ID))
						});
				this.mAdapter.notifyDataSetChanged();
				mCursor.close();
				break;
			}
		}
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		dateOfMeal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		dateOfMeal.set(Calendar.MINUTE, minute);

		TextView editTime = (TextView) getActivity()
				.findViewById(R.id.editTime);
		editTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(
				dateOfMeal.getTime()));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		dateOfMeal.set(year, month, day);

		TextView editDate = (TextView) getActivity()
				.findViewById(R.id.editDate);
		editDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(
				dateOfMeal.getTime()));
	}

	public Date getDateOfMeal() {
		return this.dateOfMeal.getTime();
	}
	
	public Cursor getFoods() {
		return this.matrixCursor;
	}
}
