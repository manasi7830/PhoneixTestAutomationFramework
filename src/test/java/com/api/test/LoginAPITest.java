package com.api.test;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Listeners(com.listeners.APITestListeners.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginAPITest {
	
	private UserBean userCreds;
	private AuthService authService;
	
	@BeforeMethod(description="Create the payload for the login API")
	public void setup() {
		userCreds =new UserBean("iamfd", "password");
		authService=new AuthService();
	}
	
	
	@Story("Valid user should be abale to login to the system ")
	@Description("Verify if FD user is able to login via API")
	@Severity(SeverityLevel.BLOCKER)
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
