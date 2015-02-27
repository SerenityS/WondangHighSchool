package wondang.icehs.kr.whdghks913.wondanghighschool.todaylist;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ListView;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

public class TodayList extends ActionBarActivity {
    ActionBarDrawerToggle mToggle;
    DrawerLayout mDrawer;
    Toolbar mToolbar;

    ListView mListView;
    TodayListAdapter mAdapter;

    /**
     * 교시 운영시간 비고
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_list);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.flat_sky_blue));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.app_name, R.string.app_name);
        mDrawer.setDrawerListener(mToggle);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new TodayListAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);
        mListView.setDivider(null);

        mAdapter.addItem("정규수업", null, null, true);

        mAdapter.addItem("학교등교시간", "08:30", null, false);
        mAdapter.addItem("학교등교시간 (생기록)", "08:40", null, false);
        mAdapter.addItem("조례 및 학생이동", "08:40 ~ 08:50", "10분", false);
        mAdapter.addItem("1 교시", "08:50 ~ 09:40", "50분", false);
        mAdapter.addItem("2 교시", "09:50 ~ 10:40", "50분", false);
        mAdapter.addItem("3 교시", "10:50 ~ 11:40", "50분", false);
        mAdapter.addItem("4 교시", "11:50 ~ 12:40", "50분", false);
        mAdapter.addItem("중식", "12:40 ~ 13:40", "60분", false);
        mAdapter.addItem("5 교시", "13:40 ~ 14:30", "50분", false);
        mAdapter.addItem("6 교시", "14:40 ~ 15:30", "50분", false);
        mAdapter.addItem("7 교시", "15:40 ~ 16:30", "50분", false);
        mAdapter.addItem("청소 및 종례", "16:30 ~ ", null, false);

        mAdapter.addItem("방과후 학교 활동", null, null, true);
        mAdapter.addItem("방과후 학교 활동", "17:00 ~ 18:00", "60분", false);
        mAdapter.addItem("석식", "18:00 ~ 19:00", "60분", false);

        mAdapter.addItem("자기주도적학습", null, null, true);
        mAdapter.addItem("자기주도적학습 준비", "18:45 ~ 19:00", "15분", false);
        mAdapter.addItem("1차시", "19:00 ~ 21:00", "120분", false);
        mAdapter.addItem("2차시", "21:10 ~ 22:00", "50분", false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
