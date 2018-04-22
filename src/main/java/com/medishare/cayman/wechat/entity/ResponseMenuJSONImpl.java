package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONArray;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

final class ResponseMenuJSONImpl implements ResponseMenu, Serializable {
    private static final long serialVersionUID = 7979110862433632825L;

    private Button[] buttons;
    private Response response;

    ResponseMenuJSONImpl(HttpResponse response) {
        this(response.asJSONObject());
    }

    ResponseMenuJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.response = new ResponseJSONImpl(jsonObject);
        if (!jsonObject.isNull("menu")) {
            JSONObject menu = jsonObject.getJSONObject("menu");

            if (!jsonObject.isNull("button")) {
                JSONArray array = menu.getJSONArray("button");

                int size = array.length();
                this.buttons = new Button[size];
                for (int i=0; i<size; i++) {
                    buttons[i] = new ButtonJSONImpl(array.getJSONObject(i));
                }
            }
        }
    }

    @Override public Button[] getButtons() {
        return buttons;
    }

    @Override public Response getResponse() {
        return response;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ResponseMenuJSONImpl that = (ResponseMenuJSONImpl)o;

        if (!Arrays.equals(buttons, that.buttons)) return false;
        if (response!=null ? !response.equals(that.response): that.response!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (buttons!=null? Arrays.hashCode(buttons): 0);
        result = 31*result + (response!=null? response.hashCode(): 0);
        return result;
    }

    @Override public String toString() {
        return response.toString();
    }
}
