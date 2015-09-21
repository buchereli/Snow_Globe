package com.example.clock;

import android.app.Activity;
import android.os.Bundle;

public class AlarmInterfaceActivity extends Activity {
	AlarmInterface AIF;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AIF = new AlarmInterface(this);
		setContentView(AIF);
	}

	@Override
	public void onPause() {
		super.onPause();
		AIF.pause();
	}

	@Override
	public void onResume() {
		super.onResume();
		AIF.resume();
	}

}
