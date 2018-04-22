package com.medishare.cayman.wechat.entity;

public interface ResponseUserCollection {
    Integer getTotal();

    Integer getCount();

    String[] getOpenIds();

    String getNextOpenId();

    Response getResponse();
}
