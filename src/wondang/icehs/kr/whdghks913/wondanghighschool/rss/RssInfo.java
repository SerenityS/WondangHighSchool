package wondang.icehs.kr.whdghks913.wondanghighschool.rss;

import java.util.ArrayList;
import java.util.HashMap;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.Webview;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class RssInfo extends Fragment {
	private Context mContext;

	private SimpleAdapter mSimpleAdapter;
	private ArrayList<HashMap<String, String>> mData;
	private ListView mListView;
	private HTMLParser mHTMLParser;

	public RssInfo(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_rss_fullrss, null);

		mData = new ArrayList<HashMap<String, String>>();
		mListView = (ListView) view.findViewById(R.id.mListView);

		mSimpleAdapter = new SimpleAdapter(mContext, mData,
				R.layout.listview_row,
				new String[] { "title", "href", "date" },
				new int[] { R.id.board_subject, R.id.board_writer,
						R.id.board_date, });

		mListView.setAdapter(mSimpleAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View mView,
					int position, long id) {
				String url = ((TextView) mView.findViewById(R.id.board_date))
						.getText().toString();

				Intent webViewIntent = new Intent(mContext, Webview.class);
				webViewIntent.putExtra("url", url);

				startActivity(webViewIntent);
			}
		});

		mHTMLParser = new HTMLParser(getActivity(), mData, mSimpleAdapter);
		mHTMLParser.start();

		return view;
	}
}