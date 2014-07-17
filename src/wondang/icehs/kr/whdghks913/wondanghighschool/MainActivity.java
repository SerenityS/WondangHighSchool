package wondang.icehs.kr.whdghks913.wondanghighschool;

import wondang.icehs.kr.whdghks913.wondanghighschool.bap.Bap;
import wondang.icehs.kr.whdghks913.wondanghighschool.schedule.Schedule;
import wondang.icehs.kr.whdghks913.wondanghighschool.song.Song;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends SherlockActivity {

	private ListView mListView;
	private ListViewAdapter mAdapter;

	private CroutonHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.mList);

		mAdapter = new ListViewAdapter(this);
		mListView.setAdapter(mAdapter);

		mAdapter.addItem(getResources().getDrawable(R.drawable.ic_launcher),
				"원당고 소개", "인천 원당고등학교를 소개합니다");
		mAdapter.addItem(getResources().getDrawable(R.drawable.song), "교가",
				"우리 학교 교가 확인하기");
		mAdapter.addItem(getResources().getDrawable(R.drawable.calender), "일정",
				"학교의 일정을 확인 할 수 있습니다");
		mAdapter.addItem(getResources().getDrawable(R.drawable.call), "연락처",
				"학교로 전화하기");
		mAdapter.addItem(getResources().getDrawable(R.drawable.bap), "급식",
				"오늘 나오는 급식은?");
		mAdapter.addItem(null, "선생님 정보", "훌륭한 선생님의 정보를 확인합니다");

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// ListData mData = mAdapter.mListData.get(position);

				if (position == 0) {
					// 소게
					startActivity(new Intent(MainActivity.this,
							WondangInfo.class));
				} else if (position == 1) {
					// 교가
					startActivity(new Intent(MainActivity.this, Song.class));
				} else if (position == 2) {
					// 일정 확인하기
					startActivity(new Intent(MainActivity.this, Schedule.class));
				} else if (position == 3) {
					// 전화 하기
					startActivity(new Intent(MainActivity.this, Call.class));
				} else if (position == 4) {
					// 급식
					startActivity(new Intent(MainActivity.this, Bap.class));
				}
			}
		});

		mHelper = new CroutonHelper(this);
		mHelper.setText("환영합니다~!");
		mHelper.setStyle(Style.INFO);
		mHelper.show();
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

	@Override
	protected void onPause() {
		super.onPause();

		mHelper.cencle(true);
	}
}
