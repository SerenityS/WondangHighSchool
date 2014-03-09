package wondang.icehs.kr.whdghks913.wondanghighschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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

	

}
