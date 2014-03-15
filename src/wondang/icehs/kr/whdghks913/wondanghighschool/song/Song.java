package wondang.icehs.kr.whdghks913.wondanghighschool.song;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ScrollView;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import de.keyboardsurfer.android.widget.crouton.Style;

public class Song extends Activity {
	private ImageDisplayView mImage;
	private ScrollView ScrollImage;

	/**
	 * true : ScrollImage, false : mImage
	 */
	private boolean isVisibility = true;

	private CroutonHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song);

		ScrollImage = (ScrollView) findViewById(R.id.ScrollImage);
		mImage = (ImageDisplayView) findViewById(R.id.imageView);
		mImage.setImageData(BitmapFactory.decodeResource(getResources(),
				R.drawable.schoolsong));

		mHelper = new CroutonHelper(this);
		mHelper.setText("�̹��� Ȯ��/��� ����� ����Ϸ���, ��� ���� ��ư�� �����ּ���");
		mHelper.setStyle(Style.INFO);
		mHelper.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.song, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		int ItemId = item.getItemId();

		if (ItemId == R.id.change) {
			if (isVisibility) {
				ScrollImage.setVisibility(View.GONE);
				mImage.setVisibility(View.VISIBLE);

				isVisibility = !isVisibility;

				mHelper.clearCroutonsForActivity();
				mHelper.setText("�հ����� ������, �ٿ��� ũ�⸦ �����Ҽ� �ֽ��ϴ�");
				mHelper.setStyle(Style.INFO);
				mHelper.show();
			} else {
				ScrollImage.setVisibility(View.VISIBLE);
				mImage.setVisibility(View.GONE);

				isVisibility = !isVisibility;

				mHelper.clearCroutonsForActivity();
				mHelper.setText("���� �¿�� ��ũ���ؼ� �Ǻ��� �� �� �ֽ��ϴ�");
				mHelper.setStyle(Style.INFO);
				mHelper.show();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();

		mHelper.cencle(true);
	}
}
