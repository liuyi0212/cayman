package com.medishare.cayman.wechat.api;

import com.medishare.cayman.wechat.entity.Response;
import com.medishare.cayman.wechat.entity.ResponseMenu;

import java.io.IOException;
import java.util.Map;

public interface MenuService {
    // 自定义菜单创建接口
    Response createMenu(Map<String, Object> menu) throws IOException;

    // 自定义菜单查询接口
    ResponseMenu getMenu() throws IOException;

    // 自定义菜单删除接口
    Response deleteMenu() throws IOException;
}
