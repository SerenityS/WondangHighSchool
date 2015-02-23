package wondang.icehs.kr.whdghks913.wondanghighschool.bap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.BapTool;

/**
 * Created by 종환 on 2015-02-17.
 */
class BapViewHolder {
    public TextView mCalender;
    public TextView mDayOfTheWeek;
    public TextView mLunch;
    public TextView mDinner;
}

class BapListData {
    public String mCalender;
    public String mDayOfTheWeek;
    public String mLunch;
    public String mDinner;
}

public class BapListAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<BapListData> mListData = new ArrayList<BapListData>();

    public BapListAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

    public void addItem(String mCalender, String mDayOfTheWeek, String mLunch, String mDinner) {

        BapListData addItemInfo = new BapListData();
        addItemInfo.mCalender = mCalender;
        addItemInfo.mDayOfTheWeek = mDayOfTheWeek;
        addItemInfo.mLunch = mLunch;
        addItemInfo.mDinner = mDinner;

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
    public BapListData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        BapViewHolder mHolder;

        if (convertView == null) {
            mHolder = new BapViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_bap_item, null);

            mHolder.mCalender = (TextView) convertView
                    .findViewById(R.id.mCalender);
            mHolder.mDayOfTheWeek = (TextView) convertView
                    .findViewById(R.id.mDayOfTheWeek);
            mHolder.mLunch = (TextView) convertView.findViewById(R.id.mLunch);
            mHolder.mDinner = (TextView) convertView.findViewById(R.id.mDinner);

            convertView.setTag(mHolder);

        } else {
            mHolder = (BapViewHolder) convertView.getTag();
        }

        BapListData mData = mListData.get(position);

        String mCalender = mData.mCalender;

        String mDayOfTheWeek = mData.mDayOfTheWeek;
        String mLunch = mData.mLunch;
        String mDinner = mData.mDinner;

        if (BapTool.mStringCheck(mLunch))
            mLunch = mData.mLunch = mContext.getResources().getString(R.string.no_data_lunch);
        if (BapTool.mStringCheck(mDinner))
            mDinner = mData.mDinner = mContext.getResources().getString(R.string.no_data_dinner);

        mHolder.mCalender.setText(mCalender);
        mHolder.mDayOfTheWeek.setText(mDayOfTheWeek);
        mHolder.mLunch.setText(mLunch);
        mHolder.mDinner.setText(mDinner);

        /*LinearLayout bapListLayout = (LinearLayout) convertView
                .findViewById(R.id.bapListLayout);

        try {
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy.MM.dd(E)",
                    Locale.KOREA);

            Calendar Date = Calendar.getInstance();
            Date.setTime(sdFormat.parse(Calender));

            if (Date.get(Calendar.YEAR) == YEAR
                    && Date.get(Calendar.MONTH) == MONTH
                    && Date.get(Calendar.DAY_OF_MONTH) == DAY_OF_MONTH) {

                bapListLayout.setBackgroundColor(mContext.getResources()
                        .getColor(R.color.background));

                return convertView;

            } else {
                bapListLayout.setBackgroundColor(mContext.getResources()
                        .getColor(android.R.color.transparent));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return convertView;
    }
}
