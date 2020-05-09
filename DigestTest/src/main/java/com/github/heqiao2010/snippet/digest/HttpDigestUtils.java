package com.github.heqiao2010.snippet.digest;

import java.net.URI;
import java.util.Collections;

import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 * http digest工具类
 * @author heqiao
 *
 */
public class HttpDigestUtils {
	/**
	 * http client
	 */
	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	
	/**
	 * 发送摘要认证请求
	 */
	@SuppressWarnings("deprecation")
	public static HttpResponse send(String username, String password, HttpUriRequest request){
		HttpResponse response = null;
		try {
			URI serverURI = request.getURI();
			Credentials creds = new UsernamePasswordCredentials(username, password);
			httpClient.getCredentialsProvider().setCredentials(new AuthScope(serverURI.getHost(), serverURI.getPort()),
					(Credentials) creds);
			httpClient.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY,
					Collections.singleton(AuthPolicy.DIGEST));
			httpClient.getAuthSchemes().register(AuthPolicy.DIGEST, new DigestSchemeFactory());
			response = httpClient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
