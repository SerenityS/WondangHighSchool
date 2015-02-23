package wondang.icehs.kr.whdghks913.wondanghighschool.tool;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import toast.library.meal.MealLibrary;

/**
 * Created by 종환 on 2015-02-17.
 */
public class BapTool {
    public static final String BAP_PREFERENCE_NAME = "BapData";
    public static final int TYPE_LUNCH = 1;
    public static final int TYPE_DINNER = 2;

    public static final String ACTION_UPDATE = "ACTION_UPDATE";

    public static String getBapStringFormat(int year, int month, int day, int type) {
        /**
         * Format : year-month-day-TYPE
         */
        return year + "-" + month + "-" + day + "-" + type;
    }

    /**
     * Pref Name Format : 2015-02-17-TYPE_index
     * ex) 2015-02-17-1_3
     */
    public static void saveBapData(Context mContext, String[] Calender, String[] Lunch, String[] Dinner) {
        Preference mPref = new Preference(mContext, BAP_PREFERENCE_NAME);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd(E)",
                Locale.KOREA);

        for (int index = 0; index < Calender.length; index++) {
            try {
                Calendar mDate = Calendar.getInstance();
                mDate.setTime(mFormat.parse(Calender[index]));

                int year = mDate.get(Calendar.YEAR);
                int month = mDate.get(Calendar.MONTH) + 1;
                int day = mDate.get(Calendar.DAY_OF_MONTH);

                String mPrefLunchName = getBapStringFormat(year, month, day, TYPE_LUNCH);
                String mPrefDinnerName = getBapStringFormat(year, month, day, TYPE_DINNER);

                String mLunch = Lunch[index];
                String mDinner = Dinner[index];

                if (!MealLibrary.isMealCheck(mLunch)) mLunch = "";
                if (!MealLibrary.isMealCheck(mDinner)) mDinner = "";

                mPref.putString(mPrefLunchName, mLunch);
                mPref.putString(mPrefDinnerName, mDinner);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Format : 2015-2-11-2
     */
    public static restoreBapDateClass restoreBapData(Context mContext, int year, int month, int day) {
        Preference mPref = new Preference(mContext, BAP_PREFERENCE_NAME);
        SimpleDateFormat mCalenderFormat = new SimpleDateFormat("yyyy년 MM월 dd일",
                Locale.KOREA);
        SimpleDateFormat mDayofWeekFormat = new SimpleDateFormat("E요일",
                Locale.KOREA);
        Calendar mDate = Calendar.getInstance();
        mDate.set(year, month, day);

        /*Log.e("YEAR", "" + mDate.get(Calendar.YEAR));
        Log.e("MONTH", "" + mDate.get(Calendar.MONTH));
        Log.e("DAY_OF_MONTH", "" + mDate.get(Calendar.DAY_OF_MONTH));*/

        restoreBapDateClass mData = new restoreBapDateClass();

        String mPrefLunchName = getBapStringFormat(year, month + 1, day, TYPE_LUNCH);
        String mPrefDinnerName = getBapStringFormat(year, month + 1, day, TYPE_DINNER);

        /*Log.e("mPrefLunchName", "" + mPrefLunchName);
        Log.e("mPrefDinnerName", "" + mPrefDinnerName);*/

        mData.Calender = mCalenderFormat.format(mDate.getTime());
        mData.DayOfTheWeek = mDayofWeekFormat.format(mDate.getTime());
        mData.Lunch = mPref.getString(mPrefLunchName, null);
        mData.Dinner = mPref.getString(mPrefDinnerName, null);

        /*Log.e("mData.Calender", "" + mData.Calender);
        Log.e("mData.DayOfTheWeek", "" + mData.DayOfTheWeek);
        Log.e("mData.Lunch", "" + mData.Lunch);
        Log.e("mData.Dinner", "" + mData.Dinner);*/

        if (mData.Lunch == null && mData.Dinner == null) {
            mData.isBlankDay = true;
        }

        return mData;
    }

    public static class restoreBapDateClass {
        public String Calender;
        public String DayOfTheWeek;
        public String Lunch;
        public String Dinner;
        public boolean isBlankDay = false;
    }
}
