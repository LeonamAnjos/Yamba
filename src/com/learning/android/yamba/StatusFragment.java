package com.learning.android.yamba;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatusFragment extends Fragment implements OnClickListener {

	private static final String TAG = "StatusActivity";
	
	private EditText editStatus;
	private Button buttonTweet;
	private TextView textCount;
	private int defaultTextColor;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_status, container, false);
		
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_status);
		
		editStatus = (EditText)view.findViewById(R.id.editStatus);
		textCount = (TextView)view.findViewById(R.id.textCount);
		buttonTweet = (Button)view.findViewById(R.id.buttonTweet);
		
		buttonTweet.setOnClickListener(this);
		
		defaultTextColor = textCount.getTextColors().getDefaultColor();
		editStatus.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				int count = 140 - editStatus.length();
				textCount.setText(Integer.toString(count));
				textCount.setTextColor(Color.GREEN);
				if(count<10)
					textCount.setTextColor(Color.RED);
				else
					textCount.setTextColor(defaultTextColor);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return view;
		
	}

/*	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
*/
	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		Log.d(TAG, "onClicked with status: " + status);
	}

}
