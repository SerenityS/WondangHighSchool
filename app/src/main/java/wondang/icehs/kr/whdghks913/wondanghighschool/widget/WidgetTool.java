package wondang.icehs.kr.whdghks913.wondanghighschool.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Calendar;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.bap.BapActivity;
import wondang.icehs.kr.whdghks913.wondanghighschool.bap.ProcessTask;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.BapTool;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Tools;

/**
 * Created by 종환 on 2015-02-23.
 */
public class WidgetTool {

    public static void updateWidgetData(Context mContext) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BapWidget.class));

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(mContext, appWidgetManager, appWidgetId, false);
        }
    }

    public static void updateAppWidget(Context mContext, AppWidgetManager appWidgetManager, int appWidgetId, boolean ifNotUpdate) {
        // Construct the RemoteViews object
        RemoteViews mViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_bap);

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        BapTool.restoreBapDateClass mData =
                BapTool.restoreBapData(mContext, year, month, day);

        mViews.setTextViewText(R.id.mCalender, mData.Calender);
        mViews.setTextViewText(R.id.mDayOfTheWeek, mData.DayOfTheWeek);

        if (mData.isBlankDay) {
            // 데이터 없음
            if (Tools.isNetwork(mContext)) {
                // 급식 데이터 받아옴
                if (ifNotUpdate) {
                    WidgetTool.BapDownloadTask mProcessTask = new WidgetTool.BapDownloadTask(mContext);
                    mProcessTask.execute(year, month, day);
                }
            } else {
                mViews.setTextViewText(R.id.mLunch, mContext.getString(R.string.widget_no_data));
                mViews.setTextViewText(R.id.mDinner, mContext.getString(R.string.widget_no_data));
            }
        } else {
            // 데이터 있음

            String mLunch = mData.Lunch;
            String mDinner = mData.Dinner;

            if (BapTool.mStringCheck(mLunch)) {
                mLunch = mContext.getString(R.string.no_data_lunch);
            } else {
                mLunch = replaceString(mLunch);
            }

            if (BapTool.mStringCheck(mDinner)) {
                mDinner = mContext.getString(R.string.no_data_dinner);
            } else {
                mDinner = replaceString(mDinner);
            }

            mViews.setTextViewText(R.id.mLunch, mLunch);
            mViews.setTextViewText(R.id.mDinner, mDinner);
        }

        PendingIntent layoutPendingIntent = PendingIntent.getActivity(mContext, 0, new Intent(mContext, BapActivity.class), 0);
        mViews.setOnClickPendingIntent(R.id.mWidgetLayout, layoutPendingIntent);

        Intent mIntent = new Intent(mContext, WidgetBroadCast.class);
        mIntent.setAction("itmir.tistory.com.UPDATE_ACTION");
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mViews.setOnClickPendingIntent(R.id.mUpdateLayout, updatePendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, mViews);
    }

    public static String replaceString(String mString) {
        String[] mTrash = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩", "⑪", "⑫", "⑬"};
        for (String e : mTrash) {
            mString = mString.replace(e, "");
        }

        mString = mString.replace("\n", "  ");

        return mString;
    }

    public static class BapDownloadTask extends ProcessTask {
        Context mContext;

        public BapDownloadTask(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

        @Override
        public void onPreDownload() {
        }

        @Override
        public void onUpdate(int progress) {
        }

        @Override
        public void onFinish(long result) {
            Intent mIntent = new Intent(mContext, WidgetBroadCast.class);
            mIntent.setAction("itmir.tistory.com.UPDATE_ACTION");
            mContext.sendBroadcast(mIntent);
        }
    }
}
