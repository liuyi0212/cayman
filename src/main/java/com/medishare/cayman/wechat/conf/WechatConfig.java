package com.medishare.cayman.wechat.conf;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.medishare.cayman.Application;
import com.medishare.cayman.wechat.conf.Configuration;
import com.medishare.cayman.wechat.conf.ConfigurationBase;
import com.medishare.cayman.wechat.conf.ConfigurationContext;

@Component
public class WechatConfig {
	
		static Logger log = LoggerFactory.getLogger(WechatConfig.class);
		
		@Value("${oauth.appId}")
	    protected String oauthAppId;
	    @Value("${oauth.appSecret}")
	    protected String oauthAppSecret;
	    @Value("${http.gzip}")
	    protected boolean gzipEnabled;
	    @Value("${http.retryCount}")
	    protected int httpRetryCount;
	    @Value("${http.readTimeout}")
	    protected int httpReadTimeout;
	    @Value("${http.connectionTimeout}")
	    protected int httpConnectionTimeout;
	    @Value("${http.retryIntervalMillis}")
	    protected int httpRetryIntervalSeconds;
	    @Value("${restBaseURL}")
	    protected String restBaseURL;
	    @Value("${mpBaseURL}")
	    protected String mpBaseURL;
	    @Value("${mediaBaseURL}")
	    protected String mediaBaseURL;
	    @Value("${oauth2CodeURL}")
	    protected String oauth2CodeURL;
	    
	    @PostConstruct
		void notes() {
	    	ConfigurationBase config = (ConfigurationBase) ConfigurationContext.getInstance();
	    	config.setOAuthAppId(oauthAppId);
	    	config.setOAuthAppSecret(oauthAppSecret);
	    	config.setGZIPEnabled(gzipEnabled);
	    	config.setHttpRetryCount(httpRetryCount);
	    	config.setHttpReadTimeout(httpReadTimeout);
	    	config.setHttpConnectionTimeout(httpConnectionTimeout);
	    	config.setHttpRetryIntervalSeconds(httpRetryIntervalSeconds);
	    	config.setRestBaseURL(restBaseURL);
	    	config.setMPBaseURL(mpBaseURL);
	    	config.setMediaBaseURL(mediaBaseURL);
	    	config.setOAuth2CodeURL(oauth2CodeURL);
	    	log.info("wechat config end");
		}
	    
}
