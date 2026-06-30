package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService {
	
	//Service class going to hold the APIs that belong to Auth 
	
	private static final String LONGIN_ENDPOINT="/login";
	private static final Logger LOGGER=  LogManager.getLogger(AuthService.class);
	
	@Step("Perform login request with userCredentials")
	public Response login(Object userCredentials ) {
		
		LOGGER.info("Making login request for the payload {}", ((UserBean)userCredentials).getUsername());
		Response response=given()
		.spec(requestSpec(userCredentials))
        .when()
		.post(LONGIN_ENDPOINT);
		
		return response;
	

}
}