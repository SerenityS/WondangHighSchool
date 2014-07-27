package wondang.icehs.kr.whdghks913.wondanghighschool.bap;

import java.lang.ref.WeakReference;

import toast.library.meal.MealLibrary;
import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class Bap extends Activity {
	/**
	 * �ѹ� �޽������� ���������� 272kb ������ �Ҹ�
	 */

	private BapListViewAdapter mAdapter;
	public static ListView mListView;
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
	private final String Syncing = "���� �ε����Դϴ�";

	private boolean isSync = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bap);

		mListView = (ListView) findViewById(R.id.mBapList);

		mAdapter = new BapListViewAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int position,
					long id) {
				BapListData mData = mAdapter.getItem(position);

				Intent msg = new Intent(Intent.ACTION_SEND);
				msg.addCategory(Intent.CATEGORY_DEFAULT);
				msg.putExtra(Intent.EXTRA_TITLE, String.format(
						getString(R.string.shareBap_message_title),
						mData.mCalender));
				msg.putExtra(Intent.EXTRA_TEXT, String.format(
						getString(R.string.shareBap_message_msg),
						mData.mCalender, mData.mMorning, mData.mLunch,
						mData.mNight));
				msg.setType("text/plain");
				startActivity(Intent.createChooser(msg,
						getString(R.string.shareBap_title)));
			}
		});

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
				addErrorList();

				mHelper.setText(noMessage);
				mHelper.setStyle(Style.ALERT);
				mHelper.setAutoTouchCencle(true);
				mHelper.show();
			}
		}
	}

	private void sync() {
		isSync = true;

		mAdapter.clearData();

		new Thread() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);

				try {
					calender = MealLibrary.getDateNew("ice.go.kr", "E100001786",
							"4", "04", "1");
					morning = MealLibrary.getMealNew("ice.go.kr", "E100001786",
							"4", "04", "1");
					lunch = MealLibrary.getMealNew("ice.go.kr", "E100001786", "4",
							"04", "2");
					night = MealLibrary.getMealNew("ice.go.kr", "E100001786", "4",
							"04", "3");

					save("calender", calender);
					save("morning", morning);
					save("lunch", lunch);
					save("night", night);

					mHandler.sendEmptyMessage(1);

					mHelper.setText(loadList);
					mHelper.setStyle(Style.CONFIRM);
					mHelper.setAutoTouchCencle(true);
					mHelper.show();
				} catch (Exception ex) {
					ex.printStackTrace();

					mAdapter.clearData();

					addErrorList();

					mHelper.setText(noMessage);
					mHelper.setStyle(Style.ALERT);
					mHelper.setAutoTouchCencle(true);
					mHelper.show();
				}
				mHandler.sendEmptyMessage(2);
				isSync = false;
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

	private boolean isNetwork() {
		ConnectivityManager manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (wifi.isConnected() || mobile.isConnected())
			return true;
		else
			return false;
	}

	private void addErrorList() {
		mAdapter.addItem("�˼� ����", "�˼� ����", noMessage, noMessage, noMessage);
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
				if (!isSync) {
					sync();
					item.setEnabled(false);
				} else {
					mHelper.clearCroutonsForActivity();
					mHelper.setText(Syncing);
					mHelper.setStyle(Style.INFO);
					mHelper.setAutoTouchCencle(true);
					mHelper.show();
				}
			} else {
				addErrorList();

				mHelper.clearCroutonsForActivity();
				mHelper.setText(noMessage);
				mHelper.setStyle(Style.ALERT);
				mHelper.setAutoTouchCencle(true);
				mHelper.show();
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mDialog != null)
			mDialog.dismiss();

		mHelper.cencle(true);
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
