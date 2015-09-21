package com.example.clock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

@SuppressLint("ClickableViewAccessibility")
public class Display extends SurfaceView implements Runnable, OnTouchListener {

	// Stuff
	private Thread t;
	boolean ok;
	private SurfaceHolder holder;
	private long frameHolder, frame;
	private boolean getFrame;
	private boolean debug;
	private boolean firstRun;
	private boolean click;
	
	// Time to display
	private int displayTime;
	private String roundTimeTo;

	// Alarms
	private ArrayList<Alarm> alarms;
	
	// Touch locations
	private int msx, msy, mcx, mcy, mex, mey;

	// Screen height and width holders
	private int hgt, wid;

	// Background
	private Bitmap background;
	private ArrayList<Cloud> clouds;

	// Random variable
	private Random r;

	public Display(Context context) {
		super(context);

		// Set Format
		getHolder().setFormat(PixelFormat.RGBA_8888);

		// Load sounds/ music
		//sound = MediaPlayer.create(getContext(), R.raw.sound);

		// Backgrounds
		//background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		
		// Images
		ArrayList<String> images = new ArrayList<String>(
				Arrays.asList("sun", "alarm", "overcast", "cloud1", "cloud2", "cloud3", "cloud4", "cloud5"));
		ImageManager.load(images);
		images.remove(0);
		images.remove(0);
		ImageManager.resize(images, 6.0);
		ImageManager.resize("alarm", 3.0);
		ImageManager.resize("sun", 6.0);
		
		// CLOUDS
		clouds = new ArrayList<Cloud>();

		
		// Stuff
		firstRun = true;
		debug = false;
		frame = 0;
		frameHolder = 0;
		getFrame = false;
		ok = false;
		t = null;
		r = new Random();
		holder = getHolder();
		setOnTouchListener(this);
		
		G.context = this.getContext();
		G.updateLatLon();
		G.getWeatherUpdates();
		alarms = G.getAlarms();
	}

	@Override
	public void run() {
		while (ok == true) {
			if (!(holder.getSurface().isValid()))
				continue;
			
			if(Info.getTotalTime() > G.weatherUpdateTime + 300000)
				G.getWeatherUpdates();
			
			// Paint stuff
			Paint p = new Paint();
			Canvas c = holder.lockCanvas();
			
			// First run
			if(firstRun){
				// Temp
				MainButton.init(c.getWidth(), c.getHeight());
				
				// Sun init
				Sun.setWidth(c.getWidth());
				Sun.setHeight(c.getHeight());
				Sun.setGlobeRadius(c.getWidth()*4/9);
				Sun.setBasedOnTime();
				
				// Save width and height to variables
				wid = c.getWidth();
				hgt = c.getHeight();
				
				// Background stuff
				//background = (Bitmap.createScaledBitmap(background, c.getWidth(), c.getHeight(), true));
				
//				for(int i = 0; i < 6; i++);
//					if(r.nextBoolean())
//						clouds.add(new Cloud(r.nextInt(c.getWidth()+200)-200, 525+r.nextInt(100), r.nextInt(5)+1, c.getWidth()));
//					else
//						clouds.add(new Cloud(r.nextInt(c.getWidth()+200)-200, 525+r.nextInt(100), r.nextInt(5)+1, c.getWidth()));
				
				WeatherDisplay.init();
					
				firstRun = false;
			}
			
			if(!Sun.isClicked() && !MainButton.isDayPlaying())
				Sun.moveToCurrent(.02);
			
			displayTime = getTimeToDisplay();
			
			// Set transparency
			p.setAlpha(255);
			p.setAntiAlias(true);

			// Font stuff
			//Typeface font = Typeface.createFromAsset(this.getContext().getAssets(), "arlrdbd.ttf");
			//p.setTypeface(font);
			p.setTextSize(32);
			p.setTextAlign(Align.CENTER);
			
			//c.drawBitmap(background, 0, 0, p);
			c.drawRect(0, 0, c.getWidth(), c.getHeight(), p);
			
			
			// Move sun with finger to see future weather
			// Draw snow globe
			p.setColor(Color.rgb(0, 224, 7));
			c.drawCircle(c.getWidth()/2, c.getHeight()/2, c.getWidth()*4/9, p);
			p.setColor(Color.rgb(0, 102, 255));
			c.drawRect(0, 0, c.getWidth(), c.getHeight()/2, p);
			p.setColor(Color.WHITE);
			p.setStyle(Style.STROKE);
			p.setStrokeWidth(4);
			c.drawCircle(c.getWidth()/2, c.getHeight()/2, c.getWidth()*4/9, p);
			p.setStyle(Style.FILL_AND_STROKE);
			
			// Draw sun
			Sun.draw(c, p);
			
			// Draw Clouds
//			int numOfClouds = (int)(Integer.parseInt(Info.predictWeatherAt("cloudcover", Sun.timeBasedOnLocation(), false))/5.0+.5);
//			if(clouds.size() < numOfClouds)
//				if(r.nextBoolean())
//					clouds.add(new Cloud(-1, 525+r.nextInt(100), r.nextInt(5)+1, c.getWidth()));
//				else
//					clouds.add(new Cloud(c.getWidth(), 525+r.nextInt(100), r.nextInt(5)+1, c.getWidth()));
//			for(int i = 0; i < clouds.size(); i++){
//				clouds.get(i).draw(c, p);
//				if(clouds.get(i).move()){
//					clouds.remove(i);
//					i--;
//				}
//			}
			WeatherDisplay.draw(c, p);
			
			// Draw alarms
			p.setColor(Color.RED);
			for(int i = 0; i < alarms.size(); i++)
				alarms.get(i).draw(c, p);
		
			// MainButton
			MainButton.moveButton();
			if(MainButton.isDayPlaying()){
				MainButton.changePlayPercent(.0015);
			}
			
			// Main button
			MainButton.draw(p, c);
			
			// Display Time
			p.setColor(Color.WHITE);
			p.setTextSize(170);
			p.setTextAlign(Align.CENTER);
			c.drawText("   "+TimeToDate.getDay(displayTime), c.getWidth()/2, c.getHeight()/2+c.getWidth()*4/18-10, p);
			p.setTextAlign(Align.RIGHT);
			p.setTextSize(70);
			c.drawText(TimeToDate.getMonth(displayTime)+"  ", c.getWidth()/2, c.getHeight()/2+c.getWidth()*4/18-10, p);
			c.drawText(TimeToDate.getDayOfWeek(displayTime)+"  ", c.getWidth()/2, c.getHeight()/2+c.getWidth()*4/18-70-10, p);
			p.setTextAlign(Align.CENTER);
			
			c.drawText(TimeToDate.getRoundTime(displayTime, roundTimeTo), c.getWidth()/2, c.getHeight()/2+c.getWidth()*5/18+10, p);
			
//			if(Sun.isClicked() || MainButton.isDayPlaying()){
//				c.drawText(Info.predictWeatherAt("tempF", Sun.timeBasedOnLocation(), false), c.getWidth()/2, c.getHeight()/2-c.getWidth()*5/18, p);
//				c.drawText(Info.predictWeatherAt("weatherDesc", Sun.timeBasedOnLocation(), true), c.getWidth()/2, c.getHeight()/2-c.getWidth()*5/18+70, p);
//			}
//			else{
//				c.drawText(Info.getWeatherInfo("current_weather", "tempF"), c.getWidth()/2, c.getHeight()/2-c.getWidth()*5/18, p);
//				c.drawText(Info.getWeatherInfo("current_weather", "weatherDesc"), c.getWidth()/2, c.getHeight()/2-c.getWidth()*5/18+70, p);
//			}
			c.drawText(Info.predictWeatherAt("chanceofrain", Sun.timeBasedOnLocation(), false), c.getWidth()/2, c.getHeight()/2-c.getWidth()*5/18+70, p);
			c.drawText(Info.predictWeatherAt("cloudcover", Sun.timeBasedOnLocation(), false), c.getWidth()/2, c.getHeight()/2-c.getWidth()*5/18, p);

			
			// Debug
			if (debug) {
				
				// Frame rate
				if (getFrame) {
					frame = SystemClock.elapsedRealtime();
					getFrame = false;
				}
				p.setStrokeWidth(1);
				p.setTextAlign(Align.LEFT);
				p.setColor(Color.GREEN);
				p.setTextSize(72);
				c.drawText("" + (int) (1000.0 / (frame - frameHolder)), 10, 75, p);
				if (r.nextInt(25) == 1) {
					frameHolder = SystemClock.elapsedRealtime();
					getFrame = true;
				}
				
			}

			// Draw Canvas
			holder.unlockCanvasAndPost(c);
		}
	}

	// Pause
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

	// Resume
	public void resume() {
		ok = true;
		t = new Thread(this);
		t.start();
	}

	@SuppressLint("NewApi")
	// On Touch events
	public boolean onTouch(View v, MotionEvent me) {

		int action = me.getAction();

		if (action == MotionEvent.ACTION_DOWN) {
			
			msx = (int) me.getX();
			msy = (int) me.getY();

			mcx = (int) me.getX();
			mcy = (int) me.getY();
			
			click = true;
			
			// Set Clicked
			MainButton.setClicked(MainButton.containsPoint(msx, msy));
			
			// Check if clicked
			if(!MainButton.isDayPlaying())
				Sun.setClicked(Sun.isPointContained(msx, msy));
			
			MainButton.setButton("play");
			for(int i = 0; i < alarms.size(); i++)
				if(alarms.get(i).isPointContained(msx, msy)){
					alarms.get(i).setClicked(true);
					MainButton.setButton("remove");
					break;
				}
			
		} else if (action == MotionEvent.ACTION_UP) {
			
			mex = (int) me.getX();
			mey = (int) me.getY();
			
			// Check Play Button
			if(MainButton.containsPoint(mex, mey)){
				if(MainButton.getButton().equals("play"))
					MainButton.setDayPlaying(true);
				else
					for(int i = 0; i < alarms.size(); i++)
						if(alarms.get(i).isClicked()){
							alarms.remove(i);
							G.saveAlarms(alarms);
							break;
						}
			}
			
			// Check to add
			clickCheckBreak:
			if(click)
				if(!Sun.isPointContained(mex, mey)){
					for(int i = 0; i < alarms.size(); i++)
						if(alarms.get(i).isClicked())
							break clickCheckBreak;
					if(Sun.getGlobeRadius() + Sun.getRadius() >
							Math.sqrt(((mex-wid/2)*(mex-wid/2)+(mey-hgt/2)*(mey-hgt/2))) 
							&& Sun.getGlobeRadius() - Sun.getRadius() <
							Math.sqrt(((mex-wid/2)*(mex-wid/2)+(mey-hgt/2)*(mey-hgt/2)))){
						
						alarms.add(new Alarm(Sun.getRadius(), Sun.getGlobeRadius(), wid, hgt));
						alarms.get(alarms.size()-1).setBasedOnLocation(msx, msy);
						G.saveAlarms(alarms);
					}
						
				}
			
			// Set Clicked false
			for(int i = 0; i < alarms.size(); i++)
				if(alarms.get(i).isClicked()){
					alarms.get(i).setClicked(false);
					G.saveAlarms(alarms);
				}
			MainButton.setClicked(false);
			MainButton.setButton("play");
			
			// Set clicked false
			Sun.setClicked(false);

		} else if (action == MotionEvent.ACTION_MOVE) {
			
			mcx = (int) me.getX();
			mcy = (int) me.getY();
			
			// Set Clicked
			MainButton.setClicked(MainButton.containsPoint(mcx, mcy));
							
			// Move Sun
			if(Sun.isClicked())
				Sun.setBasedOnLocation(mcx, mcy);	
			
			// Move Alarms
			for(int i = 0; i < alarms.size(); i++)
				if(alarms.get(i).isClicked()){
					alarms.get(i).setBasedOnLocation(mcx, mcy);
					break;
				}
			
			if(click)
				if(Math.abs(mcx-msx) > 10 || Math.abs(mcy-msy) > 10)
					click = false;

		}
		return true;
	}

	// Returns arraylist<string> of each line in file
	public ArrayList<String> loadFile(String fileName) {
		String bufferLine;

		Context context = this.getContext();
		String path = context.getFilesDir().getPath() + "/";
		
		ArrayList<String> file = new ArrayList<String>();

		try {
			InputStream is = context.getAssets().open(fileName);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader ir = new BufferedReader(isr);
			while ((bufferLine = ir.readLine()) != null) {
				file.add(bufferLine);
			}

		} catch (FileNotFoundException ex) {
			System.err.println("Unable to open file '" + path + fileName + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}

	public Display(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public int getTimeToDisplay(){
		roundTimeTo = "hour";
		if(Sun.isClicked() || MainButton.isDayPlaying())
			return Sun.timeBasedOnLocation();
		
		roundTimeTo = "minute";
		for(int i = 0; i < alarms.size(); i++)
			if(alarms.get(i).isClicked())
				return alarms.get(i).timeBasedOnLocation();
		return Info.getTimeInSeconds();
	}
}
