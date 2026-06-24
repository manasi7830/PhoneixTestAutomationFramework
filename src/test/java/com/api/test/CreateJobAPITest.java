package com.api.test;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
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
import com.api.services.JobService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.SpecUtil.*;
@Listeners(com.listeners.APITestListeners.class)
@Epic("Job Management")
@Feature("Job Creation")
public class CreateJobAPITest {
	
	private JobService jobService;
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description="Creating createjob api request payload and instantiating the job service ")
	public void setup(){
		
		Customer custmer=new Customer("Manasi","Avachat","9767145100","","manasiavachat14@gmail.com","");
		CustomerAddress customerAddress=new CustomerAddress("20B","Atria","HMTMain","HMT","Jalahalli","560089","India","Karnataka");
		CustomerProduct customerProduct=new CustomerProduct(getTimeWithDaysAgo(10),"97381664897786","97381664897786","97381664897786",getTimeWithDaysAgo(10),Product.NEXUS_2.getCode(),Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.POOR_BATTERY_LIFE.getCode(), "Battery issue");
		List<Problems> problemList=new ArrayList<Problems>();
		problemList.add(problems);
		
		createJobPayload=new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), custmer, customerAddress, customerProduct, problemList);
		jobService=new JobService();
	}
	
	@Story("FD should be able to create job")
	@Description("Verifying if  FD is able to use create job api and create Inwarrenty job is created")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description="Verifying if create job api is able to create Inwarrenty job",groups= {"api","smoke","regression"})
	public void createJobAPITest(){
		
		jobService.createJob(Role.FD, createJobPayload)
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",equalTo(1))
		.body("data.job_number",startsWith("JOB_"));
		
	}

}
