package com.muzhiyun.service;

import com.muzhiyun.service.entity.Goods;
import com.muzhiyun.service.entity.LoginResp;
import com.muzhiyun.service.entity.Uuid;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {

    /**
     * 初始化
     * @return UUid
     */
    @GET("external/login/init")
    Observable<Uuid> getUuid();

    /**
     *  生成二维码
     * @param uuid 初始化的uuid
     * @return 二维码图片
     */
    @GET("loginQr/${uuid}")
    Observable<String> generateQrcode(@Path("uuid") String uuid);

    /**
     * 验证是否登录成功
     * @param uuid 初始化服务器返回的uuid
     */
    @GET("external/login/checkLogin")
    Observable<LoginResp> checkLogin(String uuid);

    @POST("api/stocking/rfidAdd")
    void rfidAdd();

    @GET("/search")
    Observable<Goods> getSearchGoods(@Query("q") String name,
                                     @Query("tag") String tag, @Query("start") int start,
                                     @Query("count") int count);
}
