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
import android.database.Cursor;
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

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class TimeTableScrollTab extends FragmentActivity {
	public final String[] DAY = { "������", "ȭ����", "������", "�����", "�ݿ���" };

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	// String mFileName, tableName;
	public int Grade, WClass;

	private CroutonHelper mHelper;

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
		actionBar.setTitle(Grade + "�г� " + WClass + "�� �ð�ǥ");

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		final SharedPreferences mPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		if (!mPref.getBoolean("YourGradeClass", false)
				&& !mPref.getBoolean("DontShowGradeClass", false)) {

			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("�б� ���� ����");
			alert.setPositiveButton("Ȯ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences.Editor mEdit = mPref.edit();
					mEdit.putInt("YourGrade", Grade);
					mEdit.putInt("YourClass", WClass);
					mEdit.putBoolean("YourGradeClass", true).commit();

					dialog.dismiss();
				}
			});
			alert.setNegativeButton("���", null);
			alert.setNeutralButton("�ٽ�ǥ�þ���", new OnClickListener() {

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

		if (mViewPager.getChildCount() > 0) {
			setCurrentItem();
		}

		mHelper = new CroutonHelper(this);
		mHelper.setText("�� �ð�ǥ�� 100% Ȯ���Ҽ� ������ �������� �����ɼ� �ֽ��ϴ�\n���ÿ��� �����ϴ� �ð�ǥ�� �׻� ��Ȯ�� ���� ������ �����Ͻñ� �ٶ��ϴ�");
		mHelper.setStyle(Style.CONFIRM);
		mHelper.setDuration(5000);
		mHelper.setAutoTouchCencle(true);
		mHelper.show();
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
		} else if (id == R.id.mShare) {
			showShareIntent();
		}

		return super.onOptionsItemSelected(item);
	}

	public void showShareIntent() {
		/**
		 * DB�� ����� �ð�ǥ�� �����´�
		 */
		String SQL = "select * from WondangTimeTable";

		Cursor mCursor = TimeTableShow.mSQDB.rawQuery(SQL, null);

		/**
		 * ���� ����
		 */
		mCursor.moveToPosition((mViewPager.getCurrentItem() * 7) + 1);

		String[] TimeTable = new String[14];

		for (int period = 1; period <= 7; period++) {
			String subject, room;

			if (Grade == 1) {
				subject = mCursor.getString((WClass * 2) - 2);
				room = mCursor.getString((WClass * 2) - 1);
			} else if (Grade == 2) {
				subject = mCursor.getString(18 + (WClass * 2));
				room = mCursor.getString(19 + (WClass * 2));
			} else {
				subject = mCursor.getString(39 + WClass);
				room = "����";
			}

			if (subject != null && !subject.isEmpty()
					&& subject.indexOf("\n") != -1)
				subject = subject.replace("\n", "(") + ")";

			TimeTable[(period * 2) - 2] = subject;
			TimeTable[(period * 2) - 1] = room;

			mCursor.moveToNext();
		}

		String title = String.format(
				getString(R.string.shareTimeTable_message_title),
				DAY[mViewPager.getCurrentItem()]);
		Intent msg = new Intent(Intent.ACTION_SEND);
		msg.addCategory(Intent.CATEGORY_DEFAULT);
		msg.putExtra(Intent.EXTRA_TITLE, title);
		msg.putExtra(Intent.EXTRA_TEXT, String.format(
				getString(R.string.shareTimeTable_message_message),
				DAY[mViewPager.getCurrentItem()], TimeTable[0], TimeTable[1],
				TimeTable[2], TimeTable[3], TimeTable[4], TimeTable[5],
				TimeTable[6], TimeTable[7], TimeTable[8], TimeTable[9],
				TimeTable[10], TimeTable[11], TimeTable[12], TimeTable[13]));
		msg.setType("text/plain");
		startActivity(Intent.createChooser(msg, title));
	}
}
