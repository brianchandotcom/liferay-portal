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
import com.liferay.headless.portal.instances.client.dto.v1_0.Admin;
import com.liferay.headless.portal.instances.client.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.client.resource.v1_0.PortalInstanceResource;
import com.liferay.marketplace.service.ConsoleService;
import com.liferay.notification.rest.client.dto.v1_0.NotificationQueueEntry;
import com.liferay.notification.rest.client.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.client.resource.v1_0.NotificationQueueEntryResource;
import com.liferay.notification.rest.client.resource.v1_0.NotificationTemplateResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

/**
 * @author Keven Leone
 */
@RequestMapping("/trial")
@RestController
public class TrialRestController extends BaseRestController {

	@DeleteMapping("{orderId}")
	public void delete(@PathVariable String orderId) throws Exception {
		_consoleService.deleteProject(orderId);

		_deletePortalInstance(orderId);
	}

	@GetMapping("availability")
	public String getAvailability() throws Exception {
		com.liferay.headless.portal.instances.client.pagination.Page
			<PortalInstance> page = _getPortalInstancesPage();

		return new JSONObject(
		).put(
			"active", _TRIAL_MAX_INSTANCES > page.getTotalCount()
		).put(
			"available", _TRIAL_MAX_INSTANCES - page.getTotalCount()
		).put(
			"max", _TRIAL_MAX_INSTANCES
		).toString();
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

		JSONObject modelDTOOrderJSONObject = jsonObject.getJSONObject(
			"modelDTOOrder");

		if (_TRIAL_ACCOUNT_CHECK) {
			OrderResource orderResource = _getOrderResource();

			Page<Order> ordersPage = orderResource.getOrdersPage(
				"",
				"accountId/any(x:(x eq " +
					modelDTOOrderJSONObject.getString("accountId") +
						")) and orderTypeExternalReferenceCode eq 'SOLUTIONS7'",
				Pagination.of(1, 1), "");

			if (ordersPage.getTotalCount() > 1) {
				_log.error(
					"Account " +
						modelDTOOrderJSONObject.getString("accountId") +
							" already has a provisioned order");

				_updateOrder(null, orderId, _ORDER_STATUS_CANCELLED);

				return;
			}
		}

		com.liferay.headless.portal.instances.client.pagination.Page
			<PortalInstance> portalInstancesPage = _getPortalInstancesPage();

		if (portalInstancesPage.getTotalCount() == _TRIAL_MAX_INSTANCES) {
			_log.error("Order is on hold");

			_updateOrder(null, orderId, _ORDER_STATUS_ON_HOLD);

			return;
		}

		if (modelDTOOrderJSONObject.getInt("orderStatus") ==
				_ORDER_STATUS_OPEN) {

			_updateOrder(null, orderId, _ORDER_STATUS_PENDING);
		}

		_updateOrder(null, orderId, _ORDER_STATUS_PROCESSING);

		PortalInstance portalInstance = _postPortalInstance(
			jwt, modelDTOOrderJSONObject.getString("creatorEmailAddress"),
			orderId);

		try {
			_consoleService.setUpProject(
				portalInstance.getVirtualHost(), orderId);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to set up project for order " + orderId + ":",
				exception);

			_deletePortalInstance(String.valueOf(orderId));

			_updateOrder(
				HashMapBuilder.put(
					"trial-error", exception.toString()
				).put(
					"trial-error-date",
					ZonedDateTime.now(
					).format(
						DateTimeFormatter.ISO_INSTANT
					)
				).put(
					"trial-virtualhost", portalInstance.getVirtualHost()
				).build(),
				orderId, _ORDER_STATUS_CANCELLED);

			return;
		}

		_updateOrder(
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
			orderId, _ORDER_STATUS_COMPLETED);

		_postNotificationQueueEntry(
			"TRY-IT-NOW-COMPLETED-ORDER",
			modelDTOOrderJSONObject.getString("creatorEmailAddress"),
			new HashMapBuilder<String, Object>().put(
				"%EMAIL%",
				modelDTOOrderJSONObject.getString("creatorEmailAddress")
			).put(
				"%NAME%",
				jwt.getClaim(
					"username"
				).toString()
			).put(
				"%URL%", portalInstance.getVirtualHost()
			).build());
	}

	private void _deletePortalInstance(String orderId) throws Exception {
		PortalInstanceResource portalInstanceResource =
			_getPortalInstanceResource();

		com.liferay.headless.portal.instances.client.pagination.Page
			<PortalInstance> page =
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

	private OrderResource _getOrderResource() throws Exception {
		URL liferayDXPURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		return OrderResource.builder(
		).endpoint(
			liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-marketplace-etc-spring-boot-oauth-application-" +
					"headless-server")
		).build();
	}

	private PortalInstanceResource _getPortalInstanceResource()
		throws Exception {

		return PortalInstanceResource.builder(
		).endpoint(
			_externalLiferayTrialURI
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization("external-trial")
		).build();
	}

	private com.liferay.headless.portal.instances.client.pagination.Page
		<PortalInstance> _getPortalInstancesPage() throws Exception {

		PortalInstanceResource portalInstanceResource =
			_getPortalInstanceResource();

		return portalInstanceResource.getPortalInstancesPage(true);
	}

	private void _postNotificationQueueEntry(
			String templateExternalReferenceCode, String emailAddress,
			Map<String, String> map)
		throws Exception {

		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-marketplace-etc-spring-boot-oauth-application-" +
					"headless-server");
		URL liferayDXPURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		NotificationTemplateResource notificationTemplateResource =
			NotificationTemplateResource.builder(
			).endpoint(
				liferayDXPURL
			).header(
				HttpHeaders.AUTHORIZATION, authorization
			).build();

		NotificationTemplate notificationTemplate =
			notificationTemplateResource.
				getNotificationTemplateByExternalReferenceCode(
					templateExternalReferenceCode);

		if (notificationTemplate == null) {
			return;
		}

		NotificationQueueEntryResource notificationQueueEntryResource =
			NotificationQueueEntryResource.builder(
			).endpoint(
				liferayDXPURL
			).header(
				HttpHeaders.AUTHORIZATION, authorization
			).build();

		NotificationQueueEntry notificationQueueEntry =
			new NotificationQueueEntry();

		String body = notificationTemplate.getBody(
		).get(
			"en_US"
		);

		for (Map.Entry<String, String> entry : map.entrySet()) {
			body = StringUtil.replace(body, entry.getKey(), entry.getValue());
		}

		String finalBody = body;

		notificationQueueEntry.setBody(() -> finalBody);

		JSONArray jsonArray = new JSONObject(
			String.valueOf(notificationTemplate)
		).getJSONArray(
			"recipients"
		);

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		notificationQueueEntry.setRecipients(
			() -> new Object[] {
				new HashMapBuilder<String, Object>().put(
					"from", jsonObject.getString("from")
				).put(
					"fromName",
					jsonObject.getJSONObject(
						"fromName"
					).getString(
						"en_US"
					)
				).put(
					"to", emailAddress
				).build()
			});

		notificationQueueEntry.setSubject(
			() -> notificationTemplate.getSubject(
			).get(
				"en_US"
			));
		notificationQueueEntry.setType(notificationTemplate::getType);

		notificationQueueEntryResource.postNotificationQueueEntry(
			notificationQueueEntry);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Sent ", templateExternalReferenceCode,
					" notification to: ", emailAddress));
		}
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

	private void _updateOrder(
			Map<String, ?> customFields, long orderId, int orderStatus)
		throws Exception {

		OrderResource orderResource = _getOrderResource();

		Order order = new Order();

		order.setCustomFields(() -> customFields);
		order.setOrderStatus(() -> orderStatus);

		orderResource.patchOrder(orderId, order);
	}

	private static final int _ORDER_STATUS_CANCELLED = 8;

	private static final int _ORDER_STATUS_COMPLETED = 0;

	private static final int _ORDER_STATUS_ON_HOLD = 20;

	private static final int _ORDER_STATUS_OPEN = 2;

	private static final int _ORDER_STATUS_PENDING = 1;

	private static final int _ORDER_STATUS_PROCESSING = 10;

	private static final boolean _TRIAL_ACCOUNT_CHECK = GetterUtil.getBoolean(
		System.getenv(
			"LIFERAY_MARKETPLACE_ETC_SPRING_BOOT_TRIAL_ACCOUNT_CHECK"));

	private static final int _TRIAL_MAX_INSTANCES = GetterUtil.getInteger(
		System.getenv(
			"LIFERAY_MARKETPLACE_ETC_SPRING_BOOT_TRIAL_MAX_INSTANCES"),
		50);

	private static final Log _log = LogFactory.getLog(
		TrialRestController.class);

	@Autowired
	private ConsoleService _consoleService;

	@Value("${external.trial.oauth2.headless.server.home.page.uri}")
	private URL _externalLiferayTrialURI;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.marketplace.trial.dxp.domain}")
	private String _trialDXPDomain;

}