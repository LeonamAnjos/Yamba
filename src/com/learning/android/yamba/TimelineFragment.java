package com.learning.android.yamba;

import java.util.Date;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineFragment extends ListFragment implements LoaderCallbacks<Cursor> {
	static final String TAG = "TimelineFragment";
	
	private static final String[] FROM = { 
		StatusContract.Column.USER, 
		StatusContract.Column.MESSAGE,
		StatusContract.Column.CREATED_AT };
	
	private static final int[] TO = {
		R.id.list_item_text_user,
		R.id.list_item_text_message,
		R.id.list_item_text_created_at };
	
	private static final int LOADER_ID = 42;
	private SimpleCursorAdapter mAdapter;
	
//	private static final ViewBinder VIEW_BINDER = new ViewBinder() {
//		
//		@Override
//		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
//			long timestamp;
//			
//			// Custom binding
//			switch (view.getId()) {
//			case R.id.list_item_text_created_at:
//				break; 
//			}
//		}
//	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null, FROM, TO, 0);
		
		mAdapter.setViewBinder(new TimelineViewBinder());
		
		setListAdapter(mAdapter);
		
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}
	
	class TimelineViewBinder implements ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view.getId() != R.id.list_item_text_created_at)
				return false;
			
			 //Date d = new Date();
			 
			long timestamp = 1515431541;//cursor.getLong(columnIndex);
			CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(timestamp);
			((TextView)view).setText(relativeTime);
			return true;
		}
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if(id != LOADER_ID)
			return null;
		
		Log.d(TAG, "onCreateLoader");

		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI, null, null, null, StatusContract.DEFAULT_SORT);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.d(TAG, "onLoadFinished with cursor: " + data.getCount());
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
		
	}

}
