package wondang.icehs.kr.whdghks913.wondanghighschool.todaylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

/**
 * Created by 종환 on 2015-02-17.
 */
class TodayViewHolder {
    public TextView mTitleName; // 정규 수업
    public TextView mName; // 교시
    public TextView mTime; // 운영 시간

    public LinearLayout mTitleRow;
    public LinearLayout mNameRow;
}

class TodayListData {
    public String mName;
    public String mTime;
    public String mInfo;
    public boolean isTitle;
}

public class TodayListAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<TodayListData> mListData = new ArrayList<TodayListData>();

    public TodayListAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

    public void addItem(String mName, String mTime, String mInfo, boolean isTitle) {

        TodayListData addItemInfo = new TodayListData();
        addItemInfo.mName = mName;
        addItemInfo.mTime = mTime;
        addItemInfo.mInfo = mInfo;
        addItemInfo.isTitle = isTitle;

        mListData.add(addItemInfo);
    }

    public void clearData() {
        mListData.clear();
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public TodayListData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TodayViewHolder mHolder;

        if (convertView == null) {
            mHolder = new TodayViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_todaylist_item, null);

            mHolder.mTitleName = (TextView) convertView
                    .findViewById(R.id.mTitleName);
            mHolder.mName = (TextView) convertView
                    .findViewById(R.id.mName);
            mHolder.mTime = (TextView) convertView.findViewById(R.id.mTime);

            mHolder.mTitleRow = (LinearLayout) convertView.findViewById(R.id.mTitleRow);
            mHolder.mNameRow = (LinearLayout) convertView.findViewById(R.id.mNameRow);

            convertView.setTag(mHolder);

        } else {
            mHolder = (TodayViewHolder) convertView.getTag();
        }

        TodayListData mData = mListData.get(position);

        if (mData.isTitle) {
            mHolder.mTitleRow.setVisibility(View.VISIBLE);
            mHolder.mNameRow.setVisibility(View.GONE);

            mHolder.mTitleName.setText(mData.mName);

        } else {
            mHolder.mTitleRow.setVisibility(View.GONE);
            mHolder.mNameRow.setVisibility(View.VISIBLE);

            mHolder.mName.setText(mData.mName);

            String mTime = mData.mTime;
            if (mData.mInfo != null) mTime += "(" + mData.mInfo + ")";
            mHolder.mTime.setText(mTime);
        }

        return convertView;
    }
}
