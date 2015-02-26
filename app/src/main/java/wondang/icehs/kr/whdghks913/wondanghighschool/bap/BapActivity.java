package wondang.icehs.kr.whdghks913.wondanghighschool.bap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;

import com.github.mrengineer13.snackbar.SnackBar;

import java.util.Calendar;

import itmir.tistory.floatingactionbutton.FloatingActionButton;
import uk.me.lewisdeane.ldialogs.CustomDialog;
import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.BapTool;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Preference;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Tools;

public class BapActivity extends ActionBarActivity {
    ActionBarDrawerToggle mToggle;
    DrawerLayout mDrawer;
    Toolbar mToolbar;

    ListView mListView;
    BapListAdapter mAdapter;

    static Calendar mCalendar;
    static int DAY_OF_WEEK;

    BapDownloadTask mProcessTask;

    ProgressDialog mDialog;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bap);

        mCalendar = Calendar.getInstance();
        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.flat_yellow_green));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.app_name, R.string.app_name);
        mDrawer.setDrawerListener(mToggle);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new BapListAdapter(this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                BapListData mData = mAdapter.getItem(position);
                String mShareBapMsg = String.format(
                        getString(R.string.shareBap_message_msg), mData.mCalender,
                        mData.mLunch, mData.mDinner);
                bapShare(mShareBapMsg);

                return true;
            }
        });

        FloatingActionButton mFloatingButton = (FloatingActionButton) findViewById(R.id.mFloatingActionButton);
        mFloatingButton.attachToListView(mListView);
        mFloatingButton.setAlwaysOnTop(true);
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalenderBap();
            }
        });

        getBapList();
    }

    private void getBapList() {
        mAdapter.clearData();

        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        // 이번주 월요일 날짜를 가져온다
        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
        mCalendar.add(Calendar.DATE, 2 - DAY_OF_WEEK);

        for (int i = 0; i < 5; i++) {
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            BapTool.restoreBapDateClass mData =
                    BapTool.restoreBapData(this, year, month, day);

            if (mData.isBlankDay) {
                if (Tools.isNetwork(this)) {
                    if (!isUpdating) {
                        // Not Updating (If isUpdating = false)
                        mDialog = new ProgressDialog(this);
                        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mDialog.setMax(100);
                        mDialog.setTitle("로딩중");
                        mDialog.setMessage("데이터를 불러오는중입니다");
                        mDialog.setCancelable(false);
                        mDialog.show();

                        mProcessTask = new BapDownloadTask(this);
                        mProcessTask.execute(year, month, day);

                        isUpdating = true;
                    }
                } else {
                    CustomDialog.Builder builder = new CustomDialog.Builder(this, R.string.no_network_title, android.R.string.ok);
                    builder.content(getString(R.string.no_network_msg));
                    CustomDialog customDialog = builder.build();
                    customDialog.show();
                }

                return;
            }

            mAdapter.addItem(mData.Calender, mData.DayOfTheWeek, mData.Lunch, mData.Dinner);
            mCalendar.add(Calendar.DATE, 1);
        }

        mAdapter.notifyDataSetChanged();
        setCurrentItem();
    }

    public void errorMessage() {
        SnackBar.Builder mSnackBar = new SnackBar.Builder(this);
        mSnackBar.withMessage(getString(R.string.share_timetable_error));
        mSnackBar.withStyle(SnackBar.Style.INFO);
        mSnackBar.withActionMessage(getResources().getString(android.R.string.ok));
        mSnackBar.show();
    }

    public class BapDownloadTask extends ProcessTask {
        public BapDownloadTask(Context mContext) {
            super(mContext);
        }

        @Override
        public void onPreDownload() {

        }

        @Override
        public void onUpdate(int progress) {
            mDialog.setProgress(progress);
        }

        @Override
        public void onFinish(long result) {
            if (mDialog != null)
                mDialog.dismiss();

            isUpdating = false;

            if (result == -1) {
                errorMessage();
                return;
            }

            getBapList();
        }
    }

    private void setCurrentItem() {
        if (DAY_OF_WEEK > 1 && DAY_OF_WEEK < 7) {
            mListView.setSelection(DAY_OF_WEEK - 2);
        } else {
            mListView.setSelection(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mDialog != null)
            mDialog.dismiss();

        mCalendar = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calender) {
            setCalenderBap();

            return true;

        } else if (id == R.id.action_refresh) {
            if (mCalendar == null)
                mCalendar = Calendar.getInstance();

            DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
            mCalendar.add(Calendar.DATE, 2 - DAY_OF_WEEK);

            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            String mPrefLunchName = BapTool.getBapStringFormat(year, month + 1, day, BapTool.TYPE_LUNCH);
            String mPrefDinnerName = BapTool.getBapStringFormat(year, month + 1, day, BapTool.TYPE_DINNER);

            Preference mPref = new Preference(getApplicationContext(), BapTool.BAP_PREFERENCE_NAME);
            mPref.remove(mPrefLunchName);
            mPref.remove(mPrefDinnerName);

            getBapList();

            return true;
        } else if (id == R.id.action_today) {
            mCalendar = null;
            mCalendar = Calendar.getInstance();

            getBapList();

            return true;

        } else if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setCalenderBap() {
        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mCalendar.set(year, monthOfYear, dayOfMonth);
                        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
                        getBapList();
                    }
                }, year, month, day);
        mDialog.show();
    }

    private void bapShare(String mShareBapMsg) {
        Intent msg = new Intent(Intent.ACTION_SEND);
        msg.addCategory(Intent.CATEGORY_DEFAULT);
        msg.putExtra(Intent.EXTRA_TITLE, getString(R.string.shareBap_title));
        msg.putExtra(Intent.EXTRA_TEXT, mShareBapMsg);
        msg.setType("text/plain");
        startActivity(Intent.createChooser(msg, getString(R.string.shareBap_title)));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

// Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else {
            finish();
        }
    }
}
