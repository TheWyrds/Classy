package com.theWyrds.classy.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ClassyDate extends GregorianCalendar {

	int year, month, day;
	
	public ClassyDate() {
		super();

		year = this.get(YEAR);
		month = this.get(MONTH);
		day = this.get(DATE);
		
		System.out.println("year:" + year + " month:" + month + " day:" + day);
	}
	
	public ClassyDate(int year, int month, int day) {
		super(year, month, day);
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public ClassyDate(ClassyDate other) {
		this(other.year, other.month, other.day);
	}
	
	//Return string in UTC format YYYY-MM-DD HH:MM:SS
	@Override
	public String toString() {
		String formattedMonth = (month < 10? "0" + month : "" + month);
		String formattedDay = (day < 10? "0" + day : "" + day);
		
		String date = year + "-" + formattedMonth + "-" + formattedDay + " 12:00:00";
		
		return date;
	}
	
	public static String utcToReadable(String utc) {
		System.out.println("in utctoreadable with " + utc);
		
		String[] parsed = utc.split("[ -]");
		int year, month, day;
		try{
			year = Integer.parseInt(parsed[0]);
			month = Integer.parseInt(parsed[1]);
			day = Integer.parseInt(parsed[2]);
		} catch (Exception e) {
			System.out.println("utcToReadable cannot convert non-dates! \n" + e.toString());
			return null;
		}
		
		Calendar date = new ClassyDate(year, month, day);
		String displayMonth = date.getDisplayName(MONTH, SHORT, Locale.US);
		
		String readableDate = day + " " + displayMonth;
		
		System.out.println("utctoreadable returning " + readableDate);
		
		return readableDate;
	}
}
