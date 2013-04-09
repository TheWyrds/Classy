package com.example.classy.database;

import android.provider.BaseColumns;

public class DbContract {
	
	private DbContract() {}
	
	public static abstract class Classes implements BaseColumns {
		public static final String TABLE_NAME = "classes";
		
	//	public static final String ATTRIBUTE_TUPLE_ID = "classesID";
		public static final String ATTRIBUTE_NAME = "name"; 
	}

	public static abstract class Notes implements BaseColumns {
		public static final String TABLE_NAME = "notes";
		
	//	public static final String ATTRIBUTE_TUPLE_ID = "notesID";
		public static final String ATTRIBUTE_DATE = "date";
		public static final String ATTRIBUTE_CLASS = "class";
		public static final String ATTRIBUTE_DESCRIPTION = "description";
		public static final String ATTRIBUTE_NOTE = "noteFile";
	}
	
	public static abstract class Homework implements BaseColumns {
		public static final String TABLE_NAME = "homework";
		
	//	public static final String ATTRIBUTE_TUPLE_ID = "homeworkID";
		public static final String ATTRIBUTE_DATE = "date";
		public static final String ATTRIBUTE_CLASS = "class";
		public static final String ATTRIBUTE_DESCRIPTION = "description";
		public static final String ATTRIBUTE_DUE_DATE = "dueDate";
		public static final String ATTRIBUTE_IMAGE = "image";
	}
	
	public static abstract class Grades implements BaseColumns {
		public static final String TABLE_NAME = "grades";
		
	//	public static final String ATTRIBUTE_TUPLE_ID = "gradesID";
		public static final String ATTRIBUTE_DATE = "date";
		public static final String ATTRIBUTE_CLASS = "class";
		public static final String ATTRIBUTE_DESCRIPTION = "description";
		public static final String ATTRIBUTE_GRADE = "grade";
	}
}
