package com.muzhiyun.service.manager;

import android.content.Context;
import com.muzhiyun.service.RetrofitHelper;
import com.muzhiyun.service.RetrofitService;
import com.muzhiyun.service.entity.Goods;
import com.muzhiyun.service.entity.Uuid;
import retrofit2.Call;
import rx.Observable;

/**
 * @author zhouruizhong
 */
public class DataManager {

    private RetrofitService mRetrofitService;

    public void init(){

    }

    public DataManager(Context context) {
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<Goods> getSearchGoods(String name, String tag, int start, int count) {
        return mRetrofitService.getSearchGoods(name, tag, start, count);
    }

    public Observable<Uuid> getUuid(){
        return mRetrofitService.getUuid();
    }

    public Observable<String> getQrcode(String uuid){
        return mRetrofitService.generateQrcode(uuid);
    }
}
