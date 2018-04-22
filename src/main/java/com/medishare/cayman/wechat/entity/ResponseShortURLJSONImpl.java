package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseShortURLJSONImpl implements ResponseShortURL, Serializable {
    private static final long serialVersionUID = 5007831776991028780L;

    private String shortURL;
    private Response response;

    ResponseShortURLJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseShortURLJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("short_url")) {
            this.shortURL = jsonObject.getString("short_url");
        }
    }

    @Override public String getShortURL() {
        return shortURL;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseShortURLJSONImpl that = (ResponseShortURLJSONImpl)o;

        if (response!=null? !response.equals(that.response): that.response!=null) return false;
        if (shortURL!=null? !shortURL.equals(that.shortURL): that.shortURL!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (shortURL!=null? shortURL.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
