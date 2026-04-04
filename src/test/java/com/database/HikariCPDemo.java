package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {
	public static void main(String[] args) throws SQLException {
		
		HikariConfig hikariConfiguration=new HikariConfig();
		hikariConfiguration.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		hikariConfiguration.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
		hikariConfiguration.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
		hikariConfiguration.setMaximumPoolSize(10);
		hikariConfiguration.setMinimumIdle(2);
		hikariConfiguration.setIdleTimeout(10000);
		hikariConfiguration.setIdleTimeout(1000);
		hikariConfiguration.setMaxLifetime(1800000);
		hikariConfiguration.setPoolName("Phoneix Test Automation");
		
		HikariDataSource ds= new HikariDataSource(hikariConfiguration);
		Connection conn=ds.getConnection();
		System.out.println(conn);
		
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery("SELECT  first_name ,last_name ,mobile_number  FROM  tr_customer;");
		
		while(rs.next()) {
			System.out.println(rs.getString("first_name"));
		}
		ds.close();
	}

}
