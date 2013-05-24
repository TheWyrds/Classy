package com.theWyrds.classy.utilities;

import java.util.Calendar;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.theWyrds.classy.R;

/* See papers.ch/android-schedule-a-notification/ */

public class ClassyHwReminder {
	
	String cls;
	String title;
	ClassyDate date;
	Context context;
	
	public ClassyHwReminder(Context context, String cls, String title, ClassyDate date) {
		System.out.println("in constructor of reminder");
		this.context = context;
		this.cls = cls;
		this.title = title;
		this.date = date;
	}
	
	public void scheduleNotification() {
		System.out.println("in schedulenotification");
		
		//TODO pull notification prefs
		
		ClassyDate firstReminder = new ClassyDate(date);
		ClassyDate secondReminder = new ClassyDate(date);
		//TODO delete
		ClassyDate demoReminder = new ClassyDate();
		
		firstReminder.add(Calendar.DATE, -5);
		secondReminder.add(Calendar.DATE, -1);
		//TODO delete
		demoReminder.add(Calendar.SECOND, 10);
		
		//Get alarm manager from system
		AlarmManager alarmManager = 
				(AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		
		//Create id
		int id1 = (int) System.currentTimeMillis();
		int id2 = id1 + 1;
		//TODO delete
		int id3 = id1 + 2;
		
		//Prepare intents
		Intent intent = new Intent(context, NotificationReceiver.class);
		intent.putExtra("class", cls);
		intent.putExtra("title", title);
		intent.putExtra("date", date);

		//Prepare pending intents
		PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context.getApplicationContext(), id1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context.getApplicationContext(), id2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context.getApplicationContext(), id3, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		//Register alert in the system
		
		//BACKUP
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		now.add(Calendar.SECOND, 10);
		
		
		alarmManager.set(AlarmManager.RTC, firstReminder.getTimeInMillis(), pendingIntent1);
		alarmManager.set(AlarmManager.RTC, secondReminder.getTimeInMillis(), pendingIntent2);
		alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 60000, pendingIntent3);
		
		
	}
	
	public static class NotificationReceiver extends BroadcastReceiver {		
		
		@Override public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			String cls = extras.getString("class");
			String title = extras.getString("title");
			ClassyDate date = (ClassyDate) extras.get("date");
			
			String displayDate = date.day + " " + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);

			System.out.println("in receiver");
			
			//Request notification manager
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			
			// Create the notification
			@SuppressWarnings("deprecation")
			Notification notification  = new Notification.Builder(context)
								.setContentTitle(cls)
								.setContentText("Due: " + title)
								.setContentInfo(displayDate)
								.setSmallIcon(R.drawable.ic_launcher)
								.getNotification();
			
			//TODO don't overwrite other notifications of the same id

			// Notifications away!
			notificationManager.notify(1, notification);
		}
	}


}
