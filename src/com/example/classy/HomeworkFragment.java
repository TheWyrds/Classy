package com.example.classy;

<<<<<<< HEAD
import android.app.ListFragment;

public class HomeworkFragment extends ListFragment{
=======
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
>>>>>>> Changed Homework Fragment

public class HomeworkFragment extends ListFragment{
	
	Button startSetDialogButton;
	TextView textAlarmDescription;
	
	HWDialogFragment hwDialog = new HWDialogFragment();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		textAlarmDescription = (TextView)getView().findViewById(R.id.alarmDescription);
		startSetDialogButton = (Button)getView().findViewById(R.id.startDialogBox);
		
		startSetDialogButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				HWDialogFragment fragment = new HWDialogFragment();
				fragment.show(getFragmentManager(), "Alarm Settings");
			}
		});
	}
	
	public static class HWDialogFragment extends DialogFragment{
		View view = null;
		EditText editText;
		TextView textView;
		
		TimePicker myTimePicker;
		TimePickerDialog timePickerDialog;
		
		HomeworkFragment hf = new HomeworkFragment();
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			view = inflater.inflate(R.layout.dialog_fragment, null);
			
			builder.setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					EditText editText = (EditText)view.findViewById(R.id.homework_description);
					hf.textAlarmDescription.setText(editText.getText().toString());
					
					Intent intent = new Intent();
					
					PendingIntent operation = PendingIntent.getActivity(getActivity().getBaseContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
					
					/** Getting a reference to the System Service ALARM_SERVICE */
					AlarmManager alarmManager = (AlarmManager) getActivity().getBaseContext().getSystemService(Context.ALARM_SERVICE);
 
					/** Getting a reference to DatePicker object available in the MainActivity */
					DatePicker dpDate = (DatePicker) getView().findViewById(R.id.dp_date);
 
					/** Getting a reference to TimePicker object available in the MainActivity */
					TimePicker tpTime = (TimePicker) getView().findViewById(R.id.tp_time);
 
					int year = dpDate.getYear();
					int month = dpDate.getMonth();
					int day = dpDate.getDayOfMonth();
					int hour = tpTime.getCurrentHour();
					int minute = tpTime.getCurrentMinute();
 
					/** Creating a calendar object corresponding to the date and time set by the user */
					GregorianCalendar calendar = new GregorianCalendar(year,month,day, hour, minute);
 
					/** Converting the date and time in to milliseconds elapsed since epoch */
					long alarm_time = calendar.getTimeInMillis();
 
					/** Setting an alarm, which invokes the operation at alart_time */
					alarmManager.set(AlarmManager.RTC_WAKEUP  , alarm_time , operation);
 
					/** Alert is set successfully */
					Toast.makeText(getActivity().getBaseContext(), "Alarm has been set!",Toast.LENGTH_LONG).show();

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
