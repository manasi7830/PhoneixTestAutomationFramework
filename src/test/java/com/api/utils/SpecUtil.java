package com.api.utils;
import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.pojo.UserCredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	//GET--DEL
	public static RequestSpecification requestSpec() {
		
		RequestSpecification requestSpecification=new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URL"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return requestSpecification;
		
	}
	
	//POST-PUT-PATCH{BODY}
	
public static RequestSpecification requestSpec(Object userCreds) {
		
		RequestSpecification requestSpecification =new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URL"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(userCreds)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return requestSpecification;
		
	}

public static RequestSpecification requestSpecWithAuth(Role role) {
	RequestSpecification requestSpecification =new RequestSpecBuilder()
	.setBaseUri(getProperty("BASE_URL"))
	.setContentType(ContentType.JSON)
	.setAccept(ContentType.JSON)
	.setAccept(ContentType.JSON)
	.addHeader("Authorization",AuthTokenProvider.getToken(role))
	.log(LogDetail.URI)
	.log(LogDetail.METHOD)
	.log(LogDetail.HEADERS)
	.log(LogDetail.BODY)
	.build();
	return requestSpecification;
	
}

public static ResponseSpecification responseSpec_OK() {
	ResponseSpecification reponseSpecification=new ResponseSpecBuilder()
	.expectContentType(ContentType.JSON)
	.expectStatusCode(200)
	.expectResponseTime(Matchers.lessThan(1000l))
	.log(LogDetail.ALL)
	.build();
	return reponseSpecification;
}

public static ResponseSpecification responseSpec(int statusCode) {
	ResponseSpecification reponseSpecification=new ResponseSpecBuilder()
	.expectContentType(ContentType.JSON)
	.expectStatusCode(statusCode)
	.expectResponseTime(Matchers.lessThan(1000l))
	.log(LogDetail.ALL)
	.build();
	return reponseSpecification;
}

public static ResponseSpecification responseSpec_TEXT(int statusCode) {
	ResponseSpecification reponseSpecification=new ResponseSpecBuilder()
	.expectStatusCode(statusCode)
	.expectResponseTime(Matchers.lessThan(1000l))
	.log(LogDetail.ALL)
	.build();
	return reponseSpecification;
}
}
