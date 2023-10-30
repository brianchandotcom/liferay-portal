/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.provisioning.marketplace.rest.client.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.client.http.HttpInvoker;
import com.liferay.osb.provisioning.marketplace.rest.client.resource.v1_0.AppLicenseKeyResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.nio.charset.Charset;

import java.time.ZonedDateTime;

import java.util.Arrays;
import java.util.Date;

import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Keven Leone
 */
@RequestMapping("/provisioning")
@RestController
public class ProvisioningRestController extends BaseRestController {

	@PostMapping("license-keys")
	public ResponseEntity<String> createLicenseKey(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		_initResource();

		String description;
		String hostname;
		String ipAddress;
		String macAddress;
		String orderId;
		String productPurchaseKey;
		String type;
		int skuId;

		JSONObject jsonObject = new JSONObject(json);

		try {
			description = jsonObject.getString("description");
			hostname = jsonObject.getString("hostname");
			ipAddress = jsonObject.getString("ipAddress");
			macAddress = jsonObject.getString("macAddress");
			orderId = jsonObject.getString("orderId");
			skuId = jsonObject.getInt("skuId");
			productPurchaseKey = jsonObject.getString("productPurchaseKey");
			type = jsonObject.getString("type");
		}
		catch (Exception exception) {
			return new ResponseEntity<>(
				new JSONObject(
				).put(
					"stack", exception.getMessage()
				).put(
					"message",
					StringBundler.concat(
						"One of the fields: description|hostname|ipAddress",
						"macAddress|orderId|skuId|productPurchaseKey|type",
						"is missing.")
				).toString(),
				HttpStatus.BAD_REQUEST);
		}

		ProductPurchase productPurchase =
			_productPurchaseResource.getProductPurchase(productPurchaseKey);

		String version = "1.0.0";

		try {
			JSONObject skuJSONObject = getSkuJSONObject(jwt, skuId);

			JSONArray customFieldsJSONArray = skuJSONObject.getJSONArray(
				"customFields");

			for (int i = 0; i < customFieldsJSONArray.length(); i++) {
				JSONObject customFieldJSONObject =
					customFieldsJSONArray.getJSONObject(i);

				String name = customFieldJSONObject.getString("name");

				if (name.equals("Version")) {
					version = customFieldJSONObject.getJSONObject(
						"customValue"
					).getString(
						"data"
					);

					break;
				}
			}
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to set SKU Version" + exception.getMessage());
		}

		AppLicenseKey.LicenseType licenseType = null;

		if (type.equals("standard") || type.equals("trial")) {
			licenseType = AppLicenseKey.LicenseType.PRODUCTION;
		}
		else if (type.equals("developer")) {
			licenseType = AppLicenseKey.LicenseType.DEVELOPER;
		}

		AppLicenseKey appLicenseKey = new AppLicenseKey();
		String userName = jwt.getClaim("username");
		String userUUID = jwt.getClaim("sub");
		Date productPurchaseStartDate = productPurchase.getStartDate();
		Date productPurchaseEndDate = productPurchase.getEndDate();

		if (productPurchaseStartDate == null) {
			productPurchaseStartDate = new Date();
		}

		if (productPurchaseEndDate == null) {
			productPurchaseEndDate = new Date();
		}

		if (productPurchase.getPerpetual()) {
			productPurchaseEndDate = Date.from(
				ZonedDateTime.now(
				).plusYears(
					100
				).toInstant());
		}

		appLicenseKey.setActive(true);
		appLicenseKey.setCreateDate(new Date());
		appLicenseKey.setDescription(description);
		appLicenseKey.setExpirationDate(productPurchaseEndDate);
		appLicenseKey.setHostName(hostname);
		appLicenseKey.setIpAddresses(ipAddress);
		appLicenseKey.setLicenseType(licenseType);
		appLicenseKey.setMacAddresses(macAddress);
		appLicenseKey.setOrderId(orderId);
		appLicenseKey.setOwner(userName);
		appLicenseKey.setProductId(productPurchase.getProductKey());
		appLicenseKey.setProductName(
			productPurchase.getProduct(
			).getName());
		appLicenseKey.setProductVersion(version);
		appLicenseKey.setStartDate(productPurchaseStartDate);
		appLicenseKey.setUserName(userName);
		appLicenseKey.setUserUuid(userUUID);

		appLicenseKey = _appLicenseKeyResource.postAppLicenseKey(
			userName, userUUID, appLicenseKey);

		return new ResponseEntity<>(
			new JSONObject(
				appLicenseKey
			).toString(),
			HttpStatus.OK);
	}

	@GetMapping("license-keys/{id}")
	public AppLicenseKey getLicenseKey(@PathVariable("id") String id)
		throws Exception {

		_initResource();

		return _appLicenseKeyResource.getAppLicenseKey(Long.valueOf(id));
	}

	public JSONObject getSkuJSONObject(Jwt jwt, int skuId) {
		WebClient webClient = _getWebClient(jwt);

		String response = webClient.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"o/headless-commerce-admin-catalog/v1.0/skus/" + skuId
			).build()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		return new JSONObject(response);
	}
	private String _getOAuthAuthorization() throws Exception {
		if (Validator.isNotNull(_oauthAccessToken) &&
			(_oauthExpirationMillis < System.currentTimeMillis())) {

			return _oauthAccessToken;
		}

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
					jsonObject.getLong("expires_in") +
						System.currentTimeMillis();

				_oauthAccessToken =
					jsonObject.getString("token_type") + " " +
						jsonObject.getString("access_token");

				return _oauthAccessToken;
			}

			throw new Exception("Unable to get OAuth authorization");
		}
	}
	private WebClient _getWebClient(Jwt jwt) {
		WebClient.Builder builder = WebClient.builder();

		return builder.baseUrl(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
	}

	private AppLicenseKeyResource _appLicenseKeyResource;

	@Value("${com.liferay.lxc.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${com.liferay.lxc.koroneiki.auth.url}")
	private String _koroneikiAuthURL;

	private String _oauthAccessToken;
	private long _oauthExpirationMillis;
	private ProductPurchaseResource _productPurchaseResource;

	@Value("${com.liferay.lxc.provisioning.auth.client.id}")
	private String _provisioningAuthClientId;

	@Value("${com.liferay.lxc.provisioning.auth.client.secret}")
	private String _provisioningAuthClientSecret;

	@Value("${com.liferay.lxc.provisioning.auth.url}")
	private String _provisioningAuthURL;

}