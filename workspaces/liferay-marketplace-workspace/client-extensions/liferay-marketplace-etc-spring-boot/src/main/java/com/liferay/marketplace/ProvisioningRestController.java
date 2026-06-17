/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.AnalyticsService;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.ProvisioningService;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.marketplace.rest.client.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.client.http.HttpInvoker;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Page;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Pagination;
import com.liferay.osb.provisioning.marketplace.rest.client.resource.v1_0.AppLicenseKeyResource;
import com.liferay.osb.provisioning.rest.client.dto.v1_0.LicenseKey;
import com.liferay.osb.provisioning.rest.client.resource.v1_0.LicenseKeyResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.Instant;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

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

	@GetMapping("app-license-keys/{id}")
	public AppLicenseKey getAppLicenseKeys(@PathVariable("id") long id)
		throws Exception {

		AppLicenseKeyResource appLicenseKeyResource =
			_provisioningService.getAppLicenseKeyResource();

		return appLicenseKeyResource.getAppLicenseKey(id);
	}

	@GetMapping("app-license-keys/{id}/download")
	public ResponseEntity<byte[]> getAppLicenseKeysDownload(
			@PathVariable("id") long id)
		throws Exception {

		AppLicenseKeyResource appLicenseKeyResource =
			_provisioningService.getAppLicenseKeyResource();

		AppLicenseKey appLicenseKey = appLicenseKeyResource.getAppLicenseKey(
			id);

		HttpInvoker.HttpResponse httpResponse =
			appLicenseKeyResource.getAppLicenseKeyDownloadHttpResponse(
				appLicenseKey.getId());

		return _licenseKeyDownloadResponse(
			httpResponse.getBinaryContent(), appLicenseKey.getHostName(),
			appLicenseKey.getProductName(), appLicenseKey.getProductVersion());
	}

	@GetMapping("license-key/{id}")
	public LicenseKey getLicenseKey(@PathVariable("id") long id)
		throws Exception {

		LicenseKeyResource licenseKeyResource =
			_provisioningService.getLicenseKeyResource();

		return licenseKeyResource.getLicenseKey(id);
	}

	@GetMapping("license-keys/{id}/download")
	public ResponseEntity<byte[]> getLicenseKeysDownload(
			@PathVariable("id") long id)
		throws Exception {

		LicenseKeyResource licenseKeyResource =
			_provisioningService.getLicenseKeyResource();

		LicenseKey licenseKey = licenseKeyResource.getLicenseKey(id);

		com.liferay.osb.provisioning.rest.client.http.HttpInvoker.HttpResponse
			httpResponse = licenseKeyResource.getLicenseKeyDownloadHttpResponse(
				licenseKey.getId());

		return _licenseKeyDownloadResponse(
			httpResponse.getBinaryContent(), licenseKey.getHostName(),
			licenseKey.getProductName(), licenseKey.getProductVersion());
	}

	@GetMapping("order-app-license-keys/{orderId}")
	public Page<AppLicenseKey> getOrderAppLicenseKeys(
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

	@GetMapping("order-license-keys/{orderId}")
	public com.liferay.osb.provisioning.rest.client.pagination.Page<LicenseKey>
			getOrderLicenseKeys(
				@PathVariable("orderId") String orderId,
				@RequestParam(defaultValue = "1", required = false) int page,
				@RequestParam(defaultValue = "20", required = false) int
					pageSize)
		throws Exception {

		LicenseKeyResource licenseKeyResource =
			_provisioningService.getLicenseKeyResource();

		return licenseKeyResource.getLicenseKeysPage(
			"", "assetReceiptLicenseUuid eq '" + orderId + "'",
			com.liferay.osb.provisioning.rest.client.pagination.Pagination.of(
				page, pageSize),
			"");
	}

	@PostMapping("app-license-keys")
	public AppLicenseKey postAppLicenseKeys(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		AppLicenseKey appLicenseKey = AppLicenseKey.toDTO(
			new JSONObject(
				json
			).getJSONObject(
				"licenseEntry"
			).toString());

		return _provisioningService.postAppLicenseKey(appLicenseKey, jwt);
	}

	@PostMapping("app-license-keys/{id}/deactivate")
	public void postAppLicenseKeysDeactivate(
			@AuthenticationPrincipal Jwt jwt, @PathVariable("id") long id)
		throws Exception {

		AppLicenseKeyResource appLicenseKeyResource =
			_provisioningService.getAppLicenseKeyResource();

		appLicenseKeyResource.putAppLicenseKeyDeactivate(
			jwt.getClaim("username"), jwt.getClaim("sub"), new Long[] {id});
	}

	@PostMapping("cmp-beta-license-key")
	public void postCMPBetaLicenseKey(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		AppLicenseKey appLicenseKey = AppLicenseKey.toDTO(json);

		Order order = _marketplaceService.getOrder(
			GetterUtil.getLong(appLicenseKey.getOrderId()));

		_postAppLicenseKey(appLicenseKey, jwt, order);

		_marketplaceService.completeOrder(
			order.getId(),
			MarketplaceConstants.ORDER_PAYMENT_STATUS_NOT_REQUIRED);
	}

	@PostMapping("dsr-beta-license-key")
	public void postDSRBetaLicenseKey(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		AppLicenseKey appLicenseKey = AppLicenseKey.toDTO(
			new JSONObject(
				json
			).getJSONObject(
				"licenseEntry"
			).toString());

		Order order = _marketplaceService.getOrder(
			GetterUtil.getLong(appLicenseKey.getOrderId()));

		_postAppLicenseKey(appLicenseKey, jwt, order);

		JSONObject jsonObject = new JSONObject(json);

		JSONObject analyticsProjectJSONObject =
			_analyticsService.provisionAnalyticsProject(
				jsonObject.getJSONObject("analyticsForm"), null,
				order.getAccountExternalReferenceCode());

		_marketplaceService.completeOrder(
			HashMapBuilder.put(
				"order-metadata",
				MarketplaceUtil.getOrderMetadataJSONObject(
					order
				).put(
					"analyticsProject", analyticsProjectJSONObject
				).toString()
			).build(),
			order.getId(),
			MarketplaceConstants.ORDER_PAYMENT_STATUS_NOT_REQUIRED);
	}

	@PostMapping("license-key-type-free")
	public LicenseKey postLicenseKeyTypeFree(@RequestBody String json)
		throws Exception {

		LicenseKeyResource licenseKeyResource =
			_provisioningService.getLicenseKeyResource();

		LicenseKey licenseKey = licenseKeyResource.postLicenseKeyTypeFree(
			LicenseKey.toDTO(json));

		_marketplaceService.completeOrder(
			GetterUtil.getLong(licenseKey.getAssetReceiptLicenseUuid()),
			MarketplaceConstants.ORDER_PAYMENT_STATUS_NOT_REQUIRED);

		return licenseKey;
	}

	@PostMapping("license-key-type-free-domains-check")
	public void postLicenseKeyTypeFreeDomainCheck(@RequestBody String json)
		throws Exception {

		LicenseKey licenseKey = LicenseKey.toDTO(json);

		LicenseKeyResource licenseKeyResource =
			_provisioningService.getLicenseKeyResource();

		com.liferay.osb.provisioning.rest.client.pagination.Page<LicenseKey>
			licenseKeysPage = licenseKeyResource.getLicenseKeysPage(
				null,
				StringBundler.concat(
					"domains eq '", licenseKey.getDomains(), "' and owner eq '",
					licenseKey.getOwner(), "'"),
				com.liferay.osb.provisioning.rest.client.pagination.Pagination.
					of(1, 20),
				null);

		if (licenseKeysPage.fetchFirstItem() != null) {
			throw new ResponseStatusException(
				HttpStatus.CONFLICT,
				"A License key was already provisioned for the owner with " +
					"this domain");
		}
	}

	@PostMapping("license-key-type-free/{id}/renew")
	public void postLicenseKeyTypeFreeRenew(@PathVariable long id)
		throws Exception {

		LicenseKeyResource licenseKeyResource =
			_provisioningService.getLicenseKeyResource();

		LicenseKey licenseKey = licenseKeyResource.getLicenseKey(id);

		Date expirationDate = licenseKey.getExpirationDate();

		Instant instant = expirationDate.toInstant();

		if (instant.isAfter(Instant.now())) {
			return;
		}

		licenseKeyResource.postLicenseKeyTypeFree(
			new LicenseKey() {
				{
					setAssetReceiptLicenseUuid(
						licenseKey.getAssetReceiptLicenseUuid());
					setDomains(licenseKey.getDomains());
					setOwner(licenseKey.getOwner());
				}
			});
	}

	private ResponseEntity<byte[]> _licenseKeyDownloadResponse(
		byte[] content, String hostName, String productName,
		String productVersion) {

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setAccessControlExposeHeaders(
			Collections.singletonList("Content-Disposition"));
		httpHeaders.setCacheControl(
			"must-revalidate, post-check=0, pre-check=0");

		StringBuilder sb = new StringBuilder();

		sb.append("activation-key-");
		sb.append(productName);
		sb.append("-");
		sb.append(productVersion);
		sb.append("-");
		sb.append(hostName);
		sb.append(".xml");

		String fileName = sb.toString();

		fileName = fileName.replaceAll(" ", "-");

		httpHeaders.setContentDispositionFormData(
			"attachment", StringUtil.toLowerCase(fileName));

		httpHeaders.setContentType(MediaType.TEXT_XML);

		return new ResponseEntity<>(content, httpHeaders, HttpStatus.OK);
	}

	private void _postAppLicenseKey(
			AppLicenseKey appLicenseKey, Jwt jwt, Order order)
		throws Exception {

		if (Objects.equals(appLicenseKey.getHostName(), null) &&
			Objects.equals(appLicenseKey.getIpAddresses(), null) &&
			Objects.equals(appLicenseKey.getMacAddresses(), null)) {

			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST,
				"At least one of the following fields is required: host " +
					"name, IP addresses, or MAC addresses");
		}

		ProductPurchase[] productPurchases =
			_koroneikiService.postAccountProductPurchases(
				jwt, "3 Months Limited Beta", order);

		ProductPurchase productPurchase = productPurchases[0];

		if (productPurchase == null) {
			return;
		}

		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				_marketplaceService.getOrderProductId(order));

		if (Validator.isNotNull(
				productSpecificationsMap.get("app-entry-uuid"))) {

			appLicenseKey.setProductId(
				productSpecificationsMap.get("app-entry-uuid"));
		}

		_provisioningService.postAppLicenseKey(
			appLicenseKey, jwt, productPurchase);
	}

	@Autowired
	private AnalyticsService _analyticsService;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

	@Autowired
	private ProvisioningService _provisioningService;

}