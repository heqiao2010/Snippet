package excel.test.hq;

import java.net.URI;
import java.util.Collections;

import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;

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

	public static void main(String args[]) {
		HttpUriRequest req = new HttpGet("https://lvzhoutest.h3c.com/o2oportal/authMac");
		HTTPDigestClientDemo client = new HTTPDigestClientDemo("security_super", "lvzhou1-super", req);
		req.setHeader("Content-Type", "application/json");
		req.setHeader("Accept", "application/json");
		HttpResponse rep = client.send(req);
		System.out.println("rep: " + rep);
	}
}
