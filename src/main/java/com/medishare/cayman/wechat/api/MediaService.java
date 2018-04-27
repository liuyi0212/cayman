package com.medishare.cayman.wechat.api;

import com.medishare.cayman.wechat.entity.Response;
import com.medishare.cayman.wechat.entity.ResponseBytes;
import com.medishare.cayman.wechat.entity.ResponseMedia;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface MediaService {
    // 新增临时素材
    ResponseMedia uploadMedia(String type, File file) throws IOException;

    // 获取临时素材
    Response getMedia(String mediaId, File file) throws IOException;

    // 新增永久素材
    ResponseMedia addNewsMaterial(Map<String, Object> news) throws IOException;

    // 新增其他类型永久素材
    ResponseMedia addMaterial(String type, File file) throws IOException;

    // 新增其他类型永久素材
    ResponseMedia addMaterial(String type, Map<String, Object> description, File file) throws IOException;

    // 获取永久素材
    ResponseMedia getMaterial(Map<String, Object> material) throws IOException;

    // 获取素材列表
    ResponseMedia batchgetMaterial(Map<String, Object> material) throws Exception;

    // 删除永久素材
    Response deleteMaterial(Map<String, Object> material) throws IOException;

    // 获取临时素材字节流
    ResponseBytes getMediaBytes(String mediaId) throws IOException;

    com.alibaba.fastjson.JSONObject batchgetMaterialDoPost();

}