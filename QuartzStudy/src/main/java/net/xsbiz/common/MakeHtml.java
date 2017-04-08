package net.xsbiz.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MakeHtml {
	// 调用的方法
	public void execute() {
		// 需要做的事情
		System.out.println("执行targetObject中的targetMethod方法，开始！");
		System.out.println("执行targetObject中的targetMethod方法，结束！");
	}

	public static void main(String[] args) {

		System.out.println("----begin---");

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:/applicationContext.xml");
		/*ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/applicationContext.xml");*/
		System.out.println(context);

		// 如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化

		context.getBean("startQuertz");

		System.out.print("----end---");

	}

}
