 package com.example.classy;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.classy.database.Db;
import com.example.classy.database.DbContract;
import com.example.classy.utilities.*;

public class MainActivity extends Activity {

	public String currentClass;
	public Fragment currentTabFragment;
	
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
	    
	    // Set default class
	    String[] classes = getResources().getStringArray(R.array.classes);
	    currentClass = classes[0];
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);

		MenuItem classesItem = menu.findItem(R.id.class_menu);
		classesItem.setTitle(currentClass);
		
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
	
	private void setupActionBarTabs(ActionBar actionBar) {
	    actionBar.setDisplayShowTitleEnabled(false);

	    Tab tab = actionBar.newTab()
	            .setText(R.string.notes)
	            .setTabListener(new TabListener<NotesFragment>(
	            						this, "notes", NotesFragment.class));
	    actionBar.addTab(tab);

	    
	    tab = actionBar.newTab()
	        .setText(R.string.homework)
	        .setTabListener(new TabListener<HomeworkFragment>(
	                					this, "homework", HomeworkFragment.class));
	    actionBar.addTab(tab);
	    
	    tab = actionBar.newTab()
	    	.setText(R.string.grades)
	    	.setTabListener(new TabListener<GradesFragment>(
	    								this, "grades", GradesFragment.class));
	    actionBar.addTab(tab);
	}
	
	public void setCurrentTabFragment(Fragment frag) {
		currentTabFragment = frag;
	}

}
