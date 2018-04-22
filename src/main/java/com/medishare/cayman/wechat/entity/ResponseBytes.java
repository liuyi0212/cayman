package com.medishare.cayman.wechat.entity;

public interface ResponseBytes {
    Integer getErrCode();
    
    String getSuffix();
    
    byte [] getBytes();
    
    String getErrMsg();
}
