package com.medishare.cayman.service;

import com.medishare.cayman.domain.Image;
import com.medishare.cayman.domain.ImageParameter;

/**
 * Created by liuy on 2018/4/20.
 */
public interface ImageService {
    /**
     * 读取图片
     *
     * @param uri 主键
     * @param onlyMeta 是否读取图片本身
     * @param param resize,crop,thumbnail...etc变形处理
     * @return
     */
    Image loadImage(String uri, boolean onlyMeta, ImageParameter param);

    /**
     * 存储图片
     *
     * @param img
     */
    void saveImage(Image img);
}
