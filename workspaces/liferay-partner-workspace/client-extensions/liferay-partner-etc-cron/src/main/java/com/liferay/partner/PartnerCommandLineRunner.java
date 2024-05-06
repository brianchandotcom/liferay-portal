/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;

import java.net.URI;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
				"activityStatus eq 'active' and endDate lt " +
					_toString(zonedDateTime.minusDays(_EXPIRATION_DAYS))
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

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Expiring activity ", itemJSONObject.getLong("id"),
							" with name ", itemJSONObject.getString("name")));
				}
			}

			try {
				_put(itemsJSONArray.toString(), "/o/c/activities/batch");
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

				ZonedDateTime zonedActivityEndDate = ZonedDateTime.parse(
					itemJSONObject.getString("endDate"));

				ZonedDateTime zonedActivityExpirationDate =
					zonedActivityEndDate.plusDays(_EXPIRATION_DAYS);

				JSONArray mdfClaimActivitiesJSONArray =
					itemJSONObject.getJSONArray("actToMDFClmActs");

				if (mdfClaimActivitiesJSONArray.length() == 0) {
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

					if (claimedMdfClaimActivityJSONArray.length() == 0) {
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
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-partner-etc-cron-oauth-application-headless-server");
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
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	private void _sendNotification(
		JSONObject activityJSONObject, int plusDays,
		ZonedDateTime zonedActivityExpirationDate,
		ZonedDateTime zonedDateTime) {

		if (!zonedActivityExpirationDate.toLocalDate(
			).isEqual(
				zonedDateTime.plusDays(
					plusDays
				).toLocalDate()
			)) {

			return;
		}

		try {
			StringBundler sb = new StringBundler(6);

			sb.append("/o/c/activities/");
			sb.append(activityJSONObject.getLong("id"));
			sb.append("/object-actions/notificationDueDate");
			sb.append(plusDays);

			if (plusDays == 1) {
				sb.append("Day");
			}
			else {
				sb.append("Days");
			}

			sb.append("TemplateAction");

			_put("", sb.toString());

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Triggering a ", plusDays,
						" day notification for activity ",
						activityJSONObject.getLong("id"), " with name ",
						activityJSONObject.getString("name")));
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _sendNotification(
		JSONObject activityJSONObject,
		ZonedDateTime zonedActivityExpirationDate,
		ZonedDateTime zonedDateTime) {

		_sendNotification(
			activityJSONObject, 1, zonedActivityExpirationDate, zonedDateTime);
		_sendNotification(
			activityJSONObject, 5, zonedActivityExpirationDate, zonedDateTime);
		_sendNotification(
			activityJSONObject, 15, zonedActivityExpirationDate, zonedDateTime);
	}

	private String _toString(ZonedDateTime zonedDateTime) {
		return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	private static final int _EXPIRATION_DAYS = 45;

	private static final Log _log = LogFactory.getLog(
		PartnerCommandLineRunner.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}