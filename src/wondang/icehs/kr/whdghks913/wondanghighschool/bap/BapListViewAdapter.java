package wondang.icehs.kr.whdghks913.wondanghighschool.bap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
	private Calendar currentTime = Calendar.getInstance();
	private final int YEAR, MONTH, DAY_OF_MONTH;

	public BapListViewAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		YEAR = currentTime.get(Calendar.YEAR);
		MONTH = currentTime.get(Calendar.MONTH);
		DAY_OF_MONTH = currentTime.get(Calendar.DAY_OF_MONTH);
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

		String Calender = mData.mCalender;
		holder.mCalender.setText(Calender);
		holder.mDate.setText(mDate);

		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy.MM.dd(E)",
					Locale.KOREA);

			Calendar Date = Calendar.getInstance();
			Date.setTime(sdFormat.parse(Calender));

			LinearLayout bapListLayout = (LinearLayout) convertView
					.findViewById(R.id.bapListLayout);

			if (Date.get(Calendar.YEAR) == YEAR
					&& Date.get(Calendar.MONTH) == MONTH
					&& Date.get(Calendar.DAY_OF_MONTH) == DAY_OF_MONTH) {

				bapListLayout.setBackgroundColor(mContext.getResources()
						.getColor(R.color.background));

			} else {
				bapListLayout.setBackgroundColor(mContext.getResources()
						.getColor(android.R.color.transparent));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String mMorning = mData.mMorning;
		String mLunch = mData.mLunch;
		String mNight = mData.mNight;

		if (MealCheck(mMorning))
			mMorning = mData.mMorning = "아침이 없습니다";

		if (MealCheck(mLunch))
			mLunch = mData.mLunch = "점심이 없습니다";

		if (MealCheck(mNight))
			mNight = mData.mNight = "저녁이 없습니다";

		holder.mMorning.setText(mMorning.trim());
		holder.mLunch.setText(mLunch.trim());
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
}