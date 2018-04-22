package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseUserJSONImpl implements ResponseUser, Serializable {
    private static final long serialVersionUID = -4990530253578508668L;

    private User user;
    private Response response;

    ResponseUserJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseUserJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        this.user = new UserJSONImpl(jsonObject);
    }

    @Override public User getUser() {
        return user;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseUserJSONImpl that = (ResponseUserJSONImpl)o;

        if (response!=null? !response.equals(that.response): that.response!=null) return false;
        if (user!=null? !user.equals(that.user): that.user!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (user!=null? user.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
