package com.example.pressurebutton;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PressureButtonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pressure_button);
		LinearLayout parent = (LinearLayout) this.findViewById(R.id.root);
		PressureButton pb = new PressureButton(this);
		pb.setText("TESTING");
		pb.setTextColor(Color.BLACK);
		pb.setTextSize(22);
		parent.removeAllViews();
		parent.addView(pb);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pressure_button, menu);
		return true;
	}

}
