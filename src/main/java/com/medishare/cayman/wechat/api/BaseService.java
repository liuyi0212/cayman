package com.medishare.cayman.wechat.api;

import com.medishare.cayman.wechat.entity.ResponseAccessToken;
import com.medishare.cayman.wechat.entity.ResponseCallbackIP;

import java.io.IOException;

public interface BaseService {
    // 获取Access Token
    ResponseAccessToken getAccessToken() throws IOException;

    // 获取微信服务器IP地址
    ResponseCallbackIP getCallbackIP() throws IOException;
}
