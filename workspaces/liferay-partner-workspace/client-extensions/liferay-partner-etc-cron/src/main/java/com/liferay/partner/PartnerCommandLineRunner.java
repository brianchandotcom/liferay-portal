/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jair Medeiros
 */
@Component
public class PartnerCommandLineRunner
	extends BaseRestController implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();

		JSONObject responseJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/activities"
				).queryParam(
					"filter",
					"activityStatus eq 'active' and endDate lt " +
						_toString(zonedDateTime.minusDays(_EXPIRATION_DAYS))
				).queryParam(
					"page", "1"
				).queryParam(
					"pageSize", "-1"
				).build(
				).toUri()));

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
				put(
					_getAuthorization(), itemsJSONArray.toString(),
					UriComponentsBuilder.fromPath(
						"/o/c/activities/batch"
					).build(
					).toUri());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		responseJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/activities"
				).queryParam(
					"filter",
					StringBundler.concat(
						"submitted eq true and activityStatus eq 'active' and ",
						"endDate le ",
						_toString(
							zonedDateTime.minusDays(_EXPIRATION_DAYS - 15)),
						" and mdfReqToActs/mdfRequestStatus eq 'approved'")
				).queryParam(
					"nestedFields", "actToMDFClmActs"
				).queryParam(
					"page", "1"
				).queryParam(
					"pageSize", "-1"
				).build(
				).toUri()));

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

							responseJSONObject = new JSONObject(
								get(
									_getAuthorization(),
									UriComponentsBuilder.fromPath(
										"/o/c/mdfclaims/" + mdfClaimId
									).build(
									).toUri()));

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

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-partner-etc-cron-oahs");
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

			put(
				_getAuthorization(), "",
				UriComponentsBuilder.fromPath(
					sb.toString()
				).build(
				).toUri());

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

}