/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.petra.string.StringBundler;

import java.net.URI;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
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
				"/o/c/activities"
			).queryParam(
				"filter",
				StringBundler.concat(
					"submitted eq true and activityStatus eq 'active' and ",
					"endDate le ", _toString(zonedDateTime.minusDays(15)),
					" and mdfReqToActs/mdfRequestStatus eq 'approved'")
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

				long activityId = itemJSONObject.getLong("id");

				ZonedDateTime zonedActivityEndDate = ZonedDateTime.parse(
					itemJSONObject.getString("endDate"));

				ZonedDateTime zonedActivityExpirationDate =
					zonedActivityEndDate.plusDays(30);

				JSONArray mdfClaimActivitiesJSONArray =
					itemJSONObject.getJSONArray("actToMDFClmActs");

				if (mdfClaimActivitiesJSONArray.length() == 0) {
					_sendNotification(
						activityId, zonedActivityExpirationDate, zonedDateTime);
				}
				else {
					JSONArray claimedMdfClaimActivityJSONArray =
						new JSONArray();

					for (int j = 0; j < mdfClaimActivitiesJSONArray.length();
						 j++) {

						JSONObject mdfClaimActivityJSONObject =
							mdfClaimActivitiesJSONArray.getJSONObject(j);

						Boolean selectedActivity =
							mdfClaimActivityJSONObject.getBoolean("selected");

						if (selectedActivity) {
							long mdfClaimId =
								mdfClaimActivityJSONObject.getLong(
									"r_mdfClmToMDFClmActs_c_mdfClaimId");

							responseJSONObject = _get(
								uriBuilder -> uriBuilder.path(
									"/o/c/mdfclaims/" + mdfClaimId
								).build());

							JSONObject mdfClaimStatusJSONObject =
								responseJSONObject.getJSONObject(
									"mdfClaimStatus");

							String mdfClaimStatusKey =
								mdfClaimStatusJSONObject.getString("key");

							if (!mdfClaimStatusKey.equals("draft") &&
								!mdfClaimStatusKey.equals(
									"moreInfoRequested") &&
								!mdfClaimStatusKey.equals("cancel") &&
								!mdfClaimStatusKey.equals("rejected")) {

								claimedMdfClaimActivityJSONArray.put(
									mdfClaimActivityJSONObject);

								break;
							}
						}
					}

					if (claimedMdfClaimActivityJSONArray.length() == 0) {
						_sendNotification(
							activityId, zonedActivityExpirationDate,
							zonedDateTime);
					}
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
				HttpHeaders.AUTHORIZATION,
				"Bearer " + _oAuth2AccessToken.getTokenValue()
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
			HttpHeaders.AUTHORIZATION,
			"Bearer " + _oAuth2AccessToken.getTokenValue()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	private void _sendNotification(
		long activityId, ZonedDateTime zonedActivityExpirationDate,
		ZonedDateTime zonedDateTime) {

		if (zonedActivityExpirationDate.toLocalDate(
			).isEqual(
				zonedDateTime.plusDays(
					15
				).toLocalDate()
			)) {

			_put(
				"",
				"/o/c/activities/" + activityId +
					"/object-actions/notificationDueDate15DaysTemplateAction");
		}
		else if (zonedActivityExpirationDate.toLocalDate(
				).isEqual(
					zonedDateTime.plusDays(
						5
					).toLocalDate()
				)) {

			_put(
				"",
				"/o/c/activities/" + activityId +
					"/object-actions/notificationDueDate5DaysTemplateAction");
		}
		else if (zonedActivityExpirationDate.toLocalDate(
				).isEqual(
					zonedDateTime.plusDays(
						1
					).toLocalDate()
				)) {

			_put(
				"",
				"/o/c/activities/" + activityId +
					"/object-actions/notificationDueDate1DayTemplateAction");
		}
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