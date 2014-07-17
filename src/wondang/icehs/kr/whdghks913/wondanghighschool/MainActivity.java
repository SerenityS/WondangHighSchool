package wondang.icehs.kr.whdghks913.wondanghighschool;

import wondang.icehs.kr.whdghks913.wondanghighschool.bap.Bap;
import wondang.icehs.kr.whdghks913.wondanghighschool.schedule.Schedule;
import wondang.icehs.kr.whdghks913.wondanghighschool.song.Song;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends SherlockActivity {

	private ListView mListView;
	private ListViewAdapter mAdapter;

	private CroutonHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.mList);

		mAdapter = new ListViewAdapter(this);
		mListView.setAdapter(mAdapter);

		mAdapter.addItem(getResources().getDrawable(R.drawable.ic_launcher),
				"����� �Ұ�", "��õ �������б��� �Ұ��մϴ�");
		mAdapter.addItem(getResources().getDrawable(R.drawable.song), "����",
				"�츮 �б� ���� Ȯ���ϱ�");
		mAdapter.addItem(getResources().getDrawable(R.drawable.calender), "����",
				"�б��� ������ Ȯ�� �� �� �ֽ��ϴ�");
		mAdapter.addItem(getResources().getDrawable(R.drawable.call), "����ó",
				"�б��� ��ȭ�ϱ�");
		mAdapter.addItem(getResources().getDrawable(R.drawable.bap), "�޽�",
				"���� ������ �޽���?");
		mAdapter.addItem(null, "������ ����", "�Ǹ��� �������� ������ Ȯ���մϴ�");

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// ListData mData = mAdapter.mListData.get(position);

				if (position == 0) {
					// �Ұ�
					startActivity(new Intent(MainActivity.this,
							WondangInfo.class));
				} else if (position == 1) {
					// ����
					startActivity(new Intent(MainActivity.this, Song.class));
				} else if (position == 2) {
					// ���� Ȯ���ϱ�
					startActivity(new Intent(MainActivity.this, Schedule.class));
				} else if (position == 3) {
					// ��ȭ �ϱ�
					startActivity(new Intent(MainActivity.this, Call.class));
				} else if (position == 4) {
					// �޽�
					startActivity(new Intent(MainActivity.this, Bap.class));
				}
			}
		});

		mHelper = new CroutonHelper(this);
		mHelper.setText("ȯ���մϴ�~!");
		mHelper.setStyle(Style.INFO);
		mHelper.show();
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int ItemId = item.getItemId();

		if (ItemId == R.id.info) {
			startActivity(new Intent(this, MadeBy.class));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();

		mHelper.cencle(true);
	}
}
