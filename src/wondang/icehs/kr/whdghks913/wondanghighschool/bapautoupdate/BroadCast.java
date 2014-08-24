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

		if (Intent.ACTION_BOOT_COMPLETED.equals(ACTION)) {
			boolean autoUpdate = mPref.getBoolean("autoBapUpdate", false);
			if (!autoUpdate)
				return;

			int updateLife = 1;

			try {
				updateLife = Integer.parseInt(mPref
						.getString("updateLife", "1"));
			} catch (Exception e) {
				return;
			}

			Calendar mCalendar = Calendar.getInstance();
			int weekday = mCalendar.get(Calendar.DAY_OF_WEEK);

			if (weekday == Calendar.SUNDAY && updateLife == -1) {
				// �޽� ������Ʈ ȣ��
				mContext.startService(new Intent(mContext, updateService.class));
			} else if (weekday == Calendar.SATURDAY && updateLife == 0) {
				// �޽� ������Ʈ ȣ��
				mContext.startService(new Intent(mContext, updateService.class));
			} else if (updateLife == 1) {
				// �ڵ� ����
				updateAlarm updateAlarm = new updateAlarm(mContext);
				updateAlarm.autoUpdate();
			} else if (updateLife == 0) {
				// ����� ����
				updateAlarm updateAlarm = new updateAlarm(mContext);
				updateAlarm.SaturdayUpdate();
			} else if (updateLife == -1) {
				// �Ͽ��� ����
				updateAlarm updateAlarm = new updateAlarm(mContext);
				updateAlarm.SundayUpdate();
			}
		} else if ("ACTION_UPDATE".equals(ACTION)) {
			mContext.startService(new Intent(mContext, updateService.class));
		}
	}
}
