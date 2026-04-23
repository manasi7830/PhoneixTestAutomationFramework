package com.api.test;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerDao;
import com.database.model.CustomerDBModel;
import com.github.javafaker.Faker;

import static com.api.utils.SpecUtil.*;

public class CreateJobAPITestwithFakeData {
	private CreateJobPayload createJobPayload;
	private final static String COUNTRY = "India";

	@BeforeMethod(description = "Creating createjob api request payload")
	public void setup() {

		

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();

	}

	@Test(description = "Verifying if create job api is able to create Inwarrenty job", groups = { "api", "smoke",
			"regression" })
	public void createJobAPITest() {

		int customerID=given().spec(requestSpecWithAuth(Role.FD, createJobPayload)).when().log().all().post("/job/create").then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"))
				.extract().body().jsonPath().getInt("data.tr_customer_id");
				Customer expectedCustomerData=createJobPayload.customer();
				CustomerDBModel actualCustomerDataInDB=CustomerDao.getCustomerInfo(customerID);
				
				Assert.assertEquals(expectedCustomerData.first_name(), actualCustomerDataInDB.getFirst_name());
				Assert.assertEquals(expectedCustomerData.last_name(), actualCustomerDataInDB.getLast_name());
				Assert.assertEquals(expectedCustomerData.mobile_number(), actualCustomerDataInDB.getMobile_number());
				Assert.assertEquals(expectedCustomerData.email_id(), actualCustomerDataInDB.getEmail_id());
				Assert.assertEquals(expectedCustomerData.mobile_number_alt(), actualCustomerDataInDB.getMobile_number_alt());
				Assert.assertEquals(expectedCustomerData.email_id_alt(), actualCustomerDataInDB.getEmail_id_alt());

	}

}
