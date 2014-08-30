package wondang.icehs.kr.whdghks913.wondanghighschool.bapautoupdate;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BroadCast extends BroadcastReceiver {
	private SharedPreferences mPref;

	@Override
	public void onReceive(Context mContext, Intent mIntent) {
		String ACTION = mIntent.getAction();
		mPref = PreferenceManager.getDefaultSharedPreferences(mContext);

		boolean autoUpdate = mPref.getBoolean("autoBapUpdate", false);
		if (!autoUpdate)
			return;

		int updateLife = 0;

		try {
			updateLife = Integer.parseInt(mPref.getString("updateLife", "0"));
		} catch (Exception e) {
			return;
		}

		Calendar mCalendar = Calendar.getInstance();
		int weekday = mCalendar.get(Calendar.DAY_OF_WEEK);

		if (Intent.ACTION_BOOT_COMPLETED.equals(ACTION)) {
			/**
			 * ������ ����
			 */
			if (weekday == Calendar.SUNDAY && updateLife == -1) {
				// �޽� ������Ʈ ȣ��
				if (haveToUpdate(mContext, mCalendar))
					mContext.startService(new Intent(mContext,
							updateService.class));
			} else if (weekday == Calendar.SATURDAY && updateLife == 0) {
				// �޽� ������Ʈ ȣ��
				if (haveToUpdate(mContext, mCalendar))
					mContext.startService(new Intent(mContext,
							updateService.class));
			}

			updateAlarm updateAlarm = new updateAlarm(mContext);
			if (updateLife == 1) {
				// �ڵ� ����
				updateAlarm.autoUpdate();
			} else if (updateLife == 0) {
				// ����� ����
				updateAlarm.SaturdayUpdate();
			} else if (updateLife == -1) {
				// �Ͽ��� ����
				updateAlarm.SundayUpdate();
			}

		} else if ("ACTION_UPDATE".equals(ACTION)) {
			/**
			 * ������Ʈ �׼�
			 */
			if (haveToUpdate(mContext, mCalendar)) {
				mContext.startService(new Intent(mContext, updateService.class));
			}
		} else if ("ACTION_UPDATE_AUTO".equals(ACTION)) {
			/**
			 * �ڵ� ������Ʈ �׼�
			 */
			if (haveToUpdate(mContext, mCalendar)) {
				mContext.startService(new Intent(mContext, updateService.class));
			}
		}
	}

	private boolean haveToUpdate(Context mContext, Calendar mCalendar) {
		SharedPreferences bapList = mContext.getSharedPreferences("bapList", 0);

		int mCalendarMonth = mCalendar.get(Calendar.MONTH);
		int mCalendarDay = mCalendar.get(Calendar.DAY_OF_MONTH);

		int updateMonth = bapList.getInt("updateMonth", 0);
		int updateDay = bapList.getInt("updateDay", 0);

		if (updateMonth == 0 && updateDay == 0)
			return true;

		if (updateMonth == mCalendarMonth && updateDay == mCalendarDay)
			return false;

		return true;
	}
}
