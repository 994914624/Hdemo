package cn.test.hdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.isanwenyu.tabview.TabGroup;
import com.isanwenyu.tabview.TabView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;

import org.json.JSONException;
import org.json.JSONObject;

import cn.test.hdemo.activity.BaseActivity;
import cn.test.hdemo.frg.UserFragment;
import cn.test.hdemo.frg.SARecommendFragment;
import cn.test.hdemo.frg.NewsFragment;
import cn.test.hdemo.frg.SAVideoFragment;


/**
 * 主界面
 *
 */
@SensorsDataIgnoreTrackAppViewScreen
public class MainActivity extends BaseActivity {
    public static final int TAB_NEWS = 0x00;
    public static final int TAB_AV = 0x01;
    public static final int TAB_IMG = 0x02;
    public static final int TAB_USER = 0x03;
    private static final String TAG = MainActivity.class.getSimpleName();

    ViewPager mViewPager;
    TabGroup mTabGroup;
    TabView tab0;
    TabView tab1;
    TabView tab2;
    TabView tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        setBackBtnDismiss();
        setTitle("");
        mViewPager = findViewById(R.id.vp_main);
        mTabGroup = findViewById(R.id.tg_tab);
        tab0 = findViewById(R.id.tab_news);
        tab1 = findViewById(R.id.tb_av);
        tab2 = findViewById(R.id.tb_img);
        tab3 = findViewById(R.id.tb_user);

        //忽略 mViewPager
        SensorsDataAPI.sharedInstance().ignoreView(mViewPager);
        initViewPager();
        mTabGroup.setOnCheckedChangeListener(new TabGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(TabGroup group, int checkedId) {
                 String tabName ="";
                switch (checkedId) {

                    case R.id.tab_news:
                        setCurrentFragment(TAB_NEWS);

                        tabName=tab0.getTextString();

                        break;
                    case R.id.tb_av:
                        setCurrentFragment(TAB_AV);
                        tabName=tab1.getTextString();
                        Log.d(TAG,"TAB_AV");
                        break;
                    case R.id.tb_img:
                        setCurrentFragment(TAB_IMG);
                        tabName=tab2.getTextString();
                        Log.d(TAG,"TAB_IMG");
                        break;
                    case R.id.tb_user:
                        setCurrentFragment(TAB_USER);
                        tabName=tab3.getTextString();
                        Log.d(TAG,"TAB_USER");
                        break;

                }


                setTitle(tabName);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tab_name",tabName);
                    jsonObject.put("$title",tabName);
                    SensorsDataAPI.sharedInstance().track("TabClick",jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        tab0.setChecked(true);

    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(4);

        SARecommendFragment saRecommendFragment = (SARecommendFragment) SARecommendFragment.newInstance("SARecommendFragment");
        UserFragment userFragment = (UserFragment) UserFragment.newInstance("UserFragment");
        saRecommendFragment.setIDataSuccess(userFragment);
        mViewPager.setAdapter(new MainFrgAdapter.Holder(getSupportFragmentManager())
                .add(saRecommendFragment)
                .add(SAVideoFragment.newInstance("SAVideoFragment"))
                .add(NewsFragment.newInstance("NewsFragment"))
                .add(userFragment)
                .set());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((TabView) mTabGroup.getChildAt(position)).setChecked(true);
                Log.d(TAG,"onPageSelected:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 改变fragment状态
     */
    public void setCurrentFragment(final int position) {
        Log.i(TAG, "position:" + position);
        mViewPager.setCurrentItem(position, false);

    }
}
