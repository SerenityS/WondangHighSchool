package wondang.icehs.kr.whdghks913.wondanghighschool.bapautoupdate;

import java.util.Calendar;

import toast.library.meal.MealLibrary;
import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.bap.Bap;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

public class updateService extends Service {
	private ProcessTask mProcessTask;
	private String[] calender, morning, lunch, night;
	private SharedPreferences mPref;

	private boolean showNotifi = false;
	private final int WIFI_ERROR = -1;
	private final int NET_ERROR = -2;
	private final int GET_ERROR = -3;
	private final int SUCCESS = 1;

	@Override
	public void onCreate() {
		super.onCreate();

		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		showNotifi = mPref.getBoolean("updateNotifi", false);

		if (isNetwork()) {
			boolean onlyWIFI = mPref.getBoolean("updateWiFi", false);

			if (onlyWIFI) {
				if (!isWifi()) {
					// wifi ���� �ȵ� - ����
					if (showNotifi) {
						updateAlarm updateAlarm = new updateAlarm(this);
						updateAlarm.wifiOFF();
						mNotification(this, WIFI_ERROR);
					}
					stopSelf();
				}
			}

			calender = new String[7];
			morning = new String[7];
			lunch = new String[7];
			night = new String[7];

			mProcessTask = new ProcessTask();
			mProcessTask.execute();

		} else {
			// ��Ʈ��ũ ���� �ȵ� - ����
			if (showNotifi) {
				updateAlarm updateAlarm = new updateAlarm(this);
				updateAlarm.wifiOFF();
				mNotification(this, NET_ERROR);
			}
			stopSelf();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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
		return false;
	}

	private boolean isWifi() {
		ConnectivityManager manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (wifi.isConnected())
			return true;
		return false;
	}

	public class ProcessTask extends AsyncTask<String, Integer, Long> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mNotification(updateService.this);
		}

		@Override
		protected Long doInBackground(String... params) {
			final String CountryCode = "ice.go.kr"; // ���� �� ����û ������
			final String schulCode = "E100001786"; // �б� ���� �ڵ�
			final String schulCrseScCode = "4"; // �б� ���� �ڵ� 1
			final String schulKndScCode = "04"; // �б� ���� �ڵ� 2

			try {
				Calendar mCalendar = Calendar.getInstance();
				int weekday = mCalendar.get(Calendar.DAY_OF_WEEK);

				if (weekday == Calendar.SATURDAY) {
					mCalendar.set(mCalendar.get(Calendar.YEAR),
							mCalendar.get(Calendar.MONTH),
							mCalendar.get(Calendar.DAY_OF_MONTH) + 2);
				}

				String year = Integer.toString(mCalendar.get(Calendar.YEAR));
				String month = Integer
						.toString(mCalendar.get(Calendar.MONTH) + 1);
				String day = Integer.toString(mCalendar
						.get(Calendar.DAY_OF_MONTH));

				if (month.length() <= 1)
					month = "0" + month;

				calender = MealLibrary.getDateNew(CountryCode, schulCode,
						schulCrseScCode, schulKndScCode, "1", year, month, day);
				morning = MealLibrary.getMealNew(CountryCode, schulCode,
						schulCrseScCode, schulKndScCode, "1", year, month, day);
				lunch = MealLibrary.getMealNew(CountryCode, schulCode,
						schulCrseScCode, schulKndScCode, "2", year, month, day);
				night = MealLibrary.getMealNew(CountryCode, schulCode,
						schulCrseScCode, schulKndScCode, "3", year, month, day);

				// } else {
				// calender = MealLibrary.getDateNew(CountryCode, schulCode,
				// schulCrseScCode, schulKndScCode, "1");
				// morning = MealLibrary.getMealNew(CountryCode, schulCode,
				// schulCrseScCode, schulKndScCode, "1");
				// lunch = MealLibrary.getMealNew(CountryCode, schulCode,
				// schulCrseScCode, schulKndScCode, "2");
				// night = MealLibrary.getMealNew(CountryCode, schulCode,
				// schulCrseScCode, schulKndScCode, "3");
				// }

				save("calender", calender);
				save("morning", morning);
				save("lunch", lunch);
				save("night", night);

			} catch (Exception e) {
				return -1l;
			}
			return 0l;
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);

			NotificationManager nm = (NotificationManager) updateService.this
					.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancel(777);

			if (result == -1l) {
				// ����
				if (showNotifi)
					mNotification(updateService.this, GET_ERROR);

				stopSelf();
				return;
			}

			// ����
			if (showNotifi)
				mNotification(updateService.this, SUCCESS);

			stopSelf();
		}
	}

	private void save(String name, String[] value) {
		SharedPreferences bapList;
		SharedPreferences.Editor bapListeditor;

		bapList = getSharedPreferences("bapList", 0);
		bapListeditor = bapList.edit();

		for (int i = 0; i < value.length; i++) {
			bapListeditor.putString(name + "_" + i, value[i]);
		}

		bapListeditor.putBoolean("checker", true);
		bapListeditor.putInt(name, value.length);
		bapListeditor.commit();
	}

	public void mNotification(Context mContext) {
		NotificationManager nm = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(
				mContext);
		mCompatBuilder.setSmallIcon(R.drawable.ic_notifi_icon);
		mCompatBuilder.setTicker("�������б� �޽� ������Ʈ��");
		mCompatBuilder.setWhen(System.currentTimeMillis());
		mCompatBuilder.setAutoCancel(true);
		mCompatBuilder.setContentTitle("�޽� ������Ʈ��");
		mCompatBuilder.setContentText("�޽��� ������Ʈ �ϰ� �ֽ��ϴ� ��ø� ��ٷ� �ּ���");
		mCompatBuilder.setContentIntent(null);
		mCompatBuilder.setOngoing(true);

		nm.notify(777, mCompatBuilder.getNotification());
	}

	public void mNotification(Context mContext, int notifiCode) {
		NotificationManager nm = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0,
				new Intent(mContext, updateService.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(
				mContext);
		mCompatBuilder.setSmallIcon(R.drawable.ic_notifi_icon);
		mCompatBuilder.setTicker("�޽� ������Ʈ �˸�");
		mCompatBuilder.setWhen(System.currentTimeMillis());
		mCompatBuilder.setAutoCancel(true);

		if (notifiCode == WIFI_ERROR) {
			mCompatBuilder.setContentTitle("WIFI ���� �ȵ�");
			mCompatBuilder.setContentText("WIFI ������ �Ǿ� ���� �ʽ��ϴ� �ٽ� �õ��Ϸ��� ��������");

		} else if (notifiCode == NET_ERROR) {
			mCompatBuilder.setContentTitle("��Ʈ��ũ ���� �ȵ�");
			mCompatBuilder.setContentText("���ͳݿ� �����Ҽ� �����ϴ� �ٽ� �õ��Ϸ��� ��������");

		} else if (notifiCode == GET_ERROR) {
			mCompatBuilder.setContentTitle("�޽� �������� ����");
			mCompatBuilder.setContentText("�޽��� �������´� �����߽��ϴ� �ٽ� �õ��Ϸ��� ��������");

		} else {
			mCompatBuilder.setContentTitle("�޽� ������Ʈ ����");
			mCompatBuilder.setContentText("�޽��� ���������� ������Ʈ �߽��ϴ� Ȯ���Ϸ��� ��������");

			pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
					mContext, Bap.class), PendingIntent.FLAG_UPDATE_CURRENT);
		}

		mCompatBuilder.setContentIntent(pendingIntent);

		nm.notify(1010, mCompatBuilder.getNotification());
	}

}