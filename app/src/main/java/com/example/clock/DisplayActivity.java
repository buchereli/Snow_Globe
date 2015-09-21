package com.example.clock;

import android.app.Activity;
import android.os.Bundle;

public class DisplayActivity extends Activity {
	Display DSP;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DSP = new Display(this);
		setContentView(DSP);
	}

	@Override
	public void onPause() {
		super.onPause();
		DSP.pause();
	}

	@Override
	public void onResume() {
		super.onResume();
		DSP.resume();
	}

}
