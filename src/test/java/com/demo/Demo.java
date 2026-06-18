package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {
	private static Logger logger=LogManager.getLogger(Demo.class);
	public static void main(String[] args) {
		logger.info("Inside main methid");
		
		int a=10;
		logger.info("value of a{}",a);
		int b=20;
		logger.info("value of b{}",b);
		
		int result=a/b;
		logger.info("value of result {}",result);
		logger.info("Program ended");

	}

}
