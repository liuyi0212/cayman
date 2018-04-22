package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseAccessTokenJSONImpl implements ResponseAccessToken, Serializable {
    private static final long serialVersionUID = 2489527912929698492L;

    private AccessToken accessToken;
    private Response response;

    ResponseAccessTokenJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseAccessTokenJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        this.accessToken = new AccessTokenJSONImpl(jsonObject);
    }

    @Override public AccessToken getAccessToken() {
        return accessToken;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseAccessTokenJSONImpl that = (ResponseAccessTokenJSONImpl)o;

        if (accessToken!=null? !accessToken.equals(that.accessToken): that.accessToken!=null) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (accessToken!=null? accessToken.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
