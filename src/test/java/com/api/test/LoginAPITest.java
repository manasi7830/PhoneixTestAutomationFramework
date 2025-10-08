package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {
	
	private UserCredentials userCreds;
	
	@BeforeMethod(description="Create the payload for the login API")
	public void setup() {
		userCreds =new UserCredentials("iamfd", "password");
	}
	
	
	
	@Test(description="Verifying if login api is working for FD user", groups= {"api","regression","smoke"})
	public void loginTest() throws IOException {
		
		
		given()
		.spec(requestSpec(userCreds))
        .when()
		.post("login")
		.then()
		.spec(responseSpec_OK())
		.and()
		.body("message",equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
		
		
		
		
	}

}
