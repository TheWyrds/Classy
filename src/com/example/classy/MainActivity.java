 package com.example.classy;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;

import com.example.classy.database.Db;
import com.example.classy.utilities.TabListener;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    // Initialize database for future use, display progress dialog to run
	    // while it does so, preventing user interaction until the app can
	    // be populated
	    Db theDb = Db.getInstance(this);
	    ProgressDialog progressDialog = new ProgressDialog(this);
	    progressDialog.setIndeterminate(true);
	    progressDialog.show();
	    while(theDb.initialized() == false) {
	    	continue;
	    }
	    progressDialog.dismiss();
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS | 
	    							ActionBar.NAVIGATION_MODE_LIST );

	    // Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment

	    // setup action bar for tabs
	    setupActionBarTabs(actionBar);
	    
	    // 
	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
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

}
