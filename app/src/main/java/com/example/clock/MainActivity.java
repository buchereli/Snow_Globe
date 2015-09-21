package com.example.clock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	// Splash screen duration
	private int SPLASH_TIME_OUT = 500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		G.context = getBaseContext();
		String weather = G.get("weather", " ");
		if(weather != null && !weather.equals(" "))
			Info.updateWeatherInfo(TextFormat.threeHour(weather));
		
		double lat = G.get("latitude", 0.0);
		if(lat != 0.0)
			G.lat = lat;
		
		double lon = G.get("longitude", 0.0);
		if(lon != 0.0)
			G.lon = lon;
		
		G.scale = 1.0;
		ImageManager.setView(this.findViewById(android.R.id.content));
			
		new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {
        		startActivity(new Intent("com.example.clock.DISPLAYACTIVITY"));

                finish();
            }
        }, SPLASH_TIME_OUT);
		
	}
}
