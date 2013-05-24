 package com.theWyrds.classy;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.theWyrds.classy.R;
import com.theWyrds.classy.GradesDialogFragment.GradesDialogListener;
import com.theWyrds.classy.HWDialogFragment.HWDialogListener;
import com.theWyrds.classy.database.Db;
import com.theWyrds.classy.database.DbContract;
import com.theWyrds.classy.utilities.ClassyDate;
import com.theWyrds.classy.utilities.ClassyHwReminder;
import com.theWyrds.classy.utilities.ClassyTabFunctionality;
import com.theWyrds.classy.utilities.NewClassDialog;
import com.theWyrds.classy.utilities.TabListener;
import com.theWyrds.classy.utilities.NewClassDialog.NewClassDialogListener;

public class MainActivity extends Activity implements OnItemSelectedListener ,
													  NewClassDialogListener ,
													  HWDialogListener,
													  GradesDialogListener {

	public String currentClass;
	public Fragment currentTabFragment;
	private Spinner classesSpinner;
	private Menu actionBarMenu;
	

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
	    
	    while(theDb.initialized() == false) {
	    	continue;
	    }
	    progressDialog.dismiss();
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(true);
	    
	    // Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment

	    // setup action bar for tabs
	    setupActionBarTabs(actionBar);
	    			
		//Set current class
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		//Save menu for later access/modification of action bar
		actionBarMenu = menu;
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);

		/////// Initialize spinner
		classesSpinner = (Spinner) menu.findItem(R.id.class_menu).getActionView();
		 
		List<String> classesList = Db.getInstance(this).getClassesList();
		
		//Create array adapter for ListView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActionBar().getThemedContext(),
					android.R.layout.simple_spinner_item,
					classesList);
	
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		classesSpinner.setAdapter(adapter);
		
		//Set event hook for spinner
		classesSpinner.setOnItemSelectedListener(this);
		
		//Set current class
		System.out.println(classesSpinner);
		System.out.println(classesSpinner.getSelectedItem());
		currentClass = (String) classesSpinner.getSelectedItem();
		System.out.println("in oncreateoptionsmenu currentclass: " + currentClass);
		
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
	
	//Called when item selected in classesSpinner in action bar
	@Override
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
		
		//If item selected is "All classes", make add item ('+') invisible
		String selectedClass = (String) parent.getItemAtPosition(pos);
		currentClass = selectedClass;
		
		if (selectedClass == getString(R.string.all_classes))
			//Make + button invisible
			actionBarMenu.findItem(R.id.new_item).setVisible(false);
		else
			actionBarMenu.findItem(R.id.new_item).setVisible(true);
		
		System.out.println("in onitemselected currentclass:" + currentClass);
		
		// Refresh the current tab
		((ClassyTabFunctionality) currentTabFragment).refresh();
    }

	@Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onNewClassDialogPositiveClick(View dialogView) {
		EditText et = (EditText) dialogView.findViewById(R.id.newClassEditText);
		String newClass = et.getText().toString();

		Db theDb = Db.getInstance(this);
		boolean addSuccess = theDb.addClass(newClass);
		
		if (addSuccess == false) {
			Toast.makeText(this, "Class name already used", Toast.LENGTH_SHORT)
				 .show();
			return;
		}
		else {
			//Refresh list in classesSpinner
			( (ArrayAdapter<String>)classesSpinner.getAdapter() ).add(newClass);
						
			//TODO fix, unreliable since no guaranteed order
			//Set spinner to new class
			classesSpinner.setSelection(classesSpinner.getCount() - 1, true);
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
		ClassyDate date = new ClassyDate(year, month, day);
		
		Db theDb = Db.getInstance(this);
		boolean addSuccess = theDb.addHw(currentClass, title, date, description, null);
		
		if (addSuccess == false) {
			Toast.makeText(this, "Homework could not be added", Toast.LENGTH_SHORT)
				 .show();
		}
		else {
			//TODO inelegant
			((HomeworkFragment)currentTabFragment).refresh();
			
			ClassyHwReminder reminder = new ClassyHwReminder(this, currentClass, title, date);
			reminder.scheduleNotification();
		}
	}
	
	@Override
	public void onGradesDialogPositiveClick(View dialogView) {
		EditText earnedEditText = (EditText) dialogView.findViewById(R.id.earned_points_edit_text);
		EditText totalEditText = (EditText) dialogView.findViewById(R.id.total_points_edit_text);
		EditText titleEditText = (EditText) dialogView.findViewById(R.id.grade_dialog_title);
		
		String earnedString = earnedEditText.getText().toString();
		String totalString = totalEditText.getText().toString();
		String title = titleEditText.getText().toString();
		
		//TODO make this more user-friendly
		if ( !earnedString.equals("") && !totalString.equals("") && !title.equals("")) {
		
			int earnedPoints = Integer.parseInt(earnedString);
			int totalPoints = Integer.parseInt(totalString);
			
			double grade = 100 * earnedPoints / (double) totalPoints;
			
			//Insert new grade into database
			Db theDb = Db.getInstance(this);
				//TODO add date field to dialog, remove currentdate
			boolean addSuccess = theDb.addGrade(currentClass, title, grade, new ClassyDate());
			
			if (addSuccess == false) {
				Toast.makeText(this, "Grade could not be added", Toast.LENGTH_SHORT)
					 .show();
			}
			else {
				((GradesFragment)currentTabFragment).refresh();
			}
		}
		else
			Toast.makeText(this, "Grade could not be added", Toast.LENGTH_SHORT)
			     .show();
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
