package me.rasing.mydiet.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import me.rasing.mydiet.R;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment {
	private OnDateSetListener listener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		TextView editDate = (TextView) getActivity()
				.findViewById(R.id.editDate);
		String mDatum = editDate.getText().toString();

		// Use the current date as the default date in the picker
		Calendar c = Calendar.getInstance();

		try {
			Date datum = DateFormat.getDateInstance(DateFormat.LONG).parse(
					mDatum);
			c.setTime(datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this.listener, year, month, day);
	}
	
	public void setListener(OnDateSetListener listener) {
		this.listener = listener;
	}
}