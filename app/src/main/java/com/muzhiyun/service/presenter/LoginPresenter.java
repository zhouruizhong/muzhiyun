package com.muzhiyun.service.presenter;

import android.content.Context;
import android.content.Intent;
import com.muzhiyun.service.entity.LoginResp;
import com.muzhiyun.service.entity.Uuid;
import com.muzhiyun.service.manager.DataManager;
import com.muzhiyun.service.view.LoginRespView;
import com.muzhiyun.service.view.View;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import java.util.concurrent.TimeUnit;

/**
 * @author zhouruizhong
 */
public class LoginPresenter implements Presenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private LoginResp mLoginResp;
    private LoginRespView mLoginRespView;

    private static final String TAG = "Rxjava";

    public LoginPresenter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mLoginRespView = (LoginRespView)view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void checkLogin(String uuid) {
        mCompositeSubscription.add(manager.checkLogin(uuid)
                .repeat(100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResp>() {
                    @Override
                    public void onCompleted() {
                        if (mLoginResp != null) {
                            mLoginRespView.onSuccess(mLoginResp);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mLoginRespView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(LoginResp loginResp) {
                        mLoginResp = loginResp;
                    }
                })
        );
    }
}
