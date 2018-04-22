package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseMediaJSONImpl implements ResponseMedia, Serializable {
    private static final long serialVersionUID = 3504583056943641954L;

    private Media media;
    private Response response;

    ResponseMediaJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseMediaJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        this.media = new MediaJSONImpl(jsonObject);
    }

    @Override public Media getMedia() {
        return media;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseMediaJSONImpl that = (ResponseMediaJSONImpl)o;

        if (media!=null? !media.equals(that.media): that.media!=null) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (media!=null? media.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
