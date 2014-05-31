package com.learning.android.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	static final String TAG = "DbHelper";

	public DbHelper(Context context) {
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
		Log.d(TAG, String.format("DbHelper constructor"));
	}

    /**
     * Called only once first time we create the database
     * @param db The database.
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, String.format("onCreate()"));
		
		String sql = String
				.format("create table %s (%s int primary key, %s text, %s text, %s text)", 
						StatusContract.TABLE,
						StatusContract.Column.ID,
						StatusContract.Column.USER,
						StatusContract.Column.MESSAGE,
						StatusContract.Column.CREATED_AT);

		Log.d(TAG, "onCreate with SQL:" + sql);
		db.execSQL(sql);
	}

	/* 
	 * Gets called whenever existing version != mew version (schema changed)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, String.format("onUpgrade(%d, %d", oldVersion, newVersion));
	}

}
