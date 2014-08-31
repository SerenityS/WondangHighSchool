package wondang.icehs.kr.whdghks913.wondanghighschool;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.VideoView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class WondangInfo extends SherlockActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wondang_info);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			ActionBar actionBar = getActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.wondang_info, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int ItemId = item.getItemId();

		if (ItemId == R.id.goWeb) {
			Intent mIntent = new Intent(this, Webview.class);
			mIntent.putExtra("url",
					"http://wondang.icehs.kr/sub/info.do?m=0101&s=wondang");
			startActivity(mIntent);
		} else if (ItemId == R.id.infoVideo) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.videoview, null);

			final VideoView mVideo = (VideoView) view.findViewById(R.id.mVideo);
			// MediaController mController = new MediaController(this);
			// mController.setAnchorView(mVideo);
			// mVideo.setMediaController(mController);
			mVideo.setVideoURI(Uri
					.parse("http://wondang.icehs.kr/UserFiles/wondang/wondang.wmv"));
			mVideo.start();

			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(R.string.wondang_movies);
			alert.setPositiveButton(R.string.EXIT,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alert.setView(view);
			alert.show();
		}

		return super.onOptionsItemSelected(item);
	}

}
