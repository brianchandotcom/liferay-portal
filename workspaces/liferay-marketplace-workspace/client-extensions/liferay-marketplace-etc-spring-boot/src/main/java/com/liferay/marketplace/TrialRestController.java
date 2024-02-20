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

		_initResourceBuilders();

		Order order = new Order();

		JSONObject jsonObject = new JSONObject(json);

		long classPK = jsonObject.getLong("classPK");

		if (_log.isInfoEnabled()) {
			_log.info("Provision order " + classPK);
		}

		order.setId(() -> classPK);

		JSONObject modelDTOOrderJSONObject = jsonObject.getJSONObject(
			"modelDTOOrder");

		String accountId = modelDTOOrderJSONObject.getString("accountId");

		if (_hasAccountOrders(accountId)) {
			_log.error(
				"Account " + accountId + " already has a provisioned order");

			order.setOrderStatus(() -> _ORDER_STATUS_CANCELLED);

			_orderResource.patchOrder(order.getId(), order);

			return;
		}

		order.setOrderStatus(() -> _ORDER_STATUS_PROCESSING);

		_orderResource.patchOrder(order.getId(), order);

		Map<String, String> customFields =
			(Map<String, String>)new CustomField();

		customFields.put("Site Initializer", "com.liferay.blank");
		customFields.put(
			"trial-expires-in",
			new Date(
			).toString());

		PortalInstance portalInstance = _postPortalInstance(
			jwt, modelDTOOrderJSONObject.getString("creatorEmailAddress"),
			order.getId());

		customFields.put("trial-virtualhost", portalInstance.getVirtualHost());

		order.setCustomFields(() -> customFields);

		order.setOrderStatus(() -> _ORDER_STATUS_COMPLETED);

		_orderResource.patchOrder(order.getId(), order);
	}

	private boolean _hasAccountOrders(String accountId) throws Exception {

		// TODO Make this a single query

		Page<Order> ordersPage = _orderResource.getOrdersPage(
			"", "accountId/any(x:(x eq " + accountId + "))",
			Pagination.of(-1, -1), "");

		for (Order order : ordersPage.getItems()) {
			if (Objects.equals(
					order.getOrderTypeExternalReferenceCode(), "SOLUTIONS7")) {

				return true;
			}
		}

		return false;
	}

	private void _initResourceBuilders() throws Exception {
		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-marketplace-etc-spring-boot-oauth-application-" +
					"headless-server");
		URL liferayDXPURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

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

		PortalInstance portalInstance = new PortalInstance();

		portalInstance.setAdmin(
			() -> new Admin() {
				{
					setEmailAddress(() -> emailAddress);
					setFamilyName(
						() -> String.valueOf(jwt.getClaim("username")));
					setGivenName(
						() -> String.valueOf(jwt.getClaim("username")));
				}
			});

		String domain = "tryitnow-" + orderId + ".us.demo.lxc.liferay.com";

		portalInstance.setDomain(() -> domain);
		portalInstance.setPortalInstanceId(() -> domain);

		portalInstance.setSiteInitializerKey(
			() -> "com.liferay.site.initializer.welcome");
		portalInstance.setVirtualHost(() -> domain);

		portalInstance = _portalInstanceResource.postPortalInstance(
			portalInstance);

		if (_log.isInfoEnabled()) {
			_log.info("Created portal instance " + portalInstance);
		}

		return portalInstance;
	}

	private static final int _ORDER_STATUS_CANCELLED = 8;

	private static final int _ORDER_STATUS_COMPLETED = 0;

	private static final int _ORDER_STATUS_PROCESSING = 10;

	private static final Log _log = LogFactory.getLog(
		TrialRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	private OrderResource _orderResource;
	private PortalInstanceResource _portalInstanceResource;

}