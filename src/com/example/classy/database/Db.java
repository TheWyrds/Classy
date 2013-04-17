package com.example.classy.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class Db {

	private static Db classyDb;
	private SQLiteDatabase theDb;
	private boolean isReady;
	
	private Db() {}
	
	public static Db getInstance(Context context) {
		
		if (classyDb == null) {
			classyDb = new Db();
			classyDb.isReady = false;
			
			classyDb.new OpenDbAsyncTask().execute(context);
		}
		return classyDb;
	}
	
	public boolean initialized() {
		return isReady;
	}
	
	private class OpenDbAsyncTask extends 
							AsyncTask<Context, Void, Void> {
		@Override
		protected Void doInBackground(Context... params) {
			DbHelper theDbHelper = new DbHelper(params[0]);
			theDb = theDbHelper.getWritableDatabase();
			isReady = true;
			return null;
		}
	}
	
	public SQLiteDatabase getDB() {
		if (isReady == false)
			return null;
		return theDb;
	}
	
	//Returns true if successful, false if an error occurred
	public boolean addClass(String cls) {
		ContentValues newClass = new ContentValues();
		newClass.put(DbContract.Classes.ATTRIBUTE_NAME, cls);
		
		try {
			theDb.insertOrThrow(DbContract.Classes.TABLE_NAME, null, newClass);
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
		//Successful (supposedly)
		System.out.println(cls + " added to Class table.");
		return true;
	}
	
	public List<String[]> queryHomework(String... attributes) {
		
		List<String[]> retList = new ArrayList<String[]>();
		
		
		
	}
	
}
