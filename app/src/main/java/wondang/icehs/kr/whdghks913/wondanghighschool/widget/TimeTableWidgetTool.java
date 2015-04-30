package wondang.icehs.kr.whdghks913.wondanghighschool.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.timetablescroll.TimeTableScrollActivity;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Database;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Preference;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.TimeTableTool;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.copyAssets;

/**
 * Created by 종환 on 2015-02-23.
 */
public class TimeTableWidgetTool {

    /**
     * 2x2 Widget
     */
    public static void updateWidgetData2x2(Context mContext) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, TimeTableWidget2x2.class));

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget2x2(mContext, appWidgetManager, appWidgetId);
        }
    }

    private static void updateAppWidget2x2(Context mContext, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews mViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_timetable_2x2);

        Calendar mCalendar = Calendar.getInstance();

        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd(E)",
                Locale.KOREA);

        mViews.setTextViewText(R.id.mCalender, mFormat.format(mCalendar.getTime()));
        mViews.setTextViewText(R.id.mTimeTable, getTodayTimeTable(mContext, mCalendar));

        PendingIntent layoutPendingIntent = PendingIntent.getActivity(mContext, 0, new Intent(mContext, TimeTableScrollActivity.class), 0);
        mViews.setOnClickPendingIntent(R.id.mWidgetLayout, layoutPendingIntent);

        Intent mIntent = new Intent(mContext, WidgetBroadCast.class);
        mIntent.setAction("itmir.tistory.com.UPDATE_ACTION");
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mViews.setOnClickPendingIntent(R.id.mUpdateLayout, updatePendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, mViews);
    }

    public static String getTodayTimeTable(Context mContext, Calendar mCalendar) {
        Preference mPref = new Preference(mContext);

        int mGrade = mPref.getInt("myGrade", -1);
        int mClass = mPref.getInt("myClass", -1);

        if (mGrade == -1 || mClass == -1) {
            return "학급 정보가 없습니다.";
        }

        int DayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        if (DayOfWeek > 1 && DayOfWeek < 7) {
            DayOfWeek -= 2;
        } else {
            return "시간표가 없습니다.";
        }

        if (TimeTableTool.getDBUpdate(mContext)) {
            copyAssets copyAssets = new copyAssets();
            copyAssets.assetsFileCopy(mContext, TimeTableTool.mFilePath, TimeTableTool.TimeTableDBName);
        }

        boolean mFileExists = new File(TimeTableTool.mFilePath + TimeTableTool.TimeTableDBName).exists();
        if (!mFileExists)
            return "데이터가 없습니다.";

        String mTimeTable = "";

        Database mDatabase = new Database();
        mDatabase.openOrCreateDatabase(TimeTableTool.mFilePath, TimeTableTool.TimeTableDBName, TimeTableTool.tableName, "");

        Cursor mCursor = mDatabase.getData(TimeTableTool.tableName, "*");

        mCursor.moveToPosition((DayOfWeek * 7) + 1);

        for (int period = 1; period <= 7; period++) {
            String mSubject;

            if (mGrade == 1) {
                mSubject = mCursor.getString((mClass * 2) - 2);
//                mRoom = mCursor.getString((mClass * 2) - 1);
            } else if (mGrade == 2) {
                mSubject = mCursor.getString(18 + (mClass * 2));
//                mRoom = mCursor.getString(19 + (mClass * 2));
            } else {
                mSubject = mCursor.getString(39 + mClass);
//                mRoom = null;
            }

            if (mSubject != null && !mSubject.isEmpty()
                    && mSubject.indexOf("\n") != -1)
                mSubject = mSubject.replace("\n", " (") + ")";

            String tmp = Integer.toString(period) + ". " + mSubject;
            mTimeTable += tmp;

            if (mCursor.moveToNext()) {
                mTimeTable += "\n";
            }
        }

        return mTimeTable;
    }
}
