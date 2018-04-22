package com.medishare.cayman.wechat.http;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public final class HttpRequest implements Serializable {
    private static final long serialVersionUID = 1709770527970094462L;

    private final RequestMethod method;
    private final String url;
    private final HttpParameter[] parameters;
    private final Map<String, String> requestHeaders;

    private static final HttpParameter[] NULL_PARAMETERS = new HttpParameter[]{};

    /**
     * @param method         Specifies the HTTP method
     * @param url            the request to request
     * @param parameters     parameters
     * @param requestHeaders request headers
     */
    public HttpRequest(RequestMethod method, String url, HttpParameter[] parameters, Map<String, String> requestHeaders) {
        this.method = method;
        if (method!=RequestMethod.POST && parameters!=null && parameters.length != 0) {
            if (url.contains("?")) {
                this.url = url + "&" + HttpParameter.encodeParameters(parameters);
            } else {
                this.url = url + "?" + HttpParameter.encodeParameters(parameters);
            }
            this.parameters = NULL_PARAMETERS;
        } else {
            this.url = url;
            this.parameters = parameters;
        }
        this.requestHeaders = requestHeaders;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getURL(){
        return url;
    }

    public HttpParameter[] getParameters(){
        return parameters;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    @Override public boolean equals(Object o){
        if (o == this) return true;
        if (o==null || o.getClass()!=this.getClass()) return false;

        HttpRequest that = (HttpRequest)o;
        if (method!=null? !method.equals(that.method): that.method!=null)
            return false;
        if (url!=null? !url.equals(that.url): that.url!=null)
            return false;
        if (!Arrays.equals(parameters, that.parameters)) return false;
        if (requestHeaders!=null? !requestHeaders.equals(that.requestHeaders): that.requestHeaders!=null)
            return false;

        return true;
    }

    @Override public int hashCode(){
        int result = 0;
        result = result*31 + (method!=null? method.hashCode(): 0);
        result = result*31 + (url!=null? url.hashCode(): 0);
        result = result*31 + (parameters!=null? Arrays.hashCode(parameters): 0);
        result = result*31 + (requestHeaders!=null? requestHeaders.hashCode(): 0);
        return result;
    }

    @Override public String toString(){
        return "HttpRequest"
                + "{"
                + "requestMethod=" + method
                + ", url='" + url + '\''
                + ", postParams=" + (parameters == null ? null : Arrays.asList(parameters))
                + ", requestHeaders=" + requestHeaders
                + '}';
    }
}