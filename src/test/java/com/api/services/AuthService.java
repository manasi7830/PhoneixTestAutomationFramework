package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class AuthService {
	
	//Service class going to hold the APIs that belong to Auth 
	
	private static final String LONGIN_ENDPOINT="login";
	
	public Response login(Object userCredentials ) {
		
		Response response=given()
		.spec(requestSpec(userCredentials))
        .when()
		.post(LONGIN_ENDPOINT);
		
		return response;
	

}
}