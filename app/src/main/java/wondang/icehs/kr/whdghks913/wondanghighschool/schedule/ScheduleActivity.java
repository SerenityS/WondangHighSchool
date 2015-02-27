package wondang.icehs.kr.whdghks913.wondanghighschool.schedule;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mrengineer13.snackbar.SnackBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

public class ScheduleActivity extends ActionBarActivity {
    ActionBarDrawerToggle mToggle;
    DrawerLayout mDrawer;
    Toolbar mToolbar;

    ArrayList<Item> mItem = new ArrayList<Item>();
    ListView mListview;

    SnackBar.Builder mSnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.flat_bright_red_orange));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.app_name, R.string.app_name);
        mDrawer.setDrawerListener(mToggle);

        mListview = (ListView) findViewById(R.id.mListView);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int position,
                                    long ld) {
                EntryItem mEntryItem = (EntryItem) mItem.get(position);
                SimpleDateFormat sdFormat = new SimpleDateFormat(
                        "yyyy.MM.dd (E)", Locale.KOREA);
                Calendar mCalendar = Calendar.getInstance();

                try {
                    mCalendar.setTime(sdFormat.parse(mEntryItem.mSummary));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                getTime(mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH));
            }
        });

        mItem.add(new SectionItem("2015년 3월 일정"));
        mItem.add(new EntryItem("3.1절", "2015.03.01 (일)", true));
        mItem.add(new EntryItem("입학식 및 시업식", "2015.03.02 (월)"));
        mItem.add(new EntryItem("전국 연합 학력 평가 (전학년)", "2015.03.11 (수)"));
        mItem.add(new EntryItem("학부모 총회", "2015.03.13 (금)"));

        mItem.add(new SectionItem("2015년 4월 일정"));
        mItem.add(new EntryItem("전국 연합 학력 평가 (3학년)", "2015.04.09 (목)"));
        mItem.add(new EntryItem("영어 듣기 평가", "2015.04.14 (화)"));
        mItem.add(new EntryItem("영어 듣기 평가", "2015.04.15 (수)"));
        mItem.add(new EntryItem("영어 듣기 평가", "2015.04.16 (목)"));
        mItem.add(new EntryItem("1학기 1회고사", "2015.04.27 (월)"));
        mItem.add(new EntryItem("1학기 1회고사", "2015.04.28 (화)"));
        mItem.add(new EntryItem("1학기 1회고사", "2015.04.29 (수)"));
        mItem.add(new EntryItem("1학기 1회고사", "2015.04.30 (목)"));

        mItem.add(new SectionItem("2015년 5월 일정"));
        mItem.add(new EntryItem("어린이날", "2015.05.05 (화)", true));
        mItem.add(new EntryItem("꿈끼 탐색 주간", "2015.05.06 (수)"));
        mItem.add(new EntryItem("꿈끼 탐색 주간", "2015.05.07 (목)"));
        mItem.add(new EntryItem("꿈끼 탐색 주간", "2015.05.08 (금)"));
        mItem.add(new EntryItem("교내 체육대회 (1,2학년)", "2015.05.15 (금)"));
        mItem.add(new EntryItem("현장 학습 (3학년)", "2015.05.15 (금)"));
        mItem.add(new EntryItem("석가탄신일", "2015.05.25 (월)"));

        mItem.add(new SectionItem("2015년 6월 일정"));
        mItem.add(new EntryItem("전국 연합 학력 평가 (1,2학년)", "2015.06.04 (목)"));
        mItem.add(new EntryItem("대 수능 모의 평가 (3학년)", "2015.06.04 (목)"));
        mItem.add(new EntryItem("개교기념일", "2015.06.05 (금)", true));
        mItem.add(new EntryItem("현충일", "2015.06.06 (토)", true));
        mItem.add(new EntryItem("국가 수준 학업 성취도 평가 (2학년)", "2015.06.23 (화)"));

        mItem.add(new SectionItem("2015년 7월 일정"));
        mItem.add(new EntryItem("1학기 2회고사", "2015.06.29 (월)"));
        mItem.add(new EntryItem("1학기 2회고사", "2015.06.30 (화)"));
        mItem.add(new EntryItem("1학기 2회고사", "2015.07.01 (수)"));
        mItem.add(new EntryItem("1학기 2회고사", "2015.07.02 (목)"));
        mItem.add(new EntryItem("전국 연합 학력 평가 (3학년)", "2015.07.09 (목)"));
        mItem.add(new EntryItem("여름방학식", "2015.07.17 (금)", true));

        mItem.add(new SectionItem("2015년 8월 일정"));
        mItem.add(new EntryItem("개학식", "2015.08.05 (수)"));

        mItem.add(new SectionItem("2015년 9월 일정"));
        mItem.add(new EntryItem("전국 연합 학력 평가 (1,2학년)", "2015.09.02 (수)"));
        mItem.add(new EntryItem("대 수능 모의 평가 (3학년)", "2015.09.02 (수)"));
        mItem.add(new EntryItem("영어 듣기 평가", "2015.09.15 (화)"));
        mItem.add(new EntryItem("영어 듣기 평가", "2015.09.16 (수)"));
        mItem.add(new EntryItem("영어 듣기 평가", "2015.09.17 (목)"));
        mItem.add(new EntryItem("추석 연휴", "2015.9.28 (월)", true));
        mItem.add(new EntryItem("추석 연휴", "2015.9.29 (화)", true));

        mItem.add(new SectionItem("2015년 10월 일정"));
        mItem.add(new EntryItem("2학기 1회고사", "2015.09.30 (수)"));
        mItem.add(new EntryItem("2학기 1회고사", "2015.10.01 (목)"));
        mItem.add(new EntryItem("2학기 1회고사", "2015.10.02 (금)"));
        mItem.add(new EntryItem("2학기 1회고사", "2015.10.05 (월)"));
        mItem.add(new EntryItem("한글날", "2015.10.09 (금)", true));
        mItem.add(new EntryItem("전국 연합 학력 평가 (3학년)", "2015.10.13 (화)"));

        mItem.add(new SectionItem("2015년 11월 일정"));
        mItem.add(new EntryItem("대학 수학 능력 시험", "2015.11.12 (목)", true));
        mItem.add(new EntryItem("2학기 2회고사 (3학년)", "2015.11.16 (월)"));
        mItem.add(new EntryItem("2학기 2회고사 (3학년)", "2015.11.17 (화)"));
        mItem.add(new EntryItem("2학기 2회고사 (3학년)", "2015.11.18 (수)"));
        mItem.add(new EntryItem("2학기 2회고사 (3학년)", "2015.11.19 (목)"));
        mItem.add(new EntryItem("전국 연합 학력 평가 (1,2학년)", "2015.11.17 (화)"));

        mItem.add(new SectionItem("2015년 12월 일정"));
        mItem.add(new EntryItem("2학기 2회고사 (1,2학년)", "2015.11.30 (월)"));
        mItem.add(new EntryItem("2학기 2회고사 (1,2학년)", "2015.12.01 (화)"));
        mItem.add(new EntryItem("2학기 2회고사 (1,2학년)", "2015.12.02 (수)"));
        mItem.add(new EntryItem("2학기 2회고사 (1,2학년)", "2015.12.03 (목)"));
        mItem.add(new EntryItem("졸업 사정회 (3학년)", "2015.12.07 (월)"));
        mItem.add(new EntryItem("한마음 으뜸제", "2015.12.16 (수)"));
        mItem.add(new EntryItem("겨울 방학식", "2015.12.18 (금)"));

        mItem.add(new SectionItem("2016년 1월 일정"));
        mItem.add(new EntryItem("신정", "2016.01.01 (금)"));

        mItem.add(new SectionItem("2016년 2월 일정"));
        mItem.add(new EntryItem("개학식", "2016.02.01 (월)"));
        mItem.add(new EntryItem("졸업식/종업식", "2016.02.04 (목)"));

        EntryAdapter adapter = new EntryAdapter(this, mItem);
        mListview.setAdapter(adapter);
    }

    private void getTime(int year, int month, int day) {
        Calendar myTime = Calendar.getInstance();

        long nowTime = myTime.getTimeInMillis();
        myTime.set(year, month, day);
        long touchTime = myTime.getTimeInMillis();

        long diff = (touchTime - nowTime);

        boolean isPast = false;
        if (diff < 0) {
            diff = -diff;
            isPast = true;
        }

        int diffInt = (int) (diff /= 24 * 60 * 60 * 1000);

        String Text;
        if (isPast)
            Text = "선택하신 날짜는 " + diffInt + "일전 날짜입니다";
        else
            Text = "선택하신 날짜까지 " + diffInt + "일 남았습니다";

        mSnackBar = new SnackBar.Builder(this);
        mSnackBar.withMessage(Text);
        mSnackBar.withStyle(SnackBar.Style.INFO);
        mSnackBar.withActionMessage(getResources().getString(android.R.string.ok));
        mSnackBar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface Item {
        public boolean isSection();
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
