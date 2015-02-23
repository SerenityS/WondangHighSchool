package wondang.icehs.kr.whdghks913.wondanghighschool.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

public class WidgetBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        String ACTION = mIntent.getAction();

        if (Intent.ACTION_DATE_CHANGED.equals(ACTION)) {
            WidgetTool.updateWidgetData(mContext);
        } else if ("itmir.tistory.com.UPDATE_ACTION".equals(ACTION)) {
            Toast.makeText(mContext, R.string.widget_updating, Toast.LENGTH_SHORT).show();
            WidgetTool.updateWidgetData(mContext);
        }
    }
}
