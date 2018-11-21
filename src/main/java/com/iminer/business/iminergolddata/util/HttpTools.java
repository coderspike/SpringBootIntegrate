package com.iminer.business.iminergolddata.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpTools {

	private static Logger LOG = LoggerFactory.getLogger(HttpTools.class);

	public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		LOG.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		LOG.info("create httppost:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String get(String url, String code) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		// LOG.info("create httppost:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get, code);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
//		System.out.println("发送短信后，短信平台返回的Http状态" + response.getStatusLine().getStatusCode());
//		Header[] headers = response.getAllHeaders();
//		for(Header header : headers) {
//			System.out.println("发送短信后，短信平台返回的header:" + header.getName() + "-->value:" + header.getValue());
//		}

		String body = paseResponse(response);
//		System.out.println("发送短信后，短信平台返回的body：" + body);
		return body;
	}

	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost, String code) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response, code);

		return body;
	}

	private static String paseResponse(HttpResponse response, String code) {
		// LOG.info("get response from http server..");

		String body = null;
		try {
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, code);
			// LOG.info(body);
		}
		catch(ParseException e) {
			// e.printStackTrace();
		}
		catch(IOException e) {
			// e.printStackTrace();
		}
		catch(Exception e) {
			// e.printStackTrace();
		}

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		// LOG.info("get response from http server..");
		HttpEntity entity = response.getEntity();
		String body = null;
		try {
			body = EntityUtils.toString(entity);
			LOG.info(body);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	/**
	 * 返回
	 * 
	 * @param httpclient
	 * @param httpost
	 * @return
	 */
	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
		LOG.info("execute post...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		}
		catch(ClientProtocolException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 处理参数并将参数封装成httpPost
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		if(params != null && params.size() > 0) {
			Set<String> keySet = params.keySet();
			for(String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			try {
				LOG.info("set utf-8 form entity to httppost");
				httpost.setEntity(new UrlEncodedFormEntity(nvps));
			}
			catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}

		return httpost;
	}

	
	private static Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
	/**
	 * 获取一级域名
	 * 
	 * @param url
	 * @return
	 */
	public static String GetDomainName(String url) {
		if(url == null) {
			return null;
		}
		Matcher m = p.matcher(url);
		if(m.find()) {
			// System.out.println(m.group());
			return m.group();

		}
		return null;
	}

	//
	// public static String getArticleInfo(String articleId,String url ,String code){
	// int hashCode=Math.abs(articleId.hashCode());
	// int serverNum=SystemConfiguration.URL_ARTICLE_INFO_LIST.length;
	// int chooseNum=hashCode%serverNum;
	// String myUrl=url.replace("files.iguiquan.cn",SystemConfiguration.URL_ARTICLE_INFO_LIST[chooseNum]);
	// String body=get(myUrl,code);
	// if(body!=null||"".equals(body)){
	// for(int i=0;i<serverNum;i++){
	// if(i!=chooseNum){
	// myUrl=url.replace("files.iguiquan.cn",SystemConfiguration.URL_ARTICLE_INFO_LIST[i]);
	// body=get(myUrl,code);
	// System.out.println(body+"+++");
	// if(body!=null&&!"".equals(body)&&body.indexOf("{\"content\":\"<header>")==0){
	// break;
	// }
	// }
	// }
	// }
	// return body;
	// }
	public static String getArticleInfo(String articleId, String url, String code) {
		String body = get(url, code);
		return body;
	}

	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if(ip.length() > 15) {
			String[] ips = ip.split(",");
			for(int index = 0; index < ips.length; index++) {
				String strIp = (String)ips[index];
				if(!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * enableSSL:https认证
	 * @param httpclient
	 * @param serverPort
	 * @return
	 */
	public static HttpClient enableSSL(HttpClient httpclient, Integer serverPort) {
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
		    @Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		    @Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
		    @Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
		}};
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, null);
			SSLSocketFactory sf = new SSLSocketFactory(sc, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpclient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", serverPort, sf));
			httpclient = new DefaultHttpClient(ccm, httpclient.getParams());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return httpclient;
	}

}
