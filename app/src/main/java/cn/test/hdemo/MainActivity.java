package cn.test.hdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.isanwenyu.tabview.TabGroup;
import com.isanwenyu.tabview.TabView;

import cn.test.hdemo.activity.BaseActivity;
import cn.test.hdemo.frg.AVFragment;
import cn.test.hdemo.frg.SARecommendFragment;
import cn.test.hdemo.frg.NewsFragment;
import cn.test.hdemo.frg.UserFragment;


/**
 * 主界面
 *
 */
public class MainActivity extends BaseActivity {
    public static final int TAB_NEWS = 0x00;
    public static final int TAB_AV = 0x01;
    public static final int TAB_IMG = 0x02;
    public static final int TAB_USER = 0x03;
    private static final String TAG = MainActivity.class.getSimpleName();

    ViewPager mViewPager;
    TabGroup mTabGroup;
    TabView mTabNews;

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
        setTitle("新闻");
        mViewPager = findViewById(R.id.vp_main);
        mTabGroup = findViewById(R.id.tg_tab);
        mTabNews = findViewById(R.id.tab_news);

        initViewPager();
        mTabGroup.setOnCheckedChangeListener(new TabGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(TabGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_news:
                        setCurrentFragment(TAB_NEWS);
                        setTitle("神策推荐");
                        Log.d(TAG,"TAB_NEWS");
                        break;
                    case R.id.tb_av:
                        setCurrentFragment(TAB_AV);
                        setTitle("视频");
                        Log.d(TAG,"TAB_AV");
                        break;
                    case R.id.tb_img:
                        setCurrentFragment(TAB_IMG);
                        setTitle("图片");
                        Log.d(TAG,"TAB_IMG");
                        break;
                    case R.id.tb_user:
                        setCurrentFragment(TAB_USER);
                        setTitle("我");
                        Log.d(TAG,"TAB_USER");
                        break;
                }
            }
        });


        mTabNews.setChecked(true);

    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {


        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MainFrgAdapter.Holder(getSupportFragmentManager())
                .add(NewsFragment.newInstance("NewsFragment"))
                .add(AVFragment.newInstance("AVFragment"))
                .add(SARecommendFragment.newInstance("SARecommendFragment"))
                .add(UserFragment.newInstance("UserFragment"))
                .set());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
     *
     *
     */
    public void setCurrentFragment(final int position) {
        Log.i(TAG, "position:" + position);
        //不使用切换动画 避免与自定义动画冲突
        mViewPager.setCurrentItem(position, false);
    }
}
