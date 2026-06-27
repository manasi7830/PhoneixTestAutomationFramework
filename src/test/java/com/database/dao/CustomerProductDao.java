package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

import io.qameta.allure.Step;

public class CustomerProductDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerProductDao.class);
	
	private static final String PRODUCT_QUERY =
			"""
					SELECT  * from tr_customer_product where id=?;
					
					""";
	
	private CustomerProductDao() {
		
	}
	@Step("Retriving the Customer product information from DB for the specific Customer product id")
	public static CustomerProductDBModel getProductInfoFromDB(int tr_customer_product_id) {
		CustomerProductDBModel customerProductDBModel=null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			Connection conn=DatabaseManager.getConnection();
			PreparedStatement ps=conn.prepareStatement(PRODUCT_QUERY);
			ps.setInt(1, tr_customer_product_id);
			LOGGER.info("Executing the SQL query {}", PRODUCT_QUERY);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				customerProductDBModel =new CustomerProductDBModel(rs.getInt("id"),
						rs.getInt("tr_customer_id"), 
						rs.getInt("mst_model_id"), 
						rs.getString("dop"), 
						rs.getString("popurl"), 
						rs.getString("imei2"), 
						rs.getString("imei1"), 
						rs.getString("serial_number"));
			}
		}
		catch(SQLException e) {
			LOGGER.error("Cannot convert the result sey to the customerProductDBModel bean",e);
			
		}
		return customerProductDBModel;
		
	}

}
