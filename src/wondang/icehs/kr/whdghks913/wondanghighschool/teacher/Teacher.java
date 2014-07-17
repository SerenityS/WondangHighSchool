package wondang.icehs.kr.whdghks913.wondanghighschool.teacher;

import java.util.ArrayList;
import java.util.Collections;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Teacher extends Activity {
	/**
	 * 변수, 상수 정의
	 */
	private final String DBName = "Teacher";
	private final String TableName = "TeacherInfo";
	private final int DBVersion = 1;

	private SQLiteDatabase myDB;
	private DatabaseHelper dbHelper;

	private ListView mListView;
	private TeacherAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);

		dbHelper = new DatabaseHelper(this, DBName, null, DBVersion);
		myDB = dbHelper.getWritableDatabase();

		mListView = (ListView) findViewById(R.id.mList);
		mAdapter = new TeacherAdapter(this);
		mListView.setAdapter(mAdapter);

		loadMemo();
	}

	private void loadMemo() {
		mAdapter.clear();

		final Handler handler = new Handler();
		new Thread() {

			@Override
			public void run() {
				handler.post(new Runnable() {

					@Override
					public void run() {
						ProgressDialog mDialog = ProgressDialog.show(
								Teacher.this, "", "Data loding...");

						try {
							String SQL = "select name, room, roomNum,  subject"
									+ " from " + TableName;

							Cursor mCursor = myDB.rawQuery(SQL, null);
							int recordCount = mCursor.getCount();

							Log.d("recordCount", "" + recordCount);

							for (int i = 0; i < recordCount; i++) {
								mCursor.moveToNext();

								String name = mCursor.getString(0);
								String room = mCursor.getString(1);
								String roomNum = mCursor.getString(2);
								String subject = mCursor.getString(3);

								mAdapter.addItem(name, room, roomNum, subject);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						} finally {
							mDialog.cancel();
						}
					}
				});
			}
		}.start();

		mAdapter.dataChange();
	}

	public void addDB(String Name, String Room, String roomNum, String Subject) {
		myDB.beginTransaction();

		try {
			ContentValues recordValues = new ContentValues();

			recordValues.put("name", Name);
			recordValues.put("room", Room);
			recordValues.put("roomNum", roomNum);
			recordValues.put("subject", Subject);

			myDB.insert(TableName, null, recordValues);

			myDB.setTransactionSuccessful();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			myDB.endTransaction();
		}
	}

	private void deleteDB(String name, String room, String roomNum,
			String subject) {
		String[] deleteInfo = { name, room, roomNum, subject };
		myDB.delete(TableName,
				"title=? and room=? and roomNum=? and subject=?", deleteInfo);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			/**
			 * DB를 만들때 호출
			 */

			try {
				String DROP_SQL = "drop table if exists " + TableName;
				db.execSQL(DROP_SQL);

				String CREATE_SQL = "create table " + TableName + "("
						+ " _id integer PRIMARY KEY autoincrement, "
						+ " name text, " + " room text, " + " roomNum text, "
						+ " subject text)";
				db.execSQL(CREATE_SQL);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
			Log.d("onOpen", "onOpen()");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			/**
			 * DB가 업데이트 될때 호출
			 */
		}
	}

	private class TeacherAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<ListData> mListData = new ArrayList<ListData>();

		public TeacherAdapter(Context mContext) {
			super();
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			return mListData.size();
		}

		@Override
		public Object getItem(int position) {
			return mListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void addItem(String name, String room, String roomNum,
				String subject) {
			ListData teacherInfo = new ListData();
			teacherInfo.mName = name;
			teacherInfo.mRoom = room;
			teacherInfo.mRoomNum = roomNum;
			teacherInfo.mSubject = subject;

			mListData.add(teacherInfo);
		}

		public void clear() {
			mListData.clear();
		}

		public void sort() {
			Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
			dataChange();
		}

		public void dataChange() {
			mAdapter.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				holder = new ViewHolder();

				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.teacher_listview_item,
						null);

				holder.mName = (TextView) convertView.findViewById(R.id.mName);
				holder.mRoom = (TextView) convertView.findViewById(R.id.mRoom);
				holder.mRoomNum = (TextView) convertView
						.findViewById(R.id.mRoomNum);
				holder.mSubject = (TextView) convertView
						.findViewById(R.id.mSubject);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ListData mData = mListData.get(position);

			holder.mName.setText(mData.mName);
			holder.mRoom.setText(mData.mRoom);
			holder.mRoomNum.setText(mData.mRoomNum);
			holder.mSubject.setText(mData.mSubject);

			return convertView;
		}
	}

	private class ViewHolder {
		public TextView mName;
		public TextView mRoom;
		public TextView mRoomNum;
		public TextView mSubject;
	}
}
