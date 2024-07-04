/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ups;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.ups.constants.UPSServiceCodeConstants;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Alessio Antonio Rendina
 */
@RequestMapping("/options")
@RestController
public class OptionsRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		return post(jwt, json, _log);
	}

	protected ResponseEntity<String> post(Jwt jwt, String json, Log log)
		throws Exception {

		log(jwt, log, json);

		JSONArray responseJSONArray = new JSONArray();

		JSONObject jsonObject = new JSONObject(json);

		JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
			"typeSettings");

		List<String> ratingCodes = Arrays.asList(
			StringUtil.split(typeSettingsJSONObject.getString("ratingCodes")));

		double depth = 0;
		double height = 0;
		int quantity = 0;
		double weight = 0;
		double width = 0;

		JSONObject orderJSONObject = jsonObject.getJSONObject("order");

		JSONArray orderItemsJSONArray = orderJSONObject.getJSONArray(
			"orderItems");

		for (int i = 0; i < orderItemsJSONArray.length(); i++) {
			JSONObject orderItemJSONObject = orderItemsJSONArray.getJSONObject(
				i);

			quantity += orderItemJSONObject.getInt("quantity");

			JSONObject skuJSONObject = _get(
				"Bearer " + jwt.getTokenValue(),
				"/o/headless-commerce-admin-catalog/v1.0/skus/" +
					orderItemJSONObject.getString("skuId"));

			depth += skuJSONObject.getDouble("depth");
			height += skuJSONObject.getDouble("height");
			weight += skuJSONObject.getDouble("weight");
			width += skuJSONObject.getDouble("width");
		}

		JSONObject shippingAddressJSONObject = orderJSONObject.getJSONObject(
			"shippingAddress");

		for (String code : ratingCodes) {
			try {
				responseJSONArray.put(
					new JSONObject(
						_postRateRequest(
							_getBody(
								code, depth, height, quantity,
								shippingAddressJSONObject,
								typeSettingsJSONObject, weight, width),
							typeSettingsJSONObject.getString("clientId"),
							typeSettingsJSONObject.getString("clientSecret"),
							log)));
			}
			catch (Exception exception) {
				if (log.isDebugEnabled()) {
					log.debug(exception);
				}
			}
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"shippingOptions",
				_toShippingOptionsJSONArray(responseJSONArray)
			).toString(),
			HttpStatus.OK);
	}

	private JSONObject _get(String authorization, String path) {
		Mono<String> response = _getWebClient(
		).get(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).retrieve(
		).bodyToMono(
			String.class
		);

		response.subscribe();

		return new JSONObject(Objects.requireNonNull(response.block()));
	}

	private String _getAccessToken(
		String clientId, String clientSecret, Log log) {

		try {
			Base64.Encoder encoder = Base64.getEncoder();

			String credentials = clientId + ":" + clientSecret;

			WebClient webClient = WebClient.builder(
			).baseUrl(
				"https://wwwcie.ups.com"
			).defaultHeader(
				HttpHeaders.AUTHORIZATION,
				"Basic " + encoder.encodeToString(credentials.getBytes())
			).defaultHeader(
				HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE
			).build();

			JSONObject jsonObject = new JSONObject(
				webClient.post(
				).uri(
					"/security/v1/oauth/token"
				).body(
					BodyInserters.fromFormData(
						"grant_type", "client_credentials")
				).retrieve(
				).bodyToFlux(
					String.class
				).blockLast());

			return jsonObject.getString("access_token");
		}
		catch (Exception exception) {
			if (log.isDebugEnabled()) {
				log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	private String _getBody(
		String code, double depth, double height, int quantity,
		JSONObject shippingAddressJSONObject, JSONObject typeSettingsJSONObject,
		double weight, double width) {

		return new JSONObject(
		).put(
			"RateRequest",
			new JSONObject(
			).put(
				"Shipment",
				new JSONObject(
				).put(
					"NumOfPieces", String.valueOf(quantity)
				).put(
					"Package",
					new JSONObject(
					).put(
						"Dimensions",
						new JSONObject(
						).put(
							"Height", String.valueOf(height)
						).put(
							"Length", String.valueOf(depth)
						).put(
							"UnitOfMeasurement",
							new JSONObject(
							).put(
								"Code",
								typeSettingsJSONObject.getString(
									"dimensionsUnitOfMeasurementCode")
							)
						).put(
							"Width", String.valueOf(width)
						)
					).put(
						"PackagingType",
						new JSONObject(
						).put(
							"Code",
							typeSettingsJSONObject.getString(
								"packagingTypeCode")
						)
					).put(
						"PackageWeight",
						new JSONObject(
						).put(
							"UnitOfMeasurement",
							new JSONObject(
							).put(
								"Code",
								typeSettingsJSONObject.getString(
									"packageWeightUnitOfMeasurementCode")
							)
						).put(
							"Weight", String.valueOf(weight)
						)
					).put(
						"SimpleRate",
						new JSONObject(
						).put(
							"Code",
							typeSettingsJSONObject.getString("simpleRateCode")
						)
					)
				).put(
					"Service",
					new JSONObject(
					).put(
						"Code", code
					)
				).put(
					"Shipper",
					new JSONObject(
					).put(
						"Address",
						new JSONObject(
						).put(
							"AddressLine",
							new JSONArray(
							).put(
								typeSettingsJSONObject.getString(
									"shipperAddressLine1")
							).put(
								typeSettingsJSONObject.getString(
									"shipperAddressLine2")
							).put(
								typeSettingsJSONObject.getString(
									"shipperAddressLine3")
							)
						).put(
							"CountryCode",
							typeSettingsJSONObject.getString(
								"shipperCountryCode")
						).put(
							"PostalCode",
							typeSettingsJSONObject.getString(
								"shipperPostalCode")
						)
					)
				).put(
					"ShipTo",
					new JSONObject(
					).put(
						"Address",
						new JSONObject(
						).put(
							"AddressLine",
							new JSONArray(
							).put(
								shippingAddressJSONObject.getString("street1")
							).put(
								shippingAddressJSONObject.getString("street2")
							).put(
								shippingAddressJSONObject.getString("street3")
							)
						).put(
							"City", shippingAddressJSONObject.getString("city")
						).put(
							"CountryCode",
							shippingAddressJSONObject.getString(
								"countryISOCode")
						).put(
							"PostalCode",
							shippingAddressJSONObject.getString("zip")
						).put(
							"StateProvinceCode",
							shippingAddressJSONObject.getString("regionISOCode")
						)
					)
				)
			)
		).toString();
	}

	private WebClient _getWebClient() {
		return WebClient.builder(
		).baseUrl(
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
	}

	private String _postRateRequest(
		String body, String clientId, String clientSecret, Log log) {

		try {
			WebClient client = WebClient.builder(
			).baseUrl(
				"https://wwwcie.ups.com"
			).defaultHeader(
				HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
			).defaultHeader(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).defaultHeader(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + _getAccessToken(clientId, clientSecret, log)
			).build();

			return client.post(
			).uri(
				"/api/rating/v2403/Rate"
			).bodyValue(
				body
			).retrieve(
			).bodyToFlux(
				String.class
			).blockLast();
		}
		catch (Exception exception) {
			if (log.isDebugEnabled()) {
				log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	private JSONArray _toShippingOptionsJSONArray(JSONArray responseJSONArray) {
		JSONArray shippingOptionsJSONArray = new JSONArray();

		for (int i = 0; i < responseJSONArray.length(); i++) {
			JSONObject jsonObject = responseJSONArray.getJSONObject(i);

			JSONObject rateResponseJSONObject = jsonObject.getJSONObject(
				"RateResponse");

			JSONObject responseJSONObject =
				rateResponseJSONObject.getJSONObject("Response");

			JSONObject responseStatusJSONObject =
				responseJSONObject.getJSONObject("ResponseStatus");

			if (!StringUtil.equalsIgnoreCase(
					responseStatusJSONObject.getString("Code"), "1")) {

				continue;
			}

			JSONObject ratedShipmentJSONObject =
				rateResponseJSONObject.getJSONObject("RatedShipment");

			JSONObject serviceJSONObject =
				ratedShipmentJSONObject.getJSONObject("Service");
			JSONObject totalChargesJSONObject =
				ratedShipmentJSONObject.getJSONObject("TotalCharges");

			String code = serviceJSONObject.getString("Code");

			shippingOptionsJSONArray.put(
				new JSONObject(
				).put(
					"amount", totalChargesJSONObject.getString("MonetaryValue")
				).put(
					"currencyCode",
					totalChargesJSONObject.getString("CurrencyCode")
				).put(
					"key", code
				).put(
					"name", UPSServiceCodeConstants.getCodeName(code)
				).put(
					"priority", i
				));
		}

		return shippingOptionsJSONArray;
	}

	private static final Log _log = LogFactory.getLog(
		OptionsRestController.class);

}