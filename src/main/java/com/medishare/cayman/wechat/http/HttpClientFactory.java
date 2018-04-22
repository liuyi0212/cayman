package com.medishare.cayman.wechat.http;

import com.medishare.cayman.Application;
import com.medishare.cayman.wechat.conf.HttpClientConfiguration;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class HttpClientFactory implements Serializable {
	
    private static final long serialVersionUID = -5854084715172501672L;
    
    static Logger log = LoggerFactory.getLogger(HttpClientFactory.class);


    private static final Constructor<HttpClient> HTTP_CLIENT_CONSTRUCTOR;
    private static final String HTTP_CLIENT_IMPLEMENTATION = "wechat4j.http.httpClient";

    static {
        Class<?> clazz = null;
        String httpClientImpl = System.getProperty(HTTP_CLIENT_IMPLEMENTATION);
        if (httpClientImpl != null){
            try {
                clazz = Class.forName(httpClientImpl);
            } catch (ClassNotFoundException cnfe){
                throw new AssertionError(cnfe);
            }
        }

        if (null == clazz){
            try {
                clazz = Class.forName("com.medishare.cayman.wechat.http.HttpClientImpl");
            } catch (ClassNotFoundException cnfe){
                throw new AssertionError(cnfe);
            }
        }

        try {
            HTTP_CLIENT_CONSTRUCTOR = (Constructor<HttpClient>)clazz.getDeclaredConstructor(HttpClientConfiguration.class);
        } catch (NoSuchMethodException nsme){
            throw new AssertionError(nsme);
        }
    }

    public static HttpClient getInstance(HttpClientConfiguration conf){
        HttpClient client = null;
        try {
            client = HTTP_CLIENT_CONSTRUCTOR.newInstance(conf);
        } catch (InstantiationException ie){
            log.error(ie.getMessage());
        } catch (IllegalAccessException iae){
            log.error(iae.getMessage());
        } catch (InvocationTargetException ite){
            log.error(ite.getMessage());
        }

        if (client == null){
            client = NullHttpClient.getInstance();
        }
        return client;
    }
}
