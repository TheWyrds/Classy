package com.example.classy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.AdapterView.*;

import com.example.classy.database.Db;
import com.example.classy.database.DbContract;
import com.example.classy.utilities.ClassyDate;
import com.example.classy.utilities.ClassyTabFunctionality;

public class HomeworkFragment extends ListFragment implements ClassyTabFunctionality {
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		

		Cursor c = getListCursor();
		
		String[] colNames = new String[] { DbContract.Homework.ATTRIBUTE_DUEDATE, DbContract.Homework.ATTRIBUTE_TITLE };
		int[] adapterRowViews = new int[] { R.id.due_date, R.id.hw_list_title };
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.hw_list_item, c, colNames, adapterRowViews, 0);
		
		adapter.setViewBinder(new ViewBinder() {

		    public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {
	        	System.out.println("in setviewvalue columnindex: " + aColumnIndex);

		        if (aColumnIndex == 2) {
		                String tableDate = aCursor.getString(aColumnIndex);
		                TextView textView = (TextView) aView;
		                textView.setText( ClassyDate.utcToReadable(tableDate) );
		                return true;
		         }

		         return false;
		    }
		});

		setListAdapter(adapter);
		
		//Set click listeners
		//TODO
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View clickedView, int position, long id) {
				return false;
			}
		});
	}
	
	public Cursor getListCursor() {
		Db theDb = Db.getInstance(getActivity());
		String currentClass = ((MainActivity) getActivity()).currentClass;
		
		String query;
		if (currentClass == getActivity().getString(R.string.all_classes)) {
			//Choose all classes
			query = 
				"SELECT * FROM " + DbContract.Homework.TABLE_NAME + " , " + DbContract.AssignedIn.TABLE_NAME + " , " + DbContract.Classes.TABLE_NAME + "\n" +
				"WHERE " + DbContract.Homework.TABLE_NAME + "." + DbContract.Homework._ID + " = " + DbContract.AssignedIn.ATTRIBUTE_HOMEWORKID + "\n" +
				"AND " + DbContract.AssignedIn.ATTRIBUTE_CLASSID + " = " + DbContract.Classes.TABLE_NAME + "." + DbContract.Classes._ID + "\n" + 
				"ORDER BY " + DbContract.Homework.ATTRIBUTE_DUEDATE;
		}
		else {
			//Choose only from the currentClass
			query = 
				"SELECT * FROM " + DbContract.Homework.TABLE_NAME + " , " + DbContract.AssignedIn.TABLE_NAME + " , " + DbContract.Classes.TABLE_NAME + "\n" +
				"WHERE " + DbContract.Homework.TABLE_NAME + "." + DbContract.Homework._ID + " = " + DbContract.AssignedIn.ATTRIBUTE_HOMEWORKID + "\n" +
				"AND " + DbContract.AssignedIn.ATTRIBUTE_CLASSID + " = " + DbContract.Classes.TABLE_NAME + "." + DbContract.Classes._ID + "\n" + 
				"AND " + DbContract.Classes.ATTRIBUTE_NAME + " = " + "\"" + currentClass + "\"" + "\n" + 
				"ORDER BY " + DbContract.Homework.ATTRIBUTE_DUEDATE;
		}
		
		System.out.println(query);

		Cursor c = theDb.getDB().rawQuery(query, null);
		
		return c;
	}
	
	@Override
	public void addNewItem() {
		
		HWDialogFragment dialog = new HWDialogFragment();
		dialog.show(getActivity().getFragmentManager(), "addHomeworkDialog");
		
		System.out.println("HWfragment");
	}
	
	public void refresh() {
		Cursor c = getListCursor();
		
		( (SimpleCursorAdapter) getListAdapter() ).swapCursor(c);
	}


}
