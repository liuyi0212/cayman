package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseGroupJSONImpl implements ResponseGroup, Serializable {
    private static final long serialVersionUID = 8601210025149064671L;

    private Group group;
    private Response response;

    ResponseGroupJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseGroupJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        this.group = new GroupJSONImpl(jsonObject);
    }

    @Override public Group getGroup() {
        return group;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseGroupJSONImpl that = (ResponseGroupJSONImpl)o;

        if (group!=null? !group.equals(that.group): that.group!=null) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (group!=null? group.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
