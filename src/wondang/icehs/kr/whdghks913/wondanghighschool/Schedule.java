package wondang.icehs.kr.whdghks913.wondanghighschool;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class Schedule extends Activity {
	private final String loadingList = "�����͸� �������� �ֽ��ϴ�..";
	private final String monthError = "����/���� ���� �����ϴ�";
	private final String noData = "�����Ͱ� �������� �ʽ��ϴ�,\n���� ������Ʈ�� �����Ͱ� �߰��˴ϴ�";

	private ScheduleListViewAdapter mAdapter;
	private ListView mListView;
	private Handler mHandler;

	private CroutonHelper mHelper;

	private ProgressDialog mDialog;

	private Calendar mCalendar;
	private SharedPreferences ScheduleList, Info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		mListView = (ListView) findViewById(R.id.mScheduleList);
		mAdapter = new ScheduleListViewAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View mView,
					int position, long id) {
				ScheduleListData mData = mAdapter.getItem(position);
				getTime(mData.year, mData.month, mData.day);
			}
		});

		mHandler = new MyHandler(this);

		mCalendar = Calendar.getInstance();

		Info = getSharedPreferences("Info", 0);

		mHelper = new CroutonHelper(this);
		mHelper.setText("�б� ���� ���� �Դϴ�");
		mHelper.setStyle(Style.INFO);
		mHelper.setAutoTouchCencle(true);
		mHelper.show();

		sync();
	}

	private void sync() {
		mAdapter.clearData();

		new Thread() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);

				try {
					PackageManager packageManager = Schedule.this
							.getPackageManager();
					PackageInfo infor = packageManager.getPackageInfo(
							getPackageName(), PackageManager.GET_META_DATA);
					final int code = infor.versionCode;

					if (Info.getInt("update_code", 0) != code || isDataCheck()) {
						PreferenceData mData = new PreferenceData();

						mData.copyDB(Schedule.this, getPackageName(),
								"March.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"April.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"May.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"June.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"July.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"August.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"September.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"October.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"November.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"December.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"January.xml", true);
						mData.copyDB(Schedule.this, getPackageName(),
								"February.xml", true);

						Info.edit().putInt("update_code", code).commit();
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}

				mHandler.sendEmptyMessage(1);
			}
		}.start();
	}

	private boolean isDataCheck() {
		for (int i = 0; i < 10; i++) {
			ScheduleList = getSharedPreferences(getMonth(i), 0);

			if (ScheduleList.getInt("days", 9999) != 9999) {
				ScheduleList = null;
				return true;
			}
		}
		ScheduleList = null;
		return false;
	}

	private String getMonth(int month) {
		String Month = null;

		switch (month) {
		case 0:
			Month = "January";
			break;
		case 1:
			Month = "February";
			break;
		case 2:
			Month = "March";
			break;
		case 3:
			Month = "April";
			break;
		case 4:
			Month = "May";
			break;
		case 5:
			Month = "June";
			break;
		case 6:
			Month = "July";
			break;
		case 7:
			Month = "August";
			break;
		case 8:
			Month = "September";
			break;
		case 9:
			Month = "October";
			break;
		case 10:
			Month = "November";
			break;
		case 11:
			Month = "December";
			break;
		}
		return Month;
	}

	private String getMonthKorean(int month) {
		switch (month) {
		case 0:
			return "2015�� 1��";
		case 1:
			return "2015�� 2��";
		case 2:
			return "2014�� 3��";
		case 3:
			return "2014�� 4��";
		case 4:
			return "2014�� 5��";
		case 5:
			return "2014�� 6��";
		case 6:
			return "2014�� 7��";
		case 7:
			return "2014�� 8��";
		case 8:
			return "2014�� 9��";
		case 9:
			return "2014�� 10��";
		case 10:
			return "2014�� 11��";
		case 11:
			return "2014�� 12��";
		}
		return null;
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mDialog != null)
			mDialog.dismiss();

		mHelper.cencle(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.schedule, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		int ItemId = item.getItemId();

		if (ItemId == R.id.back) {
			int year = mCalendar.get(Calendar.YEAR);
			int month = mCalendar.get(Calendar.MONTH);
			int day = mCalendar.get(Calendar.DAY_OF_MONTH);

			if (--month < 0) {
				mHelper.clearCroutonsForActivity();
				mHelper.setText(monthError);
				mHelper.setStyle(Style.ALERT);
				mHelper.show();
			} else {
				mCalendar.set(year, month, day);
				sync();
			}

		} else if (ItemId == R.id.forward) {
			int year = mCalendar.get(Calendar.YEAR);
			int month = mCalendar.get(Calendar.MONTH);
			int day = mCalendar.get(Calendar.DAY_OF_MONTH);

			if (++month > 11) {
				mHelper.clearCroutonsForActivity();
				mHelper.setText(monthError);
				mHelper.setStyle(Style.ALERT);
				mHelper.show();
			} else {
				mCalendar.set(year, month, day);
				sync();
			}

		} else if (ItemId == R.id.sync) {
			sync();
		}

		return super.onOptionsItemSelected(item);
	}

	private void getTime(int year, int month, int day) {
		Calendar myTime = Calendar.getInstance();

		long nowTime = myTime.getTimeInMillis();
		myTime.set(year, month, day);
		long touchTime = myTime.getTimeInMillis();

		long diff = (touchTime - nowTime);

		boolean isPast = false;
		if (diff < 0) {
			diff = -diff;
			isPast = true;
		}

		int diffInt = (int) (diff /= 24 * 60 * 60 * 1000);

		String Text;
		if (isPast)
			Text = "�����Ͻ� ��¥�� " + diffInt + "���� ��¥�Դϴ�";
		else
			Text = "�����Ͻ� ��¥���� " + diffInt + "�� ���ҽ��ϴ�";

		mHelper.clearCroutonsForActivity();
		mHelper.setText(Text);
		mHelper.setStyle(Style.INFO);
		mHelper.show();
	}

	private class MyHandler extends Handler {
		private final WeakReference<Schedule> mActivity;

		public MyHandler(Schedule Schedule) {
			mActivity = new WeakReference<Schedule>(Schedule);
		}

		@Override
		public void handleMessage(Message msg) {
			Schedule activity = mActivity.get();
			if (activity != null) {

				if (msg.what == 0) {
					if (mDialog == null) {
						mDialog = ProgressDialog.show(Schedule.this, "",
								loadingList);
					}

				} else if (msg.what == 1) {
					int month = mCalendar.get(Calendar.MONTH);

					ScheduleList = getSharedPreferences(getMonth(month), 0);

					int days = ScheduleList.getInt("days", 9999);

					if (days != 9999) {

						for (int i = 1; i < days; i++) {
							String toString = Integer.toString(i);
							String Schedule = ScheduleList.getString(toString,
									null);

							if (Schedule == null)
								continue;

							String dayOfWeek = ScheduleList.getString(toString
									+ "_Day", null);
							boolean isHoliday = ScheduleList.getBoolean(
									toString + "_Day_holiday", false);

							if (i < 10)
								toString = "0" + toString;

							int year = mCalendar.get(Calendar.YEAR);
							if (month == 0 || month == 1)
								year = 2015;

							mAdapter.addItem(toString + "��", dayOfWeek,
									Schedule, isHoliday, year,
									mCalendar.get(Calendar.MONTH), i);
						}
					} else {
						mHelper.clearCroutonsForActivity();
						mHelper.setText(noData);
						mHelper.setStyle(Style.ALERT);
						mHelper.show();
					}

					((TextView) findViewById(R.id.mMonth))
							.setText(getMonthKorean(mCalendar
									.get(Calendar.MONTH)));

					mAdapter.notifyDataSetChanged();
					mDialog.dismiss();
				}
			}
		}
	}
}
