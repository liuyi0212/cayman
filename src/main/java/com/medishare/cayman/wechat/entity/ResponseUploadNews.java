package com.medishare.cayman.wechat.entity;

public interface ResponseUploadNews {
    String getType();

    String getMediaId();

    Long getCreateTime();

    Response getResponse();
}
