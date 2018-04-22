package com.medishare.cayman.wechat.conf;

import org.springframework.beans.factory.annotation.Autowired;

public final class ConfigurationContext {
	
    private static ConfigurationBase ROOT_CONFIGURATION;
	
	static{
		if(ROOT_CONFIGURATION==null){
			ROOT_CONFIGURATION = new ConfigurationBase();
		}
	}

    public static Configuration getInstance(){
        return ROOT_CONFIGURATION;
    }
}
