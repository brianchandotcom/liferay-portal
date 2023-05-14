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

import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.partner.dto.BaseDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Jair Medeiros
 */
public abstract class BaseObjectEntryService<T extends BaseDTO> {

	public BaseObjectEntryService(
		String entityURLPath, Function<String, T> toDTOFunction) {

		_entityURLPath = entityURLPath;
		_toDTOFunction = toDTOFunction;
	}

	public Page<T> getEntriesPage(
			String search, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		WebClient.RequestHeadersUriSpec<?> requestHeadersSpec =
			_webClient.get();

		requestHeadersSpec.uri(
			uriBuilder -> {
				if (pagination != null) {
					uriBuilder.queryParam(
						"page", String.valueOf(pagination.getPage()));
					uriBuilder.queryParam(
						"pageSize", String.valueOf(pagination.getPageSize()));
				}

				if (sortString != null) {
					uriBuilder.queryParam("sort", sortString);
				}

				if (filterString != null) {
					uriBuilder.queryParam("filter", filterString);
				}

				if (search != null) {
					uriBuilder.queryParam("search", search);
				}

				return uriBuilder.path(
					_entityURLPath
				).build();
			});

		WebClient.ResponseSpec responseSpec = requestHeadersSpec.retrieve();

		Mono<String> mono = responseSpec.bodyToMono(String.class);

		return Page.of(mono.block(), _toDTOFunction);
	}

	private final String _entityURLPath;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	private final Function<String, T> _toDTOFunction;

	@Autowired
	private WebClient _webClient;

}