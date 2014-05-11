package me.rasing.mydiet.nutritiontable;

import me.rasing.mydiet.R;
import me.rasing.mydiet.util.MyDietProvider;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class NutritionTableActivity extends Activity implements
		OnItemClickListener, FoodAmountDialog.NoticeDialogListener {
	private NutritionTableFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nutritiontable);

		fragment = new NutritionTableFragment();

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment)
				.commit();

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		fragment.setOnItemClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		fragment.setOnItemClickListener(null);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		case R.id.action_add_food:
			Intent intent = new Intent(this.getApplicationContext(),
					AddFoodActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.nutritiontable, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putString(FoodAmountDialog.DIALOG_TITLE,
				cursor.getString(cursor.getColumnIndex(Foods.COLUMN_NAME_PRODUCT_NAME)));
		//bundle.putString(FoodAmountDialog.FOOD_URI, 
		//		MyDietProvider.FOODS_URI + "/" + cursor.getInt(cursor.getColumnIndex(Foods._ID)));
		bundle.putInt(FoodAmountDialog.FOOD_URI, cursor.getInt(cursor.getColumnIndex(Foods._ID)));

		DialogFragment dialog = new FoodAmountDialog();
		dialog.setArguments(bundle);
		dialog.show(this.getFragmentManager(), "FoodAmountDialog");
	}

	@Override
	public void onDialogPositiveClick(int food_id, CharSequence food_amount) {
		Intent intent = new Intent();
		intent.putExtra("amount", food_amount.toString());
		intent.putExtra("food_uri", MyDietProvider.FOODS_URI + "/" + food_id);
		this.setResult(RESULT_OK, intent);

		this.finish();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}
}
