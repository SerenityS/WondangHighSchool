package wondang.icehs.kr.whdghks913.wondanghighschool;

import java.lang.ref.WeakReference;

import toast.library.meal.MealLibrary;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class Bap extends Activity {
	/**
	 * �ѹ� �޽������� ���������� 272kb ������ �Ҹ�
	 */

	private BapListViewAdapter mAdapter;
	private ListView mListView;
	private Handler mHandler;

	private String[] calender, morning, lunch, night;

	private CroutonHelper mHelper;

	private SharedPreferences bapList;
	private SharedPreferences.Editor bapListeditor;

	private ProgressDialog mDialog;

	private final String savedList = "����� ������ �ҷ��Խ��ϴ�\n���� �����ϰ�� ���ΰ�ħ ���ּ���";
	private final String noMessage = "������°� ���� �ʾ� �޽� ������ �޾ƿ��´� �����߽��ϴ�";
	private final String loadingList = "�޽� ������ �޾ƿ��� �ֽ��ϴ�...";
	private final String loadList = "���ͳݿ��� �޽� ������ �޾ƿԽ��ϴ�";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bap);

		mListView = (ListView) findViewById(R.id.mBapList);

		mAdapter = new BapListViewAdapter(this);
		mListView.setAdapter(mAdapter);

		bapList = getSharedPreferences("bapList", 0);
		bapListeditor = bapList.edit();

		mHandler = new MyHandler(this);

		mHelper = new CroutonHelper(this);

		if (bapList.getBoolean("checker", false)) {
			calender = restore("calender");
			morning = restore("morning");
			lunch = restore("lunch");
			night = restore("night");

			mHandler.sendEmptyMessage(1);

			mHelper.setText(savedList);
			mHelper.setStyle(Style.CONFIRM);
			mHelper.show();
		} else {
			if (isNetwork()) {
				calender = new String[7];
				morning = new String[7];
				lunch = new String[7];
				night = new String[7];

				sync();
			} else {

				mHelper.setText(noMessage);
				mHelper.setStyle(Style.ALERT);
				mHelper.setAutoTouchCencle(true);
				mHelper.show();

				errorView(true);
			}
		}
	}

	private void sync() {
		mAdapter.clearData();

		new Thread() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);

				try {
					calender = MealLibrary.getDate("ice.go.kr", "E100001786",
							"4", "04", "1");
					morning = MealLibrary.getMeal("ice.go.kr", "E100001786",
							"4", "04", "1");
					lunch = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
							"04", "2");
					night = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
							"04", "3");

					save("calender", calender);
					save("morning", morning);
					save("lunch", lunch);
					save("night", night);

					mHandler.sendEmptyMessage(1);

					errorView(false);

					mHelper.setText(loadList);
					mHelper.setStyle(Style.CONFIRM);
					mHelper.setAutoTouchCencle(true);
					mHelper.show();

				} catch (Exception ex) {
					ex.printStackTrace();

					mAdapter.clearData();

					errorView(true);

					mHelper.setText(noMessage);
					mHelper.setStyle(Style.ALERT);
					mHelper.setAutoTouchCencle(true);
					mHelper.show();
				}

				mHandler.sendEmptyMessage(2);
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

	private void save(String name, String[] value) {
		for (int i = 0; i < value.length; i++) {
			bapListeditor.putString(name + "_" + i, value[i]);
		}
		bapListeditor.putBoolean("checker", true);
		bapListeditor.putInt(name, value.length);
		bapListeditor.commit();
	}

	private String[] restore(String name) {
		int length = bapList.getInt(name, 0);
		String[] string = new String[length];

		for (int i = 0; i < length; i++) {
			string[i] = bapList.getString(name + "_" + i, "");
		}
		return string;
	}

	private void errorView(boolean isError) {
		if (isError) {
			((FrameLayout) findViewById(R.id.errorView))
					.setVisibility(View.VISIBLE);
			errorView(true);
		} else
			((FrameLayout) findViewById(R.id.errorView))
					.setVisibility(View.GONE);
	}

	private boolean isNetwork() {
		ConnectivityManager manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (wifi.isConnected() || mobile.isConnected()) {
			// �����
			return true;
		} else {
			// ���� �ȵ�
			return false;
		}
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
			if (isNetwork()) {
				sync();
			} else {
				mHelper.setText(noMessage);
				mHelper.setStyle(Style.ALERT);
				mHelper.setAutoTouchCencle(true);
				mHelper.show();

				errorView(true);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mDialog != null) {
			mDialog.dismiss();
		}
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

				if (msg.what == 0) {
					if (mDialog == null) {
						mDialog = ProgressDialog
								.show(Bap.this, "", loadingList);
					}
				} else if (msg.what == 1) {
					for (int i = 0; i < 7; i++) {
						mAdapter.addItem(calender[i], getDate(i), morning[i],
								lunch[i], night[i]);
					}
					mAdapter.notifyDataSetChanged();
				} else if (msg.what == 2) {
					mDialog.dismiss();
				}
			}
		}
	}
}
