package wondang.icehs.kr.whdghks913.wondanghighschool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
		setOnPreferenceChange(findPreference("updateWiFi"));
	}

	private void setOnPreferenceChange(Preference mPreference) {
		mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);

		if (mPreference instanceof ListPreference) {
			onPreferenceChangeListener.onPreferenceChange(
					mPreference,
					PreferenceManager.getDefaultSharedPreferences(
							mPreference.getContext()).getString(
							mPreference.getKey(), ""));
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
				 * ListPreference�� ��� stringValue�� entryValues�̱� ������ �ٷ� Summary��
				 * �������� ���Ѵ� ���� ������ entries���� String�� �ε��Ͽ� �����Ѵ�
				 */

				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

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
				alert.setTitle("���̼��� ����");
				alert.setPositiveButton("Ȯ��",
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