package com.medishare.cayman.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.config.WebConfig;
import com.medishare.cayman.constant.OAuth2UriConstant;
import com.medishare.cayman.utils.JSonUtils;
import com.medishare.cayman.utils.MessageUtil;
import com.medishare.cayman.utils.SignUtil;
import com.medishare.cayman.utils.dispatcher.MsgDispatcher;
import com.medishare.cayman.wechat.api.Wechat;
import com.medishare.cayman.wechat.client.WechatFactory;
import com.medishare.cayman.wechat.client.WechatStatic;
import com.medishare.cayman.wechat.conf.ConfigurationContext;
import com.medishare.cayman.wechat.entity.Response;
import com.medishare.cayman.wechat.entity.ResponseMedia;
import com.medishare.cayman.wechat.entity.ResponseOAuth2AccessToken;
import com.medishare.cayman.wechat.entity.ResponseUser;

@RestController
public class WechatController extends BaseController{

	@Autowired
	WebConfig webConfig;
	
//	@Autowired
//	EventDispatcher eventDispatcher;
	
	@Autowired
	MsgDispatcher msgDispatcher;
	
	/**
	 * 配置微信后台
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/wechat/", method = RequestMethod.GET)
	public void index(@RequestParam("signature")String signature,
			@RequestParam("timestamp")String timestamp,
			@RequestParam("nonce")String nonce,
			@RequestParam("echostr")String echostr,
			HttpServletRequest request,HttpServletResponse response){
		log.info("微信配置验证");
		try {
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				log.info("配置成功");
				PrintWriter out = response.getWriter();
				out.print(echostr);
				out.close();
			} else {
				log.info("这里存在非法请求！");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 配置微信接受后台消息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wechat/", method = RequestMethod.POST)
	public String DoPost(HttpServletRequest request,HttpServletResponse response) {
		String result = "";
		try{
			Map<String, String> map= MessageUtil.parseXml(request);
			log.info("收到微信消息，请求内容："+map.toString());
			String msgtype=map.get("MsgType");
			if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
//				result = eventDispatcher.processEvent(map); //进入事件处理
			}else{
				result = msgDispatcher.processMessage(map); //进入消息处理
			}
		}catch(Exception e){
			log.error("出现错误");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 认证
	 * @param response
	 * @param request
	 * @param code
	 * @param state 跳转uri 根据state进行页面跳转，如果没有绑定跳转到绑定页面
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/oauth2", method = RequestMethod.GET)
	public ModelAndView wechatOauth2(HttpServletResponse response, HttpServletRequest request,@RequestParam("code")String code,@RequestParam("state")String state) throws IOException {
		Wechat wechat = WechatFactory.getInstance();
		// 获取Code&换取Access Token
		ResponseOAuth2AccessToken accessToken = wechat.getOAuth2AccessToken(code);
		if (accessToken.getResponse().getErrCode() == 0) {
			log.info("获取认证access_token成功" + accessToken);
		} else {
			log.error("获取认证access_token失败" + accessToken);
		}
		log.info("oath2 status:"+state);
		if(accessToken!=null){
			Cookie cookie = new Cookie("OPENID", accessToken.getOpenId());
			cookie.setPath("/");
			response.addCookie(cookie);
			ResponseUser user = wechat.getUserInfo(accessToken.getOpenId(), "zh_CN ");
			if(user == null || user.getUser() == null || user.getUser().getHeadImgURL() == null){
				log.info(JSonUtils.toJsonString(user));
				user= wechat.getOAuth2UserInfo(accessToken.getAccessToken(), accessToken.getOpenId(), "zh_CN ");				
			}
			
			String headImgUrl = user.getUser().getHeadImgURL();
			
			
//			Patient patient = patientService.findPatientByOpenId(accessToken.getOpenId());
//			if(patient==null){
				return new ModelAndView("redirect:"+webConfig.getWebHttp()+OAuth2UriConstant.BIND+"?redirect_uri="+URLEncoder.encode(state, "utf-8")+"&headImgUrl="+headImgUrl);
//			}else{
//				if(accessToken!=null){
					//免密码登录
//					if(memberLoginDAO.flushAuth()==null){
//						memberLoginDAO.patientLogin(request, patient.getUsername(), "");
//						memberLoginDAO.flushAuth();
//					}
//				}
//			}
//			Cookie session = new Cookie("SESSION", request.getSession().getId());
//			session.setPath("/");
//			response.addCookie(session);
			
		}
		log.info(JSonUtils.toJsonString(request.getCookies()));
		return new ModelAndView("redirect:"+webConfig.getWebHttp()+state);
	}
	
	/**
	 * 获取当前accesstoken
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wechat/accesstoken/", method = RequestMethod.GET)
	public String getAccessToken(HttpServletResponse response, HttpServletRequest request) throws IOException {
		JSONRet ret = new JSONRet();
		Wechat wechat = WechatFactory.getInstance();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("accessToken", wechat.getAccessToken().getAccessToken().getCredential());
		ret.setData(data);
		ret.setCode(0);
		return JSonUtils.toJsonString(ret);
	}
	
	/**
	 * 获取永久素材
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wechat/batchget/material/", method = RequestMethod.POST)
	public String getBatchgetMaterial(@RequestBody Map material ,HttpServletResponse response, HttpServletRequest request) throws IOException {
		JSONRet ret = new JSONRet();
		try {
			Wechat wechat = WechatFactory.getInstance();
			System.out.println(JSonUtils.toJsonString(material));
			ResponseMedia resp = wechat.batchgetMaterial(material);
			System.out.println(resp.getMedia());
		} catch (Exception e) {
			log.error("error",e);
			return "error";
		}		
		return JSonUtils.toJsonString(ret);
	}
	
	
	
	/**
	 * js api
	 * @param url
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/wechat/jsapi/", method = RequestMethod.GET)
	public String jsapi(@RequestParam("url") String url ,HttpServletRequest request,HttpServletResponse response){
		JSONRet ret = new JSONRet();
		Map<String, String> jsapi = SignUtil.sign(WechatStatic.getJsApiTicket(), url);
		jsapi.put("appid", ConfigurationContext.getInstance().getOAuthAppId());
		ret.setData(jsapi);
		ret.setCode(0);
		return JSonUtils.toJsonString(ret);
	}
	
	
	/**
	 * 获取
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/wechat/redirect/oauth2/", method = RequestMethod.GET)
	public ModelAndView getOauth2Url(@RequestParam(value="url",required=false) String url ,HttpServletRequest request,HttpServletResponse response){
		Wechat wechat = WechatFactory.getInstance();
		if(url==null || url.trim().equals("")){
			url =  webConfig.getWebHttp()+OAuth2UriConstant.BIND;
		}
		String oauth2URL = "";
		try {
			oauth2URL = wechat.generateOAuth2URL(webConfig.getWebHttp()+"/wx/oauth2", "snsapi_base", URLEncoder.encode(url.replace(webConfig.getWebHttp(), ""),"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:"+oauth2URL);
	}
	/**
	 * 自定义菜单api
	 * @param menu
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create/menu/", method = RequestMethod.POST)
	public String createMenu(@RequestBody Map menu ,HttpServletRequest request,HttpServletResponse response){
		try {
			Wechat wechat = WechatFactory.getInstance();
			System.out.println(JSonUtils.toJsonString(menu));
			Response resp = wechat.createMenu(menu);
			System.out.println(resp.getErrMsg());
		} catch (Exception e) {
			log.error("createMenu error",e);
			return "error";
		}
		return "success";
	}


	
}
