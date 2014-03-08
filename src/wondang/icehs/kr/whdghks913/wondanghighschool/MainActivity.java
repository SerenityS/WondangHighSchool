package wondang.icehs.kr.whdghks913.wondanghighschool;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {

	private ListView mListView;
	private ListViewAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.mList);

		mAdapter = new ListViewAdapter(this);
		mListView.setAdapter(mAdapter);

		mAdapter.addItem(getResources().getDrawable(R.drawable.ic_launcher),
				"원당고 소게", "인천 원당고등학교를 소게합니다");
		mAdapter.addItem(getResources().getDrawable(R.drawable.ic_launcher),
				"교가", "우리 학교 교가 확인하기");

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// ListData mData = mAdapter.mListData.get(position);

				if (position == 0) {
					startActivity(new Intent(MainActivity.this,
							WondangInfo.class));
				} else if (position == 1) {
					startActivity(new Intent(MainActivity.this, Song.class));
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int ItemId = item.getItemId();

		if (ItemId == R.id.info) {
			startActivity(new Intent(this, MadeBy.class));
		}
		
		return super.onOptionsItemSelected(item);
	}

	private class ViewHolder {
		public ImageView mListImage;

		public TextView mListLargeText;

		public TextView mListMediumText;
	}

	private class ListViewAdapter extends BaseAdapter {
		private Context mContext = null;
		private ArrayList<ListData> mListData = new ArrayList<ListData>();

		public ListViewAdapter(Context mContext) {
			super();
			this.mContext = mContext;
		}

		public void addItem(Drawable icon, String mTitle, String mDate) {
			ListData addInfo = new ListData();
			addInfo.mIcon = icon;
			addInfo.mTitle = mTitle;
			addInfo.mDate = mDate;

			mListData.add(addInfo);

			mAdapter.notifyDataSetChanged();
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
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.main_list_item, null);

				holder.mListImage = (ImageView) convertView
						.findViewById(R.id.mListImage);
				holder.mListLargeText = (TextView) convertView
						.findViewById(R.id.mListLargeText);
				holder.mListMediumText = (TextView) convertView
						.findViewById(R.id.mListMediumText);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ListData mData = mListData.get(position);

			holder.mListImage.setImageDrawable(mData.mIcon);
			holder.mListLargeText.setText(mData.mTitle);
			holder.mListMediumText.setText(mData.mDate);

			return convertView;
		}
	}

	public class ListData {
		/**
		 * 리스트 정보를 담고 있을 객체 생성
		 */

		public Drawable mIcon;
		public String mTitle;
		public String mDate;
	}

}
