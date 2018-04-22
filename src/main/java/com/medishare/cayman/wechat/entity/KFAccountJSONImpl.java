package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class KFAccountJSONImpl implements KFAccount, Serializable {
    private static final long serialVersionUID = -2632752268592360603L;

    private String kfAccount;
    private String kfNickName;
    private String kfId;
    private String kfHeadImageURL;

    private JSONObject object;

    KFAccountJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        this.object = jsonObject;
        if (!jsonObject.isNull("kf_account")) {
            this.kfAccount = jsonObject.getString("kf_account");
        }
        if (!jsonObject.isNull("kf_nick")) {
            this.kfNickName = jsonObject.getString("kf_nick");
        }
        if (!jsonObject.isNull("kf_id")) {
            this.kfId = jsonObject.getString("kf_id");
        }
        if (!jsonObject.isNull("kf_headimgurl")) {
            this.kfHeadImageURL = jsonObject.getString("kf_headimgurl");
        }
    }

    @Override public String getKFAccount() {
        return kfAccount;
    }

    @Override public String getKFNickName() {
        return kfNickName;
    }

    @Override public String getKFId() {
        return kfId;
    }

    @Override public String getKFHeadImageURL() {
        return kfHeadImageURL;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        KFAccountJSONImpl that = (KFAccountJSONImpl)o;

        if (kfAccount!=null? !kfAccount.equals(that.kfAccount): that.kfAccount!=null) return false;
        if (kfHeadImageURL!=null? !kfHeadImageURL.equals(that.kfHeadImageURL): that.kfHeadImageURL!=null) return false;
        if (kfId!=null? !kfId.equals(that.kfId): that.kfId!=null) return false;
        if (kfNickName!=null? !kfNickName.equals(that.kfNickName): that.kfNickName!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (kfAccount!=null? kfAccount.hashCode(): 0);
        result = 31*result + (kfNickName!=null? kfNickName.hashCode(): 0);
        result = 31*result + (kfId!=null ? kfId.hashCode(): 0);
        result = 31*result + (kfHeadImageURL!=null? kfHeadImageURL.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return this.object.toString();
    }
}
