package com.example.classy;

import android.app.Fragment;

import com.example.classy.utilities.ClassyTabFunctionality;

public class NotesFragment extends Fragment implements ClassyTabFunctionality {

	
	@Override
	public void addNewItem() {
		System.out.println("notesfragment add new item");
	}
}
