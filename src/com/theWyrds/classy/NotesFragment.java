package com.theWyrds.classy;

import com.theWyrds.classy.R;
import com.theWyrds.classy.utilities.ClassyTabFunctionality;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class NotesFragment extends Fragment implements ClassyTabFunctionality {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		  View V = inflater.inflate(R.layout.notes_fragment_layout, container, false); 
          //ImageView notesBackgroundImage = (ImageView)V.findViewById(R.id.notes_background_image); 

          return V;
	}
	
	@Override
	public void addNewItem() {
		
	}
	
	@Override
	public void refresh() {
		
	}

}
