package com.medishare.cayman.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.medishare.cayman.wechat.api.Wechat;
import com.medishare.cayman.wechat.client.WechatFactory;

@Component
public class WebConfig {

	@Value("${httppath}")
	protected String webHttp;
	protected Logger log = LoggerFactory.getLogger(getClass());

	public  String getIPAddress(HttpServletRequest req) {
		String ip = req.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip)) {
			ip = req.getHeader("X-FORWARDED-FOR");
		}
		if (StringUtils.isBlank(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip)) {
			ip = req.getRemoteAddr();
		}
		if (!StringUtils.isBlank(ip)) {
			return ip;
		}
		return req.getRemoteAddr();
	}
	public String getWebHttp() {
		if (webHttp.endsWith("/")) {
			return webHttp.substring(0, webHttp.length() - 1);
		}
		return webHttp;
	}

	public void setWebHttp(String webHttp) {
		this.webHttp = webHttp;
	}

	public String getNotifyFullPath(String notifyPath) {
		return getWebHttp() + notifyPath;
	}
	public String getAuthorizationUrl(String notifyPath) {
		Wechat wechat = WechatFactory.getInstance();
		String oauth2URL = "";
		try {
			oauth2URL = wechat.generateOAuth2URL(this.webHttp+"/wx/oauth2", "snsapi_base", URLEncoder.encode(notifyPath,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("getAuthorizationUrl error",e);
		} catch (IOException e) {
			log.error("getAuthorizationUrl error",e);
		}
		return oauth2URL;
	}

}
