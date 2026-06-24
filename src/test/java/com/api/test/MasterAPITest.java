package com.api.test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.MasterService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
@Listeners(com.listeners.APITestListeners.class)
@Epic("Job Management")
@Feature("Master API")
public class MasterAPITest {
	
	private MasterService masterService;
	
	@BeforeMethod(description="Instantiating the Master Service Object")
	public void setup() {
		masterService=new MasterService();
	}
	
	@Story("Master API should bring the OEM details,Problem type and Warranty Status")
	@Description("Verifying if master api is giving correct response")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description="Verifying if master api is giving correct response",groups= {"api","smoke","regression"})
	public void masterAPI() {
		masterService.master(FD)
		.then()
		.spec(responseSpec_OK())
		.body("message",equalTo("Success"))
		.body("data",notNullValue())
		.body("data",hasKey("mst_oem"))
		.body("data",hasKey("mst_product"))
		.body("$",hasKey("data"))
		.body("data.mst_oem.size()",greaterThan(0))
		.body("data.mst_model.size()",greaterThan(0))
		.body("data.mst_oem.id",everyItem(notNullValue()))
		.body("data.mst_oem.name",everyItem(notNullValue()))
		.body(matchesJsonSchemaInClasspath("response-schema\\MasterAPIResponseSchema.json"));
	}
	@Test(description="Verifying if master api is giving status code for invalid token ",groups= {"api","negative","smoke","regression"})
	public void invalidTokenMasterAPITest() {
		given()
		.spec(requestSpec())
		.log().all()
		.when()
		.post("master")
		.then()
		.spec(responseSpec_TEXT(401));
		
	}

}
