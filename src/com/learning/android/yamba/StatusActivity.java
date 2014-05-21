package com.learning.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class StatusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.new_activity_status);
		
		// check if this activity was created before
		if (savedInstanceState == null) {
			// create a fragment
			StatusFragment fragment = new StatusFragment();
			getFragmentManager()
				.beginTransaction()
				.add(android.R.id.content,  fragment, fragment.getClass().getSimpleName())
				.commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}
	
}
