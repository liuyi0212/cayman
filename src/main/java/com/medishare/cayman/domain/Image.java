package com.medishare.cayman.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuy on 2018/4/20.
 */
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String uri;
    protected String domain;
    protected byte[] data;
    protected int[] resolution;
    protected Date created;
    protected String memo;

    public String getUri() {
        return uri;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int[] getResolution() {
        return resolution;
    }

    public void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}