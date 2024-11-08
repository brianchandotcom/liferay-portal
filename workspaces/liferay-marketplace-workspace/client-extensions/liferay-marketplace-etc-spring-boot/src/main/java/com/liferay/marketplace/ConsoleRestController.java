/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.service.ConsoleService;
import com.liferay.marketplace.service.MarketplaceService;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

		Order order = _marketplaceService.getOrder(orderId);

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		JSONArray cloudProvisioningJSONArray = new JSONArray(
			customFields.get("cloud-provisioning"));

		JSONObject jsonObject = new JSONObject(json);

		JSONObject cloudProvisioningJSONObject =
			_getCloudProvisioningJSONObject(
				cloudProvisioningJSONArray, jsonObject.getLong("orderItemId"));

		_checkAvailability(cloudProvisioningJSONObject);

		String temporaryDeploymentId = _createTemporaryDeployment(
			cloudProvisioningJSONArray, cloudProvisioningJSONObject, order,
			jsonObject.getString("projectId"));

		try {
			JSONObject appJSONObject = _consoleService.deployApp(
				jwt.getClaimAsString("username"), String.valueOf(order.getId()),
				jsonObject.getString("projectId"));

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
		catch (Exception exception) {
			_log.error(exception);

			_log.error("Unable to install app for order " + orderId);
		}

		_deleteDeployment(temporaryDeploymentId, cloudProvisioningJSONObject);

		customFields.put(
			"cloud-provisioning", cloudProvisioningJSONArray.toString());

		_marketplaceService.updateOrder(
			customFields, orderId, order.getOrderStatus());
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
				_getCloudProvisioningJSONObject(
					cloudProvisioningJSONArray,
					jsonObject.getLong("orderItemId"));

			_deleteDeployment(
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

	private void _checkAvailability(JSONObject jsonObject) throws Exception {
		if (jsonObject.getLong("shippedQuantity") >= jsonObject.getLong(
				"quantity")) {

			throw new Exception(
				"Unable to install app for order item " +
					jsonObject.getLong("orderItemId") +
						" because there are no available resources");
		}
	}

	private String _createTemporaryDeployment(
			JSONArray jsonArray, JSONObject jsonObject, Order order,
			String projectId)
		throws Exception {

		UUID uuid = UUID.randomUUID();

		jsonObject.put(
			"deployments",
			jsonObject.getJSONArray(
				"deployments"
			).put(
				new JSONObject(
				).put(
					"id", uuid.toString()
				).put(
					"loading", true
				).put(
					"projectId", projectId
				)
			));

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		customFields.put("cloud-provisioning", jsonArray.toString());

		_marketplaceService.updateOrder(
			customFields, order.getId(), order.getOrderStatus());

		return uuid.toString();
	}

	private void _deleteDeployment(String deploymentId, JSONObject jsonObject) {
		JSONArray deploymentsJSONArray = jsonObject.getJSONArray("deployments");

		for (int i = 0; i < deploymentsJSONArray.length(); i++) {
			JSONObject deploymentJSONObject =
				deploymentsJSONArray.getJSONObject(i);

			if (Objects.equals(
					deploymentJSONObject.getString("id"), deploymentId)) {

				deploymentsJSONArray.remove(i);
			}
		}
	}

	private JSONObject _getCloudProvisioningJSONObject(
		JSONArray jsonArray, long orderItemId) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (Objects.equals(
					jsonObject.getLong("orderItemId"), orderItemId)) {

				return jsonObject;
			}
		}

		return new JSONObject();
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