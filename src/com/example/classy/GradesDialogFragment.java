package com.example.classy;

import java.util.Date;

import com.example.classy.HWDialogFragment.HWDialogListener;

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

public class GradesDialogFragment extends DialogFragment {

	public interface GradesDialogListener {
		public void onGradesDialogPositiveClick(View dialogView);
	}
	
	GradesDialogListener mListener;
	View view;	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (GradesDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement GradesDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.add_grade_dialog_fragment, null);
		
		builder.setView(view)
			   .setTitle(R.string.grade_dialog_title)
			   .setPositiveButton("Save", new DialogInterface.OnClickListener(){
				   	public void onClick(DialogInterface dialog, int id){
				   		mListener.onGradesDialogPositiveClick(view);
				   	}
			   })
			   .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					   //Do a lot of nothing
				   }
			   });
		
		return builder.create();
	}
	
}
