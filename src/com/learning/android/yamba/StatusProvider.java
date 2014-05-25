package com.learning.android.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class StatusProvider extends ContentProvider {
	
	private static final String TAG = StatusProvider.class.getSimpleName();
	private DbHelper dbHelper;

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE, StatusContract.STATUS_DIR);
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE + "/#", StatusContract.STATUS_ITEM);
	}
	
	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		Log.d(TAG, "onCreated");
		return true;
	}
	
	@Override
	public String getType(Uri uri) {
		switch(sURIMatcher.match(uri)) {
			case StatusContract.STATUS_DIR:
				Log.d(TAG, "gotType:" + StatusContract.STATUS_TYPE_DIR);
				return StatusContract.STATUS_TYPE_DIR;
			case StatusContract.STATUS_ITEM:
				Log.d(TAG, "gotType:" + StatusContract.STATUS_TYPE_ITEM);
				return StatusContract.STATUS_TYPE_ITEM;
			default:
				throw new IllegalArgumentException("Illegal uri: " + uri);
		}
	}

	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri ret = null;
		
		// Assert correct uri. For a insert, it can´t be a item type (STATUS_TYPE_ITEM)
		if (sURIMatcher.match(uri) != StatusContract.STATUS_DIR)
			throw new IllegalArgumentException("Illegal uri: " + uri);
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// Write on the database and get the ID of the new record
		long rowId = db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		
		// was insert successful?
		if (rowId != -1) {
			long id = values.getAsLong(StatusContract.Column.ID);
			ret = ContentUris.withAppendedId(uri,  id);
			Log.d(TAG, "inserted uri: " + ret);
			
			// Notify that data for this uri has changed
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		
		return ret;
	}

	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		String where;
		
		switch(sURIMatcher.match(uri)) {  // check the type of the URI
		case StatusContract.STATUS_DIR:   // dont have a ID
			// so we count updated rows
			where = selection;
			break;
		case StatusContract.STATUS_ITEM:  // have a ID - parse it to the where clause
			long id = ContentUris.parseId(uri);
			where = StatusContract.Column.ID + " = " + id + (TextUtils.isEmpty(selection) ? "" : " and ( " + selection + " ) ");
			break;
		default:
				throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.update(StatusContract.TABLE, values, where, selectionArgs);  // update the database

		// was update successful?
		if (ret>0) {
			// notify that data for this URI has chaged
			getContext().getContentResolver().notifyChange(uri, null);
		}
		Log.d(TAG, "updated records: " + ret);
		return ret;
	}
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String where;
		
		switch(sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			// so we count deleted rows
			where  = (selection == null) ? "1" : selection;
			break;
		case StatusContract.STATUS_ITEM:
			long id = ContentUris.parseId(uri);
			where = StatusContract.Column.ID + " = " + id + (TextUtils.isEmpty(selection) ? "" : " and ( " + selection + " ) ");
			break;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.delete(StatusContract.TABLE, where, selectionArgs);
		
		if (ret>0) {
			// notify that data for this uri has changed
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		Log.d(TAG, "deleted records: " + ret);
		return ret;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder(); // to make it easier to put together a potentially complex query statement
		qb.setTables(StatusContract.TABLE); // must specify what table you are working on
		
		switch(sURIMatcher.match(uri)) {  // again using the matcher
		case StatusContract.STATUS_DIR:  
			break;
		case StatusContract.STATUS_ITEM:  // contains ID - let's use the query builder
			qb.appendWhere(StatusContract.Column.ID + "=" + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		
		// specify the sort order 
		String orderBy = (TextUtils.isEmpty(sortOrder)) ? StatusContract.DEFAULT_SORT : sortOrder;
		
		SQLiteDatabase db = dbHelper.getReadableDatabase(); // opening the database for read only
		Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		
		// register for uri changes
		cursor.setNotificationUri(getContext().getContentResolver(), uri); // when the insert, update or delete notify the app that the data has changed, 
																		   // this cursor will know that it may want to refresh its data
		
		Log.d(TAG, "queried records: " + cursor.getCount());
		return cursor;
	}





}
