package com.database.dao;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
	JobHeadModel jobHeadModel=JobHeadDao.getDataFromJobHead(260495);
	System.out.println(jobHeadModel);
	}

}
