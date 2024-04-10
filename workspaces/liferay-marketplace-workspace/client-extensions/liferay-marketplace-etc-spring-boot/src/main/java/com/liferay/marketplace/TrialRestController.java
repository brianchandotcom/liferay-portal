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
import com.liferay.marketplace.console.service.ConsoleService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.nio.charset.Charset;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/trial")
@RestController
public class TrialRestController extends BaseRestController {

	@DeleteMapping("{orderId}")
	private void _deleteTrial(@RequestParam String orderId) throws Exception {
		PortalInstanceResource portalInstanceResource =
			_getPortalInstanceResource();

		com.liferay.headless.portal.instances.client.pagination.Page
			<PortalInstance> portalInstancesPage =
				portalInstanceResource.getPortalInstancesPage(true);

		for (PortalInstance portalInstance : portalInstancesPage.getItems()) {
			if (Objects.equals(
					portalInstance.getVirtualHost(),
					orderId + "." + _TRIAL_DXP_DOMAIN)) {

				portalInstanceResource.putPortalInstanceDeactivate(
					portalInstance.getPortalInstanceId());

				break;
			}
		}

		_consoleService.deleteProject("ext" + orderId);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Virtual instance and cloud project deleted for Order " +
					orderId);
		}
	}

	@GetMapping("availability")
	private JSONObject _getAvailabilityJSONObject() throws Exception {
		com.liferay.headless.portal.instances.client.pagination.Page
			<PortalInstance> portalInstancesPage = _getPortalInstancesPage();

		return new JSONObject(
		).put(
			"active",
			_TRIAL_MAX_INSTANCES_IN_PROGRESS -
				portalInstancesPage.getTotalCount()
		).put(
			"available",
			_TRIAL_MAX_INSTANCES_IN_PROGRESS >
				portalInstancesPage.getTotalCount()
		).put(
			"max", _TRIAL_MAX_INSTANCES_IN_PROGRESS
		);
	}

	private String _getOAuthAuthorization() throws Exception {
		if ((_oauthAccessToken != null) &&
			(System.currentTimeMillis() < (_oauthExpirationMillis - 15000))) {

			return _oauthAccessToken;
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpPost httpPost = new HttpPost(
			new URL(_trialAuthURL) + "/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair("client_id", _trialAuthClientId),
					new BasicNameValuePair(
						"client_secret", _trialAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception("Unable to get OAuth authorization");
			}

			JSONObject jsonObject = new JSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(),
					Charset.defaultCharset()));

			_oauthExpirationMillis =
				(jsonObject.getLong("expires_in") * 1000) +
					System.currentTimeMillis();

			_oauthAccessToken =
				jsonObject.getString("token_type") + " " +
					jsonObject.getString("access_token");

			return _oauthAccessToken;
		}
	}

	private PortalInstanceResource _getPortalInstanceResource()
		throws Exception {

		return PortalInstanceResource.builder(
		).endpoint(
			new URL(_trialAuthURL)
		).header(
			HttpHeaders.AUTHORIZATION, _getOAuthAuthorization()
		).build();
	}

	private com.liferay.headless.portal.instances.client.pagination.Page
		<PortalInstance> _getPortalInstancesPage() throws Exception {

		PortalInstanceResource portalInstanceResource =
			_getPortalInstanceResource();

		return portalInstanceResource.getPortalInstancesPage(true);
	}

	private boolean _hasAccountOrders(String accountId) throws Exception {
		Page<Order> ordersPage = _orderResource.getOrdersPage(
			"",
			"accountId/any(x:(x eq " + accountId +
				")) and orderTypeExternalReferenceCode eq 'SOLUTIONS7'",
			Pagination.of(-1, -1), "");

		if (ordersPage.getTotalCount() > 1) {
			return true;
		}

		return false;
	}

	private void _initResourceBuilders() throws Exception {
		URL liferayDXPURL = new URL(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

		_orderResource = OrderResource.builder(
		).endpoint(
			liferayDXPURL
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-marketplace-etc-spring-boot-oauth-application-" +
					"headless-server")
		).build();
	}

	private boolean _isTrialAvailable() throws Exception {
		if (_TRIAL_MAX_INSTANCES_IN_PROGRESS >
				_getPortalInstancesPage().getTotalCount()) {

			return true;
		}

		return false;
	}

	private PortalInstance _postPortalInstance(
			Jwt jwt, String emailAddress, long orderId)
		throws Exception {

		PortalInstance portalInstance = new PortalInstance();

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

		String domain = orderId + "." + _TRIAL_DXP_DOMAIN;

		portalInstance.setAdmin(admin);
		portalInstance.setDomain("lxc.app");
		portalInstance.setPortalInstanceId(domain);
		portalInstance.setVirtualHost(domain);

		portalInstance = _getPortalInstanceResource().postPortalInstance(
			portalInstance);

		if (_log.isInfoEnabled()) {
			_log.info("Created portal instance " + portalInstance);
		}

		return portalInstance;
	}

	@PostMapping("provisioning")
	private void _postProvisioning(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		_initResourceBuilders();

		JSONObject jsonObject = new JSONObject(json);

		JSONObject modelDTOOrderJSONObject = jsonObject.getJSONObject(
			"modelDTOOrder");

		long orderId = jsonObject.getLong("classPK");

		if (_log.isInfoEnabled()) {
			_log.info("Provision order " + orderId);
		}

		Order order = new Order();

		if (_hasAccountOrders(modelDTOOrderJSONObject.getString("accountId"))) {
			_log.error(
				"Account " + modelDTOOrderJSONObject.getString("accountId") +
					" already has a provisioned order");

			order.setOrderStatus(() -> _ORDER_STATUS_CANCELLED);

			_orderResource.patchOrder(orderId, order);

			return;
		}

		if (!_isTrialAvailable()) {
			_log.error(
				"The limit of " + _TRIAL_MAX_INSTANCES_IN_PROGRESS +
					" has exceeded, trial installation will be scheduled");

			order.setOrderStatus(_ORDER_STATUS_ON_HOLD);

			_orderResource.patchOrder(orderId, order);

			return;
		}

		order.setOrderStatus(_ORDER_STATUS_PROCESSING);

		_orderResource.patchOrder(orderId, order);

		PortalInstance portalInstance = _postPortalInstance(
			jwt, modelDTOOrderJSONObject.getString("creatorEmailAddress"),
			orderId);

		JSONObject environmentProjectJSONObject =
			_consoleService.postEnvironmentProject("ext" + orderId);

		_consoleService.inviteProject(
			_marketplaceTrialAdminEmail,
			environmentProjectJSONObject.getString("projectId"), "admin");

		_consoleService.setupLinkBetweenPortalInstanceAndExtensionEnvironment(
			portalInstance.getVirtualHost(),
			environmentProjectJSONObject.getString("id"));

		_consoleService.deployApp(
			String.valueOf(orderId),
			environmentProjectJSONObject.getString("projectId"));

		order.setCustomFields(
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
			).build());

		order.setOrderStatus(_ORDER_STATUS_COMPLETED);

		_orderResource.patchOrder(orderId, order);
	}

	private static final int _ORDER_STATUS_CANCELLED = 8;

	private static final int _ORDER_STATUS_COMPLETED = 0;

	private static final int _ORDER_STATUS_ON_HOLD = 20;

	private static final int _ORDER_STATUS_PROCESSING = 10;

	private static final String _TRIAL_DXP_DOMAIN = "lrtrial.lxc.liferay.com";

	private static final int _TRIAL_MAX_INSTANCES_IN_PROGRESS = 50;

	private static final Log _log = LogFactory.getLog(
		TrialRestController.class);

	@Autowired
	private ConsoleService _consoleService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.marketplace.trial.admin.email}")
	private String _marketplaceTrialAdminEmail;

	private String _oauthAccessToken;
	private long _oauthExpirationMillis;
	private OrderResource _orderResource;

	@Value("${liferay.marketplace.trial.auth.client.id}")
	private String _trialAuthClientId;

	@Value("${liferay.marketplace.trial.auth.client.secret}")
	private String _trialAuthClientSecret;

	@Value("${liferay.marketplace.trial.auth.url}")
	private String _trialAuthURL;

}