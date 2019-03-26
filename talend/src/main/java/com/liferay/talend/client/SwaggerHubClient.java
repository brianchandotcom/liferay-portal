package com.liferay.talend.client;

import org.talend.sdk.component.api.service.http.Header;
import org.talend.sdk.component.api.service.http.HttpClient;
import org.talend.sdk.component.api.service.http.Request;
import org.talend.sdk.component.api.service.http.Response;

import javax.json.JsonObject;

public interface SwaggerHubClient extends HttpClient {

	@Request(path = "/apis/liferay6/commerce-admin/v2.0", method = "GET")
	public Response<JsonObject> search(
		@Header("Authorization") String auth,
		@Header("Content-Type") String contentType);

}
