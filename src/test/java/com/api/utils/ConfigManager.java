package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.services.JobService;

public class ConfigManager {

	private static Properties prop = new Properties();

	private static String path = "config/config.properties";

	private static String env;

	private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);

	private ConfigManager() {

	}

	static {
		LOGGER.info("Reading env value passed from the terminal");

		if (System.getProperty("env") == null) {
			LOGGER.warn("Env variable is not set ....using qa as the env");
		}
		env = System.getProperty("env", "qa");
		LOGGER.info("Running the test in the env {}", env);
		env = env.toLowerCase().trim();

		switch (env) {

		case "dev" -> path = "config/config.dev.properties";

		case "qa" -> path = "config/config.qa.properties";

		case "uat" -> path = "config/config.uat.properties";

		default -> path = "config/config.properties";
		}
		
		LOGGER.info("Using the Properties file from the path{}",path);

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (input == null) {
			LOGGER.error("Cannot find the file at the path{}", path);
			throw new RuntimeException("Can not find the file at path" + path);
		}

		try {

			prop.load(input);
		} catch (FileNotFoundException e) {
			LOGGER.error("Cannot find the file at the path{}", path,e);

			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("Something went wrong ....please check the file {}", path,e);

			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty(key);

	}

}
