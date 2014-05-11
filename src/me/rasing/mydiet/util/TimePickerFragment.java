package me.rasing.mydiet.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import me.rasing.mydiet.R;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TextView;

public class TimePickerFragment extends DialogFragment {

	private OnTimeSetListener listener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		TextView editTime = (TextView) getActivity()
				.findViewById(R.id.editTime);
		String mTijdstip = editTime.getText().toString();

		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();

		try {
			Date tijdstip = DateFormat.getTimeInstance(DateFormat.SHORT).parse(
					mTijdstip);
			c.setTime(tijdstip);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this.listener, hour, minute,
				android.text.format.DateFormat.is24HourFormat(getActivity()));
	}
	
	public void setListener(OnTimeSetListener listener) {
		this.listener = listener;
	}
}