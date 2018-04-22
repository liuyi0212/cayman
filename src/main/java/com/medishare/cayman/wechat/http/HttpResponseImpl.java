package com.medishare.cayman.wechat.http;

import com.medishare.cayman.wechat.conf.HttpClientConfiguration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpResponseImpl extends HttpResponse {
    private HttpURLConnection con;

    public HttpResponseImpl(HttpURLConnection con, HttpClientConfiguration conf) throws IOException {
        super(conf);
        this.con = con;

        this.statusCode = con.getResponseCode();

        if (con.getErrorStream()==null
                && this.statusCode==HttpResponseCode.OK){
            this.is = con.getInputStream();  // @TODO
        } else {
            this.responseAsString = con.getResponseMessage();
        }

        if (is!=null && "gzip".equals(con.getContentType())){
            is = new GZIPInputStream(is);
        }
    }

    public HttpResponseImpl(String content){
        super();
        this.responseAsString = content;
    }

    @Override public String getResponseHeader(String name) {
        return con.getHeaderField(name);
    }

    @Override public Map<String, List<String>> getResponseHeaderFields() {
        return con.getHeaderFields();
    }

    @Override public void disconnect(){
        con.disconnect();
    }
}
