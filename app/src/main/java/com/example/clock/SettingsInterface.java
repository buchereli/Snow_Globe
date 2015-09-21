package com.example.clock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SettingsInterface extends SurfaceView implements Runnable, OnTouchListener {

	// Stuff
	private Thread t;
	boolean ok;
	private SurfaceHolder holder;
	private long frameHolder, frame;
	private boolean getFrame;
	private boolean debug;

	// Touch locations
	private int msx, msy, mcx, mcy, mex, mey;

	// Screen height and width holders
	private int hgt, wid;

	// Background
	private Bitmap background;

	// Random variable
	private Random r;

	//Saving
	SharedPreferences mSettings;

	public SettingsInterface(Context context) {
		super(context);

		// Saved data stuff
		mSettings = context.getSharedPreferences("Motivational Alarm Data", 0);
		String category = mSettings.getString("Settings", null);

		// Set Format
		getHolder().setFormat(PixelFormat.RGBA_8888);

		// Load sounds/ music
		//sound = MediaPlayer.create(getContext(), R.raw.sound);

		// Backgrounds
		//background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

		// Stuff
		debug = true;
		frame = 0;
		frameHolder = 0;
		getFrame = false;
		ok = false;
		t = null;
		r = new Random();
		holder = getHolder();
		setOnTouchListener(this);

	}

	@Override
	public void run() {
		while (ok == true) {
			if (!(holder.getSurface().isValid()))
				continue;

			// Paint stuff
			Paint p = new Paint();
			Canvas c = holder.lockCanvas();
			
			// Set transparency
			p.setAlpha(255);

			// Font stuff
//			Typeface font = Typeface.createFromAsset(this.getContext().getAssets(), "arlrdbd.ttf");
//			p.setTypeface(font);
			p.setTextSize(32);
			p.setTextAlign(Align.LEFT);
			
			// Background stuff
//			background = (Bitmap.createScaledBitmap(background, c.getWidth(), c.getHeight(), true));
//			c.drawBitmap(background, 0, 0, p);
			
			// Save width and height to variables
			wid = c.getWidth();
			hgt = c.getHeight();
			
			// Debug
			if (debug) {
				
				// Frame rate
				if (getFrame) {
					frame = SystemClock.elapsedRealtime();
					getFrame = false;
				}
				p.setColor(Color.GREEN);
				p.setTextSize(72);
				c.drawText("" + (int) (1000.0 / (frame - frameHolder)), 10, 75, p);
				if (r.nextInt(10) == 1) {
					frameHolder = SystemClock.elapsedRealtime();
					getFrame = true;
				}
				
			}

			// Draw Canvas
			holder.unlockCanvasAndPost(c);
		}
	}

	public void pause() {
		ok = false;
		while (true) {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		t = null;
	}

	public void resume() {
		ok = true;
		t = new Thread(this);
		t.start();
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	// On Touch events
	public boolean onTouch(View v, MotionEvent me) {

		int action = me.getAction();

		if (action == MotionEvent.ACTION_DOWN) {
			
			msx = (int) me.getX();
			msy = (int) me.getY();

			mcx = (int) me.getX();
			mcy = (int) me.getY();
			
		} else if (action == MotionEvent.ACTION_UP) {
			
			mex = (int) me.getX();
			mey = (int) me.getY();

		} else if (action == MotionEvent.ACTION_MOVE) {
			
			mcx = (int) me.getX();
			mcy = (int) me.getY();

		}
		return true;
	}
	
	// Let user set location
	public void setLocation(){
		
	}
	
	// Set sun play speed
	public void setSunSpeed(){
		
	}
}
