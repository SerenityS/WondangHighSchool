package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TimeTableGrade extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss_main);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown
		// list.
				new ArrayAdapter<String>(getActionBarThemedContextCompat(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1,
						new String[] { "1학년", "2학년", "3학년", }), this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getActionBar().getThemedContext();
		} else {
			return this;
		}
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container,
						getFragmentView(getApplicationContext(), position))
				.commit();
		return true;
	}

	private Fragment getFragmentView(Context mContext, int position) {
		return new TimeTableClass(mContext, position + 1);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@SuppressLint("ValidFragment")
	public class TimeTableClass extends Fragment {
		private Context mContext;
		private int Grade;

		private ListView mListView;
		private ListViewAdapter mAdapter;

		public TimeTableClass(Context mContext, int Grade) {
			this.mContext = mContext;
			this.Grade = Grade;
		}

		@SuppressLint("NewApi")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.listview, null);

			mListView = (ListView) view.findViewById(R.id.mListView);

			mAdapter = new ListViewAdapter(mContext);
			mListView.setAdapter(mAdapter);

			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					String dbName = mAdapter.mListData.get(position).dbName;
					String tableName = mAdapter.mListData.get(position).tableName;
				}
			});

			for (int i = 1; i <= 10; i++) {
				mAdapter.addItem(Grade + "학년 " + i + "반", "WondangTimeTableG"
						+ Grade, "grade" + Grade + "class" + i);
			}
			// mAdapter.addItem(null, "교무실 (대표전화)", "032-569-0723");

			return view;
		}
	}

}