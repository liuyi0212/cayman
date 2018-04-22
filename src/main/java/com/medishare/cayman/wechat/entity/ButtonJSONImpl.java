package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.internal.json.JSONArray;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

final class ButtonJSONImpl implements Button, Serializable {
    private static final long serialVersionUID = 7096193803082894871L;

    private String type;
    private String name;
    private String url;
    private String key;
    private Button[] buttons;

    private JSONObject object;

    ButtonJSONImpl(JSONObject jsonObject) {
        init(jsonObject);
    }

    void init(JSONObject jsonObject) {
        this.object = jsonObject;
        if (!jsonObject.isNull("type")) {
            this.type = jsonObject.getString("type");
        }
        if (!jsonObject.isNull("name")) {
            this.name = jsonObject.getString("name");
        }
        if (!jsonObject.isNull("url")) {
            this.url = jsonObject.getString("url");
        }
        if (!jsonObject.isNull("key")) {
            this.key = jsonObject.getString("key");
        }
        if (!jsonObject.isNull("sub_button")) {
            JSONArray array = jsonObject.getJSONArray("sub_button");

            int size = array.length();
            this.buttons = new Button[size];
            for (int i=0; i<size; i++) {
                this.buttons[i] = new ButtonJSONImpl(array.getJSONObject(i));
            }
        }
    }

    @Override public String getType() {
        return type;
    }

    @Override public String getName() {
        return name;
    }

    @Override public String getUrl() {
        return url;
    }

    @Override public String getKey() {
        return key;
    }

    @Override public Button[] getButtons() {
        return buttons;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        ButtonJSONImpl that = (ButtonJSONImpl)o;

        if (key!=null? !key.equals(that.key): that.key!=null) return false;
        if (name!=null? !name.equals(that.name): that.name!=null) return false;
        if (!Arrays.equals(buttons, that.buttons)) return false;
        if (type!=null? !type.equals(that.type): that.type!=null) return false;
        if (url!=null? !url.equals(that.url): that.url!=null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = 0;
        result = 31*result + (type!= null? type.hashCode(): 0);
        result = 31*result + (name!= null? name.hashCode(): 0);
        result = 31*result + (url!=null? url.hashCode(): 0);
        result = 31*result + (key!=null? key.hashCode(): 0);
        result = 31*result + (buttons !=null? Arrays.hashCode(buttons): 0);
        return result;
    }

    @Override public String toString() {
        return object.toString();
    }
}
