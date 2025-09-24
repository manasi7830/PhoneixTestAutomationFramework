package com.api.test;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {
	
	@Test
	public void verifyCountAPIResponse() {
		
		given()
		.baseUri(getProperty("BASE_URL"))
		.and()
		.header("Authorization",AuthTokenProvider.getToken(Role.FD))
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("dashboard/count")
		.then()
		.log().all()
		.statusCode(200)
		.body("message",equalTo("Success"))
		.time(lessThan(2000L))
		.body("data",notNullValue())
		.body("data.size()",equalTo(3))
		.body("data.count",everyItem(greaterThanOrEqualTo(0)))
		.body("data.label",everyItem(not(blankOrNullString())))
		.body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
		.body(matchesJsonSchemaInClasspath("response-schema\\CountAPIResponseSchema-FD.json"));

		}
	
	public void countAPTTest_MissingAuthToken() {
		given()
		.baseUri(getProperty("BASE_URL"))
		.and()
		.header("Authorization",AuthTokenProvider.getToken(Role.FD))
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
	}

}
