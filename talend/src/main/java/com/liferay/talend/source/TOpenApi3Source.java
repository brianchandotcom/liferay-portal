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

package com.liferay.talend.source;

import com.liferay.talend.client.SwaggerHubClient;
import com.liferay.talend.dataset.OpenApi3DataSet;
import com.liferay.talend.processor.OpenApiPrinter;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.http.Response;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.json.JsonObject;
import javax.json.JsonValue;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Igor Beslic
 */
@Documentation("Source for swagger open api specifications")
public class TOpenApi3Source implements Serializable {

    public TOpenApi3Source(
		final OpenApi3DataSet openApi3DataSet,
		final SwaggerHubClient swaggerClient,
		final RecordBuilderFactory recordBuilderFactory) {

    	_openApi3DataSet = openApi3DataSet;
        _swaggerClient = swaggerClient;
        _recordBuilderFactory = recordBuilderFactory;
    }

    @PostConstruct
    public void init() {
    	JsonObject jsonObject = _getOpenApi3SpecJsonObject(
    		_openApi3DataSet.getDataStore().getApiKey());

		OpenApiPrinter.printTitle(jsonObject);
		OpenApiPrinter.printDescription(jsonObject);
		OpenApiPrinter.printVersion(jsonObject);

		JsonObject infoJSONObject = jsonObject.getJsonObject("info");

		_title = infoJSONObject.getString("title");
		_version = infoJSONObject.getString("version");

		JsonObject pathsJSONObject = jsonObject.getJsonObject("paths");

		Set<Map.Entry<String, JsonValue>> pathEntries =
			pathsJSONObject.entrySet();

		_pathJsonValueIterator = pathEntries.iterator();
	}

    @Producer
    public Record next() {
		if (!_pathJsonValueIterator.hasNext()) {
			return null;
		}

		Record.Builder recordBuilder = _recordBuilderFactory.newRecordBuilder();

		recordBuilder.withString("version", _version);
		recordBuilder.withString("title", _title);

		Map.Entry<String, JsonValue> pathJsonValueEntry =
			_pathJsonValueIterator.next();

		recordBuilder.withString("path", pathJsonValueEntry.getKey());

		Record record = recordBuilder.build();

		System.out.println("Produced record: " + record);

        return record;
    }

    @PreDestroy
    public void release() {
        _pathJsonValueIterator = null;
    }

	private String _extractMediaType(final Map<String, List<String>> headers) {
		if ((headers == null) || !headers.containsKey("Content-Type")) {
			return null;
		}

		List<String> headerValues = headers.get("Content-Type");


		String contentType = headerValues.get(0);

		if ((contentType == null) || contentType.isEmpty()) {
			return null;
		}

		if (!contentType.contains(";")) {
			String[] contentTypes = contentType.split(";");

			contentType = contentTypes[0];
		}

		return contentType.toLowerCase(Locale.getDefault());
	}

	private JsonObject _getOpenApi3SpecJsonObject(String auth) {
		final Response<JsonObject> response = _swaggerClient.search(
			auth, "application/json");

		if (response.status() == 200) {

			return response.body();
		}

		final String mediaType = _extractMediaType(response.headers());

		if ((mediaType != null) && mediaType.contains("application/json")) {
			final JsonObject error = response.error(JsonObject.class);

			throw new RuntimeException(
				String.format(
					_ERROR_REQUEST_FAILED, error.getString("error"),
					error.getString("description")));
		}

		throw new RuntimeException(response.error(String.class));
	}

	private static final String _ERROR_REQUEST_FAILED =
		"Request failed error: %s and description %s";

	private final OpenApi3DataSet _openApi3DataSet;
	private final SwaggerHubClient _swaggerClient;
	private final RecordBuilderFactory _recordBuilderFactory;
	private String _version;
	private String _title;
	Iterator<Map.Entry<String, JsonValue>> _pathJsonValueIterator;


}