package com.medishare.cayman.wechat.entity;

import com.medishare.cayman.wechat.http.HttpResponse;

import java.io.File;
import java.io.IOException;

public class ObjectFactory {
    public Response createResponse(HttpResponse response) throws IOException {
        return new ResponseJSONImpl(response);
    }

    public Response createResponse(HttpResponse response, File file) throws IOException {
        String contentType = response.getResponseHeader("content-type");
        if (!contentType.equals("text/plain")) {
            file = response.asFile(file);
            return new ResponseJSONImpl(0, "ok");
        } else {
            return new ResponseJSONImpl(response);
        }
    }
    
    public ResponseBytes createBytesResponse(HttpResponse response) throws IOException {
        String contentType = response.getResponseHeader("content-type");
        if (contentType.contains("image")) {
        	byte [] bytes = response.getBytes();
        	ResponseBytes ret = new ResponseBytesJSONImpl(0, "ok", bytes , contentType.substring(contentType.lastIndexOf("/")+1));
            return ret;
        } else {
            return new ResponseBytesJSONImpl(response);
        }
    }

    public AccessToken createAccessToken(String credential, Long expiresIn) {
        return new AccessTokenJSONImpl(credential, expiresIn);
    }

    public ResponseAccessToken createResponseAccessToken(HttpResponse response) {
        return new ResponseAccessTokenJSONImpl(response);
    }

    public ResponseCallbackIP createResponseCallbackIP(HttpResponse response) {
        return new ResponseCallbackIPJSONImpl(response);
    }

    public ResponseMedia createResponseMedia(HttpResponse response) {
        return new ResponseMediaJSONImpl(response);
    }

    public ResponseGroup createResponseGroup(HttpResponse response) {
        return new ResponseGroupJSONImpl(response);
    }

    public ResponseGroupCollection createResponseGroupCollection(HttpResponse response) {
        return new ResponseGroupCollectionJSONImpl(response);
    }

    public ResponseKFAccountCollection createResponseKFAccountCollection(HttpResponse response) {
        return new ResponseKFAccountCollectionJSONImpl(response);
    }

    public ResponseUploadNews createResponseUploadNews(HttpResponse response) {
        return new ResponseUploadNewsJSONImpl(response);
    }

    public ResponseMessage createResponseMessage(HttpResponse response) {
        return new ResponseMessageJSONImpl(response);
    }

    public ResponseTemplate createResponseTemplate(HttpResponse response) {
        return new ResponseTemplateJSONImpl(response);
    }

    public ResponseUser createResponseUser(HttpResponse response) {
        return new ResponseUserJSONImpl(response);
    }

    public ResponseUserCollection createResponseUserCollection(HttpResponse response) {
        return new ResponseUserCollectionJSONImpl(response);
    }

    public ResponseOAuth2AccessToken createResponseOAuth2AccessToken(HttpResponse response) {
        return new ResponseOAuth2AccessTokenJSONImpl(response);
    }

    public ResponseMenu createResponseMenu(HttpResponse response) {
        return new ResponseMenuJSONImpl(response);
    }

    public ResponseTicket createResponseTicket(HttpResponse response) {
        return new ResponseTicketJSONImpl(response);
    }

    public void createQRCode(HttpResponse response, File file) throws IOException{
        int statusCode = response.getStatusCode();
        if (statusCode == 200) {
            response.asFile(file);
        }
    }
    
    public byte[] createBytesQRCode(HttpResponse response) throws IOException{
        int statusCode = response.getStatusCode();
        if (statusCode == 200) {
           return response.getBytes();
        }
        return null;
    }

    public ResponseShortURL createResponseShortURL(HttpResponse response) {
        return new ResponseShortURLJSONImpl(response);
    }
}
