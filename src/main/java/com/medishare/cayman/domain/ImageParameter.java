package com.medishare.cayman.domain;

import java.io.Serializable;

/**
 * Created by liuy on 2018/4/20.
 */
public class ImageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int[] crop;
    protected int[] resize;
    protected double[] gaussian;

    public int[] getCrop() {
        return crop;
    }

    public void setCrop(int[] crop) {
        this.crop = crop;
    }

    public int[] getResize() {
        return resize;
    }

    public void setResize(int[] resize) {
        this.resize = resize;
    }

    public double[] getGaussian() {
        return gaussian;
    }

    public void setGaussian(double[] gaussian) {
        this.gaussian = gaussian;
    }

}

