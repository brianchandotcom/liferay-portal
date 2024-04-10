/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;

import java.net.URL;

import java.time.ZonedDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Wellington Barbosa
 */
@Component
public class MarketplaceCommandLineRunner implements CommandLineRunner {

	public void run(String... args) throws Exception {
		_processExpiredTrials();
		_processOnHoldTrials();
	}

	private void _deleteTrial(long orderId) throws Exception {
		WebClient.create(
			_marketplaceSpringBootUrl
		).delete(
		).uri(
			"/trial/" + orderId
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_liferayOAuthApplicationExternalReferenceCodes)
		).accept(
			MediaType.APPLICATION_JSON
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private JSONObject _getAvailabilityJSONObject() throws Exception {
		return new JSONObject(
			WebClient.create(
				_marketplaceSpringBootUrl
			).get(
			).uri(
				"/trial/availability"
			).header(
				HttpHeaders.AUTHORIZATION,
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_liferayOAuthApplicationExternalReferenceCodes)
			).accept(
				MediaType.APPLICATION_JSON
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private OrderResource _getOrderResource() throws Exception {
		return OrderResource.builder(
		).endpoint(
			new URL(_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain)
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_liferayOAuthApplicationExternalReferenceCodes)
		).build();
	}

	private void _postTrial(Order order) throws Exception {
		WebClient.create(
			_marketplaceSpringBootUrl
		).post(
		).uri(
			"/trial/provisioning"
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_liferayOAuthApplicationExternalReferenceCodes)
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).bodyValue(
			new JSONObject(
			).put(
				"classPK", order.getId()
			).put(
				"modelDTOOrder",
				new JSONObject(
				).put(
					"accountId", String.valueOf(order.getAccountId())
				)
			).toString()
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private void _processExpiredTrials() throws Exception {
		Page<Order> ordersPage = _getOrderResource().getOrdersPage(
			"",
			"orderStatus/any(x:(x eq " + _ORDER_STATUS_COMPLETED +
				")) and orderTypeExternalReferenceCode eq 'SOLUTIONS7'",
			Pagination.of(-1, -1), "");

		for (Order order : ordersPage.getItems()) {
			if (ZonedDateTime.parse(
					order.getCustomFields(
					).get(
						"trial-end-date"
					).toString()
				).isAfter(
					ZonedDateTime.now()
				)) {

				try {
					_deleteTrial(order.getId());

					if (_log.isInfoEnabled()) {
						_log.info(
							"Deleted expired trial for order: " +
								order.getId());
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}
	}

	private void _processOnHoldTrials() throws Exception {
		if (!_getAvailabilityJSONObject().getBoolean("available")) {
			return;
		}

		Page<Order> ordersPage = _getOrderResource().getOrdersPage(
			"",
			"orderStatus/any(x:(x eq " + _ORDER_STATUS_ON_HOLD +
				")) and orderTypeExternalReferenceCode eq 'SOLUTIONS7'",
			Pagination.of(-1, -1), "");

		for (Order order : ordersPage.getItems()) {
			try {
				_postTrial(order);

				if (_log.isInfoEnabled()) {
					_log.info("Trial process for order: " + order.getId());
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private static final int _ORDER_STATUS_COMPLETED = 0;

	private static final int _ORDER_STATUS_ON_HOLD = 20;

	private static final Log _log = LogFactory.getLog(
		MarketplaceCommandLineRunner.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${com.liferay.marketplace.spring.boot.url}")
	private String _marketplaceSpringBootUrl;

}