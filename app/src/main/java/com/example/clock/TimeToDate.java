package com.example.clock;

import java.util.HashMap;
import java.util.Map;

public class TimeToDate {
	
	public static String getMonth(int time){
		int day = Info.getDay();
		day += (int)(time/86400);
		if(checkDay(day))
			return Info.getMonth();
		
		if(Info.getMonth().equals("Jan"))
			return "Feb";
		if(Info.getMonth().equals("Feb"))
			return "Mar";
		if(Info.getMonth().equals("Mar"))
			return "Apr";
		if(Info.getMonth().equals("Apr"))
			return "May";
		if(Info.getMonth().equals("May"))
			return "Jun";
		if(Info.getMonth().equals("Jun"))
			return "Jul";
		if(Info.getMonth().equals("Jul"))
			return "Aug";
		if(Info.getMonth().equals("Aug"))
			return "Sep";
		if(Info.getMonth().equals("Sep"))
			return "Oct";
		if(Info.getMonth().equals("Oct"))
			return "Nov";
		if(Info.getMonth().equals("Nov"))
			return "Dec";
		
		return "Jan";
	}
	
	public static int getDay(int time){
		int day = Info.getDay();
		day += (int)(time/86400);
		if(checkDay(day))
			return day;
		return day - daysInMonth(Info.getMonth());
	}
	
	public static String getDayOfWeek(int time){
		String dayOfWeek = Info.getDayOfWeek();
		for(int days = (int)(time/86400); days > 0; days--){
			if(dayOfWeek.equals("Sun"))
				dayOfWeek = "Mon";
			else if(dayOfWeek.equals("Mon"))
				dayOfWeek = "Tue";
			else if(dayOfWeek.equals("Tue"))
				dayOfWeek = "Wed";
			else if(dayOfWeek.equals("Wed"))
				dayOfWeek = "Thu";
			else if(dayOfWeek.equals("Thu"))
				dayOfWeek = "Fri";
			else if(dayOfWeek.equals("Fri"))
				dayOfWeek = "Sat";
			else if(dayOfWeek.equals("Sat"))
				dayOfWeek = "Sun";
		}
		
		return dayOfWeek;
	}
	
	public static int getHour(int time){
		int days = (int)(time/86400);
		time = time - days * 86400;
		int hour = (int)(time/3600);
		return hour;
	}
	
	public static int getMin(int time){
		int days = (int)(time/86400);
		time = time - days * 86400;
		int hour = (int)(time/3600);
		time = time - hour * 3600;
		int min = (int)(time/60);
		return min;
	}
	
	public static int getSec(int time){
		int days = (int)(time/86400);
		time = time - days * 86400;
		int hour = (int)(time/3600);
		time = time - hour * 3600;
		int min = (int)(time/60);
		time = time - min * 60;
		int sec = time;
		return sec;
	}
	
	public static String getTime(int time){
		return getHour(time)+":"+getMin(time);//+":"+getSec(time);
	}
	
	public static String getRoundTime(int time, String roundTo){
		if(roundTo.equals("hour")){
			String roundTime = "00";
			int hour = 0;
			
			if(getMin(time) >= 30)
				hour = 1;
			
			if(getHour(time)+hour > 11 && getHour(time)+hour < 24)
				roundTime += " PM";
			else
				roundTime += " AM";
			
			if(getHour(time)+hour == 0)
				roundTime = "12:"+roundTime;
			else if(getHour(time)+hour > 12){
				roundTime = (getHour(time)+hour-12)+":"+roundTime;
			}
			else{
				roundTime = (getHour(time)+hour)+":"+roundTime;
			}
			
			return roundTime;
		}
		else{
			String roundTime = "";
		
			if(getHour(time) > 11 && getHour(time) < 24)
				roundTime += " PM";
			else
				roundTime += " AM";
			
			if(getMin(time) > 9)
				roundTime = getMin(time)+roundTime;
			else
				roundTime = "0"+getMin(time)+roundTime;
			
			if(getHour(time) == 0)
				roundTime = "12:"+roundTime;
			else if(getHour(time) > 12)
				roundTime = (getHour(time)-12)+":"+roundTime;
			else
				roundTime = getHour(time)+":"+roundTime;
			
			return roundTime;
		}
	}
	
	private static boolean checkDay(int day){
		if(day > daysInMonth(Info.getMonth()))
			return false;
		return true;
	}
	
	private static int daysInMonth(String month){
		Map<String, Integer> daysIn = new HashMap<String, Integer>();
		daysIn.put("Jan", 31);
		daysIn.put("Feb", 28);
		daysIn.put("Mar", 31);
		daysIn.put("Apr", 30);
		daysIn.put("May", 31);
		daysIn.put("Jun", 30);
		daysIn.put("Jul", 31);
		daysIn.put("Aug", 31);
		daysIn.put("Sep", 30);
		daysIn.put("Oct", 31);
		daysIn.put("Nov", 30);
		daysIn.put("Dec", 31);
		
		return daysIn.get(month);
	}
}
