package com.heqiao2010.aopcacheutil.starter.entity;

/**
 * User
 * 
 * @author heqiao
 *
 */
public class UserDO {
	private String name;
	private int age;
	private String testRandom;
	

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

	public String getTestRandom() {
		return testRandom;
	}

	public void setTestRandom(String testRandom) {
		this.testRandom = testRandom;
	}

	@Override
	public String toString() {
		return "UserDO [name=" + name + ", age=" + age + ", testRandom=" + testRandom + "]";
	}

}
