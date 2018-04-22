package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseUploadNewsJSONImpl implements ResponseUploadNews, Serializable {
    private static final long serialVersionUID = 903118893745807324L;

    private String type;
    private String mediaId;
    private Long createTime;

    private Response response;

    ResponseUploadNewsJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseUploadNewsJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("type")) {
            this.type = jsonObject.getString("type");
        }
        if (!jsonObject.isNull("media_id")) {
            this.mediaId = jsonObject.getString("media_id");
        }
        if (!jsonObject.isNull("created_at")) {
            this.createTime = jsonObject.getLong("created_at");
        }
    }

    @Override public String getType() {
        return this.type;
    }

    @Override public String getMediaId() {
        return this.mediaId;
    }

    @Override public Long getCreateTime() {
        return this.createTime;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseUploadNewsJSONImpl that = (ResponseUploadNewsJSONImpl)o;

        if (createTime!=null? !createTime.equals(that.createTime): that.createTime!=null) return false;
        if (mediaId!=null? !mediaId.equals(that.mediaId): that.mediaId!=null) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;
        if (type!=null? !type.equals(that.type): that.type!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (type!=null? type.hashCode(): 0);
        result = 31*result + (mediaId!=null? mediaId.hashCode(): 0);
        result = 31*result + (createTime!=null? createTime.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
