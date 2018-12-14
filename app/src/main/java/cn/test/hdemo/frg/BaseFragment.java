package cn.test.hdemo.frg;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.test.hdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public  class BaseFragment extends Fragment {

    protected static final String ARG_STR = "arg_string";

    protected String str;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_base, container, false);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            str = getArguments().getString(ARG_STR);
        }
    }

    public static BaseFragment newInstance(String str){
        BaseFragment fragment;
        switch (str){
            case "NewsFragment":
                fragment=new NewsFragment();
                break;
            case "AVFragment":
               fragment= new AVFragment();
                break;
            case "SARecommendFragment":
                fragment=new SARecommendFragment();
                break;
            case "SAVideoFragment":
                fragment=new SAVideoFragment();
                break;
                default:
                    fragment=new BaseFragment();
        }

        Bundle args = new Bundle();
        args.putString(ARG_STR, str);
        fragment.setArguments(args);
        return fragment;
    }

}
