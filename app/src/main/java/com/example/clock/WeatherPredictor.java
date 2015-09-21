package com.example.clock;

public class WeatherPredictor {

	// Returns closest sting to time in seconds
	public static String stringAt(String info, int time, int numberOfHours){
		int closestTime = (int) Math.abs(time-Info.getPercent()*86400);
		int closestTimeIndex = 0;
		
		for(int i = 1; i < numberOfHours; i++){
			int weatherTime = getTime(i);
			
			if(!getDay(i).equals(Info.getDay()+""))
				weatherTime += 86400;
			
			if(Math.abs(time-weatherTime) < closestTime){
				closestTime = Math.abs(time-weatherTime);
				closestTimeIndex = i;
			}		
		}
		
		if(closestTimeIndex == 0)
			return Info.getWeatherInfo("current_weather", info);
		
		return Info.getWeatherInfo("hour_"+closestTimeIndex+"_weather", info);
	}

	// Returns closest sting to time in seconds
	public static int intAt(String info, int time, int numberOfHours){		
		int closestTime = 999999999;
		int closestTimeIndex = 1;
		
		for(int i = 1; i < numberOfHours; i++){
			int weatherTime = getTime(i);
			
			if(!getDay(i).equals(Info.getDay()+""))
				weatherTime += 86400;
			
			if(time-weatherTime > 0)
				if(time-weatherTime < closestTime){
					closestTime = Math.abs(time-weatherTime);
					closestTimeIndex = i;
				}		
		}
		
		if((int) (time-Info.getPercent()*86400) < closestTime)
			closestTimeIndex *= -1;
		
		int weatherTime1, weatherTime2;
		int info1, info2;
		
		if(closestTimeIndex < 0){
			closestTimeIndex *= -1;
			
			weatherTime1 = (int) (Info.getPercent()*86400 +.5);
			
			weatherTime2 = getTime(closestTimeIndex+1);
			
			if(!getDay(closestTimeIndex+1).equals(Info.getDay()+""))
				weatherTime2 += 86400;
			
			info1 = Integer.parseInt(Info.getWeatherInfo("current_weather", info));
			info2 = Integer.parseInt(Info.getWeatherInfo("hour_"+(closestTimeIndex+1)+"_weather", info));	
		}
		else{
			weatherTime1 = getTime(closestTimeIndex);
			
			if(!getDay(closestTimeIndex).equals(Info.getDay()+""))
				weatherTime1 += 86400;
			
			weatherTime2 = getTime(closestTimeIndex+1);
			
			if(!getDay(closestTimeIndex+1).equals(Info.getDay()+""))
				weatherTime2 += 86400;

			info1 = Integer.parseInt(Info.getWeatherInfo("hour_"+closestTimeIndex+"_weather", info));
			info2 = Integer.parseInt(Info.getWeatherInfo("hour_"+(closestTimeIndex+1)+"_weather", info));
		}
			
		double slope = (double)(info2-info1)/(double)(weatherTime2-weatherTime1);
		
		double changeInTime = time - weatherTime1;
		
		return info1+(int)(slope*changeInTime+.5);
	}
	
	// Returns time at index
	private static int getTime(int index){
		String weatherTimeInfo = Info.getWeatherInfo("hour_"+index+"_weather", "time");
		int weatherTime = (int)(Integer.parseInt(weatherTimeInfo.substring(weatherTimeInfo.length()-2, weatherTimeInfo.length()))*.6+.5);
		weatherTimeInfo = weatherTimeInfo.substring(0, weatherTimeInfo.length()-2);
		weatherTime += Integer.parseInt(Info.getWeatherInfo("hour_"+index+"_weather", "time"))*36;
		
		return weatherTime;
	}
	
	// Returns day at index
	private static String getDay(int index){
		String weatherDay = Info.getWeatherInfo("hour_"+index+"_weather", "date");
		int character = weatherDay.length();
		while(weatherDay.charAt(character-1) != '-')
			character--;
		weatherDay = weatherDay.substring(character);
		
		return weatherDay;
	}
}
