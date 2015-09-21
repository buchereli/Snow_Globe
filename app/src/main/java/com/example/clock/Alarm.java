package com.example.clock;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Alarm {
	private int x, y;
	private int radius;
	private int screenWidth, screenHeight;
	private int globeRadius;
	private boolean clicked = false;
	private long time;
	
	public Alarm(int iradius, int iglobeRadius, int iscreenWidth, int iscreenHeight){
		radius = ImageManager.images.get("alarm").getWidth()/2;
		globeRadius = iglobeRadius;
		screenWidth = iscreenWidth;
		screenHeight = iscreenHeight;
	}

	public void setX(int value){
		x = value;
	}
	
	public void setY(int value){
		y = value;
	}
	
	public void setRadius(int value){
		radius = value;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void setWidth(int value){
		screenWidth = value;
	}
	
	public void setHeight(int value){
		screenHeight = value;
	}
	
	public void setGlobeRadius(int value){
		globeRadius = value;
	}
	
	public void setBasedOnTime(){
		x = (int) (screenWidth/2-Math.sin(Info.getPercent()*2*3.1415926535)*globeRadius+.5);
		y = (int) (screenHeight/2+Math.cos(Info.getPercent()*2*3.1415926535)*globeRadius+.5);
	}
	
	public void setBasedOnAlarmTime(){
		x = (int) (screenWidth/2-Math.sin(((time%86400000)/86400000.0)*2*3.1415926535)*globeRadius+.5);
		y = (int) (screenHeight/2+Math.cos(((time%86400000)/86400000.0)*2*3.1415926535)*globeRadius+.5);
	}
	
	public void setBasedOnpercent(double percent){
		x = (int) (screenWidth/2-Math.sin(percent*2*3.1415926535)*globeRadius+.5);
		y = (int) (screenHeight/2+Math.cos(percent*2*3.1415926535)*globeRadius+.5);
	}
	
	public void setBasedOnLocation(double px, double py){
		double slope = (screenHeight/2.0-py)/(screenWidth/2.0-px);
		double angle = Math.atan(slope);
		
		angle += Math.PI/2.0;
		if(px <= screenWidth/2)
			angle += Math.PI;
		
		x = (int) (screenWidth/2+Math.sin(angle)*globeRadius+.5);
		y = (int) (screenHeight/2-Math.cos(angle)*globeRadius+.5);
	}
	
	public double getPercentBasedOnLocation(double px, double py){
		double slope = (screenHeight/2.0-py)/(screenWidth/2.0-px);
		double angle = Math.atan(slope);
		angle += Math.PI/2.0;
		if(px > screenWidth/2)
			angle += Math.PI;
		
		return angle/(2.0*Math.PI);
	}
	
	public void setClicked(boolean value){
		clicked = value;
	}

	public boolean isClicked(){
		return clicked;
	}
	
	public boolean isPointContained(int px, int py){
		if(radius >= Math.sqrt((x-px)*(x-px)+(y-py)*(y-py)))
			return true;
		return false;
	}
	
	// Returns time in seconds
	public int timeBasedOnLocation(){
		double percent = getPercentBasedOnLocation(x, y);
		if(percent < Info.getPercent())
			percent += 1;
		return (int)(percent * 86400 + .5);
	}
	
	// Returns total time in milli secs
	public long totalTimeBasedOnLocation(){
		long time = timeBasedOnLocation();
//		time -= Info.getTimeInSeconds();
		time *= 1000;
		time += Info.getTotalTime()-Info.getTimeInSeconds()*1000;
		System.err.println((Info.getTotalTime()-Info.getTimeInSeconds()*1000)%86400000);
		return time;
	}
	
	public void setTime(long value){
		time = value;
	}
	
	public int getGlobeRadius(){
		return globeRadius;
	}
	
	public int getWidth(){
		return screenWidth;
	}
	
	public int getHeight(){
		return screenHeight;
	}
	
	public void draw(Canvas c, Paint p){
		c.drawBitmap(ImageManager.images.get("alarm"), x-radius, y-radius, p);
	}
        
}
