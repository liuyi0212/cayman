package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseOAuth2AccessTokenJSONImpl implements ResponseOAuth2AccessToken, Serializable {
    private static final long serialVersionUID = 3338613755935707398L;

    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private String openId;
    private String scope;
    private String unionId;

    private Response response;

    ResponseOAuth2AccessTokenJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseOAuth2AccessTokenJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("access_token")) {
            this.accessToken = jsonObject.getString("access_token");
        }
        if (!jsonObject.isNull("expires_in")) {
            this.expiresIn = jsonObject.getInt("expires_in");
        }
        if (!jsonObject.isNull("refresh_token")) {
            this.refreshToken = jsonObject.getString("refresh_token");
        }
        if (!jsonObject.isNull("openid")) {
            this.openId = jsonObject.getString("openid");
        }
        if (!jsonObject.isNull("scope")) {
            this.scope = jsonObject.getString("scope");
        }
        if (!jsonObject.isNull("unionid")) {
            this.unionId = jsonObject.getString("unionid");
        }
    }

    @Override public String getAccessToken() {
        return accessToken;
    }

    @Override public Integer getExpiresIn() {
        return expiresIn;
    }

    @Override public String getRefreshToken() {
        return refreshToken;
    }

    @Override public String getOpenId() {
        return openId;
    }

    @Override public String getScope() {
        return scope;
    }

    @Override public String getUnionId() {
        return unionId;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseOAuth2AccessTokenJSONImpl that = (ResponseOAuth2AccessTokenJSONImpl)o;

        if (accessToken!=null? !accessToken.equals(that.accessToken): that.accessToken!=null) return false;
        if (expiresIn!=null? !expiresIn.equals(that.expiresIn): that.expiresIn!=null) return false;
        if (openId!=null? !openId.equals(that.openId): that.openId!=null) return false;
        if (refreshToken!=null? !refreshToken.equals(that.refreshToken): that.refreshToken!=null) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;
        if (scope!=null? !scope.equals(that.scope): that.scope!=null) return false;
        if (unionId!=null? !unionId.equals(that.unionId): that.unionId!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (accessToken!=null? accessToken.hashCode(): 0);
        result = 31*result + (expiresIn!=null? expiresIn.hashCode(): 0);
        result = 31*result + (refreshToken!=null ? refreshToken.hashCode(): 0);
        result = 31*result + (openId!=null ? openId.hashCode(): 0);
        result = 31*result + (scope!=null ? scope.hashCode(): 0);
        result = 31*result + (unionId!=null ? unionId.hashCode(): 0);
        result = 31*result + (response!=null ? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
