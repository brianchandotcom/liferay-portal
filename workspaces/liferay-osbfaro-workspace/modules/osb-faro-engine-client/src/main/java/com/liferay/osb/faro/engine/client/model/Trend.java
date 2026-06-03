/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.math.BigDecimal;

/**
 * @author Nilton Vieira
 */
public class Trend {

	public BigDecimal getPercentage() {
		return _percentage;
	}

	public String getTrendClassification() {
		return _trendClassification;
	}

	public void setPercentage(BigDecimal percentage) {
		_percentage = percentage;
	}

	public void setTrendClassification(String trendClassification) {
		_trendClassification = trendClassification;
	}

	private BigDecimal _percentage;
	private String _trendClassification;

}