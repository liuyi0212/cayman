package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseJSONImpl implements Response, Serializable {
    private static final long serialVersionUID = 6843993134093306504L;

    private Integer errCode;
    private String errMsg;

    protected JSONObject object;

    ResponseJSONImpl(Integer errCode, String errMsg) {
        init(errCode, errMsg);
    }

    void init(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.object = new JSONObject(
                "{\"errcode\": " + errCode + ", \"errmsg\": \"" + errMsg + "\" }"
        );
    }

    ResponseJSONImpl(HttpResponse response) {
        this(response, 0, "ok");
    }


    ResponseJSONImpl(HttpResponse response, Integer defaultCode, String defaultMsg) {
        init(response.asJSONObject(), defaultCode, defaultMsg);
    }

    ResponseJSONImpl (JSONObject jsonObject) {
        this(jsonObject, 0, "ok");
    }

    ResponseJSONImpl (JSONObject jsonObject, Integer defaultCode, String defaultMsg) {
        init(jsonObject, defaultCode, defaultMsg);
    }

    void init(JSONObject jsonObject, Integer defaultCode, String defaultMsg) {
        this.object = jsonObject;
        if (!jsonObject.isNull("errcode")) {
            this.errCode = jsonObject.getInt("errcode");
        } else {
            this.errCode = defaultCode;
            this.object.put("errcode", defaultCode);
        }
        if (!jsonObject.isNull("errmsg")) {
            this.errMsg = jsonObject.getString("errmsg");
        } else {
            this.errMsg = defaultMsg;
            this.object.put("errmsg", defaultMsg);
        }
    }

    @Override public Integer getErrCode() {
        return errCode;
    }

    @Override public String getErrMsg() {
        return errMsg;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseJSONImpl that = (ResponseJSONImpl)o;

        if (errCode!=null? !errCode.equals(that.errCode): that.errCode!=null) return false;
        if (errMsg!=null? !errMsg.equals(that.errMsg): that.errMsg!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (errCode!=null? errCode.hashCode(): 0);
        result = 31*result + (errMsg!=null? errMsg.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return object.toString();
    }
}
