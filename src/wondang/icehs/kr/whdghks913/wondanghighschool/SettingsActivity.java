package wondang.icehs.kr.whdghks913.wondanghighschool;

import wondang.icehs.kr.whdghks913.wondanghighschool.bapautoupdate.updateAlarm;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.wondang_settings);

		setOnPreferenceClick(findPreference("openSource"));
		setOnPreferenceChange(findPreference("updateLife"));
		setOnPreferenceChange(findPreference("autoBapUpdate"));
	}

	private void setOnPreferenceChange(Preference mPreference) {
		mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);

		if (mPreference instanceof ListPreference) {
			ListPreference listPreference = (ListPreference) mPreference;
			int index = listPreference.findIndexOfValue(PreferenceManager
					.getDefaultSharedPreferences(mPreference.getContext())
					.getString(mPreference.getKey(), ""));
			mPreference
					.setSummary(index >= 0 ? listPreference.getEntries()[index]
							: "자동");
		}
	}

	private void setOnPreferenceClick(Preference mPreference) {
		mPreference.setOnPreferenceClickListener(onPreferenceClickListener);
	}

	private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			String stringValue = newValue.toString();

			if (preference instanceof ListPreference) {

				/**
				 * ListPreference의 경우 stringValue가 entryValues이기 때문에 바로 Summary를
				 * 적용하지 못한다 따라서 설정한 entries에서 String을 로딩하여 적용한다
				 */

				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: "자동");

				updateAlarm updateAlarm = new updateAlarm(SettingsActivity.this);
				updateAlarm.cancle();

				if (index == 0) {
					updateAlarm.autoUpdate();
				} else if (index == 1) {
					updateAlarm.SaturdayUpdate();
				} else if (index == 2) {
					updateAlarm.SundayUpdate();
				}

			} else if (preference instanceof CheckBoxPreference) {
				SharedPreferences mPref = PreferenceManager
						.getDefaultSharedPreferences(SettingsActivity.this);

				if (!mPref.getBoolean("autoBapUpdate", false)
						&& preference.isEnabled()) {
					int updateLife = 1;

					try {
						updateLife = Integer.parseInt(findPreference(
								"openSource").getSharedPreferences().getString(
								"updateLife", "1"));
					} catch (Exception e) {

					}

					updateAlarm updateAlarm = new updateAlarm(
							SettingsActivity.this);

					if (updateLife == 1) {
						updateAlarm.autoUpdate();
					} else if (updateLife == 0) {
						updateAlarm.SaturdayUpdate();
					} else if (updateLife == -1) {
						updateAlarm.SundayUpdate();
					}

				} else {
					updateAlarm updateAlarm = new updateAlarm(
							SettingsActivity.this);
					updateAlarm.cancle();
				}
			}

			return true;
		}

	};

	private Preference.OnPreferenceClickListener onPreferenceClickListener = new Preference.OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			String getKey = preference.getKey();

			if ("openSource".equals(getKey)) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						SettingsActivity.this);
				alert.setTitle("라이센스 정보");
				alert.setPositiveButton("확인",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				alert.setMessage(R.string.license);
				alert.show();

			}

			return true;
		}
	};

}