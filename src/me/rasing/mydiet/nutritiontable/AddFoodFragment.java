package me.rasing.mydiet.nutritiontable;

import me.rasing.mydiet.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddFoodFragment extends Fragment {
	private EditText productName;
	private EditText calories;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.nutritiontable_add_food,
				container, false);
		
		this.productName = (EditText) rootView.findViewById(R.id.productName);
		this.calories = (EditText) rootView.findViewById(R.id.calories);
		
		return rootView;
	}

	public String getProductName() {
		return this.productName.getText().toString();
	}
	
	public String getCalories() {
		return this.calories.getText().toString();
	}
}
