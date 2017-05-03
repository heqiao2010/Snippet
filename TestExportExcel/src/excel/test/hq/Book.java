package excel.test.hq;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Book {
	   private int bookId;
	   private String name;
	   private String author;
	   private float price;
	   private String isbn;
	   private String pubName;
	   private byte[] preface;
	 
	   public Book() {
	      super();
	   }
	 
	   public Book(int bookId, String name, String author, float price,
	         String isbn, String pubName, byte[] preface) {
	      super();
	      this.bookId = bookId;
	      this.name = name;
	      this.author = author;
	      this.price = price;
	      this.isbn = isbn;
	      this.pubName = pubName;
	      this.preface = preface;
	   }
	 
	   public int getBookId() {
	      return bookId;
	   }
	 
	   public void setBookId(int bookId) {
	      this.bookId = bookId;
	   }
	 
	   public String getName() {
	      return name;
	   }
	 
	   public void setName(String name) {
	      this.name = name;
	   }
	 
	   public String getAuthor() {
	      return author;
	   }
	 
	   public void setAuthor(String author) {
	      this.author = author;
	   }
	 
	   public float getPrice() {
	      return price;
	   }
	 
	   public void setPrice(float price) {
	      this.price = price;
	   }
	 
	   public String getIsbn() {
	      return isbn;
	   }
	 
	   public void setIsbn(String isbn) {
	      this.isbn = isbn;
	   }
	 
	   public String getPubName() {
	      return pubName;
	   }
	 
	   public void setPubName(String pubName) {
	      this.pubName = pubName;
	   }
	 
	   public byte[] getPreface() {
	      return preface;
	   }
	 
	   public void setPreface(byte[] preface) {
	      this.preface = preface;
	   }
	   
	   private static String getRedirectUrl(String redirectURIHeader){
		   String protocolHeader = "http://";
			String uriTail = "";
			if (redirectURIHeader.startsWith("http://")) {
				protocolHeader = "http://";
				uriTail = redirectURIHeader.substring(7);
				uriTail = uriTail.substring(uriTail.indexOf("/"));
			} else if (redirectURIHeader.startsWith("https://")) {
				protocolHeader = "https://";
				uriTail = redirectURIHeader.substring(8);
				uriTail = uriTail.substring(uriTail.indexOf("/"));
			} else {
				System.out.println("Wrong redirectUri: " + redirectURIHeader);
				// 错误的重定向URI
				return null;
			}
			return protocolHeader + "cloud.h3c.com" + uriTail;
	   }
	   
	   /**
		 * 获取登录认证成功后跳转URI
		 * 
		 * @param redirectURI
		 * @param code
		 * @param userIp
		 *            为了支持本地转发，需要将userIp回传给设备端
		 * @param isWxWiFi
		 *            微信连WiFi需要增加auth_type字段
		 * @return
		 */
		public static String getLoginRedirectURI(String redirectURI, String code, String userIp, boolean isWxWiFi) {
			String urlHeader = "";
			try {
				urlHeader = URLDecoder.decode(redirectURI, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				urlHeader = redirectURI;
			}
			return urlHeader + "?code=" + code + "&userip=" + userIp.trim()
					+ (isWxWiFi ? "&auth_type=weixin" : "") + "&portal_server=" + "http://"
					+ "h3c.lvzhou.com" + ":" + 80 + "/portal/protocol";
		}
	   
	   public static void main(String args[]){
//				String devAuthRedirectURL = getLoginRedirectURI("www.baidu.com", "code234234234", "192.168.1.1", true);
//				System.out.println("before remove authType: " + devAuthRedirectURL);
//				String urlParts[] = devAuthRedirectURL.split("&");
//				StringBuilder urlBuilder = new StringBuilder();
//				for (String part : urlParts) {
//					if (null!=part && !part.startsWith("auth_type")) {
//						urlBuilder.append(part + "&");
//					}
//				}
//				if (urlBuilder.length() > 0) {
//					urlBuilder.setLength(urlBuilder.length() - 1); // remove &
//				}
//				System.out.println("after remove authType: " + urlBuilder.toString());
		   	Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -7);
			System.out.println(c.getTime());
		   
	   }
	}
