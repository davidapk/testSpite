package com.guagua.test;

import java.util.ResourceBundle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpParams;

public class ZHttpClient extends HttpClient {
 
    public static final ResourceBundle props = ResourceBundle
            .getBundle("request");
 
    public ZHttpClient() {
        this.setConnectionTimeout(Integer.parseInt(props
                .getString("request.connTimeout")));
        this.setSoTimeout(Integer.parseInt(props.getString("request.soTimeout")));
        HttpParams params = this.getParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                HttpVersion.HTTP_1_1);
        params.setParameter(CoreProtocolPNames.USER_AGENT,
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
 
        this.setHttpRequestRetryHandler(new ZRetryHander());
    }
 
    public void setConnectionTimeout(int connectionTimeout) {
        HttpConnectionParams.setConnectionTimeout(this.getParams(),
                connectionTimeout);
    }
 
    public void setSoTimeout(int soTimeout) {
        HttpConnectionParams.setSoTimeout(this.getParams(), soTimeout);
    }
 
}