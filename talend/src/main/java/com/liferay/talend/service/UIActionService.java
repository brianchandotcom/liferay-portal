/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.talend.service;

import com.liferay.talend.client.BasicAuthenticationClient;
import com.liferay.talend.datastore.AuthenticationMethod;
import com.liferay.talend.datastore.BasicDataStore;
import com.liferay.talend.datastore.InputDataStore;
import com.liferay.talend.datastore.OAuthDataStore;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.completion.SuggestionValues;
import org.talend.sdk.component.api.service.completion.Suggestions;
import org.talend.sdk.component.api.service.healthcheck.HealthCheck;
import org.talend.sdk.component.api.service.healthcheck.HealthCheckStatus;
import org.talend.sdk.component.api.service.http.Response;

import javax.json.JsonObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 */
@Service
public class UIActionService {

	@HealthCheck("checkInputDataStore")
	public HealthCheckStatus checkInputDataStore(
		@Option InputDataStore inputDataStore,
		final BasicAuthenticationClient basicAuthenticationClient) {

		if (AuthenticationMethod.BASIC ==
			inputDataStore.getAuthenticationMethod()) {

			return _checkBasicDataStore(
				inputDataStore.getBasicDataStore(), basicAuthenticationClient);
		}

		return _checkOAuthDataStore(inputDataStore.getoAuthDataStore());
	}

	public HealthCheckStatus _checkBasicDataStore(
		@Option BasicDataStore basicDataStore,
		BasicAuthenticationClient basicAuthenticationClient) {

		if (_isNull(basicDataStore.getUser()) ||
			_isNull(basicDataStore.getPassword())) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Username and Password are required");
		}

		URL serverURL = basicDataStore.getServerURL();

		if ((serverURL == null) || _isNull(serverURL.toString())) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Server URL is required");
		}

		basicAuthenticationClient.base(serverURL.toString());

		Base64.Encoder base64Encoder = Base64.getEncoder();

		StringBuilder sb = new StringBuilder();

		sb.append(basicDataStore.getUser());
		sb.append(":");
		sb.append(basicDataStore.getPassword());

		String base64Seed = sb.toString();

		byte[] encode = base64Encoder.encode(base64Seed.getBytes());

		sb.setLength(0);

		sb.append("Basic ");
		sb.append(base64Encoder.encodeToString(encode));

		Response<JsonObject> jsonObjectResponse =
			basicAuthenticationClient.checkRequest(sb.toString(), "*/*");

		if (jsonObjectResponse.status() != 200) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Connection failed, received http response status " +
					jsonObjectResponse.status());
		}

		return new HealthCheckStatus(
			HealthCheckStatus.Status.OK, "Connection success");
	}

	public HealthCheckStatus _checkOAuthDataStore(
		@Option OAuthDataStore oAuthDataStore) {

		if (_isNull(oAuthDataStore.getConsumerKey()) ||
			_isNull(oAuthDataStore.getConsumerSecret())) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Consumer key and secret are required");
		}

		if (oAuthDataStore.getServerURL() == null) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Server URL is required");
		}

		// TODO perform connection check

		return new HealthCheckStatus(
			HealthCheckStatus.Status.OK, "Connection success");
	}

	private boolean _isNull(String value) {
		if ((value == null) || value.isEmpty()) {
			return true;
		}

		value = value.trim();

		if (value.isEmpty()) {
			return true;
		}

		return false;
	}

	@Suggestions("fetchEndpoints")
	public SuggestionValues fetchEndpoints(@Option("dataStore") final InputDataStore inputDataStore) {

		List<SuggestionValues.Item> items = new ArrayList<>();

		Map<String, String> endpoints = _getEndpointsMap("com/liferay/talend/resource/rest-openapi.yaml");

		endpoints.forEach(
			(path, returnSchemaType) -> items.add(new SuggestionValues.Item(path, returnSchemaType))
		);

		return new SuggestionValues(true, items);
	}

	private Map<String, String> _getEndpointsMap(String location) {
		OpenAPI openAPI = new OpenAPIV3Parser().read(location);
		Paths paths = openAPI.getPaths();

		return paths.entrySet().stream()
			.filter(this::_arrayTypePredicate)
			.collect(Collectors.toMap(Map.Entry::getKey, this::_mapToArrayItemReferences)
		);
	}

	private boolean _arrayTypePredicate(Map.Entry<String, PathItem> pathItemEntry) {
		String type = pathItemEntry.getValue().getGet().getResponses().get("200").getContent().
			get("application/json").getSchema().getType();

		if (type != null && type.equals("array")) {
			return true;
		}

		return false;
	}

	private String _mapToArrayItemReferences(Map.Entry<String, PathItem> stringPathItemEntry) {
		Schema schema = stringPathItemEntry.getValue().getGet().getResponses().get("200").getContent().
			get("application/json").getSchema();

		return ((ArraySchema) schema).getItems().get$ref();
	}

}
