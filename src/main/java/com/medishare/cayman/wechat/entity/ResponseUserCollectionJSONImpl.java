package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONArray;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

final class ResponseUserCollectionJSONImpl implements ResponseUserCollection, Serializable {
    private static final long serialVersionUID = 3595391418962102195L;

    private Integer total;
    private Integer count;
    private String[] openIds;
    private String nextOpenId;

    private Response response;

    ResponseUserCollectionJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseUserCollectionJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("total")) {
            this.total = jsonObject.getInt("total");
        }
        if (!jsonObject.isNull("count")) {
            this.count = jsonObject.getInt("count");
        }
        if (!jsonObject.isNull("data")) {
            JSONObject data = jsonObject.getJSONObject("data");
            if (!data.isNull("openid")) {
                JSONArray array = data.getJSONArray("openid");

                int size= array.length();
                this.openIds = new String[size];
                for (int i=0; i<size; i++) {
                    this.openIds[i] = array.getString(i);
                }
            }
        }
        if (!jsonObject.isNull("next_openid")) {
            this.nextOpenId = jsonObject.getString("next_openid");
        }
    }

    @Override public Integer getTotal() {
        return total;
    }

    @Override public Integer getCount() {
        return count;
    }

    @Override public String[] getOpenIds() {
        return openIds;
    }

    @Override public String getNextOpenId() {
        return nextOpenId;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseUserCollectionJSONImpl that = (ResponseUserCollectionJSONImpl)o;

        if (count!=null? !count.equals(that.count): that.count!=null) return false;
        if (nextOpenId!=null? !nextOpenId.equals(that.nextOpenId): that.nextOpenId!=null) return false;
        if (!Arrays.equals(openIds, that.openIds)) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;
        if (total!=null? !total.equals(that.total): that.total!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (total!=null? total.hashCode(): 0);
        result = 31*result + (count!=null? count.hashCode(): 0);
        result = 31*result + (openIds!=null? Arrays.hashCode(openIds): 0);
        result = 31*result + (nextOpenId!=null? nextOpenId.hashCode(): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
