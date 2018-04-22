package com.medishare.cayman.wechat.api;

import com.medishare.cayman.wechat.entity.Response;
import com.medishare.cayman.wechat.entity.ResponseMedia;
import com.medishare.cayman.wechat.entity.ResponseMessage;
import com.medishare.cayman.wechat.entity.ResponseTemplate;

import java.io.IOException;
import java.util.Map;

public interface TemplateService {
    // 设置所属行业
    Response setIndustryTemplate(Map<String, Object> industry) throws IOException;

    // 获得模板ID
    ResponseTemplate addTemplate(Map<String, Object> template) throws IOException;

    // 发送模板消息
    ResponseMessage sendTemplateMessage(Map<String, Object> template) throws IOException;
}
