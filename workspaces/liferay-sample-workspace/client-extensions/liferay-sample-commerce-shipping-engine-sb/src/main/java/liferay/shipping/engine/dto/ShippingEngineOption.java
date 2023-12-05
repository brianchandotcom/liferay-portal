/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package liferay.shipping.engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import java.util.StringJoiner;

/**
 * @author Luca Pellizzon
 */
public class ShippingEngineOption {

	public ShippingEngineOption(
		BigDecimal amount, String commerceShippingMethodKey, String key,
		String name, double priority) {

		_amount = amount;
		_key = key;
		_name = name;
		_priority = priority;

		_shippingMethodKey = commerceShippingMethodKey;
	}

	@JsonProperty("amount")
	public BigDecimal getAmount() {
		return _amount;
	}

	@JsonProperty("key")
	public String getKey() {
		return _key;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	@JsonProperty("priority")
	public double getPriority() {
		return _priority;
	}

	@JsonProperty("shippingMethodKey")
	public String getShippingMethodKey() {
		return _shippingMethodKey;
	}

	@Override
	public String toString() {
		return new StringJoiner(
			", ", "{", "}"
		).add(
			"amount=" + _amount
		).add(
			"shippingMethodKey=" + _shippingMethodKey
		).add(
			"key=" + _key
		).add(
			"name=" + _name
		).add(
			"priority=" + _priority
		).toString();
	}

	@JsonProperty("amount")
	private final BigDecimal _amount;

	@JsonProperty("key")
	private final String _key;

	@JsonProperty("name")
	private final String _name;

	@JsonProperty("priority")
	private final double _priority;

	@JsonProperty("shippingMethodKey")
	private final String _shippingMethodKey;

}