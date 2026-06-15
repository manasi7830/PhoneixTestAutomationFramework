package com.api.test;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import io.restassured.response.Response;

public class LoginAPITest {
	
	private UserBean userCreds;
	private AuthService authService;
	
	@BeforeMethod(description="Create the payload for the login API")
	public void setup() {
		userCreds =new UserBean("iamfd", "password");
		authService=new AuthService();
	}
	
	
	
	@Test(description="Verifying if login api is working for FD user", groups= {"api","regression","smoke"})
	public void loginTest() throws IOException {
		
		
		authService.login(userCreds)
		.then()
		.spec(responseSpec_OK())
		.and()
		.body("message",equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
		
		
		
		
	}

}
