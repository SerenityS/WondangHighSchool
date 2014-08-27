package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TimeTableGrade extends Activity {

	private ListView mListView;
	private ListViewAdapter mAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss_fullrss);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			ActionBar actionBar = getActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		mListView = (ListView) findViewById(R.id.mListView);

		mAdapter = new ListViewAdapter(this);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

			}
		});

		mAdapter.addItem("1학년 시간표", "WondangTimeTableG1");
		mAdapter.addItem("2학년 시간표", "WondangTimeTableG2");
		mAdapter.addItem("3학년 시간표", "WondangTimeTableG3");
	}
}
