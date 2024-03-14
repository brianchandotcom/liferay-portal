/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.CustomField;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.headless.portal.instances.client.dto.v1_0.Admin;
import com.liferay.headless.portal.instances.client.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.client.resource.v1_0.PortalInstanceResource;

import java.net.URL;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/trial")
@RestController
public class TrialRestController extends BaseRestController {

	@PostMapping("provisioning")
	public void postProvisioning(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		if (_log.isInfoEnabled()) {
			_log.info(
				"New trial request for order " +
					jsonObject.getNumber("classPK"));
		}

		_initResourceBuilders();

		JSONObject modelDTOOrderJSONObject = jsonObject.getJSONObject(
			"modelDTOOrder");

		String accountId = modelDTOOrderJSONObject.getString("accountId");

		Order order = new Order();

		order.setId(jsonObject.getLong("classPK"));

		if (!_checkAccountOrders(accountId)) {
			_log.error(
				accountId + " exceeded the limit of trials for this account");

			order.setOrderStatus(_COMMERCE_ORDER_STATUS_CANCELLED);

			_orderResource.patchOrder(order.getId(), order);

			return;
		}

		order.setOrderStatus(_COMMERCE_ORDER_STATUS_PROCESSING);

		_orderResource.patchOrder(order.getId(), order);

		PortalInstance portalInstance = _postPortalInstance(
			jwt, modelDTOOrderJSONObject.getString("creatorEmailAddress"),
			order.getId());

		Map<String, String> customFields =
			(Map<String, String>)new CustomField();

		customFields.put("Site Initializer", "com.liferay.blank");
		customFields.put(
			"trial-expires-in",
			new Date(
			).toString());
		customFields.put("trial-virtualhost", portalInstance.getVirtualHost());

		order.setOrderStatus(_COMMERCE_ORDER_STATUS_COMPLETED);

		_orderResource.patchOrder(order.getId(), order);
	}

	private boolean _checkAccountOrders(String accountId) throws Exception {
		int trialCount = 0;

		Page<Order> ordersPage = _orderResource.getOrdersPage(
			"", "accountId/any(x:(x eq " + accountId + "))",
			Pagination.of(-1, -1), "");

		for (Order order : ordersPage.getItems()) {
			if (Objects.equals(
					order.getOrderTypeExternalReferenceCode(),
					_ORDER_TYPE_EXTERNAL_REFERENCE_CODE)) {

				trialCount++;

				if (trialCount > 1) {
					return false;
				}
			}
		}

		return true;
	}

	private void _initResourceBuilders() throws Exception {
		URL liferayDXPURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-marketplace-etc-spring-boot-oauth-application-" +
					"headless-server");

		_orderResource = OrderResource.builder(
		).endpoint(
			liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).build();

		_portalInstanceResource = PortalInstanceResource.builder(
		).endpoint(
			liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).build();
	}

	private PortalInstance _postPortalInstance(
			Jwt jwt, String emailAddress, long orderId)
		throws Exception {

		Admin admin = new Admin();

		admin.setEmailAddress(emailAddress);
		admin.setFamilyName(
			jwt.getClaim(
				"username"
			).toString());
		admin.setGivenName(
			jwt.getClaim(
				"username"
			).toString());

		String domain = "tryitnow-" + orderId + ".us.demo.lxc.liferay.com";

		PortalInstance portalInstance = new PortalInstance();

		portalInstance.setAdmin(admin);
		portalInstance.setDomain(domain);
		portalInstance.setPortalInstanceId(domain);
		portalInstance.setSiteInitializerKey(
			"com.liferay.site.initializer.welcome");
		portalInstance.setVirtualHost(domain);

		portalInstance = _portalInstanceResource.postPortalInstance(
			portalInstance);

		if (_log.isInfoEnabled()) {
			_log.info("Portal instance created " + portalInstance);
		}

		return portalInstance;
	}

	private static final int _COMMERCE_ORDER_STATUS_CANCELLED = 8;

	private static final int _COMMERCE_ORDER_STATUS_COMPLETED = 0;

	private static final int _COMMERCE_ORDER_STATUS_PROCESSING = 10;

	private static final String _ORDER_TYPE_EXTERNAL_REFERENCE_CODE =
		"SOLUTIONS7";

	private static final Log _log = LogFactory.getLog(
		TrialRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	private OrderResource _orderResource;
	private PortalInstanceResource _portalInstanceResource;

}