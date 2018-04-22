package com.medishare.cayman.wechat.entity;

public interface ResponseMessage {
    Long getMessageId();

    String getStatus();

    Response getResponse();
}
