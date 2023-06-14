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

import java.net.URI;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

/**
 * @author Jair Medeiros
 */
@Component
public class PartnerCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();

		JSONObject responseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/activities"
			).queryParam(
				"filter",
				"activityStatus eq 'approved' and startDate le " +
					_toString(zonedDateTime)
			).queryParam(
				"page", "1"
			).queryParam(
				"pageSize", "-1"
			).build());

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

			_put(itemsJSONArray.toString(), "/o/c/activities/batch");
		}

		responseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/activities"
			).queryParam(
				"filter",
				"activityStatus eq 'active' and endDate lt " +
					_toString(zonedDateTime.minusDays(30))
			).queryParam(
				"page", "1"
			).queryParam(
				"pageSize", "-1"
			).build());

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

			_put(itemsJSONArray.toString(), "/o/c/activities/batch");
		}

		responseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/mdfclaimactivities"
			).queryParam(
				"filter",
				"selected eq true and actToMDFClmActs/submitted eq true and actToMDFClmActs/activityStatus eq 'active' and actToMDFClmActs/endDate le " +
					_toString(zonedDateTime.minusDays(15)) +
						" and mdfClmToMDFClmActs/mdfClaimStatus ne 'draft' and mdfClmToMDFClmActs/mdfClaimStatus ne 'moreInfoRequested'"
			).queryParam(
				"nestedFields", "actToMDFClmActs"
			).queryParam(
				"page", "1"
			).queryParam(
				"pageSize", "-1"
			).build());

		if (responseJSONObject.getInt("totalCount") > 0) {
			JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				String activityERC = itemJSONObject.getString(
					"r_actToMDFClmActs_c_activityERC");

				JSONObject activityJSONObject = itemJSONObject.getJSONObject(
					"r_actToMDFClmActs_c_activity");

				ZonedDateTime zonedActivityEndDate = ZonedDateTime.parse(
					activityJSONObject.getString("endDate"));

				ZonedDateTime zonedActivityExpirationDate =
					zonedActivityEndDate.plusDays(30);

				if (zonedActivityExpirationDate.toLocalDate(
					).isEqual(
						zonedDateTime.plusDays(
							15
						).toLocalDate()
					)) {

					_sendNotification(itemsJSONArray.toString(), activityERC);

					return;
				}
				else if (zonedActivityExpirationDate.toLocalDate(
						).isEqual(
							zonedDateTime.plusDays(
								5
							).toLocalDate()
						)) {

					_sendNotification(itemsJSONArray.toString(), activityERC);

					return;
				}
				else if (zonedActivityExpirationDate.toLocalDate(
						).isEqual(
							zonedDateTime.plusDays(
								1
							).toLocalDate()
						)) {

					_sendNotification(itemsJSONArray.toString(), activityERC);

					return;
				}
			}
		}
	}

	private JSONObject _get(Function<UriBuilder, URI> uriFunction) {
		return new JSONObject(
			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).get(
			).uri(
				uriBuilder -> uriFunction.apply(uriBuilder)
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private void _put(String bodyValue, String path) {
		WebClient.create(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).put(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			"Authorization", "Bearer " + _oAuth2AccessToken.getTokenValue()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	private void _sendNotification(
		String bodyValue, String externalReferenceCode) {

		_put(
			bodyValue,
			"/o/c/activities/by-external-reference-code/" +
				externalReferenceCode +
					"/object-actions/notificationDueDateTemplateAction");
	}

	private String _toString(ZonedDateTime zonedDateTime) {
		return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}