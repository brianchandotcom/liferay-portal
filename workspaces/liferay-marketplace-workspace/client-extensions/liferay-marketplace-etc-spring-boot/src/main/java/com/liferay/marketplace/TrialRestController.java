/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.portal.instances.client.dto.v1_0.Admin;
import com.liferay.headless.portal.instances.client.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.client.pagination.Page;
import com.liferay.headless.portal.instances.client.resource.v1_0.PortalInstanceResource;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.ConsoleService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.net.URL;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * @author Keven Leone
 */
@RequestMapping("/trial")
@RestController
public class TrialRestController extends BaseRestController {

	@DeleteMapping("{orderId}")
	public void delete(@PathVariable long orderId) throws Exception {
		_consoleService.deleteProject(String.valueOf(orderId));

		_deletePortalInstance(orderId);
	}

	@GetMapping("availability")
	public String getAvailability() throws Exception {
		Page<PortalInstance> page = _getPortalInstancesPage();

		return new JSONObject(
		).put(
			"active", _TRIAL_MAX_INSTANCES > page.getTotalCount()
		).put(
			"available", _TRIAL_MAX_INSTANCES - page.getTotalCount()
		).put(
			"max", _TRIAL_MAX_INSTANCES
		).toString();
	}

	@PostMapping("expire/{orderId}")
	public void postExpire(@PathVariable long orderId) throws Exception {
		_marketplaceService.updateOrder(
			null, orderId, MarketplaceConstants.ORDER_STATUS_PENDING);

		_marketplaceService.updateOrder(
			null, orderId, MarketplaceConstants.ORDER_STATUS_PROCESSING);

		_marketplaceService.updateOrder(
			null, orderId, MarketplaceConstants.ORDER_STATUS_COMPLETED);

		delete(orderId);

		if (_log.isInfoEnabled()) {
			_log.info("Expired trial " + orderId);
		}
	}

	@PostMapping("notify-end/{orderId}")
	public void postNotifyEnd(@PathVariable long orderId) throws Exception {
		Order order = _marketplaceService.getOrder(orderId);

		UserAccount userAccount = _marketplaceService.getUserAccount(
			order.getCreatorEmailAddress());
		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		_marketplaceService.postNotificationQueueEntry(
			order.getCreatorEmailAddress(), "TRY-IT-NOW-EXPIRING-ORDER",
			new HashMapBuilder<String, Object>().put(
				"%TRIAL_CREATOR_FIRST_NAME%", userAccount.getGivenName()
			).put(
				"%TRIAL_END_DATE%",
				ZonedDateTime.parse(
					customFields.get("trial-end-date")
				).format(
					DateTimeFormatter.ofPattern(
						"MMMM d, yyyy", LocaleUtil.ENGLISH)
				)
			).build());

		customFields.put(
			"trial-notify-end-date",
			ZonedDateTime.now(
			).format(
				DateTimeFormatter.ISO_INSTANT
			));

		_marketplaceService.updateOrder(
			customFields, orderId, order.getOrderStatus());
	}

	@PostMapping("provisioning")
	public void postProvisioning(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		long orderId = jsonObject.getLong("classPK");

		if (_log.isInfoEnabled()) {
			_log.info("Provisioning order " + orderId);
		}

		Page<PortalInstance> portalInstancesPage = _getPortalInstancesPage();

		if (portalInstancesPage.getTotalCount() == _TRIAL_MAX_INSTANCES) {
			_log.error("Order is on hold");

			_marketplaceService.updateOrder(
				null, orderId, MarketplaceConstants.ORDER_STATUS_ON_HOLD);

			return;
		}

		JSONObject modelDTOOrderJSONObject = jsonObject.getJSONObject(
			"modelDTOOrder");

		if (modelDTOOrderJSONObject.getInt("orderStatus") ==
				MarketplaceConstants.ORDER_STATUS_OPEN) {

			_marketplaceService.updateOrder(
				null, orderId, MarketplaceConstants.ORDER_STATUS_PENDING);
		}

		_marketplaceService.updateOrder(
			null, orderId, MarketplaceConstants.ORDER_STATUS_PROCESSING);

		Order order = _marketplaceService.getOrder(orderId);

		UserAccount userAccount = _marketplaceService.getUserAccount(
			order.getCreatorEmailAddress());

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		JSONObject trialSettingsJSONObject = new JSONObject(
			customFields.getOrDefault("trial-settings", "{}"));

		boolean sendNotificationEmail = trialSettingsJSONObject.optBoolean(
			"sendNotificationEmail", true);

		if (sendNotificationEmail) {
			_marketplaceService.postNotificationQueueEntry(
				modelDTOOrderJSONObject.getString("creatorEmailAddress"),
				"TRY-IT-NOW-PROCESSING-ORDER",
				new HashMapBuilder<String, Object>().put(
					"[%COMMERCEORDER_AUTHOR_FIRST_NAME%]",
					userAccount.getGivenName()
				).put(
					"[%COMMERCEORDER_ID%]", String.valueOf(orderId)
				).build());
		}

		PortalInstance portalInstance = _postPortalInstance(
			jwt, modelDTOOrderJSONObject.getString("creatorEmailAddress"),
			orderId);

		try {
			_consoleService.setUpProject(
				_toStringArray(
					trialSettingsJSONObject.optJSONArray(
						"consoleInviteEmailAddresses")),
				portalInstance.getVirtualHost(), orderId);

			_marketplaceService.updateOrder(
				HashMapBuilder.put(
					"trial-end-date",
					ZonedDateTime.now(
					).plusDays(
						7
					).format(
						DateTimeFormatter.ISO_INSTANT
					)
				).put(
					"trial-start-date",
					ZonedDateTime.now(
					).format(
						DateTimeFormatter.ISO_INSTANT
					)
				).put(
					"trial-virtualhost", portalInstance.getVirtualHost()
				).build(),
				orderId, MarketplaceConstants.ORDER_STATUS_IN_PROGRESS);

			if (sendNotificationEmail) {
				_marketplaceService.postNotificationQueueEntry(
					modelDTOOrderJSONObject.getString("creatorEmailAddress"),
					"TRY-IT-NOW-COMPLETED-ORDER",
					new HashMapBuilder<String, Object>().put(
						"%EMAIL%",
						modelDTOOrderJSONObject.getString("creatorEmailAddress")
					).put(
						"%NAME%", userAccount.getGivenName()
					).put(
						"%URL%", portalInstance.getVirtualHost()
					).build());
			}
		}
		catch (WebClientResponseException webClientResponseException) {
			_rollBackTrial(
				webClientResponseException.getResponseBodyAsString(), orderId,
				portalInstance);
		}
		catch (Exception exception) {
			_rollBackTrial(exception.getMessage(), orderId, portalInstance);
		}
	}

	@PostMapping("provisioning/{orderId}")
	public void postProvisioningOrder(
			@AuthenticationPrincipal Jwt jwt, @PathVariable long orderId)
		throws Exception {

		Order order = _marketplaceService.getOrder(orderId);

		postProvisioning(
			jwt,
			new JSONObject(
			).put(
				"classPK", orderId
			).put(
				"modelDTOOrder",
				new JSONObject(
				).put(
					"accountId", String.valueOf(order.getAccountId())
				).put(
					"creatorEmailAddress", order.getCreatorEmailAddress()
				).put(
					"orderStatus", order.getOrderStatus()
				)
			).toString());
	}

	private void _deletePortalInstance(long orderId) throws Exception {
		PortalInstanceResource portalInstanceResource =
			_getPortalInstanceResource();

		Page<PortalInstance> page =
			portalInstanceResource.getPortalInstancesPage(true);

		for (PortalInstance portalInstance : page.getItems()) {
			if (Objects.equals(
					portalInstance.getVirtualHost(),
					orderId + "." + _trialDXPDomain)) {

				portalInstanceResource.deletePortalInstance(
					portalInstance.getPortalInstanceId());

				break;
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Portal instance deleted for order " + orderId);
		}
	}

	private PortalInstanceResource _getPortalInstanceResource()
		throws Exception {

		return PortalInstanceResource.builder(
		).endpoint(
			_externalTrialHomePageURL
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization("external-trial")
		).build();
	}

	private Page<PortalInstance> _getPortalInstancesPage() throws Exception {
		PortalInstanceResource portalInstanceResource =
			_getPortalInstanceResource();

		return portalInstanceResource.getPortalInstancesPage(true);
	}

	private PortalInstance _postPortalInstance(
			Jwt jwt, String emailAddress, long orderId)
		throws Exception {

		PortalInstanceResource portalInstanceResource =
			_getPortalInstanceResource();

		PortalInstance portalInstance = new PortalInstance();

		Admin admin = new Admin();

		admin.setEmailAddress(() -> emailAddress);
		admin.setFamilyName(
			() -> jwt.getClaim(
				"username"
			).toString());
		admin.setGivenName(
			() -> jwt.getClaim(
				"username"
			).toString());

		portalInstance.setAdmin(() -> admin);

		portalInstance.setDomain(() -> "lxc.app");

		String domain = orderId + "." + _trialDXPDomain;

		portalInstance.setPortalInstanceId(() -> domain);
		portalInstance.setVirtualHost(() -> domain);

		portalInstance = portalInstanceResource.postPortalInstance(
			portalInstance);

		if (_log.isInfoEnabled()) {
			_log.info("Created portal instance " + portalInstance);
		}

		return portalInstance;
	}

	private void _rollBackTrial(
			String errorMessage, long orderId, PortalInstance portalInstance)
		throws Exception {

		_log.error(
			StringBundler.concat(
				"Unable to set up project for order ", orderId, ": \n",
				errorMessage));

		_deletePortalInstance(orderId);

		_marketplaceService.updateOrder(
			HashMapBuilder.put(
				"trial-error", errorMessage
			).put(
				"trial-error-date",
				ZonedDateTime.now(
				).format(
					DateTimeFormatter.ISO_INSTANT
				)
			).put(
				"trial-virtualhost", portalInstance.getVirtualHost()
			).build(),
			orderId, MarketplaceConstants.ORDER_STATUS_CANCELLED);
	}

	private String[] _toStringArray(JSONArray jsonArray) {
		List<String> list = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			list.add(jsonArray.getString(i));
		}

		return list.toArray(new String[0]);
	}

	private static final int _TRIAL_MAX_INSTANCES = GetterUtil.getInteger(
		System.getenv(
			"LIFERAY_MARKETPLACE_ETC_SPRING_BOOT_TRIAL_MAX_INSTANCES"),
		50);

	private static final Log _log = LogFactory.getLog(
		TrialRestController.class);

	@Autowired
	private ConsoleService _consoleService;

	@Value("${external.trial.oauth2.headless.server.home.page.url}")
	private URL _externalTrialHomePageURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private MarketplaceService _marketplaceService;

	@Value("${liferay.marketplace.trial.dxp.domain}")
	private String _trialDXPDomain;

}