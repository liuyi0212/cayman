package com.medishare.cayman.wechat.http;

import com.medishare.cayman.Application;
import com.medishare.cayman.wechat.conf.ConfigurationContext;
import com.medishare.cayman.wechat.conf.HttpClientConfiguration;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientImpl extends HttpClientBase implements HttpResponseCode, Serializable {
    private static final long serialVersionUID = 9146333614776061763L;

    static Logger logger = LoggerFactory.getLogger(HttpClientImpl.class);

    public HttpClientImpl(){
        super(ConfigurationContext.getInstance());
    }

    public HttpClientImpl(HttpClientConfiguration conf) {
        super(conf);
    }

    /**
     * @param request
     * @throws java.io.IOException
     * <a href="https://www.imququ.com/post/four-ways-to-post-data-in-http.html"></a>
     */
    @Override
    HttpResponse handleRequest(HttpRequest request) throws IOException {
        HttpResponse response = null;
        int retryCount = conf.getHttpRetryCount();
        for (int retriedConnt=0; retriedConnt<retryCount+1; retriedConnt++){
            int statusCode = -1;
            try {
                OutputStream os = null;
                HttpURLConnection con;
                try {
                    con = getConnection(request.getURL());
                    con.setDoInput(true);
                    setHeaders(request, con);
                    con.setRequestMethod(request.getMethod().name());
                    if (request.getMethod() == RequestMethod.POST){
                        if (HttpParameter.containsFile(request.getParameters())){
                            // file upload
                            String boundary = "----Wechat4J-upload" + System.currentTimeMillis();
                            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                            boundary = "--" + boundary;
                            con.setDoOutput(true);
                            DataOutputStream out = new DataOutputStream(con.getOutputStream());
                            for (HttpParameter param: request.getParameters()) {
                                if (param.isFile()) {
                                    out.writeBytes(boundary + "\r\n");
                                    out.writeBytes("Content-Disposition: form-data; name=\"" + param.getName() + "\"; filename=\"" + param.getFile().getName() + "\"\r\n");
                                    out.writeBytes("Content-Type: " + param.getContentType() + "\r\n\r\n");
                                    BufferedInputStream in = new BufferedInputStream(
                                            param.hasFileBody()? param.getFileBody(): new FileInputStream(param.getFile())
                                    );
                                    int length;
                                    byte[] buf = new byte[1024];
                                    while ((length=in.read(buf)) != -1) {
                                        out.write(buf, 0, length);
                                    };
                                    out.writeBytes("\r\n");
                                    in.close();
                                } else if (param.isJSON()) {
                                    out.writeBytes(boundary + "\r\n");
                                    out.writeBytes("Content-Disposition: form-data; name=\"" + param.getName() + "\"\r\n\r\n");
                                    byte[] bytes = null;
                                    if (param.isJSONObject()){
                                        bytes = param.getJSONObject().toString().getBytes("utf-8");
                                    } else if (param.isJSONArray()){
                                        bytes = param.getJsonArray().toString().getBytes("utf-8");
                                    }
                                    out.write(bytes);
                                    out.writeBytes("\r\n");
                                } else {
                                    out.writeBytes(boundary + "\r\n");
                                    out.writeBytes("Content-Disposition: form-data; name=\"" + param.getName() + "\"\r\n\r\n");
                                    out.writeBytes("Content-Type: text/plain; charset=UTF-8\r\n\r\n");
                                    logger.debug(param.getValue());
                                    out.write(param.getValue().getBytes("UTF-8"));
                                    out.writeBytes("\r\n");
                                }
                            }
                            out.writeBytes(boundary + "--\r\n");
                            os = con.getOutputStream();
                        } else if (HttpParameter.containsJSON(request.getParameters())){
                            // JSON
                            if (request.getParameters().length > 1){
                                throw new IOException("Invalid Http Parameters.");
                            }

                            con.setRequestProperty("Content-Type", "application/json");
                            con.setDoOutput(true);
                            os = con.getOutputStream();
                            for (HttpParameter param: request.getParameters()){
                                if (param.isJSON()){
                                    byte[] bytes = null;
                                    if (param.isJSONObject()){
                                        bytes = param.getJSONObject().toString().getBytes("utf-8");
                                    } else if (param.isJSONArray()){
                                        bytes = param.getJsonArray().toString().getBytes("utf-8");
                                    }
                                    os.write(bytes);
                                } else if (param.isValue()){
                                }
                            }
                        } else {
                            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            String postParams = HttpParameter.encodeParameters(request.getParameters());
                            logger.debug("Post Params: ", postParams);
                            byte[] bytes = postParams.getBytes("utf-8");
                            con.setRequestProperty("Content-Length", Integer.toString(bytes.length));
                            con.setDoOutput(true);
                            os = con.getOutputStream();
                            os.write(bytes);
                        }
                        os.flush();
                        os.close();
                    }

                    response = new HttpResponseImpl(con, conf);
                    statusCode = response.getStatusCode();
                    if (logger.isDebugEnabled()) {
                        logger.debug("Response: ");
                        Map<String, List<String>> responseHeaders = con.getHeaderFields();
                        for (String key : responseHeaders.keySet()) {
                            List<String> values = responseHeaders.get(key);
                            for (String value : values) {
                                if (key != null) {
                                    logger.debug(key + ": " + value);
                                } else {
                                    logger.debug(value);
                                }
                            }
                        }
                    }
                    if (statusCode<OK || (statusCode!=FOUND && statusCode>=MULTIPLE_CHOICES)){
                        if (statusCode==ENHANCE_YOUR_CLAIM
                                || statusCode==BAD_REQUEST
                                || statusCode<INTERNAL_SERVER_ERROR
                                || retriedConnt==conf.getHttpRetryCount()){
                            // throw new WechatException(message, response);
                            throw new IOException(response.getStatusCode() + ": " + response.asString());
                        }
                        // will retry if the status code is INTERNAL_SERVER_ERROR
                    } else {
                        break;
                    }
                } finally {
                    if (os != null){
                        try {
                            os.close();
                        } catch (IOException ioe){
                            // do noting
                        }
                    }
                }
            } catch (IOException ioe){
                throw ioe;
            }

            try {
                if (logger.isDebugEnabled() && response != null) {
                    response.asString();
                }
                logger.debug("Sleeping " + conf.getHttpRetryIntervalSeconds() + " seconds until the next retry.");
                Thread.sleep(conf.getHttpRetryIntervalSeconds());
            } catch (InterruptedException ite){
                // do noting
            }
        }
        return response;
    }

    private void setHeaders(HttpRequest request, HttpURLConnection con){
        if (logger.isDebugEnabled()) {
            logger.debug("Request: ");
            logger.debug(request.getMethod().name() + " ", request.getURL());
        }
        if (request.getRequestHeaders() != null){
            for (String key : request.getRequestHeaders().keySet()){
                con.addRequestProperty(key, request.getRequestHeaders().get(key));
                logger.debug(key + ": " + request.getRequestHeaders().get(key));
            }
        }
    }

    private boolean isProxyConfigured() {
        return conf.getHttpProxyHost()!=null && !conf.getHttpProxyHost().equals("");
    }

    private HttpURLConnection getConnection(String url) throws IOException{
        HttpURLConnection con;
        if (isProxyConfigured()) {
            if (conf.getHttpProxyUser()!=null && !conf.getHttpProxyUser().equals("")) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Proxy AuthUser: " + conf.getHttpProxyUser());
                    logger.debug("Proxy AuthPassword: " + conf.getHttpProxyPassword());
                }
                Authenticator.setDefault(
                        new Authenticator() {
                            @Override protected PasswordAuthentication getPasswordAuthentication() {
                                if (getRequestorType().equals(RequestorType.PROXY)) {
                                    return new PasswordAuthentication(
                                            conf.getHttpProxyUser(),
                                            conf.getHttpProxyPassword().toCharArray()
                                    );
                                } else {
                                    return null;
                                }
                            }
                        }
                );
            }
            final Proxy proxy = new Proxy(
                    Proxy.Type.HTTP,
                    InetSocketAddress.createUnresolved(conf.getHttpProxyHost(), conf.getHttpProxyPort())
            );
            if (logger.isDebugEnabled()) {
                logger.debug("Opening proxied connection(" + conf.getHttpProxyHost() + ":" + conf.getHttpProxyPort() + ")");
            }
            con = (HttpURLConnection)new URL(url).openConnection(proxy);
        } else {
            con = (HttpURLConnection)new URL(url).openConnection();
        }
        if (conf.getHttpConnectionTimeout() > 0){
            con.setConnectTimeout(conf.getHttpConnectionTimeout());
        }
        if (conf.getHttpReadTimeout() > 0){
            con.setReadTimeout(conf.getHttpReadTimeout());
        }
        con.setInstanceFollowRedirects(false);
        return con;
    }
}