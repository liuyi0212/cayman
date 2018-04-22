package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONArray;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

final class ResponseGroupCollectionJSONImpl implements ResponseGroupCollection, Serializable {
    private static final long serialVersionUID = -3813915987234782829L;

    private Group[] groups;
    private Response response;

    ResponseGroupCollectionJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseGroupCollectionJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("groups")) {
            JSONArray array = jsonObject.getJSONArray("groups");

            int size = array.length();
            groups = new Group[size];
            for (int i=0; i<size; i++){
                groups[i] = new GroupJSONImpl(array.getJSONObject(i));
            }
        }
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public Group[] getGroups() {
        return groups;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseGroupCollectionJSONImpl that = (ResponseGroupCollectionJSONImpl)o;

        if (!Arrays.equals(groups, that.groups)) return false;
        if (response!=null? !response.equals(that.response): that.response!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (groups!=null? Arrays.hashCode(groups): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
