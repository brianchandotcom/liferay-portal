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

package com.liferay.partner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Jair Medeiros
 * @author Thaynam Lázaro
 */
@Service
public class ObjectEntryService {

	public void postObjectEntryBatch(
		String objectDefinitionName, Object[] objects) {

		WebClient.RequestBodyUriSpec requestBodyUriSpec = _webClient.post();

		WebClient.RequestBodySpec requestBodySpec = requestBodyUriSpec.uri(
			uriBuilder -> uriBuilder.path(
				"/o/c/" + objectDefinitionName + "/batch"
			).queryParam(
				"createStrategy", "UPSERT"
			).build());

		WebClient.RequestHeadersSpec<?> requestHeadersSpec =
			requestBodySpec.bodyValue(objects);

		requestHeadersSpec.retrieve();
	}

	@Autowired
	private WebClient _webClient;

}