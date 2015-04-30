package wondang.icehs.kr.whdghks913.wondanghighschool.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class BapWidget3x2 extends AppWidgetProvider {

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
            BapWidgetTool.updateWidgetData3x2(mContext);
        }
//        else if (mAction.equals(AppWidgetManager.ACTION_APPWIDGET_DISABLED)) { }
    }

    static void updateAppWidget(Context mContext, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        BapWidgetTool.updateAppWidget3x2(mContext, appWidgetManager, appWidgetId, true);
    }
}


