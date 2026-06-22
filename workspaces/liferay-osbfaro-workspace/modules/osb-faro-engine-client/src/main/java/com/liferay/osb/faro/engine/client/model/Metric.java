/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Nilton Vieira
 */
public class Metric {

	public String getMetricType() {
		return _metricType;
	}

	public Double getPreviousValue() {
		return _previousValue;
	}

	public String getPreviousValueKey() {
		return _previousValueKey;
	}

	public Trend getTrend() {
		return _trend;
	}

	public Double getValue() {
		return _value;
	}

	public String getValueKey() {
		return _valueKey;
	}

	public void setMetricType(String metricType) {
		_metricType = metricType;
	}

	public void setPreviousValue(Double previousValue) {
		_previousValue = previousValue;
	}

	public void setPreviousValueKey(String previousValueKey) {
		_previousValueKey = previousValueKey;
	}

	public void setTrend(Trend trend) {
		_trend = trend;
	}

	public void setValue(Double value) {
		_value = value;
	}

	public void setValueKey(String valueKey) {
		_valueKey = valueKey;
	}

	private String _metricType;
	private Double _previousValue;
	private String _previousValueKey;
	private Trend _trend;
	private Double _value = 0D;
	private String _valueKey;

}