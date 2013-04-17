package com.example.classy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class HWDialogFragment extends DialogFragment{
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
