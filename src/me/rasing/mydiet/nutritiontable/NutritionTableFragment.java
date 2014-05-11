package me.rasing.mydiet.nutritiontable;

import me.rasing.mydiet.R;
import me.rasing.mydiet.util.MyDietProvider;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NutritionTableFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int LOADER_ID = 1;
	private SimpleCursorAdapter mAdapter;
	private ListView listView;
	private static final String[] PROJECTION = new String[] {
		Foods.COLUMN_NAME_PRODUCT_NAME,
		Foods._ID
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.diary_fragment_listview, container, false);

		int[] viewIDs = new int[] {
				R.id.position,
		};
		
		mAdapter = new SimpleCursorAdapter(
				getActivity(),
				R.layout.nutritiontable_fragment_listitem,
				null,
				PROJECTION,
				viewIDs,
				0);

		listView = (ListView) rootView.findViewById(R.id.diaryentries);
		listView.setAdapter(mAdapter);

		LoaderManager lm = getLoaderManager();
	    lm.initLoader(LOADER_ID, null, this);
	    
        return rootView;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(
				this.getActivity(),
				MyDietProvider.FOODS_URI,
				PROJECTION,
				null,
				null,
				null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor); //swap the new cursor in.
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	public void setOnItemClickListener(
			NutritionTableActivity activity) {
		listView.setOnItemClickListener(activity);
	}
}
