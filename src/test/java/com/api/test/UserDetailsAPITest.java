package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.UserService;

import static com.api.utils.SpecUtil.*;

import static com.api.constant.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import  static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
@Listeners(com.listeners.APITestListeners.class)

public class UserDetailsAPITest {
	
	
	private UserService userService;
	
	@BeforeMethod(description="Setting up the UserService instance")
	public void setup() {
		userService=new UserService();
	}
	
	@Test(description="Verify if the user detail API response is shown correctly",groups= {"api","smoke","regression"})
	public void userDetailsAPITest() throws IOException {
		
		userService.userDetails(FD)
		.then()
		.spec(responseSpec_OK())
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
	

	

}
