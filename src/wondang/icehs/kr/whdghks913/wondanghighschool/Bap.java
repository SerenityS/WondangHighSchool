package wondang.icehs.kr.whdghks913.wondanghighschool;

import java.lang.ref.WeakReference;

import toast.library.meal.MealLibrary;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

public class Bap extends Activity {

	private BapListViewAdapter mAdapter;
	private ListView mListView;
	private Handler handler;
	private ProgressDialog mDialog;

	private String[] calender, morning, lunch, night;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bap);

		mListView = (ListView) findViewById(R.id.mBapList);

		mAdapter = new BapListViewAdapter(this);
		mListView.setAdapter(mAdapter);

		calender = new String[7];
		morning = new String[7];
		lunch = new String[7];
		night = new String[7];

		handler = new MyHandler(this);
		new Thread() {

			@Override
			public void run() {
				handler.sendEmptyMessage(0);

				calender = MealLibrary.getDate("ice.go.kr", "E100001786", "4",
						"04", "1");
				morning = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
						"04", "1");
				lunch = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
						"04", "2");
				night = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
						"04", "3");

				handler.sendEmptyMessage(1);
			}
		}.start();

		/*
		 * mAdapter.addItem("월", "아침 없음", "점심은 라면", "저녁은 밥");
		 * mAdapter.addItem("화", "아침ㅇㅇ 없음", "점ㄴㅇㅁㄹㄴㅁㅇ심은 라면", "저녁은ㅇㅇㅇ 밥");
		 * mAdapter.addItem("수", "아침 ㅇㅇ없음", "점심ㄴㅇㄹㅇㅁㄴㅇㄴㄹ은 라면", "저녁ㅇㅇㅇ은 밥");
		 * mAdapter.addItem("목", "아침 ㅇㅇ없음", "점심ㅇㄴㄻㄴㅇㄹ은 라면", "저녁은ㅇㄹㅇㄴㄹㄴㅇ 밥");
		 * mAdapter.addItem("금", "아침 없ㅇㅇ음", "점심ㄴㅁㄹㅇㅁㄴㅇㄹ은 라면", "저ㅇㄴㄻㅁㄴㅇ녁은 밥");
		 * mAdapter.addItem("토", "아침 ㄴㅇ없음", "점심ㅇㅁㄹㄴㅁㅇ은 라면", "저녁ㅁㄴㄹㄴㅁㅇ은 밥");
		 */
	}

	private String getDate(int num) {
		if (num == 0)
			return "일요일";
		else if (num == 1)
			return "월요일";
		else if (num == 2)
			return "화요일";
		else if (num == 3)
			return "수요일";
		else if (num == 4)
			return "목요일";
		else if (num == 5)
			return "금요일";
		else if (num == 6)
			return "토요일";
		return null;
	}

	private class MyHandler extends Handler {
		private final WeakReference<Bap> mActivity;

		public MyHandler(Bap bap) {
			mActivity = new WeakReference<Bap>(bap);
		}

		@Override
		public void handleMessage(Message msg) {
			Bap activity = mActivity.get();
			if (activity != null) {
				if (msg.what == 0)
					mDialog = ProgressDialog.show(Bap.this, "",
							"Data loding...");
				else if (msg.what == 1) {
					for (int i = 0; i < 7; i++) {
						mAdapter.addItem(calender[i], getDate(i), morning[i], lunch[i], night[i]);
						
//						Log.d("날짜", calender[i]);
//						Log.d("아침", moning[i]);
//						Log.d("점심", lunch[i]);
//						Log.d("저녁", night[i]);
//						Log.d(" ", " ");
					}
					mAdapter.notifyDataSetChanged();
					mDialog.cancel();
				}
			}
		}
	}

}
