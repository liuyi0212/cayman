package com.medishare.cayman.wechat.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medishare.cayman.wechat.api.Wechat;
import com.medishare.cayman.wechat.conf.Configuration;
import com.medishare.cayman.wechat.conf.ConfigurationContext;

public class WechatFactory {
	
	static Logger log = LoggerFactory.getLogger(WechatFactory.class);
	
    private static Wechat wechat = null;
    
    
    static{
    	init();
    }
    
    private static void init(){
    	Configuration  conf = ConfigurationContext.getInstance();
    	wechat = new WechatImpl(conf);
    	try {
			wechat.getAccessToken();
		} catch (IOException e) {
			log.error("get wechat access_token  error!");
		}
    }

    public static Wechat getInstance() {
    	if(wechat == null){
    		init();
    	}
    	return wechat;
    }

    
}

