package com.medishare.cayman.wechat.api;

import com.medishare.cayman.wechat.entity.ResponseAccessToken;
import com.medishare.cayman.wechat.entity.ResponseCallbackIP;
import com.medishare.cayman.wechat.entity.ResponseTicket;

import java.io.IOException;

public interface JsService {
    // 获取JSAPI_TICKET
    ResponseTicket getJsApiTicket() throws IOException;

}
