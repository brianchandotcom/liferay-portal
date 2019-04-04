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

import com.liferay.talend.datastore.BasicDataStore;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 */
@Service
public class UIActionService {

	@Suggestions("fetchEndpoints")
	public SuggestionValues fetchEndpoints(@Option("restDataSet") final BasicDataStore dataStore) {

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
