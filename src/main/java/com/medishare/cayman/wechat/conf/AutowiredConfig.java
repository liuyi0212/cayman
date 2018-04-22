package com.medishare.cayman.wechat.conf;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AutowiredConfig {
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
	    
	    
		public String getOauthAppId() {
			return oauthAppId;
		}
		public String getOauthAppSecret() {
			return oauthAppSecret;
		}
		public boolean isGzipEnabled() {
			return gzipEnabled;
		}
		public int getHttpRetryCount() {
			return httpRetryCount;
		}
		public int getHttpReadTimeout() {
			return httpReadTimeout;
		}
		public int getHttpConnectionTimeout() {
			return httpConnectionTimeout;
		}
		public int getHttpRetryIntervalSeconds() {
			return httpRetryIntervalSeconds;
		}
		public String getRestBaseURL() {
			return restBaseURL;
		}
		public String getMpBaseURL() {
			return mpBaseURL;
		}
		public String getMediaBaseURL() {
			return mediaBaseURL;
		}
		public String getOauth2CodeURL() {
			return oauth2CodeURL;
		}
	    
}
