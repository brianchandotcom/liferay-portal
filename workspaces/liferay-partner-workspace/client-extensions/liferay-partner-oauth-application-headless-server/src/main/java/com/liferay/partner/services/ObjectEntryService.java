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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Jair Medeiros
 * @author Thaynam Lázaro
 */
@Service
public class ObjectEntryService {

	public Mono<String> addOrUpdateBatch(
		String objectDefinitionName, String value) {

		return _webClient.put(
		).uri(
			"/o/c/" + objectDefinitionName
		).bodyValue(
			value
		).retrieve(
		).bodyToMono(
			String.class
		).doOnError(
			error -> _log.error(error)
		);
	}

	private static final Log _log = LogFactory.getLog(ObjectEntryService.class);

	@Autowired
	private WebClient _webClient;

}