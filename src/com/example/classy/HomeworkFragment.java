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
	
	public static class HWDialogFragment extends DialogFragment{
		View view = null;
		EditText editText;
		TextView textView;
		
		TimePicker myTimePicker;
		TimePickerDialog timePickerDialog;
					
		HomeworkFragment hf;
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			view = inflater.inflate(R.layout.dialog_fragment, null);
			
			builder.setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					EditText editText = (EditText)view.findViewById(R.id.homework_description);
					hf.textAlarmDescription.setText(editText.getText().toString());
 
				}

			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					
				}
			});
			
			return builder.create();
		}
	}
}
