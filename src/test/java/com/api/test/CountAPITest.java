package com.api.test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.SpecUtil.*;
@Listeners(com.listeners.APITestListeners.class)
@Epic("Job Management")
@Feature("Job Count")
public class CountAPITest {

	private DashboardService dashboardService;

	@BeforeMethod(description = "Setting up DashboardService instance 	")
	public void setup() {
		dashboardService = new DashboardService();
	}
	
	@Story("Job Count Detail is shown correctly ")
	@Description("Verifying if count api is giving correct response")
	@Severity(SeverityLevel.CRITICAL)

	@Test(description = "Verifying if count api is giving correct response", groups = { "api", "smoke", "regression" })
	public void verifyCountAPIResponse() {

		dashboardService.count(FD).then().spec(responseSpec_OK()).body("message", equalTo("Success"))

				.body("data", notNullValue()).body("data.size()", equalTo(3))
				.body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString())))
				.body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
				.body(matchesJsonSchemaInClasspath("response-schema\\CountAPIResponseSchema-FD.json"));

	}

	@Test(description = "Verifying if count api is giving status code for invalid token ", groups = { "api", "negative",
			"smoke", "regression" })
	public void countAPTTest_MissingAuthToken() {
		dashboardService.countWithNoAuth().then().spec(responseSpec_TEXT(401));
	}

}
