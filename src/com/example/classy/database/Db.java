package com.example.classy.database;

import android.content.Context;
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
	
}
