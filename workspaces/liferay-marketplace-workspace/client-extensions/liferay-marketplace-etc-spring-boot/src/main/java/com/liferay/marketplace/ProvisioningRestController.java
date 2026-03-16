/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.ProvisioningService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.marketplace.rest.client.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.client.http.HttpInvoker;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Page;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Pagination;
import com.liferay.osb.provisioning.marketplace.rest.client.resource.v1_0.AppLicenseKeyResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Keven Leone
 */
@RequestMapping("/provisioning")
@RestController
public class ProvisioningRestController extends BaseRestController {

	@PostMapping("license-keys/{id}/deactivate")
	public void deactivateLicenseKeys(
			@AuthenticationPrincipal Jwt jwt, @PathVariable("id") long id)
		throws Exception {

		AppLicenseKeyResource appLicenseKeyResource =
			_provisioningService.getAppLicenseKeyResource();

		appLicenseKeyResource.putAppLicenseKeyDeactivate(
			jwt.getClaim("username"), jwt.getClaim("sub"), new Long[] {id});

		if (_log.isInfoEnabled()) {
			_log.info("License key " + id + " deactivated");
		}
	}

	@GetMapping("license-keys/{id}")
	public AppLicenseKey getLicenseKeys(@PathVariable("id") long id)
		throws Exception {

		AppLicenseKeyResource appLicenseKeyResource =
			_provisioningService.getAppLicenseKeyResource();

		return appLicenseKeyResource.getAppLicenseKey(id);
	}

	@GetMapping("license-keys/{id}/download")
	public ResponseEntity getLicenseKeysDownload(@PathVariable("id") long id)
		throws Exception {

		AppLicenseKeyResource appLicenseKeyResource =
			_provisioningService.getAppLicenseKeyResource();

		AppLicenseKey appLicenseKey = appLicenseKeyResource.getAppLicenseKey(
			id);

		HttpInvoker.HttpResponse httpResponse =
			appLicenseKeyResource.getAppLicenseKeyDownloadHttpResponse(
				appLicenseKey.getId());

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setAccessControlExposeHeaders(
			Collections.singletonList("Content-Disposition"));
		httpHeaders.setCacheControl(
			"must-revalidate, post-check=0, pre-check=0");

		StringBuilder sb = new StringBuilder();

		sb.append("activation-key-");
		sb.append(appLicenseKey.getProductName());
		sb.append(StringPool.DASH);
		sb.append(appLicenseKey.getProductVersion());
		sb.append(StringPool.DASH);
		sb.append(appLicenseKey.getHostName());
		sb.append(".xml");

		httpHeaders.setContentDispositionFormData(
			"attachment",
			sb.toString(
			).replaceAll(
				StringPool.SPACE, StringPool.DASH
			).toLowerCase());

		httpHeaders.setContentType(MediaType.TEXT_XML);

		return new ResponseEntity(
			httpResponse.getBinaryContent(), httpHeaders, HttpStatus.OK);
	}

	@GetMapping("order-license-keys/{orderId}")
	public Page<AppLicenseKey> getOrderLicenseKeys(
			@PathVariable("orderId") String orderId,
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(defaultValue = "20", required = false) int pageSize)
		throws Exception {

		AppLicenseKeyResource appLicenseKeyResource =
			_provisioningService.getAppLicenseKeyResource();

		return appLicenseKeyResource.getAppLicenseKeysPage(
			"", "active eq true and orderId eq '" + orderId + "'",
			Pagination.of(page, pageSize), "");
	}

	@PostMapping("cmp-beta-license-key")
	public AppLicenseKey postCMPBetaLicenseKey(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		AppLicenseKey appLicenseKey = AppLicenseKey.toDTO(json);

		if (Objects.equals(appLicenseKey.getHostName(), null) &&
			Objects.equals(appLicenseKey.getIpAddresses(), null) &&
			Objects.equals(appLicenseKey.getMacAddresses(), null)) {

			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST,
				"At least one of the following fields is required: host " +
					"name, IP addresses, or MAC addresses");
		}

		Order order = _marketplaceService.getOrder(
			GetterUtil.getLong(appLicenseKey.getOrderId()));

		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				_marketplaceService.getOrderProductId(order));

		_marketplaceService.updateOrder(
			null, order.getId(), MarketplaceConstants.ORDER_STATUS_PROCESSING);

		ProductPurchase[] productPurchases =
			_marketplaceService.setUpProductEntitlements(
				jwt, productSpecificationsMap.get("license-type"), order);

		ProductPurchase productPurchase = productPurchases[0];

		if (productPurchase == null) {
			return null;
		}

		appLicenseKey.setProductPurchaseKey(productPurchase.getKey());

		appLicenseKey = _provisioningService.postAppLicenseKey(
			appLicenseKey, jwt);

		_marketplaceService.updateOrder(
			null, order.getId(), MarketplaceConstants.ORDER_STATUS_COMPLETED);

		return appLicenseKey;
	}

	@PostMapping("license-keys")
	public AppLicenseKey postLicenseKeys(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		AppLicenseKey appLicenseKey;

		try {
			appLicenseKey = AppLicenseKey.toDTO(
				new JSONObject(
					json
				).getJSONObject(
					"licenseEntry"
				).toString());
		}
		catch (JSONException jsonException) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST,
				"Invalid JSON or missing 'licenseEntry' field", jsonException);
		}

		return _provisioningService.postAppLicenseKey(appLicenseKey, jwt);
	}

	private static final Log _log = LogFactory.getLog(
		ProvisioningRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private MarketplaceService _marketplaceService;

	@Autowired
	private ProvisioningService _provisioningService;

}