package wondang.icehs.kr.whdghks913.wondanghighschool.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

public class WidgetBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        String ACTION = mIntent.getAction();

        WidgetTool.updateWidgetData3x3(mContext);
        WidgetTool.updateWidgetData3x2(mContext);

        if (Intent.ACTION_BOOT_COMPLETED.equals(ACTION)) {
            Calendar mCalendar = Calendar.getInstance();
            AlarmManager mAlarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

            Intent mIntentDate = new Intent(mContext, WidgetBroadCast.class).setAction("itmir.tistory.com.UPDATE_ACTION");
            PendingIntent mPending = PendingIntent.getBroadcast(mContext, 0, mIntentDate, 0);
            mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH) + 1, 0, 0);
            mAlarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 24 * 60 * 60 * 1000, mPending);
        }
    }
}
