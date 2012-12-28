package com.example.pressurebutton;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PressureButtonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pressure_button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pressure_button, menu);
		return true;
	}

}
