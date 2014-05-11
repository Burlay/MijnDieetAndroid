package me.rasing.mydiet.diary;

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

public class DiaryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final int LOADER_ID = 1;
	private SimpleCursorAdapter mAdapter;
	private static final String[] PROJECTION = new String[] {
		Entries.COLUMN_NAME_DATE_OF_MEAL,
		EntriesFoods.COLUMN_NAME_CALORIES,
		Entries._ID
	};
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.diary_fragment_listview, container, false);

		int[] viewIDs = new int[] {
				R.id.position,
				R.id.calories,
		};
		
		mAdapter = new SimpleCursorAdapter(
				getActivity(),
				R.layout.diary_diaryentry_listitem,
				null,
				PROJECTION,
				viewIDs,
				0);

		ListView listView = (ListView) rootView.findViewById(R.id.diaryentries);
		listView.setAdapter(mAdapter);

		listView.setEmptyView(rootView.findViewById(R.id.empty));
		
		LoaderManager lm = getLoaderManager();
	    lm.initLoader(LOADER_ID, null, this);
		
        return rootView;
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(
			this.getActivity(),
			MyDietProvider.ENTRIES_URI,
			PROJECTION,
			null,
			null,
			Entries.COLUMN_NAME_DATE_OF_MEAL);
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
}
