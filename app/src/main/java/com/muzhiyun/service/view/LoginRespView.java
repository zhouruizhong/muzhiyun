package com.muzhiyun.service.view;

import com.muzhiyun.service.entity.LoginResp;

public interface LoginRespView extends View{

    /**
     *
     * @param mLoginResp
     */
    void onSuccess(LoginResp mLoginResp);

    /**
     *
     * @param result
     */
    void onError(String result);

}
