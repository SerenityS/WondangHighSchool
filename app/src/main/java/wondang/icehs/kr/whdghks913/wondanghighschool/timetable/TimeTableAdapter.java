package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

/**
 * Created by 종환 on 2015-02-18.
 */
class TimeTableViewHolder {
    public TextView mTimeText;
    public TextView mTimeName;
    public TextView mRoom;
}

class TimeTableListData {
    public String mTimeText;
    public String mSubjectName;
    public String mRoom;
}

public class TimeTableAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<TimeTableListData> mListData = new ArrayList<TimeTableListData>();

    public TimeTableAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

    public void addItem(String mTimeText, String mSubjectName, String mRoom) {

        TimeTableListData addItemInfo = new TimeTableListData();
        addItemInfo.mTimeText = mTimeText;
        addItemInfo.mSubjectName = mSubjectName;
        addItemInfo.mRoom = mRoom;

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
    public TimeTableListData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TimeTableViewHolder mHolder;

        if (convertView == null) {
            mHolder = new TimeTableViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_timetable_item, null);

            mHolder.mTimeText = (TextView) convertView
                    .findViewById(R.id.mTimeText);
            mHolder.mTimeName = (TextView) convertView
                    .findViewById(R.id.mTimeName);
            mHolder.mRoom = (TextView) convertView
                    .findViewById(R.id.mRoom);

            convertView.setTag(mHolder);

        } else {
            mHolder = (TimeTableViewHolder) convertView.getTag();
        }

        TimeTableListData mData = mListData.get(position);

        String mTimeText = mData.mTimeText;
        String mSubjectName = mData.mSubjectName;
        String mRoom = mData.mRoom;

        mHolder.mTimeText.setText(mTimeText);
        mHolder.mTimeName.setText(mSubjectName);

        if (mData.mRoom == null)
            mHolder.mRoom.setVisibility(View.GONE);
        else {
            mHolder.mRoom.setVisibility(View.VISIBLE);
            mHolder.mRoom.setText(mRoom);
        }

        return convertView;
    }
}