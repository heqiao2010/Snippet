package test.digest;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author heqiao
 * 
 */
public class HTTPDigestClientDemo {

	private DefaultHttpClient httpClient = new DefaultHttpClient();

	@SuppressWarnings("deprecation")
	public HTTPDigestClientDemo(String userName, String passWord, HttpUriRequest request) {
		try {
			URI serverURI = request.getURI();
			Credentials creds = new UsernamePasswordCredentials(userName, passWord);
			httpClient.getCredentialsProvider().setCredentials(new AuthScope(serverURI.getHost(), serverURI.getPort()),
					(Credentials) creds);
			httpClient.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY,
					Collections.singleton(AuthPolicy.DIGEST));
			httpClient.getAuthSchemes().register(AuthPolicy.DIGEST, new DigestSchemeFactory());
		} catch (Exception e) {

		}
	}

	public HttpResponse send(HttpUriRequest httpUriRequest) {
		HttpResponse response = null;
		try {
			if (null == httpClient) {
				return response;
			}
			response = httpClient.execute(httpUriRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static void main(String args[]) throws ParseException, IOException {
		String postData = "{\"nas_id\": 355957,\"user_mac\": "
				+ "\"0C-D7-46-0D-98-E8\",\"session_timeout\": 123,"
				+ "\"identifier\": \"123\",\"redirect_uri\": \"https://10.168.168.1/\","
				+ "\"result_url\": \"https://www.baidu.com/\" "
				+ ",\"ssid\":\"Chimelong\"}";
		System.out.println("postData: " + postData);
		HttpPost req = new HttpPost("http://oasisauth.h3c.com/portal/authMac.auth");
		StringEntity se = new StringEntity(postData, "UTF-8");
		req.setHeader("Content-Type", "application/json");
		req.setHeader("Accept", "application/json");
		req.setEntity(se);
		HTTPDigestClientDemo client = new HTTPDigestClientDemo("security_super", "lvzhou1-super", req);
		HttpResponse rep = client.send(req);
		System.out.println("rep: " + EntityUtils.toString(rep.getEntity()));
	}
}
