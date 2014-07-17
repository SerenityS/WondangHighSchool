package wondang.icehs.kr.whdghks913.wondanghighschool.teacher;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Teacher extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teacher, menu);
		return true;
	}

}
