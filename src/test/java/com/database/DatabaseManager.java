package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);

	private static boolean isVaultup = true;

	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USERNAME = loadSecret("DB_USER_NAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");

	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_SEC = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_SEC"));
	private static final int MAX_LIFE_TIME_IN_MINS = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS"));
	private static final int IDEL_TIMEOUT_SEC = Integer.parseInt(ConfigManager.getProperty("IDEL_TIMEOUT_SEC"));
	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");

	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;

	private DatabaseManager() {

	}

	private static void intializePool() {
		if (hikariDataSource == null) {
			LOGGER.warn("Database connection is not available ....Creating HikariDataSource");
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_SEC * 1000);
					hikariConfig.setIdleTimeout(IDEL_TIMEOUT_SEC * 1000);
					hikariConfig.setMaxLifetime(MAX_LIFE_TIME_IN_MINS * 60 * 1000);
					hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);

					hikariDataSource = new HikariDataSource(hikariConfig);
					LOGGER.info("Hikari Datasource created!!!");
				}

			}
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		if (hikariDataSource == null) {
			LOGGER.info("Intializing the DataBase Connection using HikariCP");
			intializePool();
		} else if (hikariDataSource.isClosed()) {
			LOGGER.error("HIKARI DATA SOURCE IS CLOSED");
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}

		connection = hikariDataSource.getConnection();

		return connection;
	}

	public static String loadSecret(String key) {
		String value = null;

		if (isVaultup) {
			value = VaultDBConfig.getSecret(key);

			if (value == null) {
				LOGGER.error("Vault is down or some issue with vault");
				isVaultup = false;
			} else {

				LOGGER.info("READING VALUE FOR KEY {}FROM VAULT...........", key);
				return value;
			}
		}

		
		LOGGER.info("READING VALUE FROM ENV.");
		value = EnvUtil.getValue(key);
		return value;

	}
}