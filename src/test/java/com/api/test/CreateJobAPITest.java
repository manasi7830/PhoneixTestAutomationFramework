package com.api.test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.DateTimeUtil.*;
import com.api.utils.SpecUtil;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest(){
		//Creating the CreateJobPayload object
				Customer custmer=new Customer("Manasi","Avachat","9767145100","","manasiavachat14@gmail.com","");
				CustomerAddress customerAddress=new CustomerAddress("20B","Atria","HMTMain","HMT","Jalahalli","560089","India","Karnataka");
				CustomerProduct customerProduct=new CustomerProduct(getTimeWithDaysAgo(10),"99381664897457","99381664897457","99381664897457",getTimeWithDaysAgo(10),1,1);
				Problems problems = new Problems(1, "Battery issue");
				List<Problems> problemList=new ArrayList<Problems>();
				problemList.add(problems);
				
				CreateJobPayload createJobPayload=new CreateJobPayload(0, 2, 1, 1, custmer, customerAddress, customerProduct, problemList);
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.log().all()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",equalTo(1))
		.body("data.job_number",startsWith("JOB_"));
		
	}

}
