package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilters implements Filter {

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		System.out.println("-------------------HELLO FROM FILTERS--------------------------------");
		Response response=ctx.next(requestSpec, responseSpec);
		System.out.println("----------------------I got the response in the filter--------------------");
		return response;
	}

}
