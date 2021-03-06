package cn.test.hdemo.frg;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONObject;

import cn.test.hdemo.R;

import cn.test.hdemo.custom.CustomObserver;
import cn.test.hdemo.custom.IDataSuccess;
import cn.test.hdemo.utils.HttpUtil;
import cn.test.hdemo.utils.RxUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment implements IDataSuccess {

    protected static String TAG = "UserFragment";
    private View view;
    private RecyclerView mRecyclerView;

    private int start = 0;
    private int count = 10;
    private Context context;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        context = view.getContext().getApplicationContext();
        initView();
        return view;
    }

    TextView tvUserId;
    TextView btnUserLogout;

    private void initView() {
//        mRecyclerView = view.findViewById(R.id.user_recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        tvUserId = view.findViewById(R.id.tv_user_id);

        btnUserLogout = view.findViewById(R.id.btn_user_logout);
        btnUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出
                logoutUser();
            }
        });

    }


    /**
     * 重置用户
     */
    private void logoutUser() {
        RxUtil.logoutUser(new CustomObserver(new IDataSuccess() {
            @Override
            public void onDataSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        Toast.makeText(getContext(), "账号已退出", Toast.LENGTH_SHORT).show();
                        tvUserId.setText("");
                        btnUserLogout.setText("账号已退出");
                        btnUserLogout.setBackgroundColor(Color.GRAY);
                        btnUserLogout.setEnabled(false);
                        //置空内存中的数据
                        SARecommendFragment.getSARecommendAdapter().setNewData(null);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "账号退出失败！！！", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    /*
     * 设置为可点击
     */
    private void setUserBtn() {
        if(!TextUtils.isEmpty(tvUserId.getText()))return;
        tvUserId.setText(String.format("当前用户：%s", SensorsDataAPI.sharedInstance().getAnonymousId()));
        btnUserLogout.setText("退出");
        btnUserLogout.setBackgroundColor(Color.RED);
        btnUserLogout.setEnabled(true);
    }

    @Override
    public void onDataSuccess(String response) {
        setUserBtn();
    }
}
