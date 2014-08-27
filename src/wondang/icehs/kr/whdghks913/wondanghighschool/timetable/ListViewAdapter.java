package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import java.util.ArrayList;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class ViewHolder {
	public TextView choose_class;
}

public class ListViewAdapter extends BaseAdapter {
	private Context mContext;
	public ArrayList<ListData> mListData = new ArrayList<ListData>();

	public ListViewAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public void addItem(String choose_class, String dbName) {
		ListData addInfo = new ListData();
		addInfo.choose_class = choose_class;
		addInfo.dbName = dbName;

		mListData.add(addInfo);
	}

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
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.timetable_class_row, null);

			holder.choose_class = (TextView) convertView
					.findViewById(R.id.choose_class);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ListData mData = mListData.get(position);
		holder.choose_class.setText(mData.choose_class);

		return convertView;
	}
}

class ListData {
	/**
	 * 리스트 정보를 담고 있을 객체 생성
	 */

	public String choose_class;
	public String dbName;
}