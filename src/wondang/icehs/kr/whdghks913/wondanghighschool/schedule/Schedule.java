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

		mItem.add(new SectionItem("2014�� 3�� ����"));
		mItem.add(new EntryItem("3.1��", "2014.03.01 (��)", true));
		mItem.add(new EntryItem("���н�", "2014.03.03 (��)"));
		mItem.add(new EntryItem("���� ���� �з� �� (���г�)", "2014.03.12 (��)"));
		mItem.add(new EntryItem("�кθ� ��ȸ", "2014.03.14 (��)"));
		mItem.add(new EntryItem("���� �н� (1~2�г�)", "2014.03.26 (��)"));
		mItem.add(new EntryItem("���� �н� (1~2�г�)", "2014.03.27 (��)"));
		mItem.add(new EntryItem("���� �н� (1~2�г�)", "2014.03.28 (��)"));

		mItem.add(new SectionItem("2014�� 4�� ����"));
		mItem.add(new EntryItem("���� ���� �з� �� (3�г�)", "2014.04.10 (��)"));
		mItem.add(new EntryItem("���� ��� �� (1�г�)", "2014.04.15 (ȭ)"));
		mItem.add(new EntryItem("���� ��� �� (2�г�)", "2014.04.16 (��)"));
		mItem.add(new EntryItem("���� ��� �� (3�г�)", "2014.04.17 (��)"));
		mItem.add(new EntryItem("1�б� 1ȸ���", "2014.04.28 (��)"));
		mItem.add(new EntryItem("1�б� 1ȸ���", "2014.04.29 (ȭ)"));
		mItem.add(new EntryItem("1�б� 1ȸ���", "2014.04.30 (��)"));

		mItem.add(new SectionItem("2014�� 5�� ����"));
		mItem.add(new EntryItem("��̳�", "2014.05.05 (��)", true));
		mItem.add(new EntryItem("����ź����", "2014.05.06 (ȭ)", true));
		mItem.add(new EntryItem("����ü����ȸ(1�г�, 2�г�) / �����н�(3�г�)",
				"2014.05.09 (��)"));

		mItem.add(new SectionItem("2014�� 6�� ����"));
		mItem.add(new EntryItem("���� ����", "2014.06.04 (��)", true));
		mItem.add(new EntryItem("���� �����", "2014.06.05 (��)", true));
		mItem.add(new EntryItem("������", "2014.06.06 (��)", true));
		mItem.add(new EntryItem("����ɸ�����(3�г�) / ���������з���(1�г�, 2�г�)",
				"2014.06.12 (��)"));
		mItem.add(new EntryItem("���� ���� �о� ���뵵 �� (2�г�)", "2014.06.24 (ȭ)"));

		mItem.add(new SectionItem("2014�� 7�� ����"));
		mItem.add(new EntryItem("1�б� 2ȸ���", "2014.07.04 (��)"));
		mItem.add(new EntryItem("1�б� 2ȸ���", "2014.07.07 (��)"));
		mItem.add(new EntryItem("1�б� 2ȸ���", "2014.07.08 (ȭ)"));
		mItem.add(new EntryItem("1�б� 2ȸ���", "2014.07.09 (��)"));
		mItem.add(new EntryItem("���� ���� �з� �� (3�г�)", "2014.07.10 (��)"));
		mItem.add(new EntryItem("�������н�", "2014.07.21 (��)", true));

		mItem.add(new SectionItem("2014�� 8�� ����"));
		mItem.add(new EntryItem("���н�", "2014.08.18 (��)"));

		mItem.add(new SectionItem("2014�� 9�� ����"));
		mItem.add(new EntryItem("����ɸ�����(3�г�) / ���������з���(1�г�, 2�г�)",
				"2014.09.03 (��)"));
		mItem.add(new EntryItem("�߼� ����", "2014.09.07 (��)", true));
		mItem.add(new EntryItem("�߼�", "2014.09.08 (��)", true));
		mItem.add(new EntryItem("�߼� ����", "2014.09.09 (ȭ)", true));
		mItem.add(new EntryItem("�߼� ����", "2014.09.10 (��)", true));
		mItem.add(new EntryItem("���� ��� �ɷ� �� (1�г�)", "2014.09.16 (ȭ)"));
		mItem.add(new EntryItem("���� ��� �ɷ� �� (2�г�)", "2014.09.17 (��)"));
		mItem.add(new EntryItem("���� ��� �ɷ� �� (3�г�)", "2014.09.18 (��)"));

		mItem.add(new SectionItem("2014�� 10�� ����"));
		mItem.add(new EntryItem("��õ��", "2014.10.03 (��)", true));
		mItem.add(new EntryItem("���� ���� �з� ��(3�г�)", "2014.10.07 (ȭ)"));
		mItem.add(new EntryItem("�ѱ۳�", "2014.10.09 (��)", true));
		mItem.add(new EntryItem("2�б� 1ȸ���", "2014.10.13 (��)"));
		mItem.add(new EntryItem("2�б� 1ȸ���", "2014.10.14 (ȭ)"));
		mItem.add(new EntryItem("2�б� 1ȸ���", "2014.10.15 (��)"));

		mItem.add(new SectionItem("2014�� 11�� ����"));
		mItem.add(new EntryItem("���� ���� �ɷ� ���� (����)", "2014.11.13 (��)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(3�г�)", "2014.11.17 (��)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(3�г�) / ���� ���� �з� �� (1�г�, 2�г�)",
				"2014.11.18 (ȭ)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(3�г�)", "2014.11.19 (��)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(3�г�)", "2014.11.20 (��)"));

		mItem.add(new SectionItem("2014�� 12�� ����"));
		mItem.add(new EntryItem("��������ȸ", "2014.12.08 (��)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(1�г�, 2�г�)", "2014.12.15 (��)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(1�г�, 2�г�)", "2014.12.16 (ȭ)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(1�г�, 2�г�)", "2014.12.17 (��)"));
		mItem.add(new EntryItem("2�б� 2ȸ���(1�г�, 2�г�)", "2014.12.18 (��)"));
		mItem.add(new EntryItem("�Ѹ��� ������", "2014.12.29 (��)"));
		mItem.add(new EntryItem("�ܿ� ���н�", "2014.12.31 (��)", true));

		mItem.add(new SectionItem("2015�� 1�� ����"));
		mItem.add(new EntryItem("����", "2015.01.01 (��)", true));

		mItem.add(new SectionItem("2015�� 2�� ����"));
		mItem.add(new EntryItem("���н�", "2015.02.09 (��)"));
		mItem.add(new EntryItem("������ / ������", "2015.02.13 (��)", true));
		mItem.add(new EntryItem("�� ����", "2015.02.18 (��)", true));
		mItem.add(new EntryItem("��", "2015.02.19 (��)", true));
		mItem.add(new EntryItem("�� ����", "2015.02.20 (��)", true));

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
			Text = "�����Ͻ� ��¥�� " + diffInt + "���� ��¥�Դϴ�";
		else
			Text = "�����Ͻ� ��¥���� " + diffInt + "�� ���ҽ��ϴ�";

		mHelper.clearCroutonsForActivity();
		mHelper.setText(Text);
		mHelper.setStyle(Style.INFO);
		mHelper.show();
	}

	public interface Item {
		public boolean isSection();
	}
}