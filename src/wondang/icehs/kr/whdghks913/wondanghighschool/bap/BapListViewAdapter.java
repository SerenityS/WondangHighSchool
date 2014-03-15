package wondang.icehs.kr.whdghks913.wondanghighschool.bap;

import java.util.ArrayList;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.R.id;
import wondang.icehs.kr.whdghks913.wondanghighschool.R.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class BapViewHolder {
	public TextView mMorning;

	public TextView mLunch;

	public TextView mNight;

	public TextView mDate;

	public TextView mCalender;
}

class BapListViewAdapter extends BaseAdapter {
	private Context mContext = null;
	private ArrayList<BapListData> mListData = new ArrayList<BapListData>();

	public BapListViewAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public void addItem(String mCalender, String mDate, String mMorning,
			String mLunch, String mNight) {
		BapListData addInfo = new BapListData();
		addInfo.mCalender = mCalender;
		addInfo.mDate = mDate;
		addInfo.mMorning = mMorning;
		addInfo.mLunch = mLunch;
		addInfo.mNight = mNight;

		mListData.add(addInfo);
	}

	public void clearData() {
		mListData.clear();
	}

	// public void sort() {
	// Collections.sort(mListData, BapListData.ALPHA_COMPARATOR);
	// }

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BapViewHolder holder;
		if (convertView == null) {
			holder = new BapViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.bap_list_item, null);

			holder.mDate = (TextView) convertView.findViewById(R.id.mDate);
			holder.mCalender = (TextView) convertView
					.findViewById(R.id.mCalender);
			holder.mMorning = (TextView) convertView
					.findViewById(R.id.mMorning);
			holder.mLunch = (TextView) convertView.findViewById(R.id.mLunch);
			holder.mNight = (TextView) convertView.findViewById(R.id.mNight);

			convertView.setTag(holder);
		} else {
			holder = (BapViewHolder) convertView.getTag();
		}

		BapListData mData = mListData.get(position);

		String mDate = mData.mDate;
		if ("일요일".equals(mDate)) {
			holder.mCalender.setTextColor(Color.RED);
			holder.mDate.setTextColor(Color.RED);
		} else if ("토요일".equals(mDate)) {
			holder.mCalender.setTextColor(Color.BLUE);
			holder.mDate.setTextColor(Color.BLUE);
		} else {
			holder.mCalender.setTextColor(Color.BLACK);
			holder.mDate.setTextColor(Color.BLACK);
		}

		holder.mCalender.setText(mData.mCalender);
		holder.mDate.setText(mDate);

		String mMorning = mData.mMorning;
		String mLunch = mData.mLunch;
		String mNight = mData.mNight;

		if (MealCheck(mMorning))
			holder.mMorning.setText("아침이 없습니다");
		else
			holder.mMorning.setText(mMorning.trim());

		if (MealCheck(mLunch))
			holder.mLunch.setText("점심이 없습니다 : 4교시 하고 집에 갑니다!");
		else
			holder.mLunch.setText(mLunch.trim());

		if (MealCheck(mNight))
			holder.mNight.setText("저녁이 없습니다 : 야자가 없습니다!");
		else
			holder.mNight.setText(mNight.trim());

		return convertView;
	}

	private boolean MealCheck(String Meal) {
		if ("".equals(Meal) || " ".equals(Meal) || Meal == null)
			return true;
		return false;
	}
}

class BapListData {
	/**
	 * 리스트 정보를 담고 있을 객체 생성
	 */
	public String mCalender;
	public String mDate;

	public String mMorning;
	public String mLunch;
	public String mNight;

	// public static final Comparator<BapListData> ALPHA_COMPARATOR = new
	// Comparator<BapListData>() {
	// private final Collator sCollator = Collator.getInstance();
	//
	// @Override
	// public int compare(BapListData mListDate_1, BapListData mListDate_2) {
	// return sCollator.compare(mListDate_1.mCalender,
	// mListDate_2.mCalender);
	// }
	// };
}