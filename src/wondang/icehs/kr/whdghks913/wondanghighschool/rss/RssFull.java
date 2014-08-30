package wondang.icehs.kr.whdghks913.wondanghighschool.rss;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import wondang.icehs.kr.whdghks913.wondanghighschool.Webview;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

@SuppressLint("ValidFragment")
public class RssFull extends Fragment {
	private Context mContext;
	private ArrayList<HashMap<String, String>> mArrayList = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> mHash;
	private SimpleAdapter mAdapter;
	private ListView mListView;
	private ProgressDialog mDialog;

	private final String RSS_ADRESS = "http://wondanghs.tistory.com/rss";

	public RssFull(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listview, null);

		mListView = (ListView) view.findViewById(R.id.mListView);
		mAdapter = new SimpleAdapter(mContext, mArrayList,
				R.layout.rss_listview_row, new String[] { "subject", "date",
						"category" }, new int[] { R.id.board_subject,
						R.id.board_date, R.id.board_writer });
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View mView,
					int position, long id) {
				String link = mArrayList.get(position).get("link");

				Intent webViewIntent = new Intent(mContext, Webview.class);
				webViewIntent.putExtra("url", link);

				startActivity(webViewIntent);
			}
		});

		mListView.setDivider(null);
		mListView.setDividerHeight(20);
		mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		if (isNetwork()) {
			ProcessTask mProcessTask = new ProcessTask();
			mProcessTask.execute();

		} else {
			CroutonHelper mHelper = new CroutonHelper(getActivity());
			mHelper.setText("���ͳ��� ����Ǿ� ���� �ʽ��ϴ�");
			mHelper.setStyle(Style.ALERT);
			mHelper.show();
		}

		return view;
	}

	private boolean isNetwork() {
		ConnectivityManager manager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (wifi.isConnected() || mobile.isConnected())
			return true;
		return false;
	}

	private void rssParse(String url) throws Exception {
		mArrayList.clear();

		try {
			URL mUrl = new URL(url);

			XmlPullParserFactory parserCreator = XmlPullParserFactory
					.newInstance();
			XmlPullParser mParser = parserCreator.newPullParser();

			mParser.setInput(mUrl.openStream(), null);

			int parserEvent = mParser.getEventType();
			String tag = "";

			boolean inTitle = false; // ���񿩺��Ǵ�
			boolean inItem = false; // �����ۺ��� �Ǵ�
			// boolean inWriter = false; // �ۼ��� �Ǵ�
			boolean inDate = false; // �ۼ���
			boolean inLink = false; // ��ũ
			boolean inCategory = false; // ī�װ�
			// boolean inContent = false; // ����

			// XML ��¥ ���� ��ȯ�ϱ�
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy�� MM�� dd�� HH�� mm�� ss��");
			Date pubdate = null;

			do {
				// Log.i("test", "" + count);
				switch (parserEvent) {

				case XmlPullParser.START_TAG:
					tag = mParser.getName();
					// ���� item���� �����ϴ� �±׶��
					if (tag.compareTo("item") == 0) {
						inItem = true;
						mHash = new HashMap<String, String>();
					}
					// ���� title �� �����ϴ� �±׶��
					if (tag.compareTo("title") == 0) {
						inTitle = true;
					}
					// ���� link �� �����ϴ� �±׶��
					if (tag.compareTo("link") == 0) {
						inLink = true;
					}
					// ���� category �� �����ϴ� �±׶��
					if (tag.compareTo("category") == 0) {
						inCategory = true;
					}
					// // ���� dc:creator �̰ų� author �±׶��
					// if (tag.compareTo("dc:creator") == 0
					// || tag.compareTo("author") == 0) {
					// inWriter = true;
					// }
					// ���� pubDate �±׶��
					if (tag.compareTo("pubDate") == 0) {
						inDate = true;
					}
					// // ���� description �±׶��
					// if (tag.compareTo("description") == 0) {
					// inContent = true;
					// }
					break;

				case XmlPullParser.TEXT:
					tag = mParser.getName();
					// ����
					if (inItem && inTitle) {
						mHash.put("subject", mParser.getText());
					}
					// �ۼ���
					if (inItem && inDate) {
						pubdate = new Date(Date.parse(mParser.getText()));
						mHash.put("date", sdf.format(pubdate));
					}
					// // �ۼ���
					// if (inItem && inWriter) {
					// mHash.put("writer", mParser.getText());
					// }
					// ��ũ
					if (inItem && inLink) {
						mHash.put("link", mParser.getText());
					}
					if (inItem && inCategory) {
						mHash.put("category", mParser.getText());
					}
					// // ����
					// if (inItem && inContent) {
					// // map.put("content", mParser.getText());
					// contents.add(mParser.getText());
					// }
					break;

				case XmlPullParser.END_TAG:
					tag = mParser.getName();
					// �±װ� ������
					if (tag.compareTo("item") == 0) {
						inItem = false;
						mArrayList.add(mHash);
					}
					if (tag.compareTo("title") == 0) {
						inTitle = false;
					}
					// if (tag.compareTo("dc:creator") == 0
					// || tag.compareTo("author") == 0) {
					// inWriter = false;
					// }
					if (tag.compareTo("pubDate") == 0) {
						inDate = false;
					}
					if (tag.compareTo("link") == 0) {
						inLink = false;
					}
					if (tag.compareTo("category") == 0) {
						inCategory = false;
					}
					// if (tag.compareTo("description") == 0) {
					// inContent = false;
					// }
					break;
				}

				parserEvent = mParser.next();

			} while (parserEvent != XmlPullParser.END_DOCUMENT);

		} catch (Exception e) {
			throw e;
		}
	}

	public class ProcessTask extends AsyncTask<String, Integer, Long> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (mDialog == null)
				mDialog = ProgressDialog.show(getActivity(), "�ε���",
						"��ø� ��ٷ� �ּ���");
		}

		@Override
		protected Long doInBackground(String... params) {
			try {
				rssParse(RSS_ADRESS);
			} catch (Exception e) {
				return -1l;
			}
			return 0l;
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);

			if (mDialog != null)
				mDialog.dismiss();

			if (result == -1l)
				return;

			mAdapter.notifyDataSetChanged();
		}
	}

}