 package com.example.classy;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.classy.HWDialogFragment.HWDialogListener;
import com.example.classy.database.Db;
import com.example.classy.database.DbContract;
import com.example.classy.utilities.ClassyTabFunctionality;
import com.example.classy.utilities.NewClassDialog;
import com.example.classy.utilities.NewClassDialog.NewClassDialogListener;
import com.example.classy.utilities.TabListener;

public class MainActivity extends Activity implements OnItemSelectedListener ,
													  NewClassDialogListener ,
													  HWDialogListener {

	public String currentClass;
	public Fragment currentTabFragment;
	private Spinner classesSpinner;
	
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
	   
	    /*ContentValues values = new ContentValues();
	   // values.put(DbContract.Classes.ATTRIBUTE_NAME, "Mobile");
	   // values.put(DbContract.Classes.ATTRIBUTE_NAME, "Number Theory");
	  //  values.put(DbContract.Classes.ATTRIBUTE_NAME, "Graphics");
	  //  values.put(DbContract.Classes.ATTRIBUTE_NAME, "North Korea and You");

	  //  theDb.getDB().insertOrThrow(DbContract.Classes.TABLE_NAME, null, values);
	    System.out.println(theDb);
	    
	    System.out.println(theDb.getDB());
	    while(theDb.initialized() == false) {}
	    Cursor c = theDb.getDB().rawQuery("SELECT * " + "\n" + 
	    								  "FROM " + DbContract.Classes.TABLE_NAME , null );
	   // c.moveToFirst();
	    System.out.println("num rows " + c.getCount());
	    System.out.println("num cols " + c.getColumnCount());
	    	
	    
	    System.out.println("\n");
	    for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
	    	for (int j = 0; j < c.getColumnCount(); j++) {
	    		System.out.print(c.getString(j) + "  " );
	    	}
	    	System.out.println();
	    }
	    System.out.println("\n");
	    
	    System.out.println("first class from query:" + c.getString(1));
*/
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
		getMenuInflater().inflate(R.menu.activity_main, menu);

		/////// Initialize spinner
		classesSpinner = (Spinner) menu.findItem(R.id.class_menu).getActionView();
		
		Cursor c = Db.getInstance(this).getDB().rawQuery("SELECT * " +
														 "FROM " + DbContract.Classes.TABLE_NAME, null);
		
		String[] colNames = new String[] { DbContract.Classes.ATTRIBUTE_NAME };
		int[] adapterRowViews = new int[] { android.R.id.text1 };
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
					getActionBar().getThemedContext(), android.R.layout.simple_spinner_item, 
						c, colNames, adapterRowViews, 0);
				
		//		ArrayAdapter.createFromResource(
		//				getActionBar().getThemedContext(), R.array.classes, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		classesSpinner.setAdapter(adapter);
		
		//Set event hook for spinner
		classesSpinner.setOnItemSelectedListener(this);
		
		//Set current class
		System.out.println(classesSpinner);
		System.out.println(classesSpinner.getSelectedItem());
		currentClass = ((Cursor)classesSpinner.getSelectedItem()).getString(0);
		System.out.println(currentClass);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.new_item:
				((ClassyTabFunctionality)currentTabFragment).addNewItem();
				return true;
			case R.id.add_class:
				DialogFragment addClassDialog = new NewClassDialog();
				addClassDialog.show(getFragmentManager(), "addClassDialog");
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
		
		Cursor currentItem = (Cursor) parent.getItemAtPosition(pos);
		currentClass = currentItem.getString(0);
		
		//currentClass = (String) parent.getItemAtPosition(pos);
    }

	@Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }	
	
	@Override
	public void onNewClassDialogPositiveClick(View dialogView) {
		EditText et = (EditText) dialogView.findViewById(R.id.newClassEditText);
		String newClass = et.getText().toString();

		Db theDb = Db.getInstance(this);
		boolean addSuccess = theDb.addClass(newClass);
		
		if (addSuccess == false) {
			Toast.makeText(this, "Class name already used", Toast.LENGTH_SHORT)
				 .show();
		}
		else {
			//Refresh cursor in classesSpinner
			Cursor c = theDb.getDB().rawQuery("SELECT * FROM " + DbContract.Classes.TABLE_NAME, null);
		
			((SimpleCursorAdapter)classesSpinner.getAdapter()).swapCursor(c);
		}
	}
	
	@Override
	public void onHWDialogPositiveClick(View dialogView) {
		EditText titleEditText = (EditText) dialogView.findViewById(R.id.hw_dialog_title);
		EditText desEditText = (EditText) dialogView.findViewById(R.id.hw_dialog_description);
		DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.hw_dialog_date_picker);
		
		String title = titleEditText.getText().toString();
		String description = desEditText.getText().toString();
		
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();
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
