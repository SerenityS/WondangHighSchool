package wondang.icehs.kr.whdghks913.wondanghighschool.info;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import wondang.icehs.kr.whdghks913.wondanghighschool.R;

public class SchoolInfo extends ActionBarActivity {
    ActionBarDrawerToggle mToggle;
    DrawerLayout mDrawer;
    Toolbar mToolbar;

    ListView mListView;
    InfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.flat_grey));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.app_name, R.string.app_name);
        mDrawer.setDrawerListener(mToggle);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new InfoAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);

        getListItem();
    }

    public void getListItem() {
        mAdapter.addItem("교장 선생님", R.drawable.schoolteacher, "임정순 교장선생님", null, true);
        mAdapter.addItem("교표", R.drawable.ic_launcher_big, null, null, false);
        mAdapter.addItem("교화", R.drawable.schoolflower, "해당화", null, false);
        mAdapter.addItem("교목", R.drawable.schooltree, "구상나무", null, false);
        mAdapter.addItem("약도", R.drawable.schoolmap, "인천 서구 원당동 고산로 40번길 30", null, true);
        mAdapter.addItem("교가", R.drawable.schoolsong, null, null, true);
        mAdapter.addItem("교무실 전화", 0, "교무실 1", "032-569-0723", false);
        mAdapter.addItem("교무실 전화", 0, "교무실 2", "032-569-0728", false);
        mAdapter.addItem("행정실 전화", 0, "행정실", "032-569-0720", false);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String mUri = "tel:";
                switch (position) {
                    case 0:
                    case 4:
                    case 5:
                        Intent mIntent = new Intent(getApplicationContext(), ImageDetailActivity.class);
                        mIntent.putExtra("DetailImage", mAdapter.getItem(position).mImage);
                        startActivity(mIntent);
                        break;
                    case 6:
                    case 7:
                    case 8:
                        mUri += mAdapter.getItem(position).mText2;
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse(mUri)));
                        } catch (Exception e) {

                        }
                        break;
                }
            }
        });

        mAdapter.notifyDataSetChanged();
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
