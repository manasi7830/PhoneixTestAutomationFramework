package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.management.RuntimeErrorException;

public class ConfigManager {

//WAP to read the properties file from src/test/resources/config/config.properties

	private static Properties prop = new Properties();

	private static String path = "config/config.properties";

	private static String env;

	private ConfigManager() {
		// private constructor to restrict object creatin
	}

	static {

		env = System.getProperty("env","qa");
		env=env.toLowerCase().trim();

		switch (env) {

		case "dev"-> path = "config/config.dev.properties";
			
		case "qa"->path="config/config.qa.properties";
			
		case "uat"->path="config/config.uat.properties";
		
		default->path="config/config.properties";
		}

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (input == null) {
			throw new RuntimeException("Can not find the file at path" + path);
		}

		try {

			prop.load(input);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty(key);

	}

}
