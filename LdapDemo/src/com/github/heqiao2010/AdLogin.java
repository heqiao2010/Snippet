package com.github.heqiao2010;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class AdLogin {
	public static void main(String[] args) {
		String userName = "h12111";// AD域认证，用户的登录UserName
		String password = "He13789032171+";// AD域认证，用户的登录PassWord
		String host = "h3c.huawei-3com.com";// AD域IP，必须填写正确
		String domain = "@h3c.huawei-3com.com";// 域名后缀，例.@noker.cn.com
		String port = "389"; // 端口，一般默认389
		String url = new String("ldap://" + host + ":" + port);// 固定写法
		String user = userName.indexOf(domain) > 0 ? userName : userName + domain;// 网上有别的方法，但是在我这儿都不好使，建议这么使用
		Hashtable<String, String> env = new Hashtable<String, String>();// 实例化一个Env
		DirContext ctx = null;
		env.put(Context.SECURITY_AUTHENTICATION, "simple");// LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
		env.put(Context.SECURITY_PRINCIPAL, user); // 用户名
		env.put(Context.SECURITY_CREDENTIALS, password);// 密码
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
		env.put(Context.PROVIDER_URL, url);// Url
		try {
			ctx = new InitialDirContext(env);// 初始化上下文
//			ctx.lookup(userName);
			System.out.println("身份验证成功!");
		} catch (AuthenticationException e) {
			System.out.println("身份验证失败!");
			e.printStackTrace();
		} catch (javax.naming.CommunicationException e) {
			System.out.println("AD域连接失败!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("身份验证未知异常!");
			e.printStackTrace();
		} finally {
			if (null != ctx) {
				try {
					ctx.close();
					ctx = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
