package wondang.icehs.kr.whdghks913.wondanghighschool.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class BapWidget3x3 extends AppWidgetProvider {

    @Override
    public void onUpdate(Context mContext, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(mContext, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        super.onReceive(mContext, mIntent);

        String mAction = mIntent.getAction();
        if (mAction.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            WidgetTool.updateWidgetData3x3(mContext);
        }
//        else if (mAction.equals(AppWidgetManager.ACTION_APPWIDGET_DISABLED)) { }
    }

    @Override
    public void onEnabled(Context mContext) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context mContext) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context mContext, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        WidgetTool.updateAppWidget3x3(mContext, appWidgetManager, appWidgetId, true);
    }
}


