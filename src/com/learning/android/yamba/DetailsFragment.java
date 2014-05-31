package com.learning.android.yamba;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	private TextView textUser;
	private TextView textCreateAt;
	private TextView textMessage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_item, null, false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
