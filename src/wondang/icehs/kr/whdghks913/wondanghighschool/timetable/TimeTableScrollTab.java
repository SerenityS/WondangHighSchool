package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import java.util.Calendar;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.SettingsActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

		final SharedPreferences mPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		if (!mPref.getBoolean("YourGradeClass", false)
				&& !mPref.getBoolean("DontShowGradeClass", false)) {

			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("학급 정보 설정");
			alert.setPositiveButton("확인", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences.Editor mEdit = mPref.edit();
					mEdit.putInt("YourGrade", Grade);
					mEdit.putInt("YourClass", WClass);
					mEdit.putBoolean("YourGradeClass", true).commit();

					dialog.dismiss();
				}
			});
			alert.setNegativeButton("취소", null);
			alert.setNeutralButton("다시표시안함", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences.Editor mEdit = mPref.edit();
					mEdit.remove("YourGradeClass");
					mEdit.remove("YourGrade");
					mEdit.remove("YourClass");
					mEdit.putBoolean("DontShowGradeClass", true).commit();

					dialog.dismiss();

				}
			});
			alert.setMessage(R.string.yourGradeClass);
			alert.show();
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
		} else if (id == R.id.mSetting) {
			startActivity(new Intent(this, SettingsActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

}
