package com.medishare.cayman.wechat.api;

public interface Wechat extends
        BaseService,
        MediaService,
        KFAccountService,
        MassService,
        TemplateService,
        GroupService,
        UserService,
        OAuth2Service,
        MenuService,
        QRCodeService,
        JsService,
        ShortURLService {
    // 设置Access Token
    void setAccessToken(String credential, Long expiresIn);
}
