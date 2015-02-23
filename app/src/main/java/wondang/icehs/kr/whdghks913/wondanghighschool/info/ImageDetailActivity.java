package wondang.icehs.kr.whdghks913.wondanghighschool.info;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

public class ImageDetailActivity extends ActionBarActivity {
    ActionBarDrawerToggle mToggle;
    DrawerLayout mDrawer;
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
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.app_name, R.string.app_name);
        mDrawer.setDrawerListener(mToggle);

        mImageView = (ViewTouchImage) findViewById(R.id.mImageView);
        mImageView.setImageResource(getIntent().getIntExtra("DetailImage", R.drawable.schoolteacher));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

// Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else {
            finish();
        }
    }
}
