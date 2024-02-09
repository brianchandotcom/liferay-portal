/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.petra.string.StringBundler;

import java.net.URI;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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
			List<String> activityIndicators = new ArrayList<>();

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				JSONObject activityStatusJSONObject =
					itemJSONObject.getJSONObject("activityStatus");

				activityStatusJSONObject.put(
					"key", "active"
				).put(
					"name", "Active"
				);

				activityIndicators.add(
					StringBundler.concat(
						itemJSONObject.getString("name"), " (",
						itemJSONObject.getLong("id"), ")"));
			}

			try {
				_put(itemsJSONArray.toString(), "/o/c/activities/batch");

				if (_log.isInfoEnabled()) {
					_log.info(
						"The following activities were Activated in batch: " +
							String.join(", ", activityIndicators));
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		responseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/activities"
			).queryParam(
				"filter",
				"activityStatus eq 'active' and endDate lt " +
					_toString(zonedDateTime.minusDays(_EXPIRATION_DAYS))
			).queryParam(
				"page", "1"
			).queryParam(
				"pageSize", "-1"
			).build());

		if (responseJSONObject.getInt("totalCount") > 0) {
			JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");
			List<String> activityIndicators = new ArrayList<>();

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				JSONObject activityStatusJSONObject =
					itemJSONObject.getJSONObject("activityStatus");

				activityStatusJSONObject.put(
					"key", "expired"
				).put(
					"name", "Expired"
				);

				activityIndicators.add(
					StringBundler.concat(
						itemJSONObject.getString("name"), " (",
						itemJSONObject.getLong("id"), ")"));
			}

			try {
				_put(itemsJSONArray.toString(), "/o/c/activities/batch");

				if (_log.isInfoEnabled()) {
					_log.info(
						"The following activities were Expired in batch: " +
							String.join(", ", activityIndicators));
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		responseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/activities"
			).queryParam(
				"filter",
				StringBundler.concat(
					"submitted eq true and activityStatus eq 'active' and ",
					"endDate le ",
					_toString(zonedDateTime.minusDays(_EXPIRATION_DAYS - 15)),
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

				long mdfRequestId = itemJSONObject.getLong(
					"r_mdfReqToActs_c_mdfRequestId");

				ZonedDateTime zonedActivityEndDate = ZonedDateTime.parse(
					itemJSONObject.getString("endDate"));

				ZonedDateTime zonedActivityExpirationDate =
					zonedActivityEndDate.plusDays(_EXPIRATION_DAYS);

				JSONArray mdfClaimActivitiesJSONArray =
					itemJSONObject.getJSONArray("actToMDFClmActs");

				if ((mdfClaimActivitiesJSONArray.length() == 0) &&
					(mdfRequestId == 19290271)) {

					System.out.println("mdfRequestId: " + mdfRequestId);
					_sendNotification(
						itemJSONObject, zonedActivityExpirationDate,
						zonedDateTime);
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

					if ((claimedMdfClaimActivityJSONArray.length() == 0) &&
						(mdfRequestId == 19290271)) {

						System.out.println("mdfRequestId: " + mdfRequestId);

						_sendNotification(
							itemJSONObject, zonedActivityExpirationDate,
							zonedDateTime);
					}
				}
			}
		}
	}

	private JSONObject _get(Function<UriBuilder, URI> uriFunction) {
		return new JSONObject(
			_getWebClient(
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

	private WebClient _getWebClient() {
		return WebClient.builder(
		).baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).exchangeStrategies(
			ExchangeStrategies.builder(
			).codecs(
				clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs(
				).maxInMemorySize(
					5 * 1024 * 1024
				)
			).build()
		).build();
	}

	private void _put(String bodyValue, String path) {
		_getWebClient(
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
		JSONObject activityJSONObject,
		ZonedDateTime zonedActivityExpirationDate,
		ZonedDateTime zonedDateTime) {

		long activityId = activityJSONObject.getLong("id");
		String activityName = activityJSONObject.getString("name");

		if (zonedActivityExpirationDate.toLocalDate(
			).isEqual(
				zonedDateTime.plusDays(
					15
				).toLocalDate()
			)) {

			try {
				_put(
					"",
					StringBundler.concat(
						"/o/c/activities/", activityId, "/object-actions",
						"/notificationDueDate15DaysTemplateAction"));

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Email notification about being 15 days from the ",
							"due date was triggered for the creator of ",
							"activity named: ", activityName, " (", activityId,
							") via the standalone object action"));
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
		else if (zonedActivityExpirationDate.toLocalDate(
				).isEqual(
					zonedDateTime.plusDays(
						5
					).toLocalDate()
				)) {

			try {
				_put(
					"",
					StringBundler.concat(
						"/o/c/activities/", activityId, "/object-actions",
						"/notificationDueDate5DaysTemplateAction"));

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Email notification about being 5 days from the ",
							"due date was triggered for the creator of ",
							"activity named: ", activityName, " (", activityId,
							") via the standalone object action"));
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
		else if (zonedActivityExpirationDate.toLocalDate(
				).isEqual(
					zonedDateTime.plusDays(
						1
					).toLocalDate()
				)) {

			try {
				_put(
					"",
					StringBundler.concat(
						"/o/c/activities/", activityId, "/object-actions",
						"/notificationDueDate1DayTemplateAction"));

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Email notification about being 1 day from the ",
							"due date was triggered for the creator of ",
							"activity named: ", activityName, " (", activityId,
							") via the standalone object action"));
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private String _toString(ZonedDateTime zonedDateTime) {
		return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	private static final int _EXPIRATION_DAYS = 45;

	private static final Log _log = LogFactory.getLog(
		PartnerCommandLineRunner.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}