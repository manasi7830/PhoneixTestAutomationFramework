package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static com.api.constant.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import  static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

@Test(description="Verify if the user detail API response is shown correctly",groups= {"api","smoke","regression"})
public class UserDetailsAPITest {
	
	public void userDetailsAPITest() throws IOException {
		
		
		
		given()
		.spec(requestSpecWithAuth(FD))
		.when()
		.get("userdetails")
		.then()
		.spec(responseSpec_OK())
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
	

	

}
