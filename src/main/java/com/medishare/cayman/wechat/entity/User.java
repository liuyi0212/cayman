package com.medishare.cayman.wechat.entity;

public interface User {
    Integer getSubscribe();

    String getOpenId() ;

    String getNickname();

    Integer getSex();

    String getLanguage();

    String getCity();

    String getProvince();

    String getCountry();

    String getHeadImgURL();

    Long getSubscribeTime();

    String[] getPrivilege();

    String getUnionId();
}
