package com.medishare.cayman.wechat.conf;

import java.util.Map;

public interface HttpClientConfiguration {
    Map<String, String> getRequestHeaders();

    boolean isGZIPEnabled();

    String getHttpProxyHost();

    int getHttpProxyPort();

    String getHttpProxyUser();

    String getHttpProxyPassword();

    int getHttpRetryCount();

    int getHttpReadTimeout();

    int getHttpConnectionTimeout();

    int getHttpRetryIntervalSeconds();
}
