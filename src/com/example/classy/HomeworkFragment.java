package com.example.classy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.classy.utilities.ClassyTabFunctionality;

public class HomeworkFragment extends ListFragment implements ClassyTabFunctionality {
	
	Button startSetDialogButton;
	TextView textAlarmDescription;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		

		ArrayList<Map<String, String>> data = getListForListView();
				
		SimpleAdapter adapter = 
				new SimpleAdapter(getActivity(), data, R.layout.hw_list_item, 
						new String[] {"dueDate", "title"}, 
						new int[] {R.id.due_date, R.id.hw_list_title} );
		
		setListAdapter(adapter);
		
	
		
	
	//	setListAdapter(new ArrayAdapter<String>(getActivity(),
		//		R.array.))
	}
	
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
														Bundle savedInstanceState){
		
	//	textAlarmDescription = (TextView) view.findViewById(R.id.alarm_description);
	//	startSetDialogButton = (Button) view.findViewById(R.id.alarm_button);
		
		startSetDialogButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				HWDialogFragment fragment = new HWDialogFragment();
				//fragment.show(getChildFragmentManager(), "Alarm Settings");
				fragment.show(getFragmentManager(), "Alarm Settings");
			}
		});
	}
	*/
	
	
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
