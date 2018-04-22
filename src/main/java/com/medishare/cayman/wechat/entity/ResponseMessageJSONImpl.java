package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseMessageJSONImpl implements ResponseMessage, Serializable {
    private static final long serialVersionUID = 6695701615457520924L;

    private Long msgId;
    private String status;
    private Response response;

    ResponseMessageJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseMessageJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("msg_id")) {
            this.msgId = jsonObject.getLong("msg_id");
        }
        if (!jsonObject.isNull("msgid")) {
            this.msgId = jsonObject.getLong("msgid");
        }
        if (!jsonObject.isNull("msg_status")) {
            this.status = jsonObject.getString("msg_status");
        }
    }

    @Override public Long getMessageId() {
        return this.msgId;
    }

    @Override public String getStatus() {
        return this.status;
    }

    @Override public Response getResponse() {
        return this.response;
    }

    @Override public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseMessageJSONImpl that = (ResponseMessageJSONImpl)o;

        if (msgId!=null? !msgId.equals(that.msgId): that.msgId!=null) return false;
        if (status!=null? !status.equals(that.status): that.status!=null) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (msgId!=null? msgId.hashCode(): 0);
        result = 31*result + (status!=null? status.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return this.response.toString();
    }
}
