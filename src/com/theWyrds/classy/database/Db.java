package com.theWyrds.classy.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.theWyrds.classy.R;
import com.theWyrds.classy.utilities.ClassyDate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class Db {

	private static Db classyDb;
	private SQLiteDatabase theDb;
	private boolean isReady;
	
	private static Context context;
	
	private Db() {}
	
	public static Db getInstance(Context context) {
		
		if (classyDb == null) {
			Db.context = context;

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
	
	/* ***********************************************************************
	 * Convenience functions to add and and get resources from database in a *	
	 * uniform and consistent fashion										 *					
	 * 																		 *	
	 * ***********************************************************************/
	
	///////Getters
	
	public List<String> getClassesList() {
		Cursor c = theDb.rawQuery("SELECT * " +
								  "FROM " + DbContract.Classes.TABLE_NAME, null);
		
		//Read cursor into an ArrayList
		List<String> classesList = new ArrayList<String>();
		classesList.add( context.getString(R.string.all_classes) );			//Add "All classes"
		
		while (c.moveToNext()) {
			classesList.add( c.getString( c.getColumnIndex(DbContract.Classes.ATTRIBUTE_NAME)));
		}
		
		return classesList;
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
	
	public boolean addHw(String currentClass, String title, ClassyDate date, String description, String imagePath) {
		if (title.equals("")) title = null;
		
		//TODO fix this ugly nextHwId shit
		
		Cursor classCursor = theDb.rawQuery("SELECT " + DbContract.Classes._ID + "\n" +
								  "FROM " + DbContract.Classes.TABLE_NAME + "\n" + 
								  "WHERE " + DbContract.Classes.ATTRIBUTE_NAME + " = \"" + currentClass + "\"", null);

		classCursor.moveToFirst();
		int currentClassId = classCursor.getInt(0);
		
		Cursor hwIdCursor = theDb.rawQuery("SELECT MAX(" + DbContract.Homework._ID + ") \n" +
									  "FROM " + DbContract.Homework.TABLE_NAME , null);
		hwIdCursor.moveToFirst();
		
			int nextHwId = hwIdCursor.getInt(0) + 1;
		System.out.println("nexthwid:" + nextHwId);
		
		ContentValues newHw = new ContentValues();
		newHw.put(DbContract.Homework.ATTRIBUTE_TITLE, title);
		newHw.put(DbContract.Homework.ATTRIBUTE_DUEDATE, date.toString());
		newHw.put(DbContract.Homework.ATTRIBUTE_DESCRIPTION, description);
		newHw.put(DbContract.Homework.ATTRIBUTE_IMAGE, imagePath);
		
		ContentValues newAssignedIn = new ContentValues();
		newAssignedIn.put(DbContract.AssignedIn.ATTRIBUTE_CLASSID, currentClassId);
		newAssignedIn.put(DbContract.AssignedIn.ATTRIBUTE_HOMEWORKID, nextHwId);

		theDb.beginTransaction();
		try {
			theDb.insertOrThrow(DbContract.Homework.TABLE_NAME, null, newHw);
			theDb.insertOrThrow(DbContract.AssignedIn.TABLE_NAME, null, newAssignedIn);
			theDb.setTransactionSuccessful();
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		} finally {
			theDb.endTransaction();
		}
		
		//Success
		System.out.println("(" + title + ", " + date.toString() + ", " +
						   description + ", " + imagePath + ") added to Homework table");
		System.out.println("(" + currentClassId + ", " + nextHwId + ") added to AssignedIn table");
		
		return true;
	}
	
	public boolean addGrade(String currentClass, String title, double grade, ClassyDate date) {
				if (title.equals("")) title = null;
		
		//TODO fix this ugly nextGradeId shit
		
				Cursor classCursor = theDb.rawQuery("SELECT " + DbContract.Classes._ID + "\n" +
										  "FROM " + DbContract.Classes.TABLE_NAME + "\n" + 
										  "WHERE " + DbContract.Classes.ATTRIBUTE_NAME + " = \"" + currentClass + "\"", null);

				classCursor.moveToFirst();
				int currentClassId = classCursor.getInt(0);
				
				Cursor gradesIdCursor = theDb.rawQuery("SELECT MAX(" + DbContract.Grades._ID + ") \n" +
											  "FROM " + DbContract.Grades.TABLE_NAME , null);
				gradesIdCursor.moveToFirst();
				
				int nextGradeId = gradesIdCursor.getInt(0) + 1;
				
				System.out.println("nextGradeId:" + nextGradeId);
				
				ContentValues newGrade = new ContentValues();
				newGrade.put(DbContract.Grades.ATTRIBUTE_TITLE, title);
				newGrade.put(DbContract.Grades.ATTRIBUTE_GRADE, grade);
				newGrade.put(DbContract.Grades.ATTRIBUTE_DATE, date.toString());
				
				ContentValues newGivenIn = new ContentValues();
				newGivenIn.put(DbContract.GivenIn.ATTRIBUTE_CLASSID, currentClassId);
				newGivenIn.put(DbContract.GivenIn.ATTRIBUTE_GRADESID, nextGradeId);

				theDb.beginTransaction();
				try {
					theDb.insertOrThrow(DbContract.Grades.TABLE_NAME, null, newGrade);
					theDb.insertOrThrow(DbContract.GivenIn.TABLE_NAME, null, newGivenIn);
					theDb.setTransactionSuccessful();
				} catch (SQLException e) {
					System.out.println(e.toString());
					return false;
				} finally {
					theDb.endTransaction();
				}
				
				//Success
				System.out.println("(" + title + ", " + grade + ", " + date.toString() + ") added to Grades table");
				System.out.println("(" + currentClassId + ", " + nextGradeId + ") added to GivenIn table");
				
				return true;
	}
}
