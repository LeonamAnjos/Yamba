package com.learning.android.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class RefreshService extends IntentService {
	
	static final String TAG = "RefreshService";
	
	public RefreshService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreated");
	}

	// Worker thread
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent");
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		
		if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Log.d(TAG, "Invalid username or password: " + username + "/" + password);
			return;
		}
		
		Log.d(TAG, username + "/" + password);
		Log.d(TAG, "out onHandleIntent");
		
		
		insertByDatabaseDirectly();

		
		
		
		Log.d(TAG, "onHandleIntent DONE!");
	}

	private void insertByDatabaseDirectly() {
		DbHelper dbHelper = new DbHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		// server connection
		
		List<StatusEntity> statusEntities = StatusEntity.getSomeInstances(20);
		
		for (StatusEntity statusEntity : statusEntities) {
			values.clear();
			values.put(StatusContract.Column.ID, statusEntity.getId());
			values.put(StatusContract.Column.USER, statusEntity.getUser());
			values.put(StatusContract.Column.MESSAGE, statusEntity.getMessage());
			values.put(StatusContract.Column.CREATED_AT, statusEntity.getCreatedAt());
			
			db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
			
			Log.d(TAG, "StatusEntity.ID = " + statusEntity.getId());
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return super.onBind(intent);
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStarted");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}


}
