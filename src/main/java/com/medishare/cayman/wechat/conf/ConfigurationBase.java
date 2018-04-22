package com.medishare.cayman.wechat.conf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class ConfigurationBase implements Configuration, Serializable {
    private static final long serialVersionUID = -6692513626308299468L;
    private boolean debugEnabled;

    private String loggerFactory;

    private String oauthAppId;
    
    private String oauthAppSecret;
    private String oauthAppCredential;

    private boolean gzipEnabled;

    private String httpProxyHost;
    private int httpProxyPort;
    private String httpProxyUser;
    private String httpProxyPassword;

    private int httpRetryCount;
    private int httpReadTimeout;
    private int httpConnectionTimeout;
    private int httpRetryIntervalSeconds;

    private Map<String, String> requestHeaders;
    
    private String restBaseURL;
    private String mpBaseURL;
    private String mediaBaseURL;
    private String oauth2CodeURL;

    @Override public final boolean isDebugEnabled() {
        return debugEnabled;
    }

    public final void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public final Map<String, String> getRequestHeaders(){
        return requestHeaders;
    }

    @Override public final String getLoggerFactory(){
        return loggerFactory;
    }

    public final void setLoggerFactory(String loggerFactory){
        this.loggerFactory = loggerFactory;
    }

    @Override public final String getOAuthAppId(){
        return oauthAppId;
    }

    public final void setOAuthAppId(String oauthAppId){
        this.oauthAppId = oauthAppId;
    }

    @Override public final String getOAuthAppSecret(){
        return oauthAppSecret;
    }

    public final void setOAuthAppSecret(String oauthAppSecret){
        this.oauthAppSecret = oauthAppSecret;
    }

    @Override public final String getOAuthAppCredential(){
        return oauthAppCredential;
    }

    public final void setOAuthAppCredential(String oauthAppCredential){
        this.oauthAppCredential = oauthAppCredential;
    }

    @Override public final boolean isGZIPEnabled(){
        return gzipEnabled;
    }

    public final void setGZIPEnabled(boolean gzipEnabled){
        this.gzipEnabled = gzipEnabled;
        initRequestHeaders();
    }

    private void initRequestHeaders(){
        requestHeaders = new HashMap<String, String>();
        if (isGZIPEnabled()){
            requestHeaders.put("Accept-Encoding", "gzip");
        }
    }

    @Override public final String getHttpProxyHost() {
        return httpProxyHost;
    }

    public final void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    @Override public final int getHttpProxyPort() {
        return httpProxyPort;
    }

    public final void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    @Override public final String getHttpProxyUser() {
        return httpProxyUser;
    }

    public final void setHttpProxyUser(String httpProxyUser) {
        this.httpProxyUser = httpProxyUser;
    }

    @Override public String getHttpProxyPassword() {
        return httpProxyPassword;
    }

    public final void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override public final int getHttpRetryCount(){
        return httpRetryCount;
    }

    public final void setHttpRetryCount(int httpRetryCount){
        this.httpRetryCount = httpRetryCount;
    }

    @Override public final int getHttpReadTimeout(){
        return httpReadTimeout;
    }

    public final void setHttpReadTimeout(int httpReadTimeout){
        this.httpReadTimeout = httpReadTimeout;
    }

    @Override public final int getHttpConnectionTimeout(){
        return httpConnectionTimeout;
    }

    public final void setHttpConnectionTimeout(int httpConnectionTimeout){
        this.httpConnectionTimeout = httpConnectionTimeout;
    }

    @Override public final int getHttpRetryIntervalSeconds(){
        return httpRetryIntervalSeconds;
    }

    public final void setHttpRetryIntervalSeconds(int httpRetryIntervalSeconds){
        this.httpRetryIntervalSeconds = httpRetryIntervalSeconds;
    }

    @Override public String getRestBaseURL() {
        return restBaseURL;
    }

    public final void setRestBaseURL(String restBaseURL) {
        this.restBaseURL = restBaseURL;
    }

    @Override public String getMPBaseURL() {
        return mpBaseURL;
    }

    public final void setMPBaseURL(String mpBaseURL) {
        this.mpBaseURL = mpBaseURL;
    }

    @Override public String getMediaBaseURL() {
        return mediaBaseURL;
    }

    public final void setMediaBaseURL(String mediaBaseURL) {
        this.mediaBaseURL = mediaBaseURL;
    }

    @Override public String getOAuth2CodeURL() {
        return oauth2CodeURL;
    }

    public final void setOAuth2CodeURL(String oauth2CodeURL) {
        this.oauth2CodeURL = oauth2CodeURL;
    }
}