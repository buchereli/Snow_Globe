package com.example.clock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class ImageManager {
	static Map<String, Bitmap> images = new HashMap<String, Bitmap>();
	private static View sv;
	
	// Resize cases
	public static void resize(double scale){
		ArrayList<String> keySet = new ArrayList<String>();
		keySet.addAll(images.keySet());
		
		for(int i = 0; i < keySet.size(); i++)
			images.put(keySet.get(i), resizeBitmap(keySet.get(i), scale));
	}
	
	public static void resize(String value, double scale){
		images.put(value, resizeBitmap(value, scale));
	}
	
	public static void resize(ArrayList<String> keySet, double scale){
		for(int i = 0; i < keySet.size(); i++)
			images.put(keySet.get(i), resizeBitmap(keySet.get(i), scale));
	}
	
	// Resize image
	private static Bitmap resizeBitmap(String value, double scale){
		return Bitmap.createScaledBitmap(images.get(value), (int)(images.get(value).getWidth()*scale+.5), (int)(images.get(value).getHeight()*scale+.5), false);
	}
	
	// Load cases
	public static void load(){
		ArrayList<String> keySet = new ArrayList<String>();
		keySet.addAll(images.keySet());
		
		for(int i = 0; i < keySet.size(); i++)
			images.put(keySet.get(i), loadBitmap(keySet.get(i)));
	}
	
	public static void load(String value){
		images.put(value, loadBitmap(value));
	}
	
	public static void load(ArrayList<String> keySet){
		for(int i = 0; i < keySet.size(); i++)
			images.put(keySet.get(i), loadBitmap(keySet.get(i)));
	}
	
	// Load image
	private static Bitmap loadBitmap(String value){
		Context context = sv.getContext();
		int resID = sv.getResources().getIdentifier(value, "drawable", context.getPackageName());
		Bitmap image = BitmapFactory.decodeResource(sv.getResources(), resID);
		if(image != null)
			System.err.println(context.getPackageName());
		return image;
	}
	
	// Recycle cases
	public static void recycle(){
		ArrayList<String> keySet = new ArrayList<String>();
		keySet.addAll(images.keySet());
		
		for(int i = 0; i < keySet.size(); i++)
			if(images.get(keySet.get(i)) != null)
				if(!images.get(keySet.get(i)).isRecycled())
					images.get(keySet.get(i)).recycle();
	}
	
	public static void recycle(String value){
		if(images.get(value) != null)
			if(!images.get(value).isRecycled())
				images.get(value).recycle();
	}
	
	public static void recycle(ArrayList<String> keySet){		
		for(int i = 0; i < keySet.size(); i++)
			if(images.get(keySet.get(i)) != null)
				if(!images.get(keySet.get(i)).isRecycled())
					images.get(keySet.get(i)).recycle();
	}
	
	// Set view
	public static void setView(View isv) {
		sv = isv;
	}
}
