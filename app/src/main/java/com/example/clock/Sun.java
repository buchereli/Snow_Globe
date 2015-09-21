package com.example.clock;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Sun {
	private static int x, y;
	private static int radius = ImageManager.images.get("sun").getWidth()/2;
	private static int screenWidth, screenHeight;
	private static int globeRadius;
	private static boolean clicked = false;
	
	public static void setX(int value){
		x = value;
	}
	
	public static void setY(int value){
		y = value;
	}
	
	public static void setRadius(int value){
		radius = value;
	}
	
	public static int getX(){
		return x;
	}
	
	public static int getY(){
		return y;
	}
	
	public static int getRadius(){
		return radius;
	}
	
	public static void setWidth(int value){
		screenWidth = value;
	}
	
	public static void setHeight(int value){
		screenHeight = value;
	}
	
	public static void setGlobeRadius(int value){
		globeRadius = value;
	}
	
	public static int getGlobeRadius(){
		return globeRadius;
	}
	
	public static void setBasedOnTime(){
		x = (int) (screenWidth/2-Math.sin(Info.getPercent()*2*3.1415926535)*globeRadius+.5);
		y = (int) (screenHeight/2+Math.cos(Info.getPercent()*2*3.1415926535)*globeRadius+.5);
	}
	
	public static void setBasedOnPercent(double percent){
		x = (int) (screenWidth/2-Math.sin(percent*2*3.1415926535)*globeRadius+.5);
		y = (int) (screenHeight/2+Math.cos(percent*2*3.1415926535)*globeRadius+.5);
	}
	
	public static void setBasedOnLocation(double px, double py){
		double slope = (screenHeight/2.0-py)/(screenWidth/2.0-px);
		double angle = Math.atan(slope);
		
		angle += Math.PI/2.0;
		if(px <= screenWidth/2)
			angle += Math.PI;
		
		x = (int) (screenWidth/2+Math.sin(angle)*globeRadius+.5);
		y = (int) (screenHeight/2-Math.cos(angle)*globeRadius+.5);
	}
	
	public static double getPercentBasedOnLocation(double px, double py){
		double slope = (screenHeight/2.0-py)/(screenWidth/2.0-px);
		double angle = Math.atan(slope);
		angle += Math.PI/2.0;
		if(px > screenWidth/2)
			angle += Math.PI;
		
		return angle/(2.0*Math.PI);
	}
	
	public static void setClicked(boolean value){
		clicked = value;
	}

	public static boolean isClicked(){
		return clicked;
	}
	
	public static boolean isPointContained(int px, int py){
		if(radius >= Math.sqrt((x-px)*(x-px)+(y-py)*(y-py)))
			return true;
		return false;
	}
	
	// Returns time in seconds
	public static int timeBasedOnLocation(){
		double percent = getPercentBasedOnLocation(x, y);
		if(percent < Info.getPercent())
			percent += 1;
		return (int)(percent * 86400 + .5);
	}
	
	public static void moveToCurrent(double rate){
		
		double percent = getPercentBasedOnLocation(x, y);
		double infoPercent = Info.getPercent();
		
		if(!(Math.abs(percent-infoPercent) < .01)){
			if(Math.abs(percent-infoPercent) < .5){
				if(percent-infoPercent > 0){
					if(Math.abs(percent-infoPercent) < rate){
						setBasedOnPercent(infoPercent);
					} else
						setBasedOnPercent(percent-rate);
				}
				else if(Math.abs(percent-infoPercent) < rate){
					setBasedOnPercent(infoPercent);
				} else
					setBasedOnPercent(percent+rate);
			}
			else 
				if(percent-infoPercent < 0){
					if(Math.abs(percent-infoPercent) < rate){
						setBasedOnPercent(infoPercent);
					} else
						setBasedOnPercent(percent-rate);
				}
				else if(Math.abs(percent-infoPercent) < rate){
					setBasedOnPercent(infoPercent);
				} else
					setBasedOnPercent(percent+rate);
		}
				
	}
	
	public static void draw(Canvas c, Paint p){
		c.drawBitmap(ImageManager.images.get("sun"), x-radius, y-radius, p);
	}
}
