package com.example.classy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ClassyDb.db";
    
    // SQL statement strings
    
    private static final String SQL_CREATE_TABLES =
    	    "CREATE TABLE IF NOT EXISTS " + DbContract.Classes.TABLE_NAME + " (" +
    	    		DbContract.Classes._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
    	    		DbContract.Classes.ATTRIBUTE_NAME + " " + DbContract.Classes.TYPE_NAME + " " + 
    	    			DbContract.Classes.OPTIONS_NAME + "\n" +
    	    ");\n" + 
    	    "CREATE TABLE IF NOT EXISTS " + DbContract.Notes.TABLE_NAME + " (" + 
    	    		DbContract.Notes._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
    	    		DbContract.Notes.ATTRIBUTE_FILE + " " + DbContract.Notes.TYPE_FILE + " " + 
    	    			DbContract.Notes.OPTIONS_FILE + "," + "\n" + 
    	    		DbContract.Notes.ATTRIBUTE_DATE + " " + DbContract.Notes.TYPE_DATE + " " + 
    	    			DbContract.Notes.OPTIONS_DATE  + "\n" + 
    	    ");\n" + 
    	    "CREATE TABLE IF NOT EXISTS " + DbContract.Homework.TABLE_NAME + " (" + 
    				DbContract.Homework._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
    				DbContract.Homework.ATTRIBUTE_TITLE + " " + DbContract.Homework.TYPE_TITLE + " " + 
    					DbContract.Homework.OPTIONS_TITLE + "," + "\n" + 
    				DbContract.Homework.ATTRIBUTE_DUEDATE + " " + DbContract.Homework.TYPE_DUEDATE + " " + 
    					DbContract.Homework.OPTIONS_DUEDATE + "," + "\n" + 
    				DbContract.Homework.ATTRIBUTE_DESCRIPTION + " " + DbContract.Homework.TYPE_DESCRIPTION + " " + 
    					DbContract.Homework.OPTIONS_DESCRIPTION + "," + "\n" + 
    				DbContract.Homework.ATTRIBUTE_IMAGE + " " + DbContract.Homework.TYPE_IMAGE + " " + 
    					DbContract.Homework.OPTIONS_IMAGE  + "\n" + 
    		");\n" + 
    		"CREATE TABLE IF NOT EXISTS " + DbContract.Grades.TABLE_NAME + " (" + 
				DbContract.Grades._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.Grades.ATTRIBUTE_TITLE + " " + DbContract.Grades.TYPE_TITLE + " " + 
					DbContract.Grades.OPTIONS_TITLE + "," + "\n" + 
				DbContract.Grades.ATTRIBUTE_GRADE + " " + DbContract.Grades.TYPE_GRADE + " " + 
					DbContract.Grades.OPTIONS_GRADE + "," + "\n" + 
				DbContract.Grades.ATTRIBUTE_DATE + " " + DbContract.Grades.TYPE_DATE + " " + 
					DbContract.Grades.OPTIONS_DATE  + "\n" + 
			");\n" + 
			"CREATE TABLE IF NOT EXISTS " + DbContract.TakenIn.TABLE_NAME + " (" + 
				DbContract.TakenIn._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.TakenIn.ATTRIBUTE_NOTESID + " " + DbContract.TakenIn.TYPE_NOTESID + " " + 
					DbContract.TakenIn.OPTIONS_NOTESID + "," + "\n" + 
				DbContract.TakenIn.ATTRIBUTE_CLASSID + " " + DbContract.TakenIn.TYPE_CLASSID + " " + 
					DbContract.TakenIn.OPTIONS_CLASSID  + "," + "\n" + 
				DbContract.TakenIn.TABLE_OPTIONS + "\n" + 
			");\n" + 
			"CREATE TABLE IF NOT EXISTS " + DbContract.AssignedIn.TABLE_NAME + " (" + 
				DbContract.AssignedIn._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.AssignedIn.ATTRIBUTE_HOMEWORKID + " " + DbContract.AssignedIn.TYPE_HOMEWORKID + " " + 
					DbContract.AssignedIn.OPTIONS_HOMEWORKID + "," + "\n" + 
				DbContract.AssignedIn.ATTRIBUTE_CLASSID + " " + DbContract.AssignedIn.TYPE_CLASSID + " " + 
					DbContract.AssignedIn.OPTIONS_CLASSID  + "," + "\n" + 
				DbContract.AssignedIn.TABLE_OPTIONS + "\n" + 
			");\n" + 
			"CREATE TABLE IF NOT EXISTS " + DbContract.GivenIn.TABLE_NAME + " (" + 
				DbContract.GivenIn._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.GivenIn.ATTRIBUTE_GRADESID + " " + DbContract.GivenIn.TYPE_GRADESID + " " + 
					DbContract.GivenIn.OPTIONS_GRADESID + "," + "\n" + 
				DbContract.GivenIn.ATTRIBUTE_CLASSID + " " + DbContract.GivenIn.TYPE_CLASSID + " " + 
					DbContract.GivenIn.OPTIONS_CLASSID  + "," + "\n" + 
				DbContract.GivenIn.TABLE_OPTIONS + "\n" + 
			");\n" ; 
    
    private static final String SQL_DELETE_TABLES = 
    		"DROP TABLE IF EXISTS" + DbContract.TakenIn.TABLE_NAME + ";" +
    		"DROP TABLE IF EXISTS" + DbContract.AssignedIn.TABLE_NAME + ";" + 
    		"DROP TABLE IF EXISTS" + DbContract.GivenIn.TABLE_NAME + ";" + 
    		"DROP TABLE IF EXISTS" + DbContract.Notes.TABLE_NAME + ";" +
    		"DROP TABLE IF EXISTS" + DbContract.Homework.TABLE_NAME + ";" +
    		"DROP TABLE IF EXISTS" + DbContract.Grades.TABLE_NAME + ";" ;


    public DbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}