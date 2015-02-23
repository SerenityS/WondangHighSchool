package wondang.icehs.kr.whdghks913.wondanghighschool.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

/**
 * Created by 종환 on 2015-02-22.
 */
class InfoViewHolder {
    public ImageView mImage;
    public TextView mTitle;
    public TextView mText1;
    public TextView mText2;
}

class InfoListData {
    public int mImage;
    public String mTitle;
    public String mText1;
    public String mText2;
    public boolean isAdjustViewBounds;
}

public class InfoAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<InfoListData> mListData = new ArrayList<InfoListData>();

    public InfoAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

    public void addItem(String mTitle, int mImage, String mText1, String mText2, boolean isAdjustViewBounds) {

        InfoListData addItemInfo = new InfoListData();
        addItemInfo.mTitle = mTitle;
        addItemInfo.mImage = mImage;
        addItemInfo.mText1 = mText1;
        addItemInfo.mText2 = mText2;
        addItemInfo.isAdjustViewBounds = isAdjustViewBounds;

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
    public InfoListData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        InfoViewHolder mHolder;

        if (convertView == null) {
            mHolder = new InfoViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_info_item, null);

            mHolder.mImage = (ImageView) convertView
                    .findViewById(R.id.mImage);
            mHolder.mTitle = (TextView) convertView
                    .findViewById(R.id.mTitle);
            mHolder.mText1 = (TextView) convertView.findViewById(R.id.mText1);
            mHolder.mText2 = (TextView) convertView.findViewById(R.id.mText2);

            convertView.setTag(mHolder);

        } else {
            mHolder = (InfoViewHolder) convertView.getTag();
        }

        InfoListData mData = mListData.get(position);

        mHolder.mTitle.setVisibility(View.VISIBLE);
        mHolder.mTitle.setText(mData.mTitle);

        int resId = mData.mImage;
        if (resId != 0) {
            mHolder.mImage.setVisibility(View.VISIBLE);
            mHolder.mImage.setImageResource(mData.mImage);
            mHolder.mImage.setAdjustViewBounds(mData.isAdjustViewBounds);
        } else {
            mHolder.mImage.setImageResource(0);
            mHolder.mImage.setVisibility(View.GONE);
        }

        String text1 = mData.mText1;
        if (text1 != null) {
            mHolder.mText1.setVisibility(View.VISIBLE);
            mHolder.mText1.setText(text1);
        } else {
            mHolder.mText1.setVisibility(View.GONE);
            mHolder.mText1.setText(null);
        }

        String text2 = mData.mText2;
        if (text2 != null) {
            mHolder.mText2.setVisibility(View.VISIBLE);
            mHolder.mText2.setText(text2);
        } else {
            mHolder.mText2.setVisibility(View.GONE);
            mHolder.mText2.setText(null);
        }

        return convertView;
    }
}
