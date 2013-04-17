 package com.example.classy;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.classy.database.Db;
import com.example.classy.utilities.ClassyTabFunctionality;
import com.example.classy.utilities.TabListener;

public class MainActivity extends Activity implements OnItemSelectedListener {

	public String currentClass;
	public Fragment currentTabFragment;
	
	// Global data arrays
	String[] classes;
	String[] hwTitles;
	String[] hwClasses;
	String[] hwDueDates;
	String[] grades;
	String[] gradesClasses;
	String[] gradesDates;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    // Initialize database for future use, display progress dialog to run
	    // while it does so, preventing user interaction until the app can
	    // be populated
	    Db theDb = Db.getInstance(this);
	    ProgressDialog progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
	    progressDialog.setIndeterminate(true);
	    progressDialog.show();
	    
	    ////////////////////////////////////////////
	    ContentValues values = new ContentValues();
	   // values.put(DbContract.Classes.ATTRIBUTE_NAME, "Mobile");
	   // values.put(DbContract.Classes.ATTRIBUTE_NAME, "Number Theory");
	    //values.put(DbContract.Classes.ATTRIBUTE_NAME, "Graphics");
	   // values.put(DbContract.Classes.ATTRIBUTE_NAME, "North Korea and You");

	   // theDb.getDB().insertOrThrow(DbContract.Classes.TABLE_NAME, null, values);
	    System.out.println(theDb);
	    
	    System.out.println(theDb.getDB());
	  //  Cursor c = theDb.getDB().rawQuery("SELECT * " + "\n" + 
	   // 								  "FROM " + DbContract.Classes.TABLE_NAME , null );
	  //  for( c.moveToFirst(); c.isAfterLast() ; c.moveToNext() )
	//    	System.out.println(c.getString(1));
	    
	    ////////////////////////////////////////////
	    while(theDb.initialized() == false) {
	    	continue;
	    }
	    progressDialog.dismiss();
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    // Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment

	    // setup action bar for tabs
	    setupActionBarTabs(actionBar);
	    
	    // Get and save all hardcoded data from resources
	    classes = getResources().getStringArray(R.array.classes);
		hwTitles = getResources().getStringArray(R.array.homeworkTitle);
		hwClasses = getResources().getStringArray(R.array.homeworkClass);
		hwDueDates = getResources().getStringArray(R.array.homeworkDueDate);
		grades = getResources().getStringArray(R.array.grades);
		gradesClasses = getResources().getStringArray(R.array.gradesClass);
		gradesDates = getResources().getStringArray(R.array.gradesDate);
		
		//Set current class
		currentClass = classes[0];
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);

		Spinner classesSpinner = (Spinner) menu.findItem(R.id.class_menu).getActionView();
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
						getActionBar().getThemedContext(), R.array.classes, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		classesSpinner.setAdapter(adapter);
		
		//Set event hook for spinner
		classesSpinner.setOnItemSelectedListener(this);
		
		//Set current class
		currentClass = (String) classesSpinner.getSelectedItem();
		System.out.println(currentClass);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.new_item:
				((ClassyTabFunctionality)currentTabFragment).addNewItem();
				return true;
			default: 
				return super.onOptionsItemSelected(item);
		}
	}
	
	//For class spinner in action bar
	@Override
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		
		currentClass = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }	
	private void setupActionBarTabs(ActionBar actionBar) {
	    actionBar.setDisplayShowTitleEnabled(false);

	    Tab notesTab = actionBar.newTab()
	            .setText(R.string.notes)
	            .setTabListener(new TabListener<NotesFragment>(
	            						this, "notes", NotesFragment.class));
	    actionBar.addTab(notesTab);

	    
	    Tab hwTab = actionBar.newTab()
	        .setText(R.string.homework)
	        .setTabListener(new TabListener<HomeworkFragment>(
	                					this, "homework", HomeworkFragment.class));
	    actionBar.addTab(hwTab);
	    
	    Tab gradesTab = actionBar.newTab()
	    	.setText(R.string.grades)
	    	.setTabListener(new TabListener<GradesFragment>(
	    								this, "grades", GradesFragment.class));
	    actionBar.addTab(gradesTab);
	    
	    actionBar.selectTab(hwTab);
	}
	
	public void setCurrentTabFragment(Fragment frag) {
		currentTabFragment = frag;
	}
	
}
