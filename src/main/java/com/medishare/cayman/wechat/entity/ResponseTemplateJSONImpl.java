package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseTemplateJSONImpl implements ResponseTemplate, Serializable {
    private static final long serialVersionUID = 6037293538354623851L;

    private String templateId;
    private Response response;

    ResponseTemplateJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseTemplateJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("template_id")) {
            this.templateId = jsonObject.getString("template_id");
        }
    }

    @Override public String getTemplateId() {
        return this.templateId;
    }

    @Override public Response getResponse() {
        return this.response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseTemplateJSONImpl that = (ResponseTemplateJSONImpl)o;

        if (response!=null? !response.equals(that.response): that.response!=null) return false;
        if (templateId!=null? !templateId.equals(that.templateId): that.templateId!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (templateId!=null? templateId.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override
    public String toString() {
        return response.toString();
    }
}
