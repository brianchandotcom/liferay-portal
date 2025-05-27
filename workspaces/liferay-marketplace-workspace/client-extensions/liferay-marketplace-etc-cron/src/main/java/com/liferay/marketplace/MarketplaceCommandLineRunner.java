/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;

import java.net.URI;
import java.net.URL;

import java.time.ZonedDateTime;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 * @author Wellington Barbosa
 */
@Component
public class MarketplaceCommandLineRunner
	extends BaseRestController implements CommandLineRunner {

	public void run(String... args) throws Exception {
		_processInProgressTrials();

		_processOnHoldTrials();
	}

	private JSONObject _getAvailabilityJSONObject() throws Exception {
		return new JSONObject(
			get(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_liferayOAuthApplicationExternalReferenceCodes),
				URI.create(
					_liferayMarketplaceEtcSpringBootURL.toString() +
						"/trial/availability")));
	}

	private Page<Order> _getOrdersPage(int orderStatus) throws Exception {
		OrderResource orderResource = OrderResource.builder(
		).endpoint(
			new URL(_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain)
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_liferayOAuthApplicationExternalReferenceCodes)
		).build();

		return orderResource.getOrdersPage(
			"",
			"orderStatus/any(x:(x eq " + orderStatus +
				")) and orderTypeExternalReferenceCode eq 'SOLUTIONS7'",
			Pagination.of(-1, -1), "");
	}

	private void _postTrialExpire(long orderId) throws Exception {
		post(
			null,
			Collections.singletonMap(
				HttpHeaders.AUTHORIZATION,
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_liferayOAuthApplicationExternalReferenceCodes)),
			URI.create(
				_liferayMarketplaceEtcSpringBootURL.toString() +
					"/trial/expire/" + orderId));
	}

	private void _postTrialNotifyEnd(long orderId) throws Exception {
		post(
			null,
			Collections.singletonMap(
				HttpHeaders.AUTHORIZATION,
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_liferayOAuthApplicationExternalReferenceCodes)),
			URI.create(
				_liferayMarketplaceEtcSpringBootURL.toString() +
					"/trial/notify-end/" + orderId));
	}

	private void _postTrialProvisioning(Order order) throws Exception {
		post(
			new JSONObject(
			).put(
				"classPK", order.getId()
			).put(
				"modelDTOOrder",
				new JSONObject(
				).put(
					"accountId", String.valueOf(order.getAccountId())
				)
			).toString(),
			Collections.singletonMap(
				HttpHeaders.AUTHORIZATION,
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_liferayOAuthApplicationExternalReferenceCodes)),
			URI.create(
				_liferayMarketplaceEtcSpringBootURL.toString() +
					"/trial/provisioning"));
	}

	private void _processInProgressTrials() throws Exception {
		Page<Order> page = _getOrdersPage(_ORDER_STATUS_IN_PROGRESS);

		for (Order order : page.getItems()) {
			try {
				ZonedDateTime nowZonedDateTime = ZonedDateTime.now();

				Map<String, String> customFields =
					(Map<String, String>)order.getCustomFields();

				ZonedDateTime trialEndDateZonedDateTime = ZonedDateTime.parse(
					customFields.get("trial-end-date"));

				if (nowZonedDateTime.isAfter(trialEndDateZonedDateTime)) {
					_postTrialExpire(order.getId());

					if (_log.isInfoEnabled()) {
						_log.info("Processed expired order " + order.getId());
					}

					continue;
				}

				if (customFields.get(
						"trial-notify-end-date"
					).isEmpty() &&
					Objects.equals(
						nowZonedDateTime.getDayOfMonth(),
						trialEndDateZonedDateTime.minusDays(
							1
						).getDayOfMonth())) {

					_postTrialNotifyEnd(order.getId());

					if (_log.isInfoEnabled()) {
						_log.info(
							"Processed notify end of trial for order " +
								order.getId());
					}
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _processOnHoldTrials() throws Exception {
		Page<Order> page = _getOrdersPage(_ORDER_STATUS_ON_HOLD);

		if (page.getTotalCount() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("There are no on hold orders");
			}

			return;
		}

		JSONObject availabilityJSONObject = _getAvailabilityJSONObject();

		if (!availabilityJSONObject.getBoolean("active")) {
			if (_log.isInfoEnabled()) {
				_log.info("There are no available seats");
			}

			return;
		}

		long available = availabilityJSONObject.getLong("available");

		for (Order order : page.getItems()) {
			if (available == 0) {
				if (_log.isInfoEnabled()) {
					_log.info("There are no available seats");
				}

				break;
			}

			try {
				if (_log.isInfoEnabled()) {
					_log.info("Processing on hold order " + order.getId());
				}

				_postTrialProvisioning(order);

				if (_log.isInfoEnabled()) {
					_log.info("Processed on hold order " + order.getId());
				}

				available--;
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private static final int _ORDER_STATUS_IN_PROGRESS = 6;

	private static final int _ORDER_STATUS_ON_HOLD = 20;

	private static final Log _log = LogFactory.getLog(
		MarketplaceCommandLineRunner.class);

	@Value("${liferay.marketplace.etc.spring.boot.url}")
	private URL _liferayMarketplaceEtcSpringBootURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}