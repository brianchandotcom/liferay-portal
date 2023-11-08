/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.CustomField;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.SkuResource;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.provisioning.marketplace.rest.client.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.client.http.HttpInvoker;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Page;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Pagination;
import com.liferay.osb.provisioning.marketplace.rest.client.resource.v1_0.AppLicenseKeyResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.nio.charset.Charset;

import java.time.ZonedDateTime;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
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

/**
 * @author Keven Leone
 */
@RequestMapping("/provisioning")
@RestController
public class ProvisioningRestController extends BaseRestController {

	@PostMapping("license-keys")
	public AppLicenseKey createLicenseKey(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		_initResourceBuilders();

		JSONObject jsonObject = new JSONObject(json);

		ProductPurchase productPurchase =
			_productPurchaseResource.getProductPurchase(
				jsonObject.getString("productPurchaseKey"));

		Date expirationDate = productPurchase.getEndDate();

		if (productPurchase.getPerpetual()) {
			expirationDate = Date.from(
				ZonedDateTime.now(
				).plusYears(
					100
				).toInstant());
		}

		AppLicenseKey.LicenseType licenseType =
			AppLicenseKey.LicenseType.PRODUCTION;

		if (StringUtil.equals(jsonObject.getString("type"), "developer")) {
			licenseType = AppLicenseKey.LicenseType.DEVELOPER;
		}

		Date startDate = productPurchase.getStartDate();

		if (startDate == null) {
			startDate = new Date();
		}

		AppLicenseKey appLicenseKey = AppLicenseKey.toDTO(
			jsonObject.getJSONObject(
				"licenseEntry"
			).toString());

		appLicenseKey.setActive(true);
		appLicenseKey.setCreateDate(new Date());
		appLicenseKey.setExpirationDate(expirationDate);
		appLicenseKey.setLicenseType(licenseType);
		appLicenseKey.setOwner((String)jwt.getClaim("username"));
		appLicenseKey.setProductId(productPurchase.getProductKey());
		appLicenseKey.setProductName(
			productPurchase.getProduct(
			).getName());
		appLicenseKey.setProductVersion(_getVersion(jsonObject, jwt));
		appLicenseKey.setStartDate(startDate);
		appLicenseKey.setUserName((String)jwt.getClaim("username"));
		appLicenseKey.setUserUuid((String)jwt.getClaim("sub"));

		return _appLicenseKeyResource.postAppLicenseKey(
			jwt.getClaim("username"), jwt.getClaim("sub"), appLicenseKey);
	}

	@GetMapping("license-keys/{id}/download")
	public ResponseEntity downloadLicenseKey(@PathVariable("id") String id)
		throws Exception {

		_initResourceBuilders();

		AppLicenseKey appLicenseKey = _appLicenseKeyResource.getAppLicenseKey(
			GetterUtil.getLong(id));

		HttpHeaders headers = new HttpHeaders();

		headers.setContentDispositionFormData(
			"attachment",
			StringBundler.concat(
				"activation-key-", appLicenseKey.getProductName(),
				StringPool.DASH, appLicenseKey.getProductVersion(),
				StringPool.DASH, appLicenseKey.getHostName(), ".xml"
			).replaceAll(
				StringPool.SPACE, StringPool.DASH
			).toLowerCase());
		headers.setContentType(MediaType.TEXT_XML);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		HttpInvoker.HttpResponse licenseKeyHttpResponse =
			_appLicenseKeyResource.getAppLicenseKeyDownloadHttpResponse(
				appLicenseKey.getId());

		return new ResponseEntity(
			licenseKeyHttpResponse.getBinaryContent(), headers, HttpStatus.OK);
	}

	@GetMapping("license-keys/{id}")
	public AppLicenseKey getLicenseKey(@PathVariable("id") String id)
		throws Exception {

		_initResourceBuilders();

		return _appLicenseKeyResource.getAppLicenseKey(Long.valueOf(id));
	}

	@GetMapping("order-license-keys/{orderId}")
	public Page<AppLicenseKey> getOrderLicenseKeys(
			@PathVariable("orderId") String orderId,
			@RequestParam(defaultValue = "1", required = false) String page,
			@RequestParam(defaultValue = "20", required = false) String
				pageSize)
		throws Exception {

		_initResourceBuilders();

		return _appLicenseKeyResource.getAppLicenseKeysPage(
			"", "orderId eq '" + orderId + "'",
			Pagination.of(
				GetterUtil.getInteger(page), GetterUtil.getInteger(pageSize)),
			"");
	}

	private String _getOAuthAuthorization() throws Exception {
		if (Validator.isNotNull(_oauthAccessToken) &&
			(System.currentTimeMillis() < (_oauthExpirationMillis - 15000))) {

			return _oauthAccessToken;
		}

		HttpPost httpPost = new HttpPost(
			new URL(_liferayMarketplaceProvisioningAuthURL) +
				"/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair(
						"client_id",
						_liferayMarketplaceProvisioningAuthClientId),
					new BasicNameValuePair(
						"client_secret",
						_liferayMarketplaceProvisioningAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));

		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() ==
					org.apache.http.HttpStatus.SC_OK) {

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

			throw new Exception("Unable to get OAuth authorization");
		}
	}

	private String _getVersion(JSONObject jsonObject, Jwt jwt) {
		String version = "1.0.0";

		try {
			URL liferayURL = new URL(
				lxcDXPServerProtocol + "://" + lxcDXPMainDomain);

			SkuResource skuResource = SkuResource.builder(
			).header(
				HttpHeaders.AUTHORIZATION, jwt.getTokenValue()
			).endpoint(
				liferayURL.getHost(), liferayURL.getPort(),
				liferayURL.getProtocol()
			).build();

			Sku sku = skuResource.getSku(jsonObject.getLong("skuId"));

			for (CustomField customField : sku.getCustomFields()) {
				if (StringUtil.equals(customField.getName(), "Version")) {
					version = customField.getCustomValue(
					).getData(
					).toString();

					break;
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to set SKU Version" + exception.getMessage());
		}

		return version;
	}

	private void _initResourceBuilders() throws Exception {
		URL liferayMarketplaceKoroneikiAuthURL = new URL(
			_liferayMarketplaceKoroneikiAuthURL);

		URL liferayMarketplaceProvisioningAuthURL = new URL(
			_liferayMarketplaceProvisioningAuthURL);

		_appLicenseKeyResource = AppLicenseKeyResource.builder(
		).header(
			"Authorization", _getOAuthAuthorization()
		).endpoint(
			liferayMarketplaceProvisioningAuthURL.getHost(),
			liferayMarketplaceProvisioningAuthURL.getPort(),
			liferayMarketplaceProvisioningAuthURL.getProtocol()
		).build();

		_productPurchaseResource = ProductPurchaseResource.builder(
		).header(
			"API_TOKEN", _liferayMarketplaceKoroneikiAuthToken
		).endpoint(
			liferayMarketplaceKoroneikiAuthURL.getHost(),
			liferayMarketplaceKoroneikiAuthURL.getPort(),
			liferayMarketplaceKoroneikiAuthURL.getProtocol()
		).build();
	}

	private static final Log _log = LogFactory.getLog(
		ProvisioningRestController.class);

	private AppLicenseKeyResource _appLicenseKeyResource;

	@Value("${liferay.marketplace.koroneiki.auth.token}")
	private String _liferayMarketplaceKoroneikiAuthToken;

	@Value("${liferay.marketplace.koroneiki.auth.url}")
	private String _liferayMarketplaceKoroneikiAuthURL;

	@Value("${liferay.marketplace.provisioning.auth.client.id}")
	private String _liferayMarketplaceProvisioningAuthClientId;

	@Value("${liferay.marketplace.provisioning.auth.client.secret}")
	private String _liferayMarketplaceProvisioningAuthClientSecret;

	@Value("${liferay.marketplace.provisioning.auth.url}")
	private String _liferayMarketplaceProvisioningAuthURL;

	private String _oauthAccessToken;
	private long _oauthExpirationMillis;
	private ProductPurchaseResource _productPurchaseResource;

}