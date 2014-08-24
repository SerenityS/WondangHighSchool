package wondang.icehs.kr.whdghks913.wondanghighschool;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class Call extends Activity {

	private ListView mListView;
	private ListViewAdapter mAdapter;

	private CroutonHelper mHelper;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			ActionBar actionBar = getActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		mListView = (ListView) findViewById(R.id.mContactsList);

		mAdapter = new ListViewAdapter(this);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse("tel:"
									+ mAdapter.mListData.get(position).mText)));
				} catch (Exception e) {

				}
			}
		});

		mAdapter.addItem(null, "교무실 (대표전화)", "032-569-0723");
		mAdapter.addItem(null, "교무실 2", "032-569-0728");
		mAdapter.addItem(null, "행정실", "032-569-0720");

		mHelper = new CroutonHelper(this);
		mHelper.setText("전화 하고자 하는 번호를 터치하세요\n터치한다고 해서 바로 전화가 걸리지는 않습니다");
		mHelper.setStyle(Style.INFO);
		mHelper.show();
	}

	@Override
	protected void onPause() {
		super.onPause();

		mHelper.cencle(true);
	}
}
