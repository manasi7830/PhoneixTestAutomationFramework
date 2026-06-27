package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilters;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	// GET--DEL
	@Step("Setting up the BaseUTI, Content Type as Application/JSON and attaching the SensitiveData Filters ")
	public static RequestSpecification requestSpec() {

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URL"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.log(LogDetail.URI).log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.addFilter(new SensitiveDataFilters())
				.build();
		return requestSpecification;

	}

	// POST-PUT-PATCH{BODY}
	@Step("Setting up the BaseUTI, Content Type as Application/JSON and attaching the SensitiveData Filters ")
	public static RequestSpecification requestSpec(Object payload) {

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URL"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilters())
				.build();
		return requestSpecification;

	}
	@Step("Setting up the BaseUTI, Content Type as Application/JSON and attaching the SensitiveData Filters for a role ")
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URL"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.addFilter(new SensitiveDataFilters())
				.build();
		return requestSpecification;

	}

	@Step("Setting up the BaseUTI, Content Type as Application/JSON and attaching the SensitiveData Filters for a role and attaching payload ")
	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URL"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.setBody(payload)
				.addFilter(new SensitiveDataFilters())
				.build();
		return requestSpecification;

	}
	@Step("Expecting the response to have the content type as Application/Json, Status 200 and Response Time Less Than 1000ms")
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification reponseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(1000l))

				.build();
		return reponseSpecification;
	}
	@Step("Expecting the response to have the content type as Application/Json,and Response Time Less Than 1000ms and status code")
	public static ResponseSpecification responseSpec(int statusCode) {
		ResponseSpecification reponseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000l))
				// .log(LogDetail.ALL)
				.build();
		return reponseSpecification;
	}
	@Step("Expecting the response to have the content type as Text ,and Response Time Less Than 1000ms and status code")
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification reponseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000l)).build();
		return reponseSpecification;
	}
}
