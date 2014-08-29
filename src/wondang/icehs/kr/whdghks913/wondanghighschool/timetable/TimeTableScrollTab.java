package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import java.util.Calendar;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class TimeTableScrollTab extends FragmentActivity {
	public final String[] DAY = { "월요일", "화요일", "수요일", "목요일", "금요일" };

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	// String mFileName, tableName;
	public int Grade, WClass;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetablescrolltab);

		Intent mIntent = getIntent();

		Grade = mIntent.getIntExtra("Grade", 1);
		WClass = mIntent.getIntExtra("WClass", 2);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getApplicationContext(), getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle(Grade + "학년 " + WClass + "반 시간표");

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mViewPager.getChildCount() > 0) {
			setCurrentItem();
		}
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		Context mContext;

		public SectionsPagerAdapter(Context context, FragmentManager fm) {
			super(fm);
			mContext = context;
		}

		@Override
		public Fragment getItem(int position) {
			return new TimeTableShow(mContext, position, Grade, WClass);
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return DAY[position];
		}
	}

	private void setCurrentItem() {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		if (dayOfWeek > 1 && dayOfWeek < 7) {
			mViewPager.setCurrentItem(dayOfWeek - 2);
		} else {
			mViewPager.setCurrentItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timetable, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.today) {
			setCurrentItem();
		}

		return super.onOptionsItemSelected(item);
	}

}
