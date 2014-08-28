package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import java.io.File;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class TimeTableShow extends Fragment {
	private Context mContext;
	private final int position;
	private final String dbName, tableName;

	public final String mFilePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/WondangHS/";
	public final int dbVersion = 0;

	private SQLiteDatabase mSQDB;

	private ListView mListView;
	private TimeTableShowAdapter mAdapter;

	public TimeTableShow(Context mContext, int position, String mFileName,
			String tableName) {
		this.mContext = mContext;
		this.position = position;
		this.dbName = mFileName + ".db";
		this.tableName = tableName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listview, null);

		if (getDBUpdate()) {
			copyAssets copyAssets = new copyAssets();
			copyAssets.assetsFileCopy(mContext, mFilePath, dbName);
		}

		mSQDB = SQLiteDatabase.openDatabase(mFilePath + dbName, null,
				SQLiteDatabase.OPEN_READWRITE);

		mAdapter = new TimeTableShowAdapter(mContext);
		mListView = (ListView) view.findViewById(R.id.mListView);
		mListView.setAdapter(mAdapter);

		/**
		 * DB에 저장된 시간표를 가져온다
		 */
		String SQL = "select period, subject, room " + " from " + tableName;

		Cursor mCursor = mSQDB.rawQuery(SQL, null);
		mCursor.moveToPosition(position * 7);

		for (int i = 0; i < 7; i++) {
			int period = mCursor.getInt(0);
			String subject = mCursor.getString(1);
			String room = mCursor.getString(2);

			if ("자습".equals(subject))
				room = "교실";
			else if (subject.indexOf("\n") != -1)
				subject = subject.replace("\n", "(") + ")";

			mAdapter.addItem(period, subject, room);

			mCursor.moveToNext();
		}

		return view;
	}

	private boolean getDBUpdate() {
		SharedPreferences mPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);

		boolean fileInfo = !(new File(mFilePath, dbName).exists());
		boolean versionInfo = mPref.getInt("dbVersion", 0) != dbVersion;

		if (fileInfo || versionInfo)
			return true;
		return false;
	}
}