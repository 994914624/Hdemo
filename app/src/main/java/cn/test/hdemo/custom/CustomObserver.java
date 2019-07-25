package cn.test.hdemo.custom;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yzk on 2019/7/25
 */

public  class CustomObserver implements Observer<String> {

    private static final String TAG = "CustomObserver";
    private IDataSuccess dataSuccess;

    public CustomObserver(IDataSuccess dataSuccess){
        this.dataSuccess = dataSuccess;
    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String response) {
        dataSuccess.onDataSuccess(response);
        Log.i(TAG, "onDataSuccess = " + response);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
