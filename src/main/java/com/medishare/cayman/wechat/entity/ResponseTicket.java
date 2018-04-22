package com.medishare.cayman.wechat.entity;

public interface ResponseTicket {
    String getTicket();

    Integer getExpireSeconds();

    String getURL();

    Response getResponse();
}
