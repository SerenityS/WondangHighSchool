package wondang.icehs.kr.whdghks913.wondanghighschool.bap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.Calendar;

import itmir.tistory.floatingactionbutton.FloatingActionButton;
import uk.me.lewisdeane.ldialogs.CustomDialog;
import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.BapTool;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Preference;
import wondang.icehs.kr.whdghks913.wondanghighschool.tool.Tools;

public class BapActivity extends ActionBarActivity {
    Toolbar mToolbar;

    ListView mListView;
    BapListAdapter mAdapter;

    Calendar mCalendar;
    int YEAR, MONTH, DAY;
    int DAY_OF_WEEK;

    BapDownloadTask mProcessTask;

    ProgressDialog mDialog;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bap);

        mCalendar = Calendar.getInstance();
        YEAR = mCalendar.get(Calendar.YEAR);
        MONTH = mCalendar.get(Calendar.MONTH);
        DAY = mCalendar.get(Calendar.DAY_OF_MONTH);
        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.flat_yellow_green));
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

        getBapList(true);
    }

    private void getBapList(boolean isUpdate) {
        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();

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
                    if (!isUpdating && isUpdate) {
                        // Not Updating (If isUpdating = false)
                        mDialog = new ProgressDialog(this);
                        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mDialog.setMax(100);
                        mDialog.setTitle(R.string.loading_title);
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

        mCalendar.set(YEAR, MONTH, DAY);
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

            getBapList(false);
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

            int DayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
            mCalendar.add(Calendar.DATE, 2 - DayOfWeek);

            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            String mPrefLunchName = BapTool.getBapStringFormat(year, month + 1, day, BapTool.TYPE_LUNCH);
            String mPrefDinnerName = BapTool.getBapStringFormat(year, month + 1, day, BapTool.TYPE_DINNER);

            Preference mPref = new Preference(getApplicationContext(), BapTool.BAP_PREFERENCE_NAME);
            mPref.remove(mPrefLunchName);
            mPref.remove(mPrefDinnerName);

            getBapList(true);

            return true;
        } else if (id == R.id.action_today) {
            mCalendar = null;
            mCalendar = Calendar.getInstance();
            YEAR = mCalendar.get(Calendar.YEAR);
            MONTH = mCalendar.get(Calendar.MONTH);
            DAY = mCalendar.get(Calendar.DAY_OF_MONTH);

            getBapList(true);

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

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                mCalendar.set(year, month, day);
                YEAR = year;
                MONTH = month;
                DAY = day;
                DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
                getBapList(true);
            }
        }, year, month, day, false);
        datePickerDialog.setYearRange(2006, 2030);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(getSupportFragmentManager(), "Tag");
    }

    private void bapShare(String mShareBapMsg) {
        Intent msg = new Intent(Intent.ACTION_SEND);
        msg.addCategory(Intent.CATEGORY_DEFAULT);
        msg.putExtra(Intent.EXTRA_TITLE, getString(R.string.shareBap_title));
        msg.putExtra(Intent.EXTRA_TEXT, mShareBapMsg);
        msg.setType("text/plain");
        startActivity(Intent.createChooser(msg, getString(R.string.shareBap_title)));
    }
}
