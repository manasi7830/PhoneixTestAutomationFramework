package com.api.utils;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import static com.api.constant.Role.*;

import com.api.constant.Role;
import com.api.request.model.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	

	public static String getToken(Role role) {
		

		UserCredentials userCredentials=null;
		
		if(role==FD) {
			userCredentials=new UserCredentials("iamfd", "password");
		}
		if(role==SUP) {
			userCredentials=new UserCredentials("iamsup", "password");
		}
		if(role==ENG) {
			userCredentials=new UserCredentials("iameng", "password");
		}
		if(role==QC) {
			userCredentials=new UserCredentials("iamqc", "password");
		}
		String token=given()
			.baseUri(ConfigManager.getProperty("BASE_URL"))
			.contentType(ContentType.JSON)
			.body(userCredentials)
			.when()
			.post("login")
			.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("message", equalTo("Success"))
			.extract()
			.jsonPath()
			.getString("data.token");
			
		
		System.out.println("----------------------------------------------");
		System.out.println(token);
		return token;
			
		
	}

}
