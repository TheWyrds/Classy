package com.example.classy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ClassyDb.db";
    
    // SQL statement strings
    
    private static final String SQL_CREATE_TABLE_CLASSES =  "CREATE TABLE IF NOT EXISTS " + DbContract.Classes.TABLE_NAME + " (" +
    		DbContract.Classes._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
    		DbContract.Classes.ATTRIBUTE_NAME + " " + DbContract.Classes.TYPE_NAME + " " + 
    			DbContract.Classes.OPTIONS_NAME + ")";
    
    private static final String SQL_CREATE_TABLE_NOTES =  "CREATE TABLE IF NOT EXISTS " + DbContract.Notes.TABLE_NAME + " (" + 
    		DbContract.Notes._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
    		DbContract.Notes.ATTRIBUTE_FILE + " " + DbContract.Notes.TYPE_FILE + " " + 
    			DbContract.Notes.OPTIONS_FILE + "," + "\n" + 
    		DbContract.Notes.ATTRIBUTE_DATE + " " + DbContract.Notes.TYPE_DATE + " " + 
    			DbContract.Notes.OPTIONS_DATE  + ")";
    
    private static final String SQL_CREATE_TABLE_HOMEWORK =  "CREATE TABLE IF NOT EXISTS " + DbContract.Homework.TABLE_NAME + " (" + 
			DbContract.Homework._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
			DbContract.Homework.ATTRIBUTE_TITLE + " " + DbContract.Homework.TYPE_TITLE + " " + 
				DbContract.Homework.OPTIONS_TITLE + "," + "\n" + 
			DbContract.Homework.ATTRIBUTE_DUEDATE + " " + DbContract.Homework.TYPE_DUEDATE + " " + 
				DbContract.Homework.OPTIONS_DUEDATE + "," + "\n" + 
			DbContract.Homework.ATTRIBUTE_DESCRIPTION + " " + DbContract.Homework.TYPE_DESCRIPTION + " " + 
				DbContract.Homework.OPTIONS_DESCRIPTION + "," + "\n" + 
			DbContract.Homework.ATTRIBUTE_IMAGE + " " + DbContract.Homework.TYPE_IMAGE + " " + 
				DbContract.Homework.OPTIONS_IMAGE  + ")";
    

    private static final String SQL_CREATE_TABLE_GRADES = "CREATE TABLE IF NOT EXISTS " + DbContract.Grades.TABLE_NAME + " (" + 
				DbContract.Grades._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.Grades.ATTRIBUTE_TITLE + " " + DbContract.Grades.TYPE_TITLE + " " + 
					DbContract.Grades.OPTIONS_TITLE + "," + "\n" + 
				DbContract.Grades.ATTRIBUTE_GRADE + " " + DbContract.Grades.TYPE_GRADE + " " + 
					DbContract.Grades.OPTIONS_GRADE + "," + "\n" + 
				DbContract.Grades.ATTRIBUTE_DATE + " " + DbContract.Grades.TYPE_DATE + " " + 
					DbContract.Grades.OPTIONS_DATE  + ")"; 
    
	private static final String SQL_CREATE_TABLE_TAKENIN = "CREATE TABLE IF NOT EXISTS " + DbContract.TakenIn.TABLE_NAME + " (" + 
				DbContract.TakenIn._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.TakenIn.ATTRIBUTE_NOTESID + " " + DbContract.TakenIn.TYPE_NOTESID + " " + 
					DbContract.TakenIn.OPTIONS_NOTESID + "," + "\n" + 
				DbContract.TakenIn.ATTRIBUTE_CLASSID + " " + DbContract.TakenIn.TYPE_CLASSID + " " + 
					DbContract.TakenIn.OPTIONS_CLASSID  + "," + "\n" + 
				DbContract.TakenIn.TABLE_OPTIONS + ")";
	
	private static final String SQL_CREATE_TABLE_ASSIGNEDIN = "CREATE TABLE IF NOT EXISTS " + DbContract.AssignedIn.TABLE_NAME + " (" + 
				DbContract.AssignedIn._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.AssignedIn.ATTRIBUTE_HOMEWORKID + " " + DbContract.AssignedIn.TYPE_HOMEWORKID + " " + 
					DbContract.AssignedIn.OPTIONS_HOMEWORKID + "," + "\n" + 
				DbContract.AssignedIn.ATTRIBUTE_CLASSID + " " + DbContract.AssignedIn.TYPE_CLASSID + " " + 
					DbContract.AssignedIn.OPTIONS_CLASSID  + "," + "\n" + 
				DbContract.AssignedIn.TABLE_OPTIONS + ")";
	
	private static final String SQL_CREATE_TABLE_GIVENIN = "CREATE TABLE IF NOT EXISTS " + DbContract.GivenIn.TABLE_NAME + " (" + 
				DbContract.GivenIn._ID + " " + DbContract.OPTIONS_ID +"," + "\n" + 
				DbContract.GivenIn.ATTRIBUTE_GRADESID + " " + DbContract.GivenIn.TYPE_GRADESID + " " + 
					DbContract.GivenIn.OPTIONS_GRADESID + "," + "\n" + 
				DbContract.GivenIn.ATTRIBUTE_CLASSID + " " + DbContract.GivenIn.TYPE_CLASSID + " " + 
					DbContract.GivenIn.OPTIONS_CLASSID  + "," + "\n" + 
				DbContract.GivenIn.TABLE_OPTIONS + ")";
    
	private static final String SQL_DELETE_TABLE_TAKENIN = "DROP TABLE IF EXISTS" + DbContract.TakenIn.TABLE_NAME + ";" ;
	private static final String SQL_DELETE_TABLE_ASSIGNEDIN = "DROP TABLE IF EXISTS" + DbContract.AssignedIn.TABLE_NAME + ";" ;
	private static final String SQL_DELETE_TABLE_GIVENIN = "DROP TABLE IF EXISTS" + DbContract.GivenIn.TABLE_NAME + ";" ;
	private static final String SQL_DELETE_TABLE_NOTES = "DROP TABLE IF EXISTS" + DbContract.Notes.TABLE_NAME + ";";
	private static final String SQL_DELETE_TABLE_HOMEWORK = "DROP TABLE IF EXISTS" + DbContract.Homework.TABLE_NAME + ";";
	private static final String SQL_DELETE_TABLE_GRADES = "DROP TABLE IF EXISTS" + DbContract.Grades.TABLE_NAME + ";" ;
	private static final String SQL_DELETE_TABLE_CLASSES = "DROP TABLE IF EXISTS" + DbContract.Classes.TABLE_NAME + ";" ;   

    private Context context;
    
    public DbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
        this.context = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    	System.out.println("Tables created");
       // db.execSQL(SQL_CREATE_TABLES);
    	
    	db.execSQL(SQL_CREATE_TABLE_CLASSES);
    	db.execSQL(SQL_CREATE_TABLE_NOTES);
    	db.execSQL(SQL_CREATE_TABLE_HOMEWORK);
    	db.execSQL(SQL_CREATE_TABLE_GRADES);
    	db.execSQL(SQL_CREATE_TABLE_TAKENIN);
    	db.execSQL(SQL_CREATE_TABLE_ASSIGNEDIN);
    	db.execSQL(SQL_CREATE_TABLE_GIVENIN);
    	
    	//Inserts a default value into classes to prevent crashing
    	db.execSQL("INSERT INTO classes(name) VALUES(\"Mobile\")");


    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_TAKENIN);
        db.execSQL(SQL_DELETE_TABLE_ASSIGNEDIN);
        db.execSQL(SQL_DELETE_TABLE_GIVENIN);
        db.execSQL(SQL_DELETE_TABLE_NOTES);
        db.execSQL(SQL_DELETE_TABLE_HOMEWORK);
        db.execSQL(SQL_DELETE_TABLE_GRADES);
        db.execSQL(SQL_DELETE_TABLE_CLASSES);
        
        System.out.println("Tables deleted");
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}