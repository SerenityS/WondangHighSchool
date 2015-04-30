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
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Preference;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Tools;

/**
 * Created by 종환 on 2015-02-23.
 */
public class BapWidgetTool {

    /**
     * 3x3 Widget
     */
    public static void updateWidgetData3x3(Context mContext) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BapWidget3x3.class));

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget3x3(mContext, appWidgetManager, appWidgetId, false);
        }
    }

    public static void updateAppWidget3x3(Context mContext, AppWidgetManager appWidgetManager, int appWidgetId, boolean ifNotUpdate) {
        // Construct the RemoteViews object
        RemoteViews mViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_bap_3x3);

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
                // Only Wifi && Not Wifi
                if (new Preference(mContext).getBoolean("updateWiFi", true) && !Tools.isWifi(mContext)) {
                    mViews.setTextViewText(R.id.mLunch, mContext.getString(R.string.widget_no_data));
                    mViews.setTextViewText(R.id.mDinner, mContext.getString(R.string.widget_no_data));
                }
                // 급식 데이터 받아옴
                else if (ifNotUpdate) {
                    BapWidgetTool.BapDownloadTask mProcessTask = new BapWidgetTool.BapDownloadTask(mContext);
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

    /**
     * 3x2 Widget
     */
    public static void updateWidgetData3x2(Context mContext) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BapWidget3x2.class));

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget3x2(mContext, appWidgetManager, appWidgetId, false);
        }
    }

    public static void updateAppWidget3x2(Context mContext, AppWidgetManager appWidgetManager, int appWidgetId, boolean ifNotUpdate) {
        // Construct the RemoteViews object
        RemoteViews mViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_bap_3x2);

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        BapTool.restoreBapDateClass mData =
                BapTool.restoreBapData(mContext, year, month, day);

        mViews.setTextViewText(R.id.mCalender, mData.Calender);

        if (mData.isBlankDay) {
            // 데이터 없음
            if (Tools.isNetwork(mContext)) {
                // Only Wifi && Not Wifi
                if (new Preference(mContext).getBoolean("updateWiFi", true) && !Tools.isWifi(mContext)) {
                    mViews.setTextViewText(R.id.mLunch, mContext.getString(R.string.widget_no_data));
                } else if (ifNotUpdate) {
                    // 급식 데이터 받아옴
                    BapWidgetTool.BapDownloadTask mProcessTask = new BapWidgetTool.BapDownloadTask(mContext);
                    mProcessTask.execute(year, month, day);
                }
            } else {
                mViews.setTextViewText(R.id.mLunch, mContext.getString(R.string.widget_no_data));
            }
        } else {
            // 데이터 있음

            /**
             * hour : 0~23
             *
             * 0~13 : Lunch
             * 14~23 : Dinner
             */
            String mTitle, mTodayMeal;
            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            if (hour <= 13) {
                mTitle = mContext.getString(R.string.today_lunch);
                mTodayMeal = mData.Lunch;
                if (BapTool.mStringCheck(mTodayMeal))
                    mTodayMeal = mContext.getString(R.string.no_data_lunch);
                else mTodayMeal = replaceString(mTodayMeal);
            } else {
                mTitle = mContext.getString(R.string.today_dinner);
                mTodayMeal = mData.Dinner;
                if (BapTool.mStringCheck(mTodayMeal))
                    mTodayMeal = mContext.getString(R.string.no_data_dinner);
                else mTodayMeal = replaceString(mTodayMeal);
            }

            mViews.setTextViewText(R.id.mLunchTitle, mTitle);
            mViews.setTextViewText(R.id.mLunch, mTodayMeal);
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
//            mIntent.setAction("itmir.tistory.com.UPDATE_ACTION");
            mContext.sendBroadcast(mIntent);
        }
    }
}
