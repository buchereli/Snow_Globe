package com.example.clock;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Button {
	private Bitmap image, clickedImage;
	private int x, y;
	private int w, h;
	private boolean clicked;
	private Rect button;
	
	public Button(Bitmap iimage, Bitmap iclickedImage, int ix, int iy){
		image = iimage;
		clickedImage = iclickedImage;
		
		x = ix;
		y = iy;
		w = image.getWidth();
		h = image.getHeight();
		
		button = new Rect(x, y, x+w, y+h);
		
		clicked = false;
	}
	
	public boolean containsPoints(int px, int py){
		button = new Rect(x, y, x+w, y+h);
		return button.contains(px, py);
	}
	
	public boolean isClicked(){
		return clicked;
	}
	
	public void setClicked(boolean value){
		clicked = value;
	}
	
	public Bitmap getImage(){
		if(clicked)
			return clickedImage;
		return image;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return w;
	}
	
	public int getHeight(){
		return h;
	}
	
	public void setY(int value){
		y = value;
	}
	
	public void setX(int value){
		x = value;
	}
	
	public Rect getRect(){
		button = new Rect(x, y, x+w, y+h);
		return button;
	}
}