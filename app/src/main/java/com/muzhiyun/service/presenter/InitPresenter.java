package com.muzhiyun.service.presenter;

import android.content.Context;
import android.content.Intent;
import com.muzhiyun.service.entity.Uuid;
import com.muzhiyun.service.manager.DataManager;
import com.muzhiyun.service.view.UuidView;
import com.muzhiyun.service.view.View;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author zhouruizhong
 */
public class InitPresenter implements Presenter{

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private UuidView mUuidView;
    private Uuid mUuid;

    public InitPresenter(Context mContext) {
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
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mUuidView = (UuidView)view;
    }

    @Override
    public void attachIncomingIntent(Intent intetn) {
    }

    public void getUuid(){
        mCompositeSubscription.add(manager.getUuid()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uuid>() {
                    @Override
                    public void onCompleted() {
                        if (mUuid != null){
                            mUuidView.onSuccess(mUuid);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mUuidView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(Uuid uuid) {
                        mUuid = uuid;
                    }
                })
        );
    }

    public void loadQRcode(){

    }

    public void checkLogin(String uuid){
        System.out.println("---------------------------验证登录的uuid=" + uuid + "-----------------------");
    }
}
