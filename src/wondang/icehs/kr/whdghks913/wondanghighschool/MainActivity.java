package wondang.icehs.kr.whdghks913.wondanghighschool;

import wondang.icehs.kr.whdghks913.wondanghighschool.bap.Bap;
import wondang.icehs.kr.whdghks913.wondanghighschool.rss.InfoRSSActivity;
import wondang.icehs.kr.whdghks913.wondanghighschool.schedule.Schedule;
import wondang.icehs.kr.whdghks913.wondanghighschool.song.Song;
import wondang.icehs.kr.whdghks913.wondanghighschool.timetable.TimeTableGrade;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

		Resources mRes = getResources();
		mAdapter.addItem(mRes.getDrawable(R.drawable.ic_launcher), "원당고 소개",
				"인천 원당고등학교를 소개합니다");
		mAdapter.addItem(mRes.getDrawable(R.drawable.bap), "급식", "오늘 나오는 급식은?");
		mAdapter.addItem(mRes.getDrawable(R.drawable.timetable), "시간표",
				"학급 시간표를 확인합니다");
		mAdapter.addItem(mRes.getDrawable(R.drawable.calender), "일정",
				"학교의 일정을 확인 할 수 있습니다");
		mAdapter.addItem(mRes.getDrawable(R.drawable.rss),
				mRes.getString(R.string.rss_info), "대회정보를 확인합니다");
		mAdapter.addItem(mRes.getDrawable(R.drawable.song), "교가",
				"우리 학교 교가 확인하기");
		mAdapter.addItem(mRes.getDrawable(R.drawable.call), "연락처", "학교로 전화하기");

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
					// 급식
					startActivity(new Intent(MainActivity.this, Bap.class));
				} else if (position == 2) {
					// 시간표
					startActivity(new Intent(MainActivity.this,
							TimeTableGrade.class));
				} else if (position == 3) {
					// 일정
					startActivity(new Intent(MainActivity.this, Schedule.class));
				} else if (position == 4) {
					// 대회정보
					startActivity(new Intent(MainActivity.this,
							InfoRSSActivity.class));
				} else if (position == 5) {
					// 교가
					startActivity(new Intent(MainActivity.this, Song.class));
				} else if (position == 6) {
					// 연락처
					startActivity(new Intent(MainActivity.this, Call.class));
				}
			}
		});

		mHelper = new CroutonHelper(this);
		mHelper.setText("환영합니다~!");
		mHelper.setStyle(Style.INFO);
		mHelper.show();

		try {
			final SharedPreferences mPref = PreferenceManager
					.getDefaultSharedPreferences(this);

			PackageManager packageManager = this.getPackageManager();
			PackageInfo infor = packageManager.getPackageInfo(getPackageName(),
					PackageManager.GET_META_DATA);
			final int code = infor.versionCode;

			if (mPref.getInt("versionCode", 0) != code) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("어플이 업데이트 되었습니다!");
				alert.setPositiveButton("확인",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mPref.edit().putInt("versionCode", code)
										.commit();
								dialog.dismiss();
							}
						});
				alert.setMessage(R.string.changeLog);
				alert.show();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
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

		// if (ItemId == R.id.info) {
		// startActivity(new Intent(this, MadeBy.class));
		// } else
		if (ItemId == R.id.setting) {
			startActivity(new Intent(this, SettingsActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();

		mHelper.cencle(true);
	}
}
