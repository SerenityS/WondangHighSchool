package wondang.icehs.kr.whdghks913.wondanghighschool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import toast.library.meal.MealLibrary;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
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
	private ProgressDialog mDialog;

	private String[] calender, morning, lunch, night;

	private CroutonHelper mHelper;

	private SharedPreferences bapList;
	private SharedPreferences.Editor bapListeditor;

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

			mAdapter.sort();
			mAdapter.notifyDataSetChanged();

			mHelper.setText("����� ������ �ҷ��Խ��ϴ�, ���� �����ϰ�� ���ΰ�ħ ���ּ���");
			mHelper.setStyle(Style.CONFIRM);
			mHelper.show();
		} else {
			calender = new String[7];
			morning = new String[7];
			lunch = new String[7];
			night = new String[7];

			sync();
		}
	}

	private void sync() {
		mAdapter.clearData();
		mAdapter.notifyDataSetChanged();

		bapListeditor.clear().commit();

		new Thread() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);

				calender = MealLibrary.getDate("ice.go.kr", "E100001786", "4",
						"04", "1");
				morning = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
						"04", "1");
				lunch = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
						"04", "2");
				night = MealLibrary.getMeal("ice.go.kr", "E100001786", "4",
						"04", "3");

				mHandler.sendEmptyMessage(1);
				mHandler.sendEmptyMessage(2);

				save("calender", calender);
				save("morning", morning);
				save("lunch", lunch);
				save("night", night);

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

	/**
	 * Thanks NaraePreference
	 */

	@SuppressLint("NewApi")
	private void save(String name, String[] value) {
		ArrayList<String> arraylist = new ArrayList<String>(
				Arrays.asList(value));
		try {
			Set<String> list = new HashSet<String>(arraylist);
			bapListeditor.putStringSet(name, list);
			bapListeditor.putBoolean("checker", true);
			bapListeditor.commit();
		} catch (Exception e) {
			JSONArray a = new JSONArray();
			for (int i = 0; i < arraylist.size(); i++) {
				a.put(arraylist.get(i));
			}
			if (!arraylist.isEmpty()) {
				bapListeditor.putString(name, a.toString());
			} else {
				bapListeditor.putString(name, null);
			}
			bapListeditor.putBoolean("checker", true);
			bapListeditor.commit();
		}
	}

	@SuppressLint("NewApi")
	private String[] restore(String name) {
		try {
			return bapList.getStringSet(name, null).toArray(new String[7]);
		} catch (Exception e) {
			try {
				String json = bapList.getString(name, null);
				ArrayList<String> urls = new ArrayList<String>();
				if (json != null) {
					try {
						JSONArray a = new JSONArray(json);
						for (int i = 0; i < a.length(); i++) {
							String url = a.optString(i);
							urls.add(url);
						}
					} catch (JSONException ex) {
						ex.printStackTrace();
					}
				}
				return urls.toArray(new String[0]);
			} catch (Exception ex) {
				return null;
			}
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
				} else if (msg.what == 2) {
					mDialog.cancel();
				}
			}
		}
	}
}
