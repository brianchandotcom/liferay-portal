/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot2.BaseRestController;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.service.ConsoleService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.util.MarketplaceUtil;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/console")
@RestController
public class ConsoleRestController extends BaseRestController {

	@GetMapping("projects-usage")
	public String getProjectsUsage(
			@AuthenticationPrincipal Jwt jwt,
			@RequestParam(required = false) String emailAddress)
		throws Exception {

		if (emailAddress == null) {
			emailAddress = String.valueOf(
				jwt.getClaims(
				).get(
					"username"
				));
		}

		return _consoleService.getProjectsUsage(emailAddress);
	}

	@GetMapping("subscriptions/{orderId}")
	public String getSubscriptions(@PathVariable("orderId") long orderId)
		throws Exception {

		Order order = _marketplaceService.getOrder(orderId);

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		return customFields.get("cloud-provisioning");
	}

	@PostMapping("provisioning/{orderId}")
	public void postProvisioning(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("orderId") long orderId, @RequestBody String json)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Provisioning order " + orderId);
		}

		Order order = _marketplaceService.getOrder(orderId);

		_marketplaceService.deployCloudService(new JSONObject(json), order);
	}

	@PostMapping("uninstall-app/{orderId}")
	public void uninstallApp(
			@PathVariable("orderId") long orderId, @RequestBody String json)
		throws Exception {

		try {
			_consoleService.uninstallApp(orderId);

			JSONObject jsonObject = new JSONObject(json);

			Order order = _marketplaceService.getOrder(orderId);

			Map<String, String> customFields =
				(Map<String, String>)order.getCustomFields();

			JSONArray cloudProvisioningJSONArray = new JSONArray(
				customFields.get("cloud-provisioning"));

			JSONObject cloudProvisioningJSONObject =
				MarketplaceUtil.getCloudProvisioningJSONObject(
					cloudProvisioningJSONArray,
					jsonObject.getLong("orderItemId"));

			MarketplaceUtil.deleteDeployment(
				jsonObject.getString("id"), cloudProvisioningJSONObject);

			cloudProvisioningJSONObject.put(
				"shippedQuantity",
				cloudProvisioningJSONObject.getJSONArray(
					"deployments"
				).length());

			customFields.put(
				"cloud-provisioning", cloudProvisioningJSONArray.toString());

			_marketplaceService.updateOrder(
				customFields, orderId, order.getOrderStatus());

			if (_log.isInfoEnabled()) {
				_log.info("Uninstalled app for order " + orderId);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			_log.error("Unable to uninstall app for order " + orderId);

			throw exception;
		}
	}

	private static final Log _log = LogFactory.getLog(
		ConsoleRestController.class);

	@Value("${liferay.marketplace.console.auth.url}")
	private String _consoleAuthURL;

	@Autowired
	private ConsoleService _consoleService;

	@Autowired
	private MarketplaceService _marketplaceService;

}