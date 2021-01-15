package com.artcweb.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class BaiduPushTest {
	
	public static void main(String[] args) {
		BaiduPushTest t = new BaiduPushTest();
		String postUrl ="https://dz.artcweb.com/admin/login.do";
		String domain ="https://dz.artcweb.com";
		String bdToken ="P0qP5zknvJwHw07n";
		t.postUrl(postUrl, domain, bdToken);
	}


    /**
     * 百度链接实时推送
     * @param PostUrl 推送的链接
     * @param domain  推送链接的域名
     * @param bdToken 百度主动推送链接准入密钥
     * @return
     */
    public String postUrl(String PostUrl,String domain, String bdToken){
        String linkSubmitUrl="http://data.zz.baidu.com/urls";
        String host="data.zz.baidu.com";
        linkSubmitUrl+="?site="+domain+"&token="+bdToken;
        String result= null;
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient client = httpClientBuilder.build();
        client = (CloseableHttpClient) wrapClient(client);
        HttpPost post = new HttpPost(linkSubmitUrl);
        //发送请求参数
        try
        {
            StringEntity s = new StringEntity(PostUrl,"utf-8");
            s.setContentType("application/json");
            post.setEntity(s);
            post.setHeader("Host", host);
            post.setHeader("User-Agent", "curl/7.12.1");
            post.setHeader("Content-Type", "text/plain");
            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            result= EntityUtils.toString(entity, "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    private static org.apache.http.client.HttpClient wrapClient(HttpClient client) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[] { "TLSv1" }, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;

        } catch (Exception ex) {
            return null;
        }
    }
}