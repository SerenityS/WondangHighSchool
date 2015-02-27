package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mrengineer13.snackbar.SnackBar;

import java.io.File;
import java.util.Calendar;

import uk.me.lewisdeane.ldialogs.CustomListDialog;
import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Database;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Preference;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.TimeTableTool;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.copyAssets;

public class TimeTableActivity extends ActionBarActivity {
    Toolbar mToolbar;

    ListView mListView;
    TimeTableAdapter mAdapter;

    Database mDatabase;

    Calendar mCalendar;
    int DayOfWeek;

    Preference mPref;

    TextView mTimeName, mDayOfTheWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.flat_melon_yellow));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new TimeTableAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));

        mTimeName = (TextView) findViewById(R.id.mTimeName);
        mDayOfTheWeek = (TextView) findViewById(R.id.mDayOfTheWeek);

        mPref = new Preference(getApplicationContext());

        mCalendar = Calendar.getInstance();
        DayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        getDayOfWeek();

        showTimeTable();
    }

    private void showTimeTable() {
        int mGrade = mPref.getInt("myGrade", -1);
        int mClass = mPref.getInt("myClass", -1);

        if (mGrade == -1 || mClass == -1) {
            mTimeName.setText(String.format(getString(R.string.no_setting_mygrade), mGrade, mClass));
            resetGrade();
            return;
        }

        mAdapter.clearData();
        mTimeName.setText(String.format(getString(R.string.timetable_title), mGrade, mClass));
        mDayOfTheWeek.setText(TimeTableTool.mDisplayName[DayOfWeek]);

//        if (true) {
//            mAdapter.addItem(Integer.toString(1), "DB 준비중", "2015년 DB를 준비중입니다");
//            mAdapter.notifyDataSetChanged();
//            return;
//        }

        try {
            if (getDBUpdate()) {
                copyAssets copyAssets = new copyAssets();
                copyAssets.assetsFileCopy(getApplicationContext(), TimeTableTool.mFilePath, TimeTableTool.TimeTableDBName);
            }

            mDatabase = new Database();
            mDatabase.openOrCreateDatabase(TimeTableTool.mFilePath, TimeTableTool.TimeTableDBName, TimeTableTool.tableName, "");

            Cursor mCursor = mDatabase.getData(TimeTableTool.tableName, "*");

            /**
             * Move to Row
             * ---- moveToFirst
             * ---- moveToNext
             * ---- moveToPosition
             * ---- moveToLast
             *
             * Mon : DayOfWeek : 0
             * Tus : DayOfWeek : 1
             * ...
             * Fri : DayOfWeek : 4
             */
            mCursor.moveToPosition((DayOfWeek * 7) + 1);

            for (int period = 1; period <= 7; period++) {
                String mSubject, mRoom = null;

                /**
                 * | | | |
                 * 0 1 2 3
                 */
                if (mGrade == 1) {
                    mSubject = mCursor.getString((mClass * 2) - 2);
//                    mRoom = mCursor.getString((mClass * 2) - 1);
                } else if (mGrade == 2) {
                    mSubject = mCursor.getString(18 + (mClass * 2));
//                    mRoom = mCursor.getString(19 + (mClass * 2));
                } else {
                    mSubject = mCursor.getString(39 + mClass);
                    mRoom = null;
                }

//                if (mSubject != null && !mSubject.isEmpty()
//                        && mSubject.indexOf("\n") != -1)
//                    mSubject = mSubject.replace("\n", "(") + ")";

                mAdapter.addItem(Integer.toString(period), mSubject, mRoom);

                mCursor.moveToNext();
            }

            mAdapter.notifyDataSetChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean getDBUpdate() {
        boolean fileInfo = !(new File(TimeTableTool.mFilePath + TimeTableTool.TimeTableDBName).exists());
        boolean versionInfo = mPref.getInt("TimeTableDBVersion", 0) != TimeTableTool.dbVersion;

        if (fileInfo || versionInfo) {
            mPref.putInt("TimeTableDBVersion", TimeTableTool.dbVersion);
            return true;
        }
        return false;
    }

    private void getDayOfWeek() {
        if (DayOfWeek > 1 && DayOfWeek < 7) {
            DayOfWeek -= 2;
        } else {
            DayOfWeek = 0;
        }
    }

    private void resetGrade() {
        mPref.remove("myGrade");

        CustomListDialog.Builder builder = new CustomListDialog.Builder(this, R.string.action_setting_mygrade, R.array.myGrade);

        CustomListDialog customListDialog = builder.build();
        customListDialog.show();
        customListDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int position, String[] items, String item) {
                mPref.putInt("myGrade", position + 1);
                resetClass();
            }
        });
    }

    private void resetClass() {
        mPref.remove("myClass");

        CustomListDialog.Builder builder = new CustomListDialog.Builder(this, R.string.action_setting_myclass, R.array.myClass);

        CustomListDialog customListDialog = builder.build();
        customListDialog.show();
        customListDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int position, String[] items, String item) {
                mPref.putInt("myClass", position + 1);
                showTimeTable();
            }
        });
    }

    private void settingWeek() {
        CustomListDialog.Builder builder = new CustomListDialog.Builder(this, R.string.action_setting_myweek, R.array.myWeek);

        CustomListDialog customListDialog = builder.build();
        customListDialog.show();
        customListDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int position, String[] items, String item) {
                DayOfWeek = position;
                showTimeTable();
            }
        });
    }

    private void shareTimeTable() {
        try {
            int max = mAdapter.getCount();
            String[] TimeTable = new String[max];

            String mText = "";
            for (int i = 0; i < max; i++) {
                TimeTableListData mData = mAdapter.getItem(i);
                String mRoom = mData.mRoom;
                if (mRoom == null) mRoom = "정보없음";
                TimeTable[i] = mData.mSubjectName + "(" + mRoom + ")";
                mText += "\n" + (i + 1) + "교시 : " + TimeTable[i];
            }

            String title = getString(R.string.action_share_timetable);
            Intent msg = new Intent(Intent.ACTION_SEND);
            msg.addCategory(Intent.CATEGORY_DEFAULT);
            msg.putExtra(Intent.EXTRA_TITLE, title);
            msg.putExtra(Intent.EXTRA_TEXT, String.format(
                    getString(R.string.action_share_timetable_msg),
                    TimeTableTool.mDisplayName[DayOfWeek], mText));
            msg.setType("text/plain");
            startActivity(Intent.createChooser(msg, title));
        } catch (Exception ex) {
            ex.printStackTrace();

            SnackBar.Builder mSnackBar = new SnackBar.Builder(this);
            mSnackBar.withMessage(getString(R.string.share_timetable_error));
            mSnackBar.withStyle(SnackBar.Style.INFO);
            mSnackBar.withActionMessage(getString(android.R.string.ok));
            mSnackBar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_reset_mygrade) {
            resetGrade();
            return true;
        } else if (id == R.id.action_setting_myweek) {
            settingWeek();
            return true;

        } else if (id == R.id.action_share_timetable) {
            shareTimeTable();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
