package com.medishare.cayman.wechat.entity;

public interface Button {
    String getType();

    String getName();

    String getUrl();

    String getKey();

    Button[] getButtons();
}
