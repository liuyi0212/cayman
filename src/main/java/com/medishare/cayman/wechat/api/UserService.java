package com.medishare.cayman.wechat.api;

import com.medishare.cayman.wechat.entity.Response;
import com.medishare.cayman.wechat.entity.ResponseOAuth2AccessToken;
import com.medishare.cayman.wechat.entity.ResponseUser;
import com.medishare.cayman.wechat.entity.ResponseUserCollection;

import java.io.IOException;
import java.util.Map;

public interface UserService {
    // 设置备注名
    Response updateRemark(Map<String, Object> remark) throws IOException;

    // 获取用户基本信息（包括UnionID机制）
    ResponseUser getUserInfo(String openId, String lang) throws IOException;

    // 获取用户列表
    ResponseUserCollection getUserList(String nextOpenId) throws IOException;

    // 生成授权链接
    String generateOAuth2URL(String redirectURI, String scope, String state) throws IOException;

    // 通过code换取网页授权access_token
    ResponseOAuth2AccessToken getOAuth2AccessToken(String code) throws IOException;

    // 刷新access_token
    ResponseOAuth2AccessToken refreshOAuth2AccessToken(String refreshToken) throws IOException;

    // 拉取用户信息(需scope为 snsapi_userinfo)
    ResponseUser getOAuth2UserInfo(String accessToken, String openId, String lang) throws IOException;

    // 检验授权凭证（access_token）是否有效
    Response validateOAuth2AccessToken(String accessToken, String openId) throws IOException;
}