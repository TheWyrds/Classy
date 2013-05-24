package com.theWyrds.classy.utilities;

import com.theWyrds.classy.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

//import com.example.classy.R;

public class NewClassDialog extends DialogFragment {

	public interface NewClassDialogListener {
		public void onNewClassDialogPositiveClick(View dialogView);
	}
	
	NewClassDialogListener mListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (NewClassDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement NewClassDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.new_class_dialog, null);
		
		builder.setView(dialogView)
			   .setTitle(R.string.title_new_class_dialog)
			   .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   mListener.onNewClassDialogPositiveClick(dialogView);
				   }
			   })
			   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   //Do nothing
				   }
			   });
		
		return builder.create();
	}
	
}
