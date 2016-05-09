package com.guagua.test;

import java.io.IOException;
import java.util.logging.Logger;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;
import org.omg.CORBA.Request;

public class ZRetryHander implements HttpRequestRetryHandler {
 
    private static final Logger logger = Logger.getLogger(ZRetryHander.class);
    private int retryTimeout = Integer.parseInt(ZHttpClient.props
            .getString("retry.timeout"));
    private int maxRetryTimes = Integer.parseInt(ZHttpClient.props
            .getString("retry.maxTimes"));
 
    public int getRetryTimeout() {
        return retryTimeout;
    }
 
    public void setRetryTimeout(int retryTimeout) {
        this.retryTimeout = retryTimeout;
    }
 
    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }
 
    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }
 
	@Override
    public boolean retryRequest(IOException exception, int executionCount,
            HttpContext context) {
        String url = Singleton.self().get(Request.class).getCurrentTarget();
        try {
            logger.warn("连接超时，重试等待中，url=" + url);
            Thread.sleep(this.getRetryTimeout());
        } catch (InterruptedException e) {
            logger.error("连接超时等待中出错", e);
        }
        logger.warn("重试次数=" + executionCount + "，url=" + url);
        if (executionCount >= this.getMaxRetryTimes()) {
            // Do not retry if over max retry count
            return false;
        }
        if (exception instanceof NoHttpResponseException) {
            // Retry if the server dropped connection on us
            return true;
        }
        if (exception instanceof SSLHandshakeException) {
            // Do not retry on SSL handshake exception
            return false;
        }
        HttpRequest request = (HttpRequest) context
                .getAttribute(ExecutionContext.HTTP_REQUEST);
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
        if (idempotent) {
            // Retry if the request is considered idempotent
            return true;
        }
        return false;
 
    }

	@Override
	public boolean retryRequest(IOException arg0, int arg1, HttpContext arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retryRequest(IOException arg0, int arg1, HttpContext arg2) {
		// TODO Auto-generated method stub
		return false;
	}
}