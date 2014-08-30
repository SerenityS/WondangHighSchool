package wondang.icehs.kr.whdghks913.wondanghighschool.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class Schedule extends Activity {

	private ArrayList<Item> mItem = new ArrayList<Item>();
	private ListView mListview;
	private CroutonHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_listview);

		mListview = (ListView) findViewById(R.id.mListView);
		mListview.setOnItemClickListener(new OnItemClickListener() {

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

		mItem.add(new SectionItem("2014년 3월 일정"));
		mItem.add(new EntryItem("3.1절", "2014.03.01 (토)", true));
		mItem.add(new EntryItem("입학식", "2014.03.03 (월)"));
		mItem.add(new EntryItem("전국 연합 학력 평가 (전학년)", "2014.03.12 (수)"));
		mItem.add(new EntryItem("학부모 총회", "2014.03.14 (금)"));
		mItem.add(new EntryItem("현장 학습 (1~2학년)", "2014.03.26 (수)"));
		mItem.add(new EntryItem("현장 학습 (1~2학년)", "2014.03.27 (목)"));
		mItem.add(new EntryItem("현장 학습 (1~2학년)", "2014.03.28 (금)"));

		mItem.add(new SectionItem("2014년 4월 일정"));
		mItem.add(new EntryItem("전국 연합 학력 평가 (3학년)", "2014.04.10 (목)"));
		mItem.add(new EntryItem("영어 듣기 평가 (1학년)", "2014.04.15 (화)"));
		mItem.add(new EntryItem("영어 듣기 평가 (2학년)", "2014.04.16 (수)"));
		mItem.add(new EntryItem("영어 듣기 평가 (3학년)", "2014.04.17 (목)"));
		mItem.add(new EntryItem("1학기 1회고사", "2014.04.28 (월)"));
		mItem.add(new EntryItem("1학기 1회고사", "2014.04.29 (화)"));
		mItem.add(new EntryItem("1학기 1회고사", "2014.04.30 (수)"));

		mItem.add(new SectionItem("2014년 5월 일정"));
		mItem.add(new EntryItem("어린이날", "2014.05.05 (월)", true));
		mItem.add(new EntryItem("석가탄신일", "2014.05.06 (화)", true));
		mItem.add(new EntryItem("교내체육대회(1학년, 2학년) / 현장학습(3학년)",
				"2014.05.09 (금)"));

		mItem.add(new SectionItem("2014년 6월 일정"));
		mItem.add(new EntryItem("지방 선거", "2014.06.04 (수)", true));
		mItem.add(new EntryItem("개교 기념일", "2014.06.05 (목)", true));
		mItem.add(new EntryItem("현충일", "2014.06.06 (금)", true));
		mItem.add(new EntryItem("대수능모의평가(3학년) / 전국연합학력평가(1학년, 2학년)",
				"2014.06.12 (목)"));
		mItem.add(new EntryItem("국가 수준 학업 성취도 평가 (2학년)", "2014.06.24 (화)"));

		mItem.add(new SectionItem("2014년 7월 일정"));
		mItem.add(new EntryItem("1학기 2회고사", "2014.07.04 (금)"));
		mItem.add(new EntryItem("1학기 2회고사", "2014.07.07 (월)"));
		mItem.add(new EntryItem("1학기 2회고사", "2014.07.08 (화)"));
		mItem.add(new EntryItem("1학기 2회고사", "2014.07.09 (수)"));
		mItem.add(new EntryItem("전국 연합 학력 평가 (3학년)", "2014.07.10 (목)"));
		mItem.add(new EntryItem("여름방학식", "2014.07.21 (월)", true));

		mItem.add(new SectionItem("2014년 8월 일정"));
		mItem.add(new EntryItem("개학식", "2014.08.18 (월)"));

		mItem.add(new SectionItem("2014년 9월 일정"));
		mItem.add(new EntryItem("대수능모의평가(3학년) / 전국연합학력평가(1학년, 2학년)",
				"2014.09.03 (수)"));
		mItem.add(new EntryItem("추석 연휴", "2014.09.07 (일)", true));
		mItem.add(new EntryItem("추석", "2014.09.08 (월)", true));
		mItem.add(new EntryItem("추석 연휴", "2014.09.09 (화)", true));
		mItem.add(new EntryItem("추석 연휴", "2014.09.10 (수)", true));
		mItem.add(new EntryItem("영어 듣기 능력 평가 (1학년)", "2014.09.16 (화)"));
		mItem.add(new EntryItem("영어 듣기 능력 평가 (2학년)", "2014.09.17 (수)"));
		mItem.add(new EntryItem("영어 듣기 능력 평가 (3학년)", "2014.09.18 (목)"));

		mItem.add(new SectionItem("2014년 10월 일정"));
		mItem.add(new EntryItem("개천절", "2014.10.03 (금)", true));
		mItem.add(new EntryItem("전국 연합 학력 평가(3학년)", "2014.10.07 (화)"));
		mItem.add(new EntryItem("한글날", "2014.10.09 (목)", true));
		mItem.add(new EntryItem("2학기 1회고사", "2014.10.13 (월)"));
		mItem.add(new EntryItem("2학기 1회고사", "2014.10.14 (화)"));
		mItem.add(new EntryItem("2학기 1회고사", "2014.10.15 (수)"));

		mItem.add(new SectionItem("2014년 11월 일정"));
		mItem.add(new EntryItem("대학 수학 능력 시험 (수능)", "2014.11.13 (목)"));
		mItem.add(new EntryItem("2학기 2회고사(3학년)", "2014.11.17 (월)"));
		mItem.add(new EntryItem("2학기 2회고사(3학년) / 전국 연합 학력 평가 (1학년, 2학년)",
				"2014.11.18 (화)"));
		mItem.add(new EntryItem("2학기 2회고사(3학년)", "2014.11.19 (수)"));
		mItem.add(new EntryItem("2학기 2회고사(3학년)", "2014.11.20 (목)"));

		mItem.add(new SectionItem("2014년 12월 일정"));
		mItem.add(new EntryItem("졸업사정회", "2014.12.08 (월)"));
		mItem.add(new EntryItem("2학기 2회고사(1학년, 2학년)", "2014.12.15 (월)"));
		mItem.add(new EntryItem("2학기 2회고사(1학년, 2학년)", "2014.12.16 (화)"));
		mItem.add(new EntryItem("2학기 2회고사(1학년, 2학년)", "2014.12.17 (수)"));
		mItem.add(new EntryItem("2학기 2회고사(1학년, 2학년)", "2014.12.18 (목)"));
		mItem.add(new EntryItem("한마음 으뜸제", "2014.12.29 (월)"));
		mItem.add(new EntryItem("겨울 방학식", "2014.12.31 (수)", true));

		mItem.add(new SectionItem("2015년 1월 일정"));
		mItem.add(new EntryItem("신정", "2015.01.01 (목)", true));

		mItem.add(new SectionItem("2015년 2월 일정"));
		mItem.add(new EntryItem("개학식", "2015.02.09 (월)"));
		mItem.add(new EntryItem("졸업식 / 종업식", "2015.02.13 (금)", true));
		mItem.add(new EntryItem("설 연휴", "2015.02.18 (수)", true));
		mItem.add(new EntryItem("설", "2015.02.19 (목)", true));
		mItem.add(new EntryItem("설 연휴", "2015.02.20 (금)", true));

		EntryAdapter adapter = new EntryAdapter(this, mItem);
		mListview.setAdapter(adapter);

		mHelper = new CroutonHelper(this);
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

		mHelper.clearCroutonsForActivity();
		mHelper.setText(Text);
		mHelper.setStyle(Style.INFO);
		mHelper.show();
	}

	public interface Item {
		public boolean isSection();
	}
}