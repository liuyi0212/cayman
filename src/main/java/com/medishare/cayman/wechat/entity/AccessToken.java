package com.medishare.cayman.wechat.entity;

public interface AccessToken {
    String getCredential();

    Long getExpiresIn();
}