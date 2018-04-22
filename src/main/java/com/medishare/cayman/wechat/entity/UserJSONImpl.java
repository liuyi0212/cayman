package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.internal.json.JSONArray;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

final class UserJSONImpl implements User, Serializable {
    private static final long serialVersionUID = -51605717981805752L;

    private Integer subscribe;
    private String openId;
    private String nickname;
    private Integer sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headImgURL;
    private Long subscribeTime;
    private String unionId;
    private String[] privilege;

    private JSONObject object;

    UserJSONImpl(String openId) {
        this.openId = openId;
        this.object = new JSONObject(
                "{\"openid\": \"" + openId + "\"}"
        );
    }

    UserJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.object = jsonObject;
        if (!jsonObject.isNull("subscribe")) {
            this.subscribe = jsonObject.getInt("subscribe");
        }
        if (!jsonObject.isNull("openid")) {
            this.openId = jsonObject.getString("openid");
        }
        if (!jsonObject.isNull("nickname")){
            this.nickname = jsonObject.getString("nickname");
        }
        if (!jsonObject.isNull("sex")) {
            this.sex = jsonObject.getInt("sex");
        }
        if (!jsonObject.isNull("language")){
            this.language = jsonObject.getString("language");
        }
        if (!jsonObject.isNull("city")){
            this.city = jsonObject.getString("city");
        }
        if (!jsonObject.isNull("province")) {
            this.province = jsonObject.getString("province");
        }
        if (!jsonObject.isNull("country")){
            this.country = jsonObject.getString("country");
        }
        if (!jsonObject.isNull("headimgurl")){
            this.headImgURL = jsonObject.getString("headimgurl");
        }
        if (!jsonObject.isNull("subscribe_time")) {
            this.subscribeTime = jsonObject.getLong("subscribe_time");
        }
        if (!jsonObject.isNull("privilege")) {
            JSONArray array = jsonObject.getJSONArray("privilege");

            int size = array.length();
            this.privilege = new String[size];
            for (int i=0; i<size; i++) {
                this.privilege[i] = array.getString(i);
            }
        }
        if (!jsonObject.isNull("unionid")) {
            this.unionId = jsonObject.getString("unionid");
        }
    }

    @Override public Integer getSubscribe() {
        return subscribe;
    }

    @Override public String getOpenId() {
        return openId;
    }

    @Override public String getNickname() {
        return nickname;
    }

    @Override public Integer getSex() {
        return sex;
    }

    @Override public String getLanguage() {
        return language;
    }

    @Override public String getCity() {
        return city;
    }

    @Override public String getProvince() {
        return province;
    }

    @Override public String getCountry() {
        return country;
    }

    @Override public String getHeadImgURL() {
        return headImgURL;
    }

    @Override public Long getSubscribeTime() {
        return subscribeTime;
    }

    @Override public String[] getPrivilege() {
        return privilege;
    }

    @Override public String getUnionId() {
        return unionId;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        UserJSONImpl that = (UserJSONImpl)o;

        if (city!=null? !city.equals(that.city): that.city!=null) return false;
        if (country!=null? !country.equals(that.country): that.country!=null) return false;
        if (headImgURL!=null? !headImgURL.equals(that.headImgURL): that.headImgURL!=null) return false;
        if (language!=null? !language.equals(that.language): that.language!=null) return false;
        if (nickname!=null? !nickname.equals(that.nickname): that.nickname!=null) return false;
        if (openId!=null? !openId.equals(that.openId): that.openId!=null) return false;
        if (!Arrays.equals(privilege, that.privilege)) return false;
        if (province!=null? !province.equals(that.province): that.province!=null) return false;
        if (sex!=null? !sex.equals(that.sex): that.sex!=null) return false;
        if (subscribe!=null? !subscribe.equals(that.subscribe): that.subscribe!=null) return false;
        if (subscribeTime!=null? !subscribeTime.equals(that.subscribeTime): that.subscribeTime!=null) return false;
        if (unionId!=null? !unionId.equals(that.unionId): that.unionId!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (subscribe!=null? subscribe.hashCode(): 0);
        result = 31*result + (openId!=null? openId.hashCode(): 0);
        result = 31*result + (nickname!=null? nickname.hashCode(): 0);
        result = 31*result + (sex!=null? sex.hashCode(): 0);
        result = 31*result + (language!=null? language.hashCode(): 0);
        result = 31*result + (city!=null? city.hashCode(): 0);
        result = 31*result + (province!=null? province.hashCode(): 0);
        result = 31*result + (country!=null? country.hashCode(): 0);
        result = 31*result + (headImgURL!=null? headImgURL.hashCode(): 0);
        result = 31*result + (subscribeTime!=null? subscribeTime.hashCode(): 0);
        result = 31*result + (unionId!=null? unionId.hashCode(): 0);
        result = 31*result + (privilege!=null? Arrays.hashCode(privilege): 0);
        return result;
    }

    @Override public String toString() {
        return object.toString();
    }
}
