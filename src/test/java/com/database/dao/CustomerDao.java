package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {

	private static final String CUSTOMER_DETAIL_QUERY = """
			SELECT * from tr_customer where id= ?
				""";

	private CustomerDao() {

	}

	public static CustomerDBModel getCustomerInfo(int CustomerId) {
		CustomerDBModel customerDBModel = null;
		try {
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
			preparedStatement.setInt(1, CustomerId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println(resultSet.getString("first_name"));
				System.out.println(resultSet.getString("email_id"));

				customerDBModel = new CustomerDBModel(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("mobile_number"),
						resultSet.getString("mobile_number_alt"), resultSet.getString("email_id"),
						resultSet.getString("email_id_alt"), resultSet.getInt("tr_customer_address_id"));
			}
		} catch (SQLException e) {
			System.err.print(e.getMessage());
		}
		return customerDBModel;
	}

}
