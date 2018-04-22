package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class MediaJSONImpl implements Media, Serializable {
    private static final long serialVersionUID = 444900494832942094L;

    private String mediaId;
    private String type;
    private String title;
    private String description;
    private String downURL;
    private Long createTime;

    JSONObject object;

    MediaJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    MediaJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.object = jsonObject;
        if (!jsonObject.isNull("media_id")) {
            this.mediaId = jsonObject.getString("media_id");
        }
        if (!jsonObject.isNull("type")) {
            this.type = jsonObject.getString("type");
        }
        if (!jsonObject.isNull("title")) {
            this.title = jsonObject.getString("title");
        }
        if (!jsonObject.isNull("description")) {
            this.description = jsonObject.getString("description");
        }
        if (!jsonObject.isNull("down_url")) {
            this.downURL = jsonObject.getString("down_url");
        }
        if (!jsonObject.isNull("created_at")) {
            this.createTime = jsonObject.getLong("created_at");
        }
    }

    @Override public String getMediaId() {
        return mediaId;
    }

    @Override public String getType() {
        return type;
    }

    @Override public String getDownURL() {
        return downURL;
    }

    @Override public String getDescription() {
        return description;
    }

    @Override public String getTitle() {
        return title;
    }

    @Override public Long getCreatedTime() {
        return createTime;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        MediaJSONImpl that = (MediaJSONImpl)o;

        if (mediaId!=null? !mediaId.equals(that.mediaId): that.mediaId!=null) return false;
        if (type!=null? !type.equals(that.type): that.type!=null) return false;
        if (title!=null? !title.equals(that.title): that.title!=null) return false;
        if (description!=null? !description.equals(that.description): that.description!=null) return false;
        if (downURL!=null? !downURL.equals(that.downURL): that.downURL!=null) return false;
        if (createTime!=null? !createTime.equals(that.createTime): that.createTime!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (mediaId!=null? mediaId.hashCode(): 0);
        result = 31*result + (type!=null? type.hashCode(): 0);
        result = 31*result + (title!=null? title.hashCode(): 0);
        result = 31*result + (description!=null? description.hashCode(): 0);
        result = 31*result + (downURL!=null? downURL.hashCode(): 0);
        result = 31*result + (createTime!=null? createTime.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return object.toString();
    }
}