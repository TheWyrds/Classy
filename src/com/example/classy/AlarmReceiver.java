package com.example.classy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{
	
	private Context myContext;
	
	@Override
	public void onReceive(Context c, Intent i){
		myContext = c;
	}

}
