package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constant.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import  static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

@Test
public class UserDetailsAPITest {
	
	public void userDetailsAPITest() throws IOException {
		
		
		Header authHeader=new Header("Authorization",getToken(FD));
		given()
		.baseUri(getProperty("BASE_URL"))
		.and()
		.header(authHeader)
		.and()
		.accept(ContentType.JSON)
		.log().all()
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("userdetails")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(2000L))
		.and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
	

	

}
