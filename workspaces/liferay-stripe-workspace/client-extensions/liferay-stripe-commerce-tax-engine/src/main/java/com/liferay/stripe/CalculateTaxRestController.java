/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stripe;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import com.stripe.Stripe;
import com.stripe.model.tax.Calculation;
import com.stripe.param.tax.CalculationCreateParams;

import java.math.BigDecimal;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivica Cardic
 */
@RequestMapping("/calculate-tax")
@RestController
public class CalculateTaxRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		String errorMessages = null;
		BigDecimal taxValue = _taxValues.getIfPresent(json.hashCode());

		if (taxValue != null) {
			return new ResponseEntity<>(
				new JSONObject(
				).put(
					"amount", taxValue
				).put(
					"errorMessages", errorMessages
				).toString(),
				HttpStatus.OK);
		}

		try {
			JSONObject jsonObject = new JSONObject(json);

			JSONObject typeSettingsJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			Stripe.apiKey = typeSettingsJSONObject.getString("apiKey");

			JSONObject commerceTaxCalculateRequestJSONObject =
				jsonObject.getJSONObject("commerceTaxCalculateRequest");

			Calculation calculation = _createCalculation(
				commerceTaxCalculateRequestJSONObject);

			taxValue = BigDecimal.valueOf(calculation.getTaxAmountExclusive());

			_taxValues.put(json.hashCode(), taxValue);
		}
		catch (Exception exception) {
			errorMessages = ExceptionUtils.getStackTrace(exception);

			_log.error(errorMessages);
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"amount", taxValue
			).put(
				"errorMessages", errorMessages
			).toString(),
			HttpStatus.OK);
	}

	private Calculation _createCalculation(
			JSONObject commerceTaxCalculateRequestJSONObject)
		throws Exception {

		CalculationCreateParams params = CalculationCreateParams.builder(
		).addLineItem(
			_getLineItem(commerceTaxCalculateRequestJSONObject)
		).setCurrency(
			commerceTaxCalculateRequestJSONObject.getString("currencyCode")
		).setCustomerDetails(
			CalculationCreateParams.CustomerDetails.builder(
			).setAddress(
				_getCustomerDetailsAddress(
					commerceTaxCalculateRequestJSONObject.getJSONObject(
						"shippingAddress"))
			).setAddressSource(
				CalculationCreateParams.CustomerDetails.AddressSource.SHIPPING
			).build()
		).build();

		return Calculation.create(params);
	}

	private CalculationCreateParams.CustomerDetails.Address
		_getCustomerDetailsAddress(JSONObject shippingAddressJSONObject) {

		return CalculationCreateParams.CustomerDetails.Address.builder(
		).setCity(
			shippingAddressJSONObject.getString("city")
		).setCountry(
			shippingAddressJSONObject.getString("countryISOCode")
		).setLine1(
			shippingAddressJSONObject.getString("street1")
		).setPostalCode(
			shippingAddressJSONObject.getString("zip")
		).setState(
			shippingAddressJSONObject.getString("regionISOCode")
		).build();
	}

	private CalculationCreateParams.LineItem _getLineItem(
		JSONObject commerceTaxCalculateRequestJSONObject) {

		return CalculationCreateParams.LineItem.builder(
		).setAmount(
			commerceTaxCalculateRequestJSONObject.getLong("price")
		).setReference(
			"L1"
		).setTaxBehavior(
			CalculationCreateParams.LineItem.TaxBehavior.EXCLUSIVE
		).setTaxCode(
			_getTaxCode(commerceTaxCalculateRequestJSONObject)
		).build();
	}

	private String _getTaxCode(
		JSONObject commerceTaxCalculateRequestJSONObject) {

		if (commerceTaxCalculateRequestJSONObject.has("taxCode")) {
			return commerceTaxCalculateRequestJSONObject.getString("taxCode");
		}

		return null;
	}

	private static final Log _log = LogFactory.getLog(
		CalculateTaxRestController.class);

	private static final Cache<Integer, BigDecimal> _taxValues =
		Caffeine.newBuilder(
		).expireAfterAccess(
			3, TimeUnit.DAYS
		).maximumSize(
			5000000
		).build();

}