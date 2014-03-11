package wondang.icehs.kr.whdghks913.wondanghighschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

public class MadeBy extends Activity {

	private CroutonHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_made_by);

		mHelper = new CroutonHelper(this);

		mHelper.setText("이 앱을 만든이 정보입니다");
		mHelper.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.madeby, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int ItemId = item.getItemId();

		if (ItemId == R.id.license) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("라이센스 정보");
			alert.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alert.setMessage(R.string.license);
			alert.show();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();

		mHelper.cencle(true);
	}
}
