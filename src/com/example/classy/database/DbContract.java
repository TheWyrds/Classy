package com.example.classy.database;

import android.provider.BaseColumns;

public class DbContract {
	
	public static final String OPTIONS_ID = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final String TRIGGERS = 
			"CREATE TRIGGER IF NOT EXISTS deleteNoteOnClassDelete \n" + 
			"AFTER DELETE ON " + TakenIn.TABLE_NAME + "\n" +
			"BEGIN\n" + 
				"DELETE FROM " + Notes.TABLE_NAME + " WHERE old." + TakenIn.ATTRIBUTE_NOTESID + 
					" = " +  Notes._ID + "\n" +
			"END;\n" +

			"CREATE TRIGGER IF NOT EXISTS deleteHomeworkOnClassDelete \n" + 
			"AFTER DELETE ON " + AssignedIn.TABLE_NAME + "\n" +
			"BEGIN\n" + 
				"DELETE FROM " + Homework.TABLE_NAME + " WHERE old." + AssignedIn.ATTRIBUTE_HOMEWORKID + 
					" = " +  Homework._ID + "\n" +
			"END;\n" +
					
			"CREATE TRIGGER IF NOT EXISTS deleteGradeOnClassDelete \n" + 
			"AFTER DELETE ON " + GivenIn.TABLE_NAME + "\n" +
			"BEGIN\n" + 
				"DELETE FROM " + Grades.TABLE_NAME + " WHERE old." + GivenIn.ATTRIBUTE_GRADESID + 
					" = " +  Grades._ID + "\n" +
			"END;\n" ;
	
	private DbContract() {}
	
	public static abstract class Classes implements BaseColumns {
		public static final String TABLE_NAME = "classes";
		
		public static final String ATTRIBUTE_NAME = "name"; 
		public static final String TYPE_NAME = "TEXT";
		public static final String OPTIONS_NAME = " NOT NULL UNIQUE";
	}

	public static abstract class Notes implements BaseColumns {
		public static final String TABLE_NAME = "notes";
		
		public static final String ATTRIBUTE_FILE = "file";
		public static final String TYPE_FILE = "TEXT";
		public static final String OPTIONS_FILE = "NOT NULL UNIQUE";
		
		public static final String ATTRIBUTE_DATE = "date";
		public static final String TYPE_DATE = "TEXT";
		public static final String OPTIONS_DATE = "NOT NULL";

	}
	
	public static abstract class Homework implements BaseColumns {
		public static final String TABLE_NAME = "homework";
		
		public static final String ATTRIBUTE_TITLE = "title";
		public static final String TYPE_TITLE = "TEXT";
		public static final String OPTIONS_TITLE = "NOT NULL";
		
		public static final String ATTRIBUTE_DUEDATE = "dueDate";
		public static final String TYPE_DUEDATE = "TEXT";
		public static final String OPTIONS_DUEDATE = "NOT NULL";
		
		public static final String ATTRIBUTE_DESCRIPTION = "description";
		public static final String TYPE_DESCRIPTION = "TEXT";
		public static final String OPTIONS_DESCRIPTION = "";

		public static final String ATTRIBUTE_IMAGE = "image";
		public static final String TYPE_IMAGE = "TEXT";
		public static final String OPTIONS_IMAGE = "";
		
	}
	
	public static abstract class Grades implements BaseColumns {
		public static final String TABLE_NAME = "grades";
		
		public static final String ATTRIBUTE_TITLE = "title";
		public static final String TYPE_TITLE = "TEXT";
		public static final String OPTIONS_TITLE = "NOT NULL";
		
		public static final String ATTRIBUTE_GRADE = "grade";
		public static final String TYPE_GRADE = "TEXT";
		public static final String OPTIONS_GRADE = "NOT NULL";
		
		public static final String ATTRIBUTE_DATE = "date";
		public static final String TYPE_DATE = "TEXT";
		public static final String OPTIONS_DATE = "";	
	}
	///////
	//Relationships
	///////
	public static abstract class TakenIn implements BaseColumns {
		public static final String TABLE_NAME = "takenIn";
		
		public static final String ATTRIBUTE_NOTESID = "notesId";
		public static final String TYPE_NOTESID = "INTEGER";
		public static final String OPTIONS_NOTESID = "NOT NULL UNIQUE";
		
		public static final String ATTRIBUTE_CLASSID = "classId";
		public static final String TYPE_CLASSID = "INTEGER";
		public static final String OPTIONS_CLASSID = "NOT NULL";	
		
		public static final String TABLE_OPTIONS =
				"FOREIGN KEY (" + ATTRIBUTE_NOTESID + ") REFERENCES " + Notes.TABLE_NAME 
						+ "(" + Notes._ID + ") " + "ON DELETE CASCADE ON UPDATE CASCADE" + "," + "\n" + 
				"FOREIGN KEY (" + ATTRIBUTE_CLASSID + ") REFERENCES " + Classes.TABLE_NAME
						+ "(" + Classes._ID + ") " + "ON DELETE CASCADE ON UPDATE CASCADE" ;
	}
	
	public static abstract class AssignedIn implements BaseColumns {
		public static final String TABLE_NAME = "assignedIn";
		
		public static final String ATTRIBUTE_HOMEWORKID = "homeworkId";
		public static final String TYPE_HOMEWORKID = "INTEGER";
		public static final String OPTIONS_HOMEWORKID = "NOT NULL UNIQUE";
		
		public static final String ATTRIBUTE_CLASSID = "classId";
		public static final String TYPE_CLASSID = "INTEGER";
		public static final String OPTIONS_CLASSID = "NOT NULL";	
		
		public static final String TABLE_OPTIONS =
				"FOREIGN KEY (" + ATTRIBUTE_HOMEWORKID + ") REFERENCES " + Homework.TABLE_NAME 
						+ "(" + Homework._ID + ") " + "ON DELETE CASCADE ON UPDATE CASCADE" + "," + "\n" + 
				"FOREIGN KEY (" + ATTRIBUTE_CLASSID + ") REFERENCES " + Classes.TABLE_NAME
						+ "(" + Classes._ID + ") " + "ON DELETE CASCADE ON UPDATE CASCADE" ;
	}
	
	public static abstract class GivenIn implements BaseColumns {
		public static final String TABLE_NAME = "givenIn";
		
		public static final String ATTRIBUTE_GRADESID = "gradesId";
		public static final String TYPE_GRADESID = "INTEGER";
		public static final String OPTIONS_GRADESID = "NOT NULL UNIQUE";
		
		public static final String ATTRIBUTE_CLASSID = "classId";
		public static final String TYPE_CLASSID = "INTEGER";
		public static final String OPTIONS_CLASSID = "NOT NULL";	
		
		public static final String TABLE_OPTIONS =
				"FOREIGN KEY (" + ATTRIBUTE_GRADESID + ") REFERENCES " + Grades.TABLE_NAME 
						+ "(" + Grades._ID + ") " + "ON DELETE CASCADE ON UPDATE CASCADE" + "," + "\n" + 
				"FOREIGN KEY (" + ATTRIBUTE_CLASSID + ") REFERENCES " + Classes.TABLE_NAME
						+ "(" + Classes._ID + ") " + "ON DELETE CASCADE ON UPDATE CASCADE" ;
	}
}
