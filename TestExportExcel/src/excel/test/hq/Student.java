package excel.test.hq;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Student {
	 
	   private long id;
	   private String name;
	   private int age;
	   private boolean sex;
	   private Date birthday;
	 
	   public Student() {
	      super();
	      // TODO Auto-generated constructor stub
	   }
	 
	   public Student(long id, String name, int age, boolean sex, Date birthday) {
	      super();
	      this.id = id;
	      this.name = name;
	      this.age = age;
	      this.sex = sex;
	      this.birthday = birthday;
	   }
	 
	   public long getId() {
	      return id;
	   }
	 
	   public void setId(long id) {
	      this.id = id;
	   }
	 
	   public String getName() {
	      return name;
	   }
	 
	   public void setName(String name) {
	      this.name = name;
	   }
	 
	   public int getAge() {
	      return age;
	   }
	 
	   public void setAge(int age) {
	      this.age = age;
	   }
	 
	   public boolean getSex() {
	      return sex;
	   }
	 
	   public void setSex(boolean sex) {
	      this.sex = sex;
	   }
	 
	   public Date getBirthday() {
	      return birthday;
	   }
	 
	   public void setBirthday(Date birthday) {
	      this.birthday = birthday;
	   }
	   
	   private static String calculateSign(String src) throws NoSuchAlgorithmException {
	        // MD5Ç©ÃûÉú³É
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(src.getBytes());
	        byte[] digest = md.digest();
	        StringBuffer hexstr = new StringBuffer();
	        String shaHex = "";
	        for (int i = 0; i < digest.length; i++) {
	            shaHex = Integer.toHexString(digest[i] & 0xFF);
	            if (shaHex.length() < 2) {
	                hexstr.append(0);
	            }
	            hexstr.append(shaHex);
	        }
	        return hexstr.toString();
	    }
	   
	   public static void main(String [] args){
//		   Date date=new Date();
//		   DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
//		   String time=format.format(date);
//		   time += "-" + date.getTime();
//		   System.out.println(time);
//		   String ip = "255.255.255.255";
//		   String mac = "FF-FF-FF-FF-FF-FF";
//		   try {
//			String digest = FuncUtil.convertHostIpToLong(ip) + "-" + FuncUtil.convertMacToLong(mac, "-");
//			System.out.println(digest);
//			System.out.println(Long.MAX_VALUE);
//			System.out.println(UUID.randomUUID().toString().replace("-", "") + "-" +  digest);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		   
//		   List<String> list = new ArrayList<String>();
//		   List<String> list2 = new ArrayList<String>();
//		   list.add("12");
//		   list.add("23");
//		   list2.add("13");
//		   list2.add("14");
//		   list2.add("56");
//		   
//		   list.addAll(list2);
//		   
//		   System.out.println(list.toString());
		   Calendar c = Calendar.getInstance();
		   c.add(Calendar.DAY_OF_MONTH, -7);
		   System.out.println(c.getTime());
	   }
	 
	}
