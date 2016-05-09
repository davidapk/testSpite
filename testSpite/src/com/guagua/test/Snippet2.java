package com.guagua.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONObject;
import com.guagua.backstage.util.HTTPUtil;

public class Snippet2 {
	public static class RetrivePage {
		private static HttpClient httpClient = new HttpClient();// 设置代理服务器

		public static boolean downloadPage(String path) throws HttpException, IOException {
			
			try{
		        String result = HTTPUtil.get(getUrl + id);
		        JSONObject json = JSONObject.parseObject(result);
		        if (json.getIntValue("retcode")==0) {
		            data.setResultState(true);
		            data.setResultMsg("成功");
		        }
			}catch (Exception e) {
				e.printStackTrace();
				data.setResultState(false);
				data.setResultMsg("调用同步借口失败 ,需要手工同步借口>" + e.getMessage());
			}
			
			InputStream input = null;
			OutputStream output = null;// 得到post方法
			PostMethod postMethod = new PostMethod(path);// 设置post方法的参数
			NameValuePair[] postData = new NameValuePair[6];
			postData[0] = new NameValuePair("neednum", "20");
			postData[1] = new NameValuePair("pageno", "0");
			postData[2] = new NameValuePair("openid", "C09A358A8212862CB5CC15F49B64D8B0");
			postData[3] = new NameValuePair("openkey", "");
			postData[4] = new NameValuePair("rank", "1");
			postData[5] = new NameValuePair("_", "1441001952838");
			postMethod.addParameters(postData);
			// 执行，返回状态码
			int statusCode = httpClient.executeMethod(postMethod);
			// 针对状态码进行处理(简单起见，只处理返回值为200的状态码)
			if (statusCode == HttpStatus.SC_OK) {
				input = postMethod.getResponseBodyAsStream();
				// 得到文件名
				String filename = "D:/test.txt";
				// 获得文件输出流
				output = new FileOutputStream(filename);
				// 输出到文件
				int tempByte = 0;
				while ((tempByte = input.read()) > 0) {
					output.write(tempByte);
				}

				// 关闭输入输出流
				if (input != null) {
					input.close();
				}

				if (output != null) {
					output.close();
				}
				return true;
			}
			return false;
		}

		/**
		 * 测试代码
		 */
		public static void main(String[] args) {
			// 抓取lietu首页，输出
			try {
				RetrivePage.downloadPage("http://cgi.tiantian.qq.com/tiantian/get_rcmd_anchor_info");
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
