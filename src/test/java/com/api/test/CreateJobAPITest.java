package com.api.test;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

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
import com.api.utils.SpecUtil;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest(){
		//Creating the CreateJobPayload object
				Customer custmer=new Customer("Manasi","Avachat","9767145100","","manasiavachat14@gmail.com","");
				CustomerAddress customerAddress=new CustomerAddress("20B","Atria","HMTMain","HMT","Jalahalli","560089","India","Karnataka");
				CustomerProduct customerProduct=new CustomerProduct(getTimeWithDaysAgo(10),"97381664897457","97381664897457","97381664897457",getTimeWithDaysAgo(10),Product.NEXUS_2.getCode(),Model.NEXUS_2_BLUE.getCode());
				Problems problems = new Problems(Problem.POOR_BATTERY_LIFE.getCode(), "Battery issue");
				List<Problems> problemList=new ArrayList<Problems>();
				problemList.add(problems);
				
				CreateJobPayload createJobPayload=new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), custmer, customerAddress, customerProduct, problemList);
		
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
