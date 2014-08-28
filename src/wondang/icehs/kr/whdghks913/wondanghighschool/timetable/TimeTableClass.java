package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class TimeTableClass extends Fragment {
	private Context mContext;
	private String Grade;

	private ListView mListView;
	private ListViewAdapter mAdapter;

	public TimeTableClass(Context mContext, String Grade) {
		this.mContext = mContext;
		this.Grade = Grade;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_rss_fullrss, null);

		mListView = (ListView) view.findViewById(R.id.mListView);

		mAdapter = new ListViewAdapter(mContext);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				String dbName = mAdapter.mListData.get(position).dbName;
				String tableName = mAdapter.mListData.get(position).tableName;

				Log.d("ddddd", dbName + " ddd " + tableName);
			}
		});

		for (int i = 1; i <= 10; i++) {
			mAdapter.addItem(Grade + "학년 " + i + "반", "WondangTimeTableG"
					+ Grade, "grade" + Grade + "class" + i);
		}
		// mAdapter.addItem(null, "교무실 (대표전화)", "032-569-0723");

		return view;
	}
}
