package wondang.icehs.kr.whdghks913.wondanghighschool;

import com.tistory.whdghks913.croutonhelper.CroutonHelper;

import android.os.Bundle;
import android.app.Activity;

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

}
