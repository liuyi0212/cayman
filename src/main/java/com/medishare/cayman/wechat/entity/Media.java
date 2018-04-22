package com.medishare.cayman.wechat.entity;

public interface Media {
    String getMediaId();

    String getType();

    String getTitle();

    String getDescription();

    String getDownURL();

    Long getCreatedTime();
}
