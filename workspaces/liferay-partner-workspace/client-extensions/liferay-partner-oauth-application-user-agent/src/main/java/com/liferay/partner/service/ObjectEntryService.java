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

package com.liferay.partner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Jair Medeiros
 */
@Service
public class ObjectEntryService {

	public void postObjectEntryBatch(String body, String restContextPath)
		throws Exception {

		WebClient.RequestBodyUriSpec requestBodyUriSpec = _webClient.post();

		WebClient.RequestBodySpec requestBodySpec = requestBodyUriSpec.uri(
			uriBuilder -> uriBuilder.path(
				restContextPath + "/batch"
			).queryParam(
				"createStrategy", "UPSERT"
			).build());

		WebClient.RequestHeadersSpec<?> requestHeadersSpec =
			requestBodySpec.bodyValue(body);

		WebClient.ResponseSpec responseSpec = requestHeadersSpec.retrieve();

		Mono<Void> mono = responseSpec.bodyToMono(Void.class);

		mono.block();
	}

	@Autowired
	private WebClient _webClient;

}