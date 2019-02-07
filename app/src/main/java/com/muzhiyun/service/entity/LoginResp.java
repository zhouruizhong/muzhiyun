package com.muzhiyun.service.entity;

public class LoginResp {
    /**
     * 200表示成功
     */
    private String code;
    /**
     * 返回提示信息
     */
    private String message;
    /**
     * 加密的Key
     */
    private String keyName;
    /**
     * 加密的secret
     */
    private String secretName;
    /**
     * 鉴权token
     */
    private String token;
    /**
     * 登陆uuid
     */
    private String loginUuid;

}
