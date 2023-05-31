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

package com.liferay.partner;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Jair Medeiros
 */
@Component
public class PartnerCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		ZonedDateTime nowZonedDateTime = ZonedDateTime.now();

		String formattedNowZonedDateTime = nowZonedDateTime.format(
			DateTimeFormatter.ISO_LOCAL_DATE);

		String response = WebClient.create(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).get(
		).uri(
			uriBuilder -> {
				uriBuilder.queryParam("page", String.valueOf(1));
				uriBuilder.queryParam("pageSize", String.valueOf(-1));

				uriBuilder.queryParam(
					"filter",
					"activityStatus eq 'approved' and startDate le " +
						formattedNowZonedDateTime);

				return uriBuilder.path(
					"/o/c/activities"
				).build();
			}
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		JSONObject responseJSONObject = new JSONObject(response);

		if (responseJSONObject.getInt("totalCount") > 0) {
			JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				JSONObject activityStatusJSONObject =
					itemJSONObject.getJSONObject("activityStatus");

				activityStatusJSONObject.put(
					"key", "active"
				).put(
					"name", "Active"
				);
			}

			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).put(
			).uri(
				uriBuilder -> uriBuilder.path(
					"/o/c/activities/batch"
				).build()
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).header(
				"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
			).bodyValue(
				itemsJSONArray.toString()
			).retrieve(
			).bodyToMono(
				Void.class
			).block();
		}

		ZonedDateTime nowZonedDateTimeMinus30Days = nowZonedDateTime.minusDays(
			30);

		String formattedNowZonedDateTimeMinus30Days =
			nowZonedDateTimeMinus30Days.format(
				DateTimeFormatter.ISO_LOCAL_DATE);

		response = WebClient.create(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).get(
		).uri(
			uriBuilder -> {
				uriBuilder.queryParam("page", String.valueOf(1));
				uriBuilder.queryParam("pageSize", String.valueOf(-1));

				uriBuilder.queryParam(
					"filter",
					"activityStatus eq 'active' and endDate lt " +
						formattedNowZonedDateTimeMinus30Days);

				return uriBuilder.path(
					"/o/c/activities"
				).build();
			}
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		responseJSONObject = new JSONObject(response);

		if (responseJSONObject.getInt("totalCount") > 0) {
			JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				JSONObject activityStatusJSONObject =
					itemJSONObject.getJSONObject("activityStatus");

				activityStatusJSONObject.put(
					"key", "expired"
				).put(
					"name", "Expired"
				);
			}

			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).put(
			).uri(
				uriBuilder -> uriBuilder.path(
					"/o/c/activities/batch"
				).build()
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).header(
				"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
			).bodyValue(
				itemsJSONArray.toString()
			).retrieve(
			).bodyToMono(
				Void.class
			).block();
		}
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}