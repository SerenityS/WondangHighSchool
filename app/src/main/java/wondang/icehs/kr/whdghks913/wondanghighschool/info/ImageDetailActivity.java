package wondang.icehs.kr.whdghks913.wondanghighschool.info;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

public class ImageDetailActivity extends ActionBarActivity {
    Toolbar mToolbar;

    ViewTouchImage mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.flat_grey));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        mImageView = (ViewTouchImage) findViewById(R.id.mImageView);
        mImageView.setImageResource(getIntent().getIntExtra("DetailImage", R.drawable.schoolteacher));
    }
}
