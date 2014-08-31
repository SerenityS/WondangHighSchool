package wondang.icehs.kr.whdghks913.wondanghighschool;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

public class MadeBy extends Activity {

	private CroutonHelper mHelper;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_made_by);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			ActionBar actionBar = getActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		mHelper = new CroutonHelper(this);
		mHelper.setText(R.string.madeby_info);
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
			alert.setTitle("���̼��� ����");
			alert.setPositiveButton("Ȯ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alert.setMessage(R.string.license);
			alert.show();

		} else if (ItemId == android.R.id.home) {
			// NavUtils.navigateUpFromSameTask(this);
			Toast.makeText(this, "������ EasterEgg�� �߰��߽��ϴ�~~ ���ϵ帳�ϴ�~",
					Toast.LENGTH_LONG).show();
			return false;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();

		mHelper.cencle(true);
	}
}
