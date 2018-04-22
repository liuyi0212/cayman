package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONArray;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

final class ResponseCallbackIPJSONImpl implements ResponseCallbackIP, Serializable {
    private String[] ipList;
    private Response response;

    ResponseCallbackIPJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseCallbackIPJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("ip_list")) {
            JSONArray array = jsonObject.getJSONArray("ip_list");

            int size = array.length();
            ipList = new String[size];
            for (int i=0; i<size; i++){
                ipList[i] = array.getString(i);
            }
        }
    }

    @Override public String[] getIpList() {
        return ipList;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseCallbackIPJSONImpl that = (ResponseCallbackIPJSONImpl)o;

        if (!Arrays.equals(ipList, that.ipList)) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (ipList!=null? Arrays.hashCode(ipList): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
