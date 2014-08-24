package wondang.icehs.kr.whdghks913.wondanghighschool.bapautoupdate;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class updateAlarm {
	private AlarmManager mAlarm;
	private PendingIntent mIntent;
	private Context mContext;
	private Calendar mCalendar;

	public updateAlarm(Context mContext) {
		this.mContext = mContext;
		this.mCalendar = Calendar.getInstance();
		this.mAlarm = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
	}

	public void autoUpdate() {
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);

		Intent intent = new Intent(mContext, BroadCast.class);
		intent.setAction("ACTION_UPDATE");
		mIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		mCalendar.set(year, month, day + 1, 1, 0);
		mAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
				mCalendar.getTimeInMillis(), 24 * 60 * 60 * 1000, mIntent);
	}

	public void SundayUpdate() {
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

		// 저번주 일요일을 구한다
		mCalendar.add(Calendar.DAY_OF_WEEK, -1 * (dayOfWeek - 1));
		// 다음주 일요일을 구한다
		mCalendar.add(Calendar.DAY_OF_WEEK, 7);

		day = mCalendar.get(Calendar.DAY_OF_MONTH);

		Intent intent = new Intent(mContext, BroadCast.class);
		intent.setAction("ACTION_UPDATE");
		mIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		mCalendar.set(year, month, day, 1, 0);
		mAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
				mCalendar.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, mIntent);
	}

	public void SaturdayUpdate() {
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

		// 저번주 일요일을 구한다
		mCalendar.add(Calendar.DAY_OF_WEEK, -1 * (dayOfWeek - 1));
		// 다음주 토요일을 구한다
		mCalendar.add(Calendar.DAY_OF_WEEK, 6);

		day = mCalendar.get(Calendar.DAY_OF_MONTH);

		Intent intent = new Intent(mContext, BroadCast.class);
		intent.setAction("ACTION_UPDATE");
		mIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		mCalendar.set(year, month, day, 1, 0);
		mAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
				mCalendar.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, mIntent);
	}

	public void wifiOFF() {
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		int hour = mCalendar.get(Calendar.HOUR_OF_DAY);

		Intent intent = new Intent(mContext, BroadCast.class);
		intent.setAction("ACTION_UPDATE");
		mIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		mCalendar.set(year, month, day, hour + 2, 0);
		mAlarm.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),
				mIntent);
	}

	public void cancle() {
		Intent intent = new Intent(mContext, BroadCast.class);
		intent.setAction("ACTION_UPDATE");
		mIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

		mAlarm.cancel(mIntent);
	}

}
