package com.example.classy;

import java.text.DecimalFormat;

import com.example.classy.database.Db;
import com.example.classy.database.DbContract;
import com.example.classy.utilities.ClassyDate;
import com.example.classy.utilities.ClassyTabFunctionality;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public class GradesFragment extends ListFragment implements ClassyTabFunctionality {
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		//TODO fix "due_date" id
		//TODO fix hw_list_item layout
		
		super.onActivityCreated(savedInstanceState);		

		Cursor c = getListCursor();
		
		String[] colNames = new String[] { DbContract.Grades.ATTRIBUTE_GRADE, DbContract.Grades.ATTRIBUTE_TITLE };
		int[] adapterRowViews = new int[] { R.id.due_date, R.id.hw_list_title };
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.hw_list_item, c, colNames, adapterRowViews, 0);

		adapter.setViewBinder(new ViewBinder() {

		    public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {
	        	System.out.println("in setviewvalue columnindex: " + aColumnIndex);

		        if (aColumnIndex == 2) {
		                double grade = aCursor.getDouble(aColumnIndex);
		                TextView textView = (TextView) aView;
		                textView.setText( new DecimalFormat("#.#").format(grade) );
		                return true;
		         }

		         return false;
		    }
		});
		
		setListAdapter(adapter);
	}
	
	public Cursor getListCursor() {
		Db theDb = Db.getInstance(getActivity());
		String currentClass = ((MainActivity) getActivity()).currentClass;
		
		String query;
		if (currentClass == getActivity().getString(R.string.all_classes)){
			//Choose all classes
			query = 
				"SELECT * FROM " + DbContract.Grades.TABLE_NAME + " , " + DbContract.GivenIn.TABLE_NAME + " , " + DbContract.Classes.TABLE_NAME + "\n" +
				"WHERE " + DbContract.Grades.TABLE_NAME + "." + DbContract.Grades._ID + " = " + DbContract.GivenIn.ATTRIBUTE_GRADESID + "\n" +
				"AND " + DbContract.GivenIn.ATTRIBUTE_CLASSID + " = " + DbContract.Classes.TABLE_NAME + "." + DbContract.Classes._ID + "\n" + 
				"ORDER BY " + DbContract.Grades.ATTRIBUTE_DATE;
		}
		else {
			//Choose only from the currentClass
			query = 
				"SELECT * FROM " + DbContract.Grades.TABLE_NAME + " , " + DbContract.GivenIn.TABLE_NAME + " , " + DbContract.Classes.TABLE_NAME + "\n" +
				"WHERE " + DbContract.Grades.TABLE_NAME + "." + DbContract.Grades._ID + " = " + DbContract.GivenIn.ATTRIBUTE_GRADESID + "\n" +
				"AND " + DbContract.GivenIn.ATTRIBUTE_CLASSID + " = " + DbContract.Classes.TABLE_NAME + "." + DbContract.Classes._ID + "\n" + 
				"AND " + DbContract.Classes.ATTRIBUTE_NAME + " = " + "\"" + currentClass + "\"" + "\n" + 
				"ORDER BY " + DbContract.Grades.ATTRIBUTE_DATE;
		}
		System.out.println(query);

		Cursor c = theDb.getDB().rawQuery(query, null);
		
		return c;
	}
	
	public void addNewItem() {
		
		GradesDialogFragment dialog = new GradesDialogFragment();
		dialog.show(getActivity().getFragmentManager(), "addGradeDialog");

		
		System.out.println("GradesFragment");
	}
	
	public void refresh() {
		Cursor c = getListCursor();
		
		( (SimpleCursorAdapter) getListAdapter() ).swapCursor(c);

	}
	
}
