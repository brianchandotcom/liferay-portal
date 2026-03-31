/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.JSONObject;

/**
 * @author Felipe Veloso
 */
public abstract class BaseUsageStrategy implements UsageStrategy {

	protected JSONObject createUsageJSONObject(
		BigDecimal usedCount, long maxCount) {

		BigDecimal percentage = _getPercentage(
			usedCount, new BigDecimal(maxCount));

		JSONObject jsonObject = new JSONObject();

		return jsonObject.put(
			"maxCount", maxCount
		).put(
			"percentage", percentage.toPlainString()
		).put(
			"usedCount", usedCount
		);
	}

	protected JSONObject createUsageJSONObject(
		BigDecimal gbUsedCount, long maxCount, String maxCountUnit) {

		BigDecimal usedCount = gbUsedCount;
		String usedCountUnit = UNIT_GIB;

		if (gbUsedCount.compareTo(new BigDecimal("1024")) >= 0) {
			usedCount = gbUsedCount.divide(
				new BigDecimal(1024), 2, RoundingMode.HALF_UP);
			usedCountUnit = UNIT_TIB;
		}

		BigDecimal percentage = _getPercentage(
			usedCount, usedCountUnit, maxCount, maxCountUnit);

		JSONObject jsonObject = new JSONObject();

		return jsonObject.put(
			"maxCount", maxCount
		).put(
			"maxCountUnits", maxCountUnit
		).put(
			"percentage", percentage.toPlainString()
		).put(
			"usedCount",
			usedCount.setScale(
				2, RoundingMode.DOWN
			).floatValue()
		).put(
			"usedCountUnits", usedCountUnit
		);
	}

	protected JSONObject createUsageJSONObject(long usedCount, long maxCount) {
		return createUsageJSONObject(new BigDecimal(usedCount), maxCount);
	}

	private BigDecimal _getPercentage(
		BigDecimal usedCount, BigDecimal maxCount) {

		if (maxCount.signum() <= 0) {
			return BigDecimal.ZERO;
		}

		return usedCount.multiply(
			new BigDecimal("100")
		).divide(
			maxCount, 2, RoundingMode.HALF_UP
		).setScale(
			4, RoundingMode.DOWN
		);
	}

	private BigDecimal _getPercentage(
		BigDecimal usedCount, String usedCountUnit, long maxCount,
		String maxCountUnit) {

		if (maxCount <= 0) {
			return BigDecimal.ZERO;
		}

		if (maxCountUnit.equals(UNIT_TIB) && usedCountUnit.equals(UNIT_GIB)) {
			return _getPercentage(
				usedCount.divide(
					new BigDecimal("1024"), 10, RoundingMode.HALF_UP),
				new BigDecimal(maxCount));
		}

		return _getPercentage(usedCount, new BigDecimal(maxCount));
	}

}