package wondang.icehs.kr.whdghks913.wondanghighschool;

import java.lang.ref.WeakReference;

import toast.library.meal.MealLibrary;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class Bap extends Activity {

	private BapListViewAdapter mAdapter;
	private ListView mListView;
	private Handler handler;
	private ProgressDialog mDialog;

	private String[] calender, morning, lunch, night;

	private CroutonHelper mHelper;

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

		mHelper = new CroutonHelper(this);

		sync();
	}

	private void sync() {
		mAdapter.clearData();
		mAdapter.notifyDataSetChanged();

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

				mHelper.setText("���ͳݿ��� �޽� ������ �޾ƿԽ��ϴ�");
				mHelper.setStyle(Style.CONFIRM);
				mHelper.show();
			}
		}.start();
	}

	private String getDate(int num) {
		if (num == 0)
			return "�Ͽ���";
		else if (num == 1)
			return "������";
		else if (num == 2)
			return "ȭ����";
		else if (num == 3)
			return "������";
		else if (num == 4)
			return "�����";
		else if (num == 5)
			return "�ݿ���";
		else if (num == 6)
			return "�����";
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bap, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		int ItemId = item.getItemId();

		if (ItemId == R.id.sync) {
			sync();
		}

		return super.onOptionsItemSelected(item);
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
							"�޽� ������ �޾ƿ��� �ֽ��ϴ�...");
				else if (msg.what == 1) {
					for (int i = 0; i < 7; i++) {
						mAdapter.addItem(calender[i], getDate(i), morning[i],
								lunch[i], night[i]);
					}
					mAdapter.notifyDataSetChanged();
					mDialog.cancel();
				}
			}
		}
	}
}
