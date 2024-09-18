/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.service.ConsoleService;
import com.liferay.marketplace.service.MarketplaceService;

import java.util.Map;
import java.util.Objects;

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

		String finalEmailAddress = emailAddress;

		return _consoleService.getWebClient(
		).get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"/admin/user-projects-plan-usage"
			).queryParam(
				"userEmail", finalEmailAddress
			).build()
		).retrieve(
		).bodyToMono(
			String.class
		).block();
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

		Order order = _marketplaceService.getOrder(orderId);

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		JSONObject jsonObject = new JSONObject(json);

		JSONObject appJSONObject = _consoleService.deployApp(
			jwt.getClaimAsString("username"), String.valueOf(orderId),
			jsonObject.getString("projectId"));

		JSONArray cloudProvisioningJSONArray = new JSONArray(
			customFields.get("cloud-provisioning"));

		for (int i = 0; i < cloudProvisioningJSONArray.length(); i++) {
			JSONObject cloudProvisioningJSONObject =
				cloudProvisioningJSONArray.getJSONObject(i);

			if (!Objects.equals(
					cloudProvisioningJSONObject.getLong("orderItemId"),
					jsonObject.getLong("orderItemId"))) {

				continue;
			}

			cloudProvisioningJSONObject.put(
				"deployments",
				cloudProvisioningJSONObject.getJSONArray(
					"deployments"
				).put(
					appJSONObject
				)
			).put(
				"shippedQuantity",
				cloudProvisioningJSONObject.getInt("shippedQuantity") + 1
			);
		}

		customFields.put(
			"cloud-provisioning", cloudProvisioningJSONArray.toString());

		_marketplaceService.updateOrder(
			customFields, orderId, order.getOrderStatus());
	}

	@PostMapping("uninstall-app/{orderId}")
	public void uninstallApp(@PathVariable("orderId") long orderId)
		throws Exception {

		try {
			_consoleService.uninstallApp(orderId);

			if (_log.isInfoEnabled()) {
				_log.info("Uninstalled app for order " + orderId);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			_log.error("Unable to uninstall app for order " + orderId);
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