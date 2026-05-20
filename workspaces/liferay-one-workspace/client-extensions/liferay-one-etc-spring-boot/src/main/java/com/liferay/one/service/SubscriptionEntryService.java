/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.one.model.SubscriptionEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jenny Chen
 * @author Amos Fong
 */
@Component
public class SubscriptionEntryService extends BaseService {

	public SubscriptionEntry addSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		SubscriptionEntry existingSubscriptionEntry = fetchSubscriptionEntry(
			className, classPK, userId);

		if (existingSubscriptionEntry != null) {
			return existingSubscriptionEntry;
		}

		JSONObject subscriptionEntryJSONObject = new JSONObject(
		).put(
			"className", className
		).put(
			"classPK", classPK
		).put(
			"userId", userId
		);

		String response = post(
			getAuthorization(), subscriptionEntryJSONObject.toString(),
			UriComponentsBuilder.fromPath(
				"/o/c/subscriptionentries"
			).build(
			).toUri());

		return new SubscriptionEntry(new JSONObject(response));
	}

	public void deleteSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		SubscriptionEntry subscriptionEntry = fetchSubscriptionEntry(
			className, classPK, userId);

		if (subscriptionEntry == null) {
			return;
		}

		delete(
			getAuthorization(), "",
			UriComponentsBuilder.fromPath(
				"/o/c/subscriptionentries/" +
					subscriptionEntry.getSubscriptionEntryId()
			).build(
			).toUri());
	}

	public SubscriptionEntry fetchSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		List<SubscriptionEntry> subscriptionEntries = getSubscriptionEntries(
			StringBundler.concat(
				"(className eq '", className, "') and (classPK eq ", classPK,
				") and (userId eq ", userId, ")"));

		if (subscriptionEntries.isEmpty()) {
			return null;
		}

		return subscriptionEntries.get(0);
	}

	public List<SubscriptionEntry> getSubscriptionEntries(String filterString)
		throws Exception {

		UriComponentsBuilder uriComponentsBuilder =
			UriComponentsBuilder.fromPath("/o/c/subscriptionentries");

		if (filterString != null) {
			uriComponentsBuilder.queryParam("filter", filterString);
		}

		String response = get(
			getAuthorization(),
			uriComponentsBuilder.build(
			).toUri());

		List<SubscriptionEntry> subscriptionEntries = new ArrayList<>();

		if (Validator.isNull(response)) {
			return subscriptionEntries;
		}

		try {
			JSONObject jsonObject = new JSONObject(response);

			JSONArray jsonArray = jsonObject.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++) {
				subscriptionEntries.add(
					new SubscriptionEntry(jsonArray.getJSONObject(i)));
			}

			return subscriptionEntries;
		}
		catch (Exception exception) {
			_log.error("Unable to parse JSON: " + response, exception);

			return subscriptionEntries;
		}
	}

	protected String getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-one-etc-spring-boot-oaua");
	}

	private static final Log _log = LogFactory.getLog(
		SubscriptionEntryService.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}