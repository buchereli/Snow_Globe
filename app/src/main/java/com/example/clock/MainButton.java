package com.example.clock;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Canvas;
import android.graphics.Paint;

public class MainButton {
	private static Button playButton;
	private static Button removeButton;
	private static int width, height;
	private static String currentButton;
	private static Boolean playDay;
	private static double playPercent;
	
	public static void init(int iwidth, int iheight){
		width = iwidth;
		height = iheight;
		
		ArrayList<String> images = new ArrayList<String>(
				Arrays.asList("playunclicked", "playclicked", "removeunclicked", "removeclicked"));
		ImageManager.load(images);
		ImageManager.resize(images, 6.0);
		
		playButton = new Button(ImageManager.images.get("playunclicked"), ImageManager.images.get("playclicked"),
				width/2-ImageManager.images.get("playunclicked").getWidth()/2, height-ImageManager.images.get("playunclicked").getHeight());
		removeButton = new Button(ImageManager.images.get("removeunclicked"), ImageManager.images.get("removeclicked"),
				width/2-ImageManager.images.get("removeunclicked").getWidth()/2, height-ImageManager.images.get("removeunclicked").getHeight());
		
		currentButton = "play";
		
		playDay = false;
		playPercent = 0;
	}
	
	public static Button getPlayButton(){
		return playButton;
	}
	
	public static Button getRemoveButton(){
		return removeButton;
	}
	
	public static double getPlayPercent(){
		return playPercent;
	}
	
	public static void changePlayPercent(double value){
		playPercent += value;
		Sun.setBasedOnPercent(playPercent+Info.getPercent());
		if(playPercent > 1.0){
			playPercent = 0;
			playDay = false;
		}
	}
	
	public static boolean isDayPlaying(){
		return playDay;
	}
	
	public static void setDayPlaying(boolean value){
		playDay = value;
	}
	
	public static void setButton(String value){
		currentButton = value;
	}
	
	// Moves buttons to proper places
	public static void moveButton(){
		if(currentButton.equals("play")){
			if(removeButton.getY() < height)
				removeButton.setY(removeButton.getY()+removeButton.getHeight()/10);
			else if(height-playButton.getY() < playButton.getHeight()*1.25)
				playButton.setY(playButton.getY()-playButton.getHeight()/10);
		}
		else if(currentButton.equals("remove"))
			if(playButton.getY() < height)
				playButton.setY(playButton.getY()+playButton.getHeight()/10);
			else if(height-removeButton.getY() < removeButton.getHeight()*1.25)
				removeButton.setY(removeButton.getY()-removeButton.getHeight()/10);
	}
	
	public static boolean containsPoint(int px, int py){
		if(currentButton.equals("play"))
			return playButton.containsPoints(px, py);
		if(currentButton.equals("remove")){
			return removeButton.containsPoints(px, py);
		}
		return false;
	}
	
	public static String getButton(){
		return currentButton;
	}
	
	public static void setClicked(boolean value){
		playButton.setClicked(value);
		removeButton.setClicked(value);
	}
	
	public static void draw(Paint p, Canvas c){
		c.drawBitmap(getPlayButton().getImage(), getPlayButton().getX(), getPlayButton().getY(), p);
		c.drawBitmap(getRemoveButton().getImage(), getRemoveButton().getX(), getRemoveButton().getY(), p);
	}
}
