package com.example.pressurebutton;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PressureButtonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pressure_button);
		LinearLayout parent = (LinearLayout) this.findViewById(R.id.root);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		lp.setMargins(10, 10, 10, 10);
		PressureButton pb = new PressureButton(this);
		pb.setText("TESTING");
		pb.setTextSize(32);
		pb.setPadding(17, 17, 17, 17);
		parent.removeAllViews();
		parent.addView(pb, lp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pressure_button, menu);
		return true;
	}

}
