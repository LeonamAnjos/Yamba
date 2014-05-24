package com.learning.android.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class RefreshService extends IntentService {
	
	public RefreshService() {
		super(TAG);
	}

	static final String TAG = "RefreshService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreated");
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
		
		
	}
	
	

}
