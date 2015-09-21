package com.example.clock;

import android.app.Activity;
import android.os.Bundle;

public class SettingsInterfaceActivity extends Activity {
	SettingsInterface SIF;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SIF = new SettingsInterface(this);
		setContentView(SIF);
	}

	@Override
	public void onPause() {
		super.onPause();
		SIF.pause();
	}

	@Override
	public void onResume() {
		super.onResume();
		SIF.resume();
	}

}
