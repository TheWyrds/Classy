package com.example.classy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.classy.database.Db;
import com.example.classy.database.DbContract;
import com.example.classy.utilities.ClassyTabFunctionality;

public class HomeworkFragment extends ListFragment implements ClassyTabFunctionality {
	
	Button startSetDialogButton;
	TextView textAlarmDescription;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		
		String currentClass = ((MainActivity) getActivity()).currentClass;
		
		Db theDb = Db.getInstance(getActivity());
		
		//Cursor c = theDb.getDB().rawQuery("SELECT, null)

	/*	ArrayList<Map<String, String>> data = getListForListView();
				
		SimpleAdapter adapter = 
				new SimpleAdapter(getActivity(), data, R.layout.hw_list_item, 
						new String[] {DbContract.Homework.ATTRIBUTE_DUEDATE, DbContract.Homework.ATTRIBUTE_TITLE}, 
						new int[] {R.id.due_date, R.id.hw_list_title} );
	*/
		String query = 
				"SELECT * FROM " + DbContract.Homework.TABLE_NAME + " , " + DbContract.AssignedIn.TABLE_NAME + " , " + DbContract.Classes.TABLE_NAME + "\n" +
				"WHERE " + DbContract.Homework.TABLE_NAME + "." + DbContract.Homework._ID + " = " + DbContract.AssignedIn.ATTRIBUTE_HOMEWORKID + "\n" +
				"AND " + DbContract.AssignedIn.ATTRIBUTE_CLASSID + " = " + DbContract.Classes.TABLE_NAME + "." + DbContract.Classes._ID + "\n" + 
				"AND " + DbContract.Classes.ATTRIBUTE_NAME + " = " + "\"" + currentClass + "\"";
		System.out.println(query);
		Cursor c = theDb.getDB().rawQuery(query, null);
		
		String[] colNames = new String[] { DbContract.Homework.ATTRIBUTE_DUEDATE, DbContract.Homework.ATTRIBUTE_TITLE };
		int[] adapterRowViews = new int[] { R.id.due_date, R.id.hw_list_title };
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.hw_list_item, c, colNames, adapterRowViews, 0);

		setListAdapter(adapter);
	}
	
	
	@Override
	public void addNewItem() {
		
		System.out.println("HWfragment");
	}
	
	private ArrayList<Map<String, String>> getListForListView() {
		
		ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
		String currentClass = ((MainActivity) getActivity()).currentClass;
		
		String[] hwTitles = ((MainActivity) getActivity()).hwTitles;
		String[] hwClasses = ((MainActivity) getActivity()).hwClasses;
		String[] hwDueDates = ((MainActivity) getActivity()).hwDueDates;
		
		for (int i = 0; i < hwClasses.length; i++) {
			System.out.println(hwClasses[i]);
			
			if (hwClasses[i].equals(currentClass)) {
				System.out.println("in if on " + i);
				Map<String, String> newRow = new HashMap<String, String>();
				newRow.put("dueDate", hwDueDates[i]);
				newRow.put("title", hwTitles[i]);
				data.add(newRow);
				
				System.out.println(hwDueDates[i] + "  " + hwTitles[i]);
			}
		}
		
		return data;
	}
	

}
