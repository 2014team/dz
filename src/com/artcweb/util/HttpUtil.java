package com.artcweb.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.artcweb.generator.util.Tools;

/**
 * @ClassName: HttpUtil
 * @Description: HttpUtil工具类
 */
public class HttpUtil {
	public static boolean proxySet = false;
	public static String proxyHost = "127.0.0.1";
	public static int proxyPort = 80;

	
	public static String httpGet(String url, String encode, Map<String, String> headers) {
		if (encode == null) {
			encode = "utf-8";
		}
		String content = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
 
		// 设置 header
		Header headerss[] = buildHeader(headers);
		if (headerss != null && headerss.length > 0) {
			httpGet.setHeaders(headerss);
		}
		HttpResponse http_response;
		try {
			http_response = httpclient.execute(httpGet);
			HttpEntity entity = http_response.getEntity();
			content = EntityUtils.toString(entity, encode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//断开连接
//			httpGet.releaseConnection();
		}
		return content;
	}
	
	/**
	 * 组装请求头
	 * 
	 * @param params
	 * @return
	 */
	public static Header[] buildHeader(Map<String, String> params) {
		Header[] headers = null;
		if (params != null && params.size() > 0) {
			headers = new BasicHeader[params.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : params.entrySet()) {
				headers[i] = new BasicHeader(entry.getKey(), entry.getValue());
				i++;
			}
		}
		return headers;
	}
 
	
	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param url
	 * @return
	 */
	public static String httpRequest(String reqUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
			e.getStackTrace();
			LogUtil.logError(e.getMessage());
		}
		return buffer.toString();
	}

	/**
	 * 发送http请求取得返回的输入流
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @return InputStream
	 */
	public static InputStream httpRequestIO(String requestUrl) {
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			// 获得返回的输入流
			inputStream = httpUrlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				LogUtil.logInfo(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, Map<String, String> params) {
		// 作为StringBuffer初始化的字符串
		String encode = "utf-8";
		StringBuffer buffer = new StringBuffer();
		try {
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					// 完成转码操作
					String value = null == entry.getValue() ? "" : entry.getValue();
					buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(value, encode)).append("&");
				}
				// 删除掉最有一个&
				buffer.deleteCharAt(buffer.length() - 1);
			}

			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setConnectTimeout(3000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);// 表示从服务器获取数据
			urlConnection.setDoOutput(true);// 表示向服务器写数据
			// 获得上传信息的字节大小以及长度
			byte[] mydata = buffer.toString().getBytes();
			// 表示设置请求体的类型是文本类型
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
			// 获得输出流,向服务器输出数据
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(mydata, 0, mydata.length);
			outputStream.close();
			// 获得服务器响应的结果和状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				return changeInputStream(urlConnection.getInputStream(), encode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param isproxy
	 *            是否使用代理模式
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, boolean isproxy) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = null;
			if (isproxy) {// 使用代理模式
				@SuppressWarnings("static-access")
				Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
				conn = (HttpURLConnection) realUrl.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) realUrl.openConnection();
			}
			// 打开和URL之间的连接

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST"); // POST方法

			// 设置通用的请求属性

			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			conn.connect();

			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @Title: changeInputStream
	 * @Description: 将一个输入流转换成指定编码的字符串
	 * @param inputStream
	 * @param encode
	 * @return
	 */
	private static String changeInputStream(InputStream inputStream, String encode) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @Title: getSortRequestParams
	 * @Description: 参数排序
	 * @param request
	 * @return
	 */
	public static SortedMap<String, Object> getSortRequestParams(HttpServletRequest request) {

		SortedMap<String, Object> map = new TreeMap<String, Object>();

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				// if (paramValue.length() != 0) {
				// map.put(paramName, paramValue);
				// }
				map.put(paramName, paramValue);
			}
		}
		return map;
	}

	/**
	 * @Title: urlEncode
	 * @Description: 编码
	 * @param source
	 * @param encode
	 * @return
	 */
	public static String urlEncode(String source, String encode) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "0";
		}
		return result;
	}

	/**
	 * @Title: urlEncodeGBK
	 * @Description: 解码
	 * @param source
	 * @return
	 */
	public static String urlEncodeGBK(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "0";
		}
		return result;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		// 直接获取跟本服务器连接的客户端ip就好了，这里不用管它是不是代理的

		// 如果所有请求都经过我们自己的nginx才进入到我们后端服务器，才需要通过获取代理列表"X-Forwarded-For"获取到真正的客户端ip。
		// 即使我们用了nginx，如果有人故意构造了X-Forwarded-For请求头，也是无法获取到用户的真正ip的，其实我们只要获取跟nginx通信的那个客户端ip就可以了，没必要获取坏人的那个真正ip
		// 获取客户端ip可以参考：https://imququ.com/post/x-forwarded-for-header-in-http.html
		//return request.getRemoteAddr();// 这个方法并不能确保是用户真正的ip，在现在我们没用nginx的情况下，直接获取跟我们服务器通信的客户端ip就好了

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	public static JSONObject getRequestParams(HttpServletRequest request) {

		JSONObject paramJson = new JSONObject();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					paramJson.put(paramName, paramValue);
				}
			}
		}
		return paramJson;
	}

	public static SortedMap<String, Object> getRequestParams2(HttpServletRequest request) {

		SortedMap<String, Object> map = new TreeMap<String, Object>();

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
//				if (paramValue.length() != 0) {
//					map.put(paramName, paramValue);
//				}
				map.put(paramName, paramValue);
			}
		}
		return map;
	}
	
	public static SortedMap<String, Object> getRequestParams3(HttpServletRequest request) {
		
		SortedMap<String, Object> map = new TreeMap<String, Object>();
		
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			String paramValue = "";
			if(null != paramValues && paramValues.length > 0){
				if(null != Arrays.asList(paramValues) && Arrays.asList(paramValues).size() > 0){
					paramValue = Arrays.asList(paramValues).toString();
				}
			}
			map.put(paramName, paramValue);
		}
		return map;
	}
	
	
	/**
	* @Title: sendFile
	* @Description: 发送到图片服务器
	* @param tmpFile
	* @return
	*/
	public static String sendFile(MultipartFile tmpFile) {
		String resJson = "";
		try {
			//是否存在这个临时目录,不存在就创建
			File fileTemp = new File(Tools.getWebRoot() + "/tempUpload");
			if(!fileTemp.exists()){
				fileTemp.mkdir();
			}
			File file = new File(Tools.getWebRoot() + "/tempUpload" + File.separator + tmpFile.getOriginalFilename());
			tmpFile.transferTo(file);
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			String server = PropertiesUtils.getValue("aliyun_vod_server");
			
			HttpPost httppost = new HttpPost(server);

			MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
			mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			mEntityBuilder.setCharset(Charset.forName("UTF-8"));
			mEntityBuilder.addBinaryBody("file", file);

			HttpEntity reqEntity = mEntityBuilder.build();

			httppost.setEntity(reqEntity);

			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			if (resEntity == null) {
				return resJson;
			}
			resJson = EntityUtils.toString(resEntity, "UTF-8");
			EntityUtils.consume(resEntity);
			response.close();
			httpclient.close();
			// 删除临时文件
			file.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resJson;
	}


}
