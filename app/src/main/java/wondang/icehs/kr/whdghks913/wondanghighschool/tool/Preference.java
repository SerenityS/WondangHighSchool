package wondang.icehs.kr.whdghks913.wondanghighschool.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    private SharedPreferences mRref;
    private SharedPreferences.Editor mEditor;

    public Preference(Context mContext) {
        mRref = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mRref.edit();
    }

    public Preference(Context mContext, String prefName) {
        mRref = mContext.getSharedPreferences(prefName, 0);
        mEditor = mRref.edit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mRref.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mRref.getInt(key, defValue);
    }

    public String getString(String key, String defValue) {
        return mRref.getString(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public void clear() {
        mEditor.clear().commit();
    }

    public void remove(String key) {
        mEditor.remove(key).commit();
    }
}