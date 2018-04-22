package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseTicketJSONImpl implements ResponseTicket, Serializable {
    private static final long serialVersionUID = -5012141780169014047L;

    private String ticket;
    private Integer expireSeconds;
    private String url;

    private Response response;

    ResponseTicketJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseTicketJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("ticket")) {
            this.ticket = jsonObject.getString("ticket");
        }
        if (!jsonObject.isNull("expire_seconds")) {
            this.expireSeconds = jsonObject.getInt("expire_seconds");
        }
        if (!jsonObject.isNull("url")) {
            this.url = jsonObject.getString("url");
        }
    }

    @Override public String getTicket() {
        return this.ticket;
    }

    @Override public Integer getExpireSeconds() {
        return this.expireSeconds;
    }

    @Override public String getURL() {
        return this.url;
    }

    @Override public Response getResponse() {
        return this.response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseTicketJSONImpl that = (ResponseTicketJSONImpl)o;

        if (expireSeconds!=null? !expireSeconds.equals(that.expireSeconds): that.expireSeconds!=null) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;
        if (ticket!=null? !ticket.equals(that.ticket): that.ticket!=null) return false;
        if (url!=null? !url.equals(that.url): that.url!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (ticket!=null? ticket.hashCode(): 0);
        result = 31*result + (expireSeconds!=null? expireSeconds.hashCode(): 0);
        result = 31*result + (url!=null? url.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
