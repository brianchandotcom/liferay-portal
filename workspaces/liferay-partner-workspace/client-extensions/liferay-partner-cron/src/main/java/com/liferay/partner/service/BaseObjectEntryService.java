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
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.reactive.function.client.WebClient;

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

		return Page.of(
			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).get(
			).uri(
				uriBuilder -> {
					if (pagination != null) {
						uriBuilder.queryParam(
							"page", String.valueOf(pagination.getPage()));
						uriBuilder.queryParam(
							"pageSize",
							String.valueOf(pagination.getPageSize()));
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
				}
			).header(
				"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block(),
			_toDTOFunction);
	}

	public void putEntryBatch(String callbackURL, Object object)
		throws Exception {

		WebClient.create(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).put(
		).uri(
			uriBuilder -> {
				if (callbackURL != null) {
					uriBuilder.queryParam(
						"callbackURL", String.valueOf(callbackURL));
				}

				return uriBuilder.path(
					_entityURLPath + "/batch"
				).build();
			}
		).header(
			"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
		).bodyValue(
			object.toString()
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	private final String _entityURLPath;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

	private final Function<String, T> _toDTOFunction;

}