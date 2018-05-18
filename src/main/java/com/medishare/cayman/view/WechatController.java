package com.medishare.cayman.view;

import com.medishare.cayman.Application;
import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.config.WebConfig;
import com.medishare.cayman.constant.OAuth2UriConstant;
import com.medishare.cayman.domain.Member;
import com.medishare.cayman.service.ArticleService;
import com.medishare.cayman.service.MemberService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WechatController{

	static Logger log = LoggerFactory.getLogger(WechatController.class);


	@Autowired
	WebConfig webConfig;
	
//	@Autowired
//	EventDispatcher eventDispatcher;
	
	@Autowired
	MsgDispatcher msgDispatcher;

	@Autowired
	MemberService memberService;

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
		log.info("===================log进入了方法oauth2===================");
		log.info("===================log进入了方法oauth2===================");
		log.info("===================log进入了方法oauth2===================");
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
			log.info("===================accessToken.getOpenId()==================="+accessToken.getOpenId());
			log.info("===================accessToken.getOpenId()==================="+accessToken.getOpenId());
			log.info("===================accessToken.getOpenId()==================="+accessToken.getOpenId());

			Cookie cookie = new Cookie("OPENID", accessToken.getOpenId());
			cookie.setPath("/");
			response.addCookie(cookie);
			log.info(JSonUtils.toJsonString("===================accessToken.getCookiesA()==================="+request.getCookies()));
			log.info(JSonUtils.toJsonString("===================accessToken.getCookiesA()==================="+request.getCookies()));
			log.info(JSonUtils.toJsonString("===================accessToken.getCookiesA()==================="+request.getCookies()));
			Member member = memberService.getMemberInfoByOpenId(accessToken.getOpenId());
			if(member == null){
				return new ModelAndView("redirect:"+webConfig.getWebHttp()+OAuth2UriConstant.BASICINFO);
			}
			return new ModelAndView("redirect:"+webConfig.getWebHttp()+OAuth2UriConstant.PATH+state);
		}
		log.info(JSonUtils.toJsonString("===================accessToken.getCookies()==================="+request.getCookies()));
		log.info(JSonUtils.toJsonString("===================accessToken.getCookies()==================="+request.getCookies()));
		log.info(JSonUtils.toJsonString("===================accessToken.getCookies()==================="+request.getCookies()));
		return new ModelAndView("redirect:"+webConfig.getWebHttp()+OAuth2UriConstant.BASICINFO);
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
	@RequestMapping(value = "/wechat/batchget/material/", method = RequestMethod.GET)
	public String getBatchgetMaterial(HttpServletResponse response, HttpServletRequest request) throws IOException {
//	public String getBatchgetMaterial(@RequestBody Map material ,HttpServletResponse response, HttpServletRequest request) throws IOException {
		JSONRet ret = new JSONRet();
		List<Map<String, Object>> list = new ArrayList<>();

		try {
//			Map<String, Object> map = new HashMap<>();
//			map.put("type","news");
//			map.put("offset","0");
//			map.put("count","20");
//
//			Wechat wechat = WechatFactory.getInstance();^&
//			System.out.println(JSonUtils.toJsonString(map));
//            com.alibaba.fastjson.JSONObject jsonObject = wechat.batchgetMaterialDoPost();
//
//            List<Map<String, Object>> lista = (List<Map<String, Object>>) jsonObject.get("item");
//            lista.forEach(l->{
//                Map<String, Object> m2 = (Map<String, Object>) l.get("content");
//                List<Map<String, Object>> l2 = (List<Map<String, Object>>) m2.get("news_item");
//                System.out.println(JSonUtils.toJsonString(l2));
//            });
//			ret.setData(lista);
			Map<String, Object> map01 = new HashMap<>();
			map01.put("thumb_url","http://mmbiz.qpic.cn/mmbiz_jpg/EIvvvz1ymI6mia3BDJc4w1JKx4iahBAsmgTLtb1zIQCguRicU5hHZ2vhtFyHIQWaRPFu5YL7R3sZ4A7a20mQtbqfQ/0?wx_fmt=jpeg");
			map01.put("title","测试数据title01");
			map01.put("url","http://mp.weixin.qq.com/s?__biz=MzI5NzM4MTY2MA==&mid=100000011&idx=4&sn=782860b715d9eac9a5efd5aef733b35d&chksm=6cb4bb785bc3326e63b94a3e48f9d94b8b3593c1d60c6cf96652b523ba736179c58fe9c34cc1#rd");

			Map<String, Object> map02 = new HashMap<>();
			map02.put("thumb_url","http://mmbiz.qpic.cn/mmbiz_jpg/EIvvvz1ymI6mia3BDJc4w1JKx4iahBAsmgTLtb1zIQCguRicU5hHZ2vhtFyHIQWaRPFu5YL7R3sZ4A7a20mQtbqfQ/0?wx_fmt=jpeg");
			map02.put("title","测试数据title02");
			map02.put("url","http://mp.weixin.qq.com/s?__biz=MzI5NzM4MTY2MA==&mid=100000011&idx=4&sn=782860b715d9eac9a5efd5aef733b35d&chksm=6cb4bb785bc3326e63b94a3e48f9d94b8b3593c1d60c6cf96652b523ba736179c58fe9c34cc1#rd");
			list.add(map01);
			list.add(map02);
			ret.setData(list);


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
		String oauth2URL = "";
//		if(url==null || url.trim().equals("")){
//			url =  webConfig.getWebHttp()+OAuth2UriConstant.BASICINFO;
//		}
		try {
			oauth2URL = wechat.generateOAuth2URL(webConfig.getWebHttp()+"/oauth2", "snsapi_base", URLEncoder.encode(url.replace(webConfig.getWebHttp(), ""),"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("========into getOauth2Url=======");
		log.info("========into getOauth2Url=======");
		log.info("========into getOauth2Url=======");
		log.info(oauth2URL);
		log.info("========into getOauth2Url=======");
		log.info("========into getOauth2Url=======");
		log.info("========into getOauth2Url=======");
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
