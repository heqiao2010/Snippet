package com.yunzero;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext springContext = 
    			new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
