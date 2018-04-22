package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class GroupJSONImpl implements Group, Serializable {
    private static final long serialVersionUID = 815958452399628409L;

    private Integer id;
    private String name;
    private Integer count;

    private JSONObject object;

    GroupJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.object = jsonObject;
        if (!jsonObject.isNull("group")) {
            JSONObject group = jsonObject.getJSONObject("group");
            if (!group.isNull("id")) {
                this.id = group.getInt("id");
            }
            if (!group.isNull("name")) {
                this.name = group.getString("name");
            }
        } else {
            if (!jsonObject.isNull("id")){
                this.id = jsonObject.getInt("id");
            }
            if (!jsonObject.isNull("groupid")) {
                this.id = jsonObject.getInt("groupid");
            }
            if (!jsonObject.isNull("name")){
                this.name = jsonObject.getString("name");
            }
            if (!jsonObject.isNull("count")){
                this.count = jsonObject.getInt("count");
            }
        }
    }

    @Override public Integer getId() {
        return id;
    }

    @Override public String getName() {
        return name;
    }

    @Override public Integer getCount() {
        return count;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        GroupJSONImpl that = (GroupJSONImpl)o;

        if (id!=null? !id.equals(that.id): that.id!=null) return false;
        if (name!=null? !name.equals(that.name): that.name!=null) return false;
        if (count!=null? !count.equals(that.count): that.count!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (id!=null? id.hashCode(): 0);
        result = 31*result + (name!=null? name.hashCode(): 0);
        result = 31*result + (count!=null? count.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return object.toString();
    }
}
