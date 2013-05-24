package com.theWyrds.classy;

import java.util.Date;

import com.theWyrds.classy.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class HWDialogFragment extends DialogFragment{
	
	public interface HWDialogListener {
		public void onHWDialogPositiveClick(View dialogView);
	}
	
	HWDialogListener mListener;
	
	View view = null;
	EditText editText;
	TextView textView;
	
	TimePicker myTimePicker;
	TimePickerDialog timePickerDialog;
				
	HomeworkFragment hf;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (HWDialogListener) activity; 
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement HWDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.add_homework_dialog_fragment, null);
		
		builder.setView(view)
			   .setTitle(R.string.hw_dialog_title)
			   .setPositiveButton("Save", new DialogInterface.OnClickListener(){
				   	public void onClick(DialogInterface dialog, int id){
				   		mListener.onHWDialogPositiveClick(view);
				   	}
			   })
			   .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					   //Do a lot of nothing
				   }
			   });
		
		//Initialize datepicker
		DatePicker datePicker = (DatePicker) view.findViewById(R.id.hw_dialog_date_picker);
		datePicker.setCalendarViewShown(false);
		datePicker.setMinDate( new Date().getTime() - 1000 );		// - 1000 milliseconds to ensure min time is less than current time
		
		return builder.create();
	}
}
