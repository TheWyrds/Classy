package com.example.classy;

import com.example.classy.utilities.ClassyTabFunctionality;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class HomeworkFragment extends ListFragment implements ClassyTabFunctionality {
	
	Button startSetDialogButton;
	TextView textAlarmDescription;
	
	HWDialogFragment hwDialog = new HWDialogFragment();
	
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
		
	}
	

}
