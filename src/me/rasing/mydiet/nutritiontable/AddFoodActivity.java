package me.rasing.mydiet.nutritiontable;

import java.util.UUID;

import me.rasing.mydiet.R;
import me.rasing.mydiet.util.MyDietProvider;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AddFoodActivity extends Activity {
	private AddFoodFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfood);
		
		fragment = new AddFoodFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment)
				.commit();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		case R.id.action_save_food:
			ContentValues values = new ContentValues();
			values.put(Foods.COLUMN_NAME_GUID, UUID.randomUUID().toString());
			values.put(Foods.COLUMN_NAME_PRODUCT_NAME, this.fragment.getProductName());
			values.put(Foods.COLUMN_NAME_CALORIES, this.fragment.getCalories());
			this.getContentResolver().insert(MyDietProvider.FOODS_URI, values);
			
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.addfood, menu);
	    return true;
    }
}
