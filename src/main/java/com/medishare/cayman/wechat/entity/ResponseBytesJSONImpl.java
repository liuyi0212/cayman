package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;

final class ResponseBytesJSONImpl implements ResponseBytes, Serializable {
    private static final long serialVersionUID = 6843993134093306504L;

    private Integer errCode;
    private String errMsg;
    private byte[] bytes;
    private String suffix;

    protected JSONObject object;

    ResponseBytesJSONImpl(Integer errCode, String errMsg) {
        init(errCode, errMsg);
    }
    
    ResponseBytesJSONImpl(Integer errCode, String errMsg,byte[] bytes,String suffix) {
    	initBytes(errCode, errMsg , bytes ,suffix);
    }

    void init(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.object = new JSONObject(
                "{\"errcode\": " + errCode + ", \"errmsg\": \"" + errMsg + "\" }"
        );
    }
    
    void initBytes(Integer errCode, String errMsg, byte [] bytes ,String suffix) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.object = new JSONObject(
                "{\"errcode\": " + errCode + ", \"errmsg\": \"" + errMsg + "\" }"
        );
        this.bytes = bytes;
        this.suffix = suffix;
    }

    ResponseBytesJSONImpl(HttpResponse response) {
        this(response, 0, "ok");
    }


    ResponseBytesJSONImpl(HttpResponse response, Integer defaultCode, String defaultMsg) {
        init(response.asJSONObject(), defaultCode, defaultMsg);
    }

    ResponseBytesJSONImpl (JSONObject jsonObject) {
        this(jsonObject, 0, "ok");
    }

    ResponseBytesJSONImpl (JSONObject jsonObject, Integer defaultCode, String defaultMsg) {
        init(jsonObject, defaultCode, defaultMsg);
    }

    void init(JSONObject jsonObject, Integer defaultCode, String defaultMsg) {
        this.object = jsonObject;
        if (!jsonObject.isNull("errcode")) {
            this.errCode = jsonObject.getInt("errcode");
        } else {
            this.errCode = defaultCode;
            this.object.put("errcode", defaultCode);
        }
        if (!jsonObject.isNull("errmsg")) {
            this.errMsg = jsonObject.getString("errmsg");
        } else {
            this.errMsg = defaultMsg;
            this.object.put("errmsg", defaultMsg);
        }
    }

    @Override public Integer getErrCode() {
        return errCode;
    }

    @Override public String getErrMsg() {
        return errMsg;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseBytesJSONImpl that = (ResponseBytesJSONImpl)o;

        if (errCode!=null? !errCode.equals(that.errCode): that.errCode!=null) return false;
        if (errMsg!=null? !errMsg.equals(that.errMsg): that.errMsg!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (errCode!=null? errCode.hashCode(): 0);
        result = 31*result + (errMsg!=null? errMsg.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return object.toString();
    }

	@Override
	public byte[] getBytes() {
		return this.bytes;
	}

	@Override
	public String getSuffix() {
		return this.suffix;
	}

}
