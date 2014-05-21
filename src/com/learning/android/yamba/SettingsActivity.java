package com.learning.android.yamba;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
			return;
		
		SettingsFragment fragment = new SettingsFragment();
		getFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
					.commit();
	}
}
