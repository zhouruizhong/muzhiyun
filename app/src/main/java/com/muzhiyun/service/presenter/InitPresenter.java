package com.muzhiyun.service.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.muzhiyun.service.entity.LoginResp;
import com.muzhiyun.service.entity.Uuid;
import com.muzhiyun.service.manager.DataManager;
import com.muzhiyun.service.view.UuidView;
import com.muzhiyun.service.view.View;
import com.muzhiyun.ui.IndexActivity;
import com.muzhiyun.ui.MainActivity;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * @author zhouruizhong
 */
public class InitPresenter  implements Presenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private UuidView mUuidView;
    private Uuid mUuid;

    private static final String TAG = "Rxjava";

    /**
     * 设置变量 = 模拟轮询服务器次数
     */
    private int i = 0 ;

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
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mUuidView = (UuidView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {
    }

    public void getUuid() {
        mCompositeSubscription.add(manager.getUuid()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uuid>() {
                    @Override
                    public void onCompleted() {
                        if (mUuid != null) {
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

    public void loadQRcode() {
        mCompositeSubscription.add(manager.getQrcode(mUuid.getUuid()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                })
        );
    }

    public void checkLogin(String uuid) {
        System.out.println("---------------------------验证登录的uuid=" + uuid + "-----------------------");
        mCompositeSubscription.add(manager.checkLogin(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResp>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "对Complete事件作出响应");
                        Toast.makeText(mContext,"大傻逼",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        Log.d(TAG,  e.toString());
                    }

                    @Override
                    public void onNext(LoginResp loginResp) {

                    }
                })
        );
    }
}
