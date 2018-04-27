package com.medishare.cayman.wechat.client;

import com.alibaba.fastjson.JSON;
import com.medishare.cayman.utils.HttpHelper;
import com.medishare.cayman.utils.JSonUtils;
import com.medishare.cayman.wechat.api.Wechat;
import com.medishare.cayman.wechat.conf.Configuration;
import com.medishare.cayman.wechat.entity.*;
import com.medishare.cayman.wechat.http.HttpClient;
import com.medishare.cayman.wechat.http.HttpClientFactory;
import com.medishare.cayman.wechat.http.HttpParameter;
import com.medishare.cayman.wechat.http.HttpResponse;
import com.medishare.cayman.wechat.internal.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

final class WechatImpl implements Wechat, Serializable {
    private static final long serialVersionUID = 8970456419937426235L;

    private Configuration conf;
    private HttpClient http;
    private ObjectFactory factory;

    private AccessToken accessToken;

    WechatImpl(Configuration conf) {
        init(conf);
    }

    private void init(Configuration conf) {
        this.conf = conf;
        setHttp();
        setFactory();
        setToken();
    }

    private void setHttp() {
        this.http = HttpClientFactory.getInstance(conf);
    }

    private void setFactory() {
        this.factory = new ObjectFactory();
    }

    private void setToken() {
        String credential = conf.getOAuthAppCredential();
        if (credential != null) {
            this.accessToken = factory.createAccessToken(credential, null);
        }
    }

    private HttpResponse get(String url, HttpParameter[] params) throws IOException {
        return http.get(url, params);
    }

    private HttpResponse post(String url, HttpParameter[] params) throws IOException{
        return http.post(url, params);
    }

    @Override public void setAccessToken(String credential, Long expiresIn) {
        this.accessToken = factory.createAccessToken(credential, expiresIn);
    }

    // 获取Access Token
    @Override public ResponseAccessToken getAccessToken() throws IOException{
        String url = conf.getRestBaseURL() + "/cgi-bin/token";
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("grant_type", "client_credential"),
                new HttpParameter("appid", conf.getOAuthAppId()),
                new HttpParameter("secret", conf.getOAuthAppSecret())
        };
        ResponseAccessToken responseAccessToken
                = factory.createResponseAccessToken(get(url, params));
        this.accessToken = responseAccessToken.getAccessToken();
        return responseAccessToken;
    }

    // 获取微信服务器IP地址
    @Override public ResponseCallbackIP getCallbackIP() throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/getcallbackip"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
        };
        return factory.createResponseCallbackIP(get(url, params));
    }

    // 添加客服帐号
    @Override public Response createKFAccount(Map<String, Object> kfAccount) throws IOException {
        String url = conf.getRestBaseURL() + "/customservice/kfaccount/add"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(kfAccount))
        };
        return factory.createResponse(post(url, params));
    }

    // 修改客服账号
    @Override public Response updateKFAccount(Map<String, Object> kfAccount) throws IOException{
        String url = conf.getRestBaseURL() + "/customservice/kfaccount/update"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(kfAccount))
        };
        return factory.createResponse(post(url, params));
    }

    // 删除客服账号
    @Override public Response deleteKFAccount(Map<String, Object> kfAccount) throws IOException {
        String url = conf.getRestBaseURL() + "/customservice/kfaccount/del"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(kfAccount))
        };
        return factory.createResponse(post(url, params));
    }

    // 设置客服帐号的头像
    @Override public Response uploadHeadImage(String kfAccount, File file) throws IOException{
        String url = conf.getRestBaseURL() + "/customservice/kfaccount/uploadheadimg"
                + "?access_token=" + accessToken.getCredential()
                + "&kf_account=" + kfAccount;
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(file.getName(), file)
        };
        return factory.createResponse(post(url, params));
    }

    // 获取所有客服账号
    @Override public ResponseKFAccountCollection listKFAccount() throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/customservice/getkflist"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
        };
        return factory.createResponseKFAccountCollection(post(url, params));
    }

    // 客服接口-发消息
    @Override public Response sendMessage(Map<String, Object> message) throws IOException{
        String url = conf.getRestBaseURL() + "/cgi-bin/message/custom/send"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(message))
        };
        return factory.createResponse(post(url, params));
    }

    // 上传图文消息素材
    @Override public ResponseUploadNews uploadNews(Map<String, Object> news) throws IOException {
        String url = conf.getMediaBaseURL() + "/cgi-bin/media/uploadnews"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(news))
        };
        return factory.createResponseUploadNews(post(url, params));
    }

    // 根据分组进行群发
    @Override public ResponseMessage massSendAll(Map<String, Object> msg) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/message/mass/sendall"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(msg))
        };
        return factory.createResponseMessage(post(url, params));
    }

    // 根据OpenID列表群发
    @Override public ResponseMessage massSend(Map<String, Object> msg) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/message/mass/send"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(msg))
        };
        return factory.createResponseMessage(post(url, params));
    }

    // 删除群发
    @Override public Response massDelete(Map<String, Object> msg) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/message/mass/delete"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(msg))
        };
        return factory.createResponse(post(url, params));
    }

    // 预览接口
    @Override public ResponseMessage massPreview(Map<String, Object> msg) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/message/mass/preview"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(msg))
        };
        return factory.createResponseMessage(post(url, params));
    }

    // 查询群发消息发送状态
    @Override public ResponseMessage massGet(Map<String, Object> msg) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/message/mass/get"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(msg))
        };
        return factory.createResponseMessage(post(url, params));
    }

    // 设置所属行业
    @Override public Response setIndustryTemplate(Map<String, Object> industry) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/template/api_set_industry"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(industry))
        };
        return factory.createResponse(post(url, params));
    }

    // 获得模板ID
    @Override public ResponseTemplate addTemplate(Map<String, Object> template) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/template/api_add_template"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(template))
        };
        return factory.createResponseTemplate(post(url, params));
    }

    // 发送模板消息
    @Override public ResponseMessage sendTemplateMessage(Map<String, Object> template) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/message/template/send"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(template))
        };
        return factory.createResponseMessage(post(url, params));
    }

    // 新增临时素材
    @Override public ResponseMedia uploadMedia(String type, File file) throws IOException{
        String url = conf.getMediaBaseURL() + "/cgi-bin/media/upload"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("type", type),
                new HttpParameter("media", file)
        };
        return factory.createResponseMedia(post(url, params));
    }

    // 获取临时素材
    @Override public Response getMedia(String mediaId, File file) throws IOException {
        String url = conf.getMediaBaseURL() + "/cgi-bin/media/get"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("media_id", mediaId)
        };
        return factory.createResponse(post(url, params), file);
    }

    // 获取临时素材字节流
    @Override public ResponseBytes getMediaBytes(String mediaId) throws IOException {
        String url = conf.getMediaBaseURL() + "/cgi-bin/media/get"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("media_id", mediaId)
        };
        return factory.createBytesResponse(post(url, params));
    }


    // 新增永久素材
    @Override public ResponseMedia addNewsMaterial(Map<String, Object> news) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/material/add_news"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(news))
        };
        return factory.createResponseMedia(post(url, params));
    }

    // 新增其他类型永久素材
    @Override public ResponseMedia addMaterial(String type, File file) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/material/add_material"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("type", type),
                new HttpParameter("media", file)
        };
        return factory.createResponseMedia(post(url, params));
    }

    // 新增其他类型永久素材
    @Override public ResponseMedia addMaterial(String type, Map<String, Object> description, File file) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/material/add_material"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("type", type),
                new HttpParameter("description", new JSONObject(description)),
                new HttpParameter("media", file)
        };
        return factory.createResponseMedia(post(url, params));
    }

    // 获取永久素材
    @Override public ResponseMedia getMaterial(Map<String, Object> material) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/material/get_material"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(material))
        };
        return factory.createResponseMedia(post(url, params));
    }

    // 获取素材列表
    @Override public ResponseMedia batchgetMaterial(Map<String, Object> material) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/material/batchget_material"
                + "?access_token=" + accessToken.getCredential();
    	HttpParameter[] params = new HttpParameter[] {
    			new HttpParameter(new JSONObject(material))
    	};
        return factory.createResponseMedia(post(url, params));
    }

//    // 获取素材总数
//    @Override public ResponseMedia getMaterialCount(Map<String, Object> material) throws IOException {
//    	String url = conf.getRestBaseURL() + "/cgi-bin/material/get_materialcount"
//    			+ "?access_token=" + accessToken.getCredential();
//    	HttpParameter[] params = new HttpParameter[] {
//    			new HttpParameter(new JSONObject(material))
//    	};
//    	return factory.createResponseMedia(post(url,null));
//    }

    // 删除永久素材
    @Override public Response deleteMaterial(Map<String, Object> material) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/material/del_material"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(material))
        };
        return factory.createResponse(post(url, params));
    }

    // 创建分组
    @Override public ResponseGroup createGroup(Map<String, Object> group) throws IOException{
        String url = conf.getRestBaseURL() + "/cgi-bin/groups/create"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(group))
        };
        return factory.createResponseGroup(post(url, params));
    }

    // 查询所有分组
    @Override public ResponseGroupCollection listGroup() throws IOException{
        String url = conf.getRestBaseURL() + "/cgi-bin/groups/get";
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("access_token", accessToken.getCredential())
        };
        return factory.createResponseGroupCollection(get(url, params));
    }

    // 查询用户所在分组
    @Override public ResponseGroup listGroup(Map<String, Object> user) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/groups/getid"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(user))
        };
        return factory.createResponseGroup(post(url, params));
    }

    // 修改分组名
    @Override public Response updateGroup(Map<String, Object> group) throws IOException{
        String url = conf.getRestBaseURL() + "/cgi-bin/groups/update"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(group))
        };
        return factory.createResponse(post(url, params));
    }

    // 移动用户分组
    @Override public Response moveGroup(Map<String, Object> group) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/groups/members/update"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(group))
        };
        return factory.createResponse(post(url, params));
    }

    // 批量移动用户分组
    @Override public Response batchMoveGroup(Map<String, Object> group) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/user/info/updateremark"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(group))
        };
        return factory.createResponse(post(url, params));
    }

    // 删除分组
    @Override public Response deleteGroup(Map<String, Object> group) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/groups/delete"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(group))
        };
        return factory.createResponse(post(url, params));
    }

    // 设置备注名
    @Override public Response updateRemark(Map<String, Object> remark) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/groups/members/batchupdate"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter(new JSONObject(remark))
        };
        return factory.createResponse(post(url, params));
    }

    // 获取用户基本信息（包括UnionID机制）
    @Override public ResponseUser getUserInfo(String openId, String lang) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/user/info"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("openid", openId),
                new HttpParameter("lang", lang)
        };
        return factory.createResponseUser(get(url, params));
    }

    // 获取用户列表
    @Override public ResponseUserCollection getUserList(String nextOpenId) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/user/get"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("next_openid", nextOpenId),
        };
        return factory.createResponseUserCollection(get(url, params));
    }

    // 生成授权链接
    @Override public String generateOAuth2URL(String redirectURI, String scope, String state) throws IOException {
        try {
            return conf.getOAuth2CodeURL() +"/connect/oauth2/authorize"
                    + "?appid=" + conf.getOAuthAppId()
                    + "&redirect_uri=" + URLEncoder.encode(redirectURI, "utf-8")
                    + "&response_type=" + "code"
                    + "&scope=" + scope
                    + "&state=" + state
                    + "#wechat_redirect";
        } catch (UnsupportedEncodingException uee) {
            throw new IOException("can not generate oauth2 url", uee);
        }
    }

    // 通过code换取网页授权access_token
    @Override public ResponseOAuth2AccessToken getOAuth2AccessToken(String code) throws IOException {
        String url = conf.getRestBaseURL() + "/sns/oauth2/access_token";
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("appid", conf.getOAuthAppId()),
                new HttpParameter("secret", conf.getOAuthAppSecret()),
                new HttpParameter("code", code),
                new HttpParameter("grant_type", "authorization_code")
        };
        return factory.createResponseOAuth2AccessToken(get(url, params));
    }

    // 刷新access_token
    @Override public ResponseOAuth2AccessToken refreshOAuth2AccessToken(String refreshToken) throws IOException {
        String url = conf.getRestBaseURL() + "/sns/oauth2/access_token";
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("appid", conf.getOAuthAppId()),
                new HttpParameter("grant_type", "refresh_token"),
                new HttpParameter("refresh_token", refreshToken)
        };
        return factory.createResponseOAuth2AccessToken(get(url, params));
    }

    // 拉取用户信息(需scope为 snsapi_userinfo)
    @Override public ResponseUser getOAuth2UserInfo(String accessToken, String openId, String lang) throws IOException {
        String url = conf.getRestBaseURL() + "/sns/userinfo";
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("access_token", accessToken),
                new HttpParameter("openid", openId),
                new HttpParameter("lang", lang)
        };
        return factory.createResponseUser(get(url, params));
    }

    // 检验授权凭证（access_token）是否有效
    @Override public Response validateOAuth2AccessToken(String accessToken, String openId) throws IOException {
        String url = conf.getRestBaseURL() + "/sns/auth";
        HttpParameter[] params = new HttpParameter[] {
                new HttpParameter("access_token", accessToken),
                new HttpParameter("openid", openId)
        };
        return factory.createResponse(get(url, params));
    }

    // 自定义菜单创建接口
    @Override public Response createMenu(Map<String, Object> menu) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/menu/create"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[]{
                new HttpParameter(new JSONObject(menu))
        };
        return factory.createResponse(post(url, params));
    }

    // 自定义菜单查询接口
    @Override public ResponseMenu getMenu() throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/menu/get"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[]{
        };
        return factory.createResponseMenu(get(url, params));
    }

    // 自定义菜单删除接口
    @Override public Response deleteMenu() throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/menu/delete"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[]{
        };
        return factory.createResponse(get(url, params));
    }

    // 创建二维码ticket
    @Override public ResponseTicket createTicket(Map<String, Object> ticket) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/qrcode/create"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[]{
                new HttpParameter(new JSONObject(ticket))
        };
        return factory.createResponseTicket(post(url, params));
    }

    // 通过ticket换取二维码
    @Override public void showQRCode(String ticket, File file) throws IOException {
        String url = conf.getMPBaseURL() + "/cgi-bin/showqrcode";
        HttpParameter[] params = new HttpParameter[]{
                new HttpParameter("ticket", ticket)
        };
        factory.createQRCode(get(url, params), file);
    }

    // 通过ticket换取二维码bytes
    @Override public byte [] getBytesQRCode(String ticket) throws IOException {
        String url = conf.getMPBaseURL() + "/cgi-bin/showqrcode";
        HttpParameter[] params = new HttpParameter[]{
                new HttpParameter("ticket", ticket)
        };
        return factory.createBytesQRCode(get(url, params));
    }

    // 将一条长链接转成短链接
    @Override public ResponseShortURL createShortURL(Map<String, Object> longURL) throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/shorturl"
                + "?access_token=" + accessToken.getCredential();
        HttpParameter[] params = new HttpParameter[]{
                new HttpParameter(new JSONObject(longURL))
        };
        return factory.createResponseShortURL(post(url, params));
    }

    //获取jsAPI ticket
    @Override
    public ResponseTicket getJsApiTicket() throws IOException {
        String url = conf.getRestBaseURL() + "/cgi-bin/ticket/getticket"
                + "?access_token=" + accessToken.getCredential()+"&type=jsapi";
        HttpParameter[] params = new HttpParameter[]{
        };
        return factory.createResponseTicket(get(url, params));
    }

    @Override
    public com.alibaba.fastjson.JSONObject batchgetMaterialDoPost() {
        String url = conf.getRestBaseURL() + "/cgi-bin/material/batchget_material"
                + "?access_token=" + accessToken.getCredential();
        Map<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("type","news");
        paramMap.put("offset","0");
        paramMap.put("count","22");
        Object data= JSON.toJSON(paramMap);

        System.out.println(JSonUtils.toJsonString(data));
        com.alibaba.fastjson.JSONObject jsonObject = null;
        try {
            jsonObject = HttpHelper.doPost(url, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
