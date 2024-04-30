/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.provisioning.marketplace.rest.client.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.client.http.HttpInvoker;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Page;
import com.liferay.osb.provisioning.marketplace.rest.client.pagination.Pagination;
import com.liferay.osb.provisioning.marketplace.rest.client.resource.v1_0.AppLicenseKeyResource;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.nio.charset.Charset;

import java.time.ZonedDateTime;

import java.util.Arrays;
import java.util.Collections;
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

	@PostMapping("license-keys/{id}/deactivate")
	public void deactivateLicenseKeys(
			@AuthenticationPrincipal Jwt jwt, @PathVariable("id") long id)
		throws Exception {

		_initResourceBuilders();

		_appLicenseKeyResource.putAppLicenseKeyDeactivate(
			jwt.getClaim("username"), jwt.getClaim("sub"), new Long[] {id});

		if (_log.isInfoEnabled()) {
			_log.info("License key " + id + " deactivated");
		}
	}

	@GetMapping("license-keys/{id}")
	public AppLicenseKey getLicenseKeys(@PathVariable("id") String id)
		throws Exception {

		_initResourceBuilders();

		return _appLicenseKeyResource.getAppLicenseKey(Long.valueOf(id));
	}

	@GetMapping("license-keys/{id}/download")
	public ResponseEntity getLicenseKeysDownload(@PathVariable("id") long id)
		throws Exception {

		_initResourceBuilders();

		AppLicenseKey appLicenseKey = _appLicenseKeyResource.getAppLicenseKey(
			id);

		HttpInvoker.HttpResponse httpResponse =
			_appLicenseKeyResource.getAppLicenseKeyDownloadHttpResponse(
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

		_initResourceBuilders();

		return _appLicenseKeyResource.getAppLicenseKeysPage(
			"", "active eq true and orderId eq '" + orderId + "'",
			Pagination.of(page, pageSize), "");
	}

	@PostMapping("license-keys")
	public AppLicenseKey postLicenseKeys(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		_initResourceBuilders();

		JSONObject jsonObject = new JSONObject(json);

		AppLicenseKey appLicenseKey = AppLicenseKey.toDTO(
			jsonObject.getJSONObject(
				"licenseEntry"
			).toString());

		appLicenseKey.setActive(true);
		appLicenseKey.setCreateDate(new Date());

		ProductPurchase productPurchase =
			_productPurchaseResource.getProductPurchase(
				appLicenseKey.getProductPurchaseKey());

		Date expirationDate = productPurchase.getEndDate();

		if (productPurchase.getPerpetual()) {
			expirationDate = Date.from(
				ZonedDateTime.now(
				).plusYears(
					100
				).toInstant());
		}

		appLicenseKey.setExpirationDate(expirationDate);

		AppLicenseKey.LicenseType licenseType =
			AppLicenseKey.LicenseType.PRODUCTION;

		appLicenseKey.setLicenseType(licenseType);

		appLicenseKey.setOwner((String)jwt.getClaim("username"));

		if (appLicenseKey.getProductId() == null) {
			appLicenseKey.setProductId(productPurchase.getProductKey());
		}

		appLicenseKey.setProductName(
			productPurchase.getProduct(
			).getName());
		appLicenseKey.setProductVersion("1");

		Date startDate = productPurchase.getStartDate();

		if (startDate == null) {
			startDate = new Date();
		}

		appLicenseKey.setStartDate(startDate);
		appLicenseKey.setUserName((String)jwt.getClaim("username"));
		appLicenseKey.setUserUuid((String)jwt.getClaim("sub"));

		appLicenseKey = _appLicenseKeyResource.postAppLicenseKey(
			jwt.getClaim("username"), jwt.getClaim("sub"), appLicenseKey);

		if (_log.isInfoEnabled()) {
			_log.info("Created app license key " + appLicenseKey);
		}

		return appLicenseKey;
	}

	private String _getOAuthAuthorization() throws Exception {
		if ((_oauthAccessToken != null) &&
			(System.currentTimeMillis() < (_oauthExpirationMillis - 15000))) {

			return _oauthAccessToken;
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpPost httpPost = new HttpPost(
			new URL(_provisioningAuthURL) + "/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair(
						"client_id", _provisioningAuthClientId),
					new BasicNameValuePair(
						"client_secret", _provisioningAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build();
			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() !=
					org.apache.http.HttpStatus.SC_OK) {

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

	private void _initResourceBuilders() throws Exception {
		URL liferayMarketplaceKoroneikiAuthURL = new URL(_koroneikiAuthURL);

		URL liferayMarketplaceProvisioningAuthURL = new URL(
			_provisioningAuthURL);

		_appLicenseKeyResource = AppLicenseKeyResource.builder(
		).header(
			"Authorization", _getOAuthAuthorization()
		).endpoint(
			liferayMarketplaceProvisioningAuthURL
		).build();

		_productPurchaseResource = ProductPurchaseResource.builder(
		).header(
			"API_TOKEN", _koroneikiAuthToken
		).endpoint(
			liferayMarketplaceKoroneikiAuthURL
		).build();
	}

	private static final Log _log = LogFactory.getLog(
		ProvisioningRestController.class);

	private AppLicenseKeyResource _appLicenseKeyResource;

	@Value("${liferay.marketplace.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${liferay.marketplace.koroneiki.auth.url}")
	private String _koroneikiAuthURL;

	private String _oauthAccessToken;
	private long _oauthExpirationMillis;
	private ProductPurchaseResource _productPurchaseResource;

	@Value("${liferay.marketplace.provisioning.auth.client.id}")
	private String _provisioningAuthClientId;

	@Value("${liferay.marketplace.provisioning.auth.client.secret}")
	private String _provisioningAuthClientSecret;

	@Value("${liferay.marketplace.provisioning.auth.url}")
	private String _provisioningAuthURL;

}