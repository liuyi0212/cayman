package com.medishare.cayman.wechat.api;

import com.medishare.cayman.wechat.entity.ResponseShortURL;

import java.io.IOException;
import java.util.Map;

public interface ShortURLService {
    // 将一条长链接转成短链接
    ResponseShortURL createShortURL(Map<String, Object> url) throws IOException;
}
