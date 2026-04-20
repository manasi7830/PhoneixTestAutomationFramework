package com.database.model;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBData=CustomerDao.getCustomerInfo();
		System.out.println(customerDBData);
		System.out.println(customerDBData.getFirst_name());
		System.out.println(customerDBData.getLast_name());
		System.out.println(customerDBData.getEmail_id());
		Customer customer=new Customer("Skyla", "Jacobi", "9767145100", " ", "manasiavachat14@gmail.com", " ");
		System.out.println(customer.first_name());
		Assert.assertEquals(customerDBData.getFirst_name(), customer.first_name());

	}

}
