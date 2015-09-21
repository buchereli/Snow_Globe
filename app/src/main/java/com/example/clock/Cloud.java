package com.example.clock;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Cloud {
	private double x, y;
	private double vx;
	private int type;
	private int screenWidth;
	private int cloudWidth;
	private boolean speed;
	
	public Cloud(double ix, double iy, int itype, int iscreenWidth){
		x = ix;
		y = iy;
		
		type = itype;
		
		vx = (Math.random()*2+.5) * G.scale;
		
		if(x > 0)
			vx = -vx;
		
		screenWidth = iscreenWidth;
		cloudWidth = ImageManager.images.get("cloud"+type).getWidth();
		if(ix == -1)
			x = -cloudWidth;
		
		speed = false;
	}
	
	public boolean move(){
		if(speed){
			vx += (vx/Math.abs(vx))*G.scale;
			if(Math.abs(vx) > 10.0*G.scale)
				vx = (vx/Math.abs(vx))*10.0*G.scale;
		}
		else if(x+cloudWidth > screenWidth)
			x += vx*10.0*G.scale*(x-screenWidth+cloudWidth)/cloudWidth;
		else if(x < 0)
			x -= vx*10.0*G.scale*x/cloudWidth;
		x += vx;
		
		if(vx > 0 && x > screenWidth)
			return true;
		if(vx < 0 && x < -cloudWidth)
			return true;
		return false;
	}
	
	public void draw(Canvas c, Paint p){
		c.drawBitmap(ImageManager.images.get("cloud"+type), (int)x, (int)y, p);
	}
	
	public void speedUp(){
		speed = true;
	}
	
}
