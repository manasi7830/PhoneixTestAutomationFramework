package com.api.tests.datadriven;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
@Listeners(com.listeners.APITestListeners.class)
public class LoginAPIJExcelDataDrivenTest {
		
	private AuthService authService;
	
	@BeforeMethod(description="Settingup the auth service reference")
	public void setup() {
		authService=new AuthService();
	}
	
	@Test(description="Verifying if login api is working for FD user", 
			groups= {"api","regression","smoke"},
			dataProviderClass =com.dataproviders.DataProviderUtils.class ,
			dataProvider = "LoginAPIExcelDataProvider")
	public void loginTest(UserBean userBean) {
		
		
		authService.login(userBean)
		.then()
		.spec(responseSpec_OK())
		.and()
		.body("message",equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
		
		
		
		
	}

}
