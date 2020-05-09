package com.github.heqiao2010.snippet.digest;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/**
 * @author heqiao
 * 
 */
public class HTTPDigestClientDemo {


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
		HttpResponse rep = HttpDigestUtils.send("XXX", "XXX", req);
		System.out.println("rep: " + EntityUtils.toString(rep.getEntity()));
	}
}
