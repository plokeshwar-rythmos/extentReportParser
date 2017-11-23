package com.methods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tester {
	
	public static void main(String[] args) {
		//String time = String.valueOf(System.currentTimeMillis());
		String time = "1506507419386";
		System.out.println(time);
		
		getTime(Long.valueOf(time));
	
	
	}

	
	private static Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        System.err.println(calendar.getTime());
        return calendar.getTime();      
    }
}
