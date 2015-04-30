package wondang.icehs.kr.whdghks913.wondanghighschool.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Implementation of App Widget functionality.
 */
public class TimeTableWidget2x2 extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        super.onReceive(mContext, mIntent);

        String mAction = mIntent.getAction();
        if (mAction.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            TimeTableWidgetTool.updateWidgetData2x2(mContext);
        }
    }

    static void updateAppWidget(Context mContext, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        TimeTableWidgetTool.updateWidgetData2x2(mContext);
    }
}


